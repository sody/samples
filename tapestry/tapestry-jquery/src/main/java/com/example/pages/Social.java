package com.example.pages;

import com.example.internal.google.Google;
import com.example.internal.google.GoogleProfile;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.oauth1.OAuth1ServiceProvider;
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

	@Inject
	private Messages messages;

	@InjectService("facebookService")
	private OAuth2ServiceProvider<Facebook> facebookService;

	@InjectService("twitterService")
	private OAuth1ServiceProvider<Twitter> twitterService;

	@InjectService("googleService")
	private OAuth2ServiceProvider<Google> googleService;

	@Persist
	private Map<String, String> socialProfile;

	@Persist
	@Property
	private String errorMessage;

	public Map<String, String> getSocialProfile() {
		return socialProfile;
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "facebook")
	void facebookConnected(final String accessToken) {
		final FacebookProfile profile = facebookService.getApi(accessToken).userOperations().getUserProfile();

		errorMessage = null;
		socialProfile = new HashMap<String, String>();
		socialProfile.put("id", profile.getId());
		socialProfile.put("name", profile.getName());
		socialProfile.put("gender", profile.getGender());
		socialProfile.put("locale", String.valueOf(profile.getLocale()));
		socialProfile.put("link", profile.getLink());
		socialProfile.put("image", Facebook.GRAPH_API_URL + profile.getId() + "/picture?type=large");
	}

	@OnEvent(value = EventConstants.FAILURE, component = "facebook")
	void facebookFailure() {
		socialProfile = null;
		errorMessage = messages.format("message.connection-denied", "Facebook");
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "twitter")
	void twitterConnected(final String accessToken, final String accessTokenSecret) {
		final TwitterProfile profile = twitterService.getApi(accessToken, accessTokenSecret).userOperations().getUserProfile();

		errorMessage = null;
		socialProfile = new HashMap<String, String>();
		socialProfile.put("id", String.valueOf(profile.getId()));
		socialProfile.put("name", profile.getName());
		socialProfile.put("gender", "");
		socialProfile.put("locale", profile.getLanguage());
		socialProfile.put("link", profile.getProfileUrl());
		socialProfile.put("image", profile.getProfileImageUrl());
	}

	@OnEvent(value = EventConstants.FAILURE, component = "twitter")
	void twitterFailure() {
		socialProfile = null;
		errorMessage = messages.format("message.connection-denied", "Twitter");
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "google")
	void googleConnected(final String accessToken) {
		final GoogleProfile profile = googleService.getApi(accessToken).userOperations().getUserProfile();

		errorMessage = null;
		socialProfile = new HashMap<String, String>();
		socialProfile.put("id", String.valueOf(profile.getId()));
		socialProfile.put("name", profile.getName());
		socialProfile.put("gender", "");
		socialProfile.put("locale", String.valueOf(profile.getLocale()));
		socialProfile.put("link", profile.getLink());
		socialProfile.put("image", profile.getPictureUrl());
	}

	@OnEvent(value = EventConstants.FAILURE, component = "google")
	void googleFailure(final String error, final String errorDescription) {
		socialProfile = null;
		errorMessage = messages.format("message.connection-denied", "Google");
	}
}
