package com.example.internal.google;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * This class represents Google OAuth2 ServiceProvider implementation.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GoogleServiceProvider extends AbstractOAuth2ServiceProvider<Google> {

	public GoogleServiceProvider(final String clientId, final String clientSecret) {
		super(new GoogleOAuth2Template(clientId, clientSecret));
	}

	@Override
	public Google getApi(final String accessToken) {
		return new GoogleTemplate(accessToken);
	}
}
