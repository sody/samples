package com.example.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.corelib.base.AbstractComponentEventLink;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This component represents link that connects users to their facebook accounts using OAuth2 protocol. After clicking
 * this link users will be redirected to facebook authorization page and then returned back to the page where they come
 * from. After facebook authorization this component triggers {@link EventConstants#SUCCESS} or
 * {@link EventConstants#FAILURE} events according to authorization result. Success event comes with access token
 * as event context, so developers can then use it to access facebook api.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FacebookConnect extends AbstractComponentEventLink {
	private static final String CONNECT_EVENT = "connect";
	private static final String CONNECTED_EVENT = "connected";

	/**
	 * This parameter defines OAuth2 application scope.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String scope;

	@Inject
	private ComponentResources resources;

	/**
	 * Injected spring-social OAuth2 service for facebook. It should be configured somewhere in application modules.
	 */
	@InjectService("facebookService")
	private OAuth2ServiceProvider<Facebook> facebookService;

	/**
	 * Generates OAuth2 redirectUri as event link to current component with internal {@code 'connected'} event.
	 * This event will be processed inside this component later.
	 *
	 * @return OAuth2 redirectUri
	 */
	@Cached
	protected String getRedirectUri() {
		return resources.createEventLink(CONNECTED_EVENT).toAbsoluteURI();
	}

	/**
	 * Generates component link that will present on html markup as a connect URL. This link will produce internal
	 * {@code 'connect'} event. Then this event will be processed by this component to generate correct facebook
	 * authorization URL.
	 *
	 * @param eventContext event context
	 * @return link that will present on html markup as a connect URL
	 */
	@Override
	protected Link createLink(final Object[] eventContext) {
		return resources.createEventLink(CONNECT_EVENT);
	}

	/**
	 * Event handler for internal {@code 'connect'} event. Generates correct facebook authorization URL with all needed
	 * parameters.
	 *
	 * @return facebook authorization URL
	 * @throws MalformedURLException for incorrect URL produced by facebook service
	 */
	@OnEvent(CONNECT_EVENT)
	URL connect() throws MalformedURLException {
		// generate oauth parameters with redirect uri
		final OAuth2Parameters parameters = new OAuth2Parameters();
		if (scope != null) {
			parameters.setScope(scope);
		}
		parameters.setRedirectUri(getRedirectUri());
		// build and return connection url as redirect response
		return new URL(buildConnectURL(parameters));
	}

	/**
	 * Event handler for internal {@code 'connected'} event. Processes reply came from facebook authorization page. If
	 * access was granted then it tries to get OAuth2 access token and triggers {@link EventConstants#SUCCESS} event with
	 * access token as event context. If access was denied or error occurs while getting access token then
	 * {@link EventConstants#FAILURE} event will be triggered.
	 *
	 * @param code			 authorized OAuth token came from facebook
	 * @param error			error came from facebook
	 * @param errorReason	  error reason came from facebook
	 * @param errorDescription error description came from facebook
	 * @return result came from container for triggered events or null if was not processed.
	 */
	@OnEvent(CONNECTED_EVENT)
	Object connected(
			@RequestParameter(value = "code", allowBlank = true) final String code,
			@RequestParameter(value = "error", allowBlank = true) final String error,
			@RequestParameter(value = "error_reason", allowBlank = true) final String errorReason,
			@RequestParameter(value = "error_description", allowBlank = true) final String errorDescription) {

		final CaptureResultCallback<Object> callback = new CaptureResultCallback<Object>();
		// null code means that access was not granted
		if (code != null) {
			final AccessGrant accessGrant;
			try {
				// request for access token from twitter
				accessGrant = facebookService.getOAuthOperations().exchangeForAccess(code, getRedirectUri(), null);
				// create success even context
				final Object[] context = {accessGrant.getAccessToken()};
				// trigger success event
				final boolean handled = resources.triggerEvent(EventConstants.SUCCESS, context, callback);
				// if event was processed return result
				if (handled) {
					return callback.getResult();
				}
				// return null if not processed
				return null;
			} catch (Exception e) {
				// error occurs, so failure event will be triggered
			}
		}

		final Object[] context = {errorDescription};
		// handle failure event if access was denied or error occurs while requesting access token
		final boolean handled = resources.triggerEvent(EventConstants.FAILURE, context, callback);

		// if event was processed return result
		if (handled) {
			return callback.getResult();
		}
		// return null if not processed
		return null;
	}

	/**
	 * Generates OAuth2 authorization URL according to specified parameters.
	 *
	 * @param parameters OAuth2 parameters
	 * @return OAuth2 authorization URL
	 */
	private String buildConnectURL(final OAuth2Parameters parameters) {
		return facebookService.getOAuthOperations().buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, parameters);
	}
}
