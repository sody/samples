package com.example.internal.google;

/**
 * This interface represents API for performing operations on Google user profiles.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface UserOperations {

	/**
	 * Retrieves the profile for the authenticated user. Current user should be authenticated.
	 *
	 * @return the user's profile information.
	 * @throws org.springframework.social.ApiException
	 *          if there is an error while communicating with Google.
	 * @throws org.springframework.social.MissingAuthorizationException
	 *          if GoogleTemplate was not created with an access token.
	 */
	GoogleProfile getUserProfile();
}
