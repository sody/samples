package com.example.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.oauth1.OAuth1ServiceProvider;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.oauth2.OAuth2ServiceProvider;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Social {

	@InjectService("facebookService")
	private OAuth2ServiceProvider<Facebook> facebookService;

	@InjectService("twitterService")
	private OAuth1ServiceProvider<Twitter> twitterService;

	@Persist
	@Property
	private Map<String, String> socialProfile;

	@OnEvent(value = EventConstants.SUCCESS, component = "facebook")
	void facebookConnected(final String accessToken) {
		final FacebookProfile profile = facebookService.getApi(accessToken).userOperations().getUserProfile();

		socialProfile = new HashMap<String, String>();
		socialProfile.put("id", profile.getId());
		socialProfile.put("name", profile.getName());
		socialProfile.put("gender", profile.getGender());
		socialProfile.put("locale", String.valueOf(profile.getLocale()));
		socialProfile.put("link", profile.getLink());
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "twitter")
	void twitterConnected(final String accessToken, final String accessTokenSecret) {
		final TwitterProfile profile = twitterService.getApi(accessToken, accessTokenSecret).userOperations().getUserProfile();

		socialProfile = new HashMap<String, String>();
		socialProfile.put("id", String.valueOf(profile.getId()));
		socialProfile.put("name", profile.getName());
		socialProfile.put("gender", "");
		socialProfile.put("locale", profile.getLanguage());
		socialProfile.put("link", profile.getProfileUrl());
	}
}
