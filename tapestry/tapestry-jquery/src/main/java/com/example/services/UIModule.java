package com.example.services;

import com.example.internal.google.Google;
import com.example.internal.google.GoogleServiceProvider;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookServiceProvider;
import org.springframework.social.oauth1.OAuth1ServiceProvider;
import org.springframework.social.oauth2.OAuth2ServiceProvider;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterServiceProvider;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class UIModule {
	private static final String FACEBOOK_CLIENT_ID = "facebook.client-id";
	private static final String FACEBOOK_CLIENT_SECRET = "facebook.client-secret";

	private static final String TWITTER_CONSUMER_KEY = "twitter.consumer-key";
	private static final String TWITTER_CONSUMER_SECRET = "twitter.consumer-secret";

	private static final String GOOGLE_CLIENT_ID = "google.client-id";
	private static final String GOOGLE_CLIENT_SECRET = "google.client-secret";

	public void contributeApplicationDefaults(final MappedConfiguration<String, String> configuration) {
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "ru,en");
		configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
		// created here https://developers.facebook.com/apps
		configuration.add(FACEBOOK_CLIENT_ID, "-client-id-");
		configuration.add(FACEBOOK_CLIENT_SECRET, "-secret-");
		// created here https://dev.twitter.com/apps/new
		configuration.add(TWITTER_CONSUMER_KEY, "-client-id-");
		configuration.add(TWITTER_CONSUMER_SECRET, "-secret-");
		//created here https://code.google.com/apis/console
		configuration.add(GOOGLE_CLIENT_ID, "-client-id-");
		configuration.add(GOOGLE_CLIENT_SECRET, "-secret-");
	}

	public OAuth2ServiceProvider<Facebook> buildFacebookService(@Symbol(FACEBOOK_CLIENT_ID) final String clientId,
																@Symbol(FACEBOOK_CLIENT_SECRET) final String clientSecret) {
		return new FacebookServiceProvider(clientId, clientSecret);
	}

	public OAuth1ServiceProvider<Twitter> buildTwitterService(@Symbol(TWITTER_CONSUMER_KEY) final String consumerKey,
															  @Symbol(TWITTER_CONSUMER_SECRET) final String consumerSecret) {
		return new TwitterServiceProvider(consumerKey, consumerSecret);
	}

	public OAuth2ServiceProvider<Google> buildGoogleService(@Symbol(GOOGLE_CLIENT_ID) final String clientId,
															@Symbol(GOOGLE_CLIENT_SECRET) final String clientSecret) {
		return new GoogleServiceProvider(clientId, clientSecret);
	}
}
