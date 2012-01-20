package com.example.internal.google;

import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * This class represents default API implementation for performing operations on Google user profiles.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class UserTemplate implements UserOperations {
	private static final LinkedMultiValueMap<String, String> EMPTY_PARAMETERS = new LinkedMultiValueMap<String, String>();

	private final RestTemplate template;
	private final boolean authorized;

	/**
	 * Creates new instance of API implementation for performing operations on Google user profiles with defined rest
	 * template and authorization flag.
	 *
	 * @param template   rest template, not {@code null}
	 * @param authorized authorization flag
	 */
	public UserTemplate(final RestTemplate template, final boolean authorized) {
		this.template = template;
		this.authorized = authorized;
	}

	/**
	 * {@inheritDoc}
	 */
	public GoogleProfile getUserProfile() {
		requireAuthorization();
		return template.getForObject(buildUri("userinfo"), GoogleProfile.class);
	}

	/**
	 * Checks if current template is authorized and throws exception if not.
	 *
	 * @throws MissingAuthorizationException if current template is not authorized
	 */
	protected void requireAuthorization() {
		if (!authorized) {
			throw new MissingAuthorizationException();
		}
	}

	/**
	 * Builds URI for specified path without request parameters. It will use Google APIs base url as a part of
	 * resulting URI.
	 *
	 * @param path path within Google APIs base url, not {@code null}
	 * @return URI for specified path without request parameters, not {@code null}
	 */
	protected URI buildUri(final String path) {
		return buildUri(path, EMPTY_PARAMETERS);
	}

	/**
	 * Builds URI for specified path with one defined request parameter. It will use Google APIs base url as a part of
	 * resulting URI.
	 *
	 * @param path		   path within Google APIs base url, not {@code null}
	 * @param parameterName  request parameter name, not {@code null}
	 * @param parameterValue request parameter value, can be {@code null}
	 * @return URI for specified path with one defined request parameter, not {@code null}
	 */
	protected URI buildUri(final String path, final String parameterName, final String parameterValue) {
		final MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set(parameterName, parameterValue);
		return buildUri(path, parameters);
	}

	/**
	 * Builds URI for specified path with defined request parameters. It will use Google APIs base url as a part of
	 * resulting URI.
	 *
	 * @param path	   path within Google APIs base url, not {@code null}
	 * @param parameters request parameters, not {@code null}, can be empty
	 * @return URI for specified path with defined request parameters, not {@code null}
	 */
	protected URI buildUri(final String path, final MultiValueMap<String, String> parameters) {
		return URIBuilder.fromUri(Google.API_URL_BASE + path).queryParams(parameters).build();
	}
}
