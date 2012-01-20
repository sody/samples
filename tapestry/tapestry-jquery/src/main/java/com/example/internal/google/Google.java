package com.example.internal.google;

import org.springframework.social.ApiBinding;

/**
 * Interface specifying a basic set of operations for interacting with Google APIs.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Google extends ApiBinding {

	/**
	 * Base Google APIs URL.
	 */
	String API_URL_BASE = "https://www.googleapis.com/oauth2/v2/";

	/**
	 * API for performing operations on Google user profiles.
	 *
	 * @return google user profiles operations, not {@code null}
	 */
	UserOperations userOperations();

}
