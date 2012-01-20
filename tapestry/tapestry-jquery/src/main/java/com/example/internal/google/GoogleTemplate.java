package com.example.internal.google;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

/**
 * This class represents default implementation of operations for interacting with Google APIs.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GoogleTemplate extends AbstractOAuth2ApiBinding implements Google {
	private UserOperations userOperations;

	/**
	 * Creates new instance of unauthenticated google template.
	 */
	public GoogleTemplate() {
		initialize();
	}

	/**
	 * Creates new instance of authenticated google template with defined access token.
	 *
	 * @param accessToken oauth access token, not {@code null}
	 */
	public GoogleTemplate(final String accessToken) {
		super(accessToken);
		initialize();
	}

	/**
	 * {@inheritDoc}
	 */
	public UserOperations userOperations() {
		return userOperations;
	}

	/**
	 * Overrides default JSON bindings using special Jackson module for google APIs.
	 */
	@Override
	protected MappingJacksonHttpMessageConverter getJsonMessageConverter() {
		final MappingJacksonHttpMessageConverter converter = super.getJsonMessageConverter();
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new GoogleModule());
		converter.setObjectMapper(objectMapper);
		return converter;
	}

	/**
	 * Initializes all operation templates.
	 */
	private void initialize() {
		userOperations = new UserTemplate(getRestTemplate(), isAuthorized());
	}
}
