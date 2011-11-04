package com.example.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.base.AbstractComponentEventLink;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuth1ServiceProvider;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This component represents link that connects users to their twitter accounts using OAuth protocol. After clicking
 * this link users will be redirected to twitter authorization page and then returned back to the page where they come
 * from. After twitter authorization this component triggers {@link EventConstants#SUCCESS} or
 * {@link EventConstants#FAILURE} events according to authorization result. Success event comes with access token
 * (token + secret) as event context, so developers can then use it to access twitter api.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
@Events({EventConstants.SUCCESS, EventConstants.FAILURE})
public class TwitterConnect extends AbstractComponentEventLink {
	private static final String CONNECT_EVENT = "connect";
	private static final String CONNECTED_EVENT = "connected";

	/**
	 * This parameter defines OAuth application scope.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String scope;

	@Inject
	private ComponentResources resources;

	/**
	 * Injected spring-social OAuth service for twitter. It should be configured somewhere in application modules.
	 */
	@InjectService("twitterService")
	private OAuth1ServiceProvider<Twitter> twitterService;

	/**
	 * Generates OAuth callbackUrl as event link to current component with internal {@code 'connected'} event.
	 * This event will be processed inside this component later.
	 *
	 * @return OAuth callbackUrl
	 */
	@Cached
	protected String getCallbackUrl() {
		return resources.createEventLink(CONNECTED_EVENT).toAbsoluteURI();
	}

	/**
	 * Generates component link that will present on html markup as a connect URL. This link will produce internal
	 * {@code 'connect'} event. Then this event will be processed by this component to generate correct twitter
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
	 * Event handler for internal {@code 'connect'} event. Generates correct twitter authorization URL with all needed
	 * parameters.
	 *
	 * @return twitter authorization URL
	 * @throws MalformedURLException for incorrect URL produced by twitter service
	 */
	@OnEvent(CONNECT_EVENT)
	URL connect() throws MalformedURLException {
		// generate oauth parameters with callback url
		final OAuth1Parameters parameters = new OAuth1Parameters();
		parameters.setCallbackUrl(getCallbackUrl());
		// build and return connection url as redirect response
		return new URL(buildConnectUrl(parameters));
	}

	/**
	 * Event handler for internal {@code 'connected'} event. Processes reply came from twitter authorization page. If
	 * access was granted then it tries to get OAuth access token and triggers {@link EventConstants#SUCCESS} event with
	 * access token as event context. If access was denied or error occurs while getting access token then
	 * {@link EventConstants#FAILURE} event will be triggered.
	 *
	 * @param token	authorized OAuth token came from twitter
	 * @param verifier authorized OAuth token secret came from twitter
	 * @param denied   error came from twitter
	 * @return result came from container for triggered events or null if was not processed.
	 */
	@OnEvent(CONNECTED_EVENT)
	Object connected(
			@RequestParameter(value = "oauth_token", allowBlank = true) final String token,
			@RequestParameter(value = "oauth_verifier", allowBlank = true) final String verifier,
			@RequestParameter(value = "denied", allowBlank = true) final String denied) {

		final CaptureResultCallback<Object> callback = new CaptureResultCallback<Object>();
		// null verifier means tha access was not granted
		if (verifier != null) {
			try {
				// create authorized OAuth request token
				final AuthorizedRequestToken requestToken =
						new AuthorizedRequestToken(new OAuthToken(token, verifier), verifier);
				// request for access token from twitter
				final OAuthToken accessToken = twitterService.getOAuthOperations()
						.exchangeForAccessToken(requestToken, null);
				// create success even context
				final Object[] context = {accessToken.getValue(), accessToken.getSecret()};
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

		// handle failure event if access was denied or error occurs while requesting access token
		final boolean handled = resources.triggerEvent(EventConstants.FAILURE, new Object[]{}, callback);

		// if event was processed return result
		if (handled) {
			return callback.getResult();
		}
		// return null if not processed
		return null;
	}

	/**
	 * Generates OAuth authorization URL according to specified parameters.
	 *
	 * @param parameters OAuth parameters
	 * @return OAuth authorization URL
	 */
	private String buildConnectUrl(final OAuth1Parameters parameters) {
		final OAuthToken token = twitterService.getOAuthOperations().fetchRequestToken(getCallbackUrl(), null);
		return twitterService.getOAuthOperations().buildAuthorizeUrl(token.getValue(), parameters);
	}
}
