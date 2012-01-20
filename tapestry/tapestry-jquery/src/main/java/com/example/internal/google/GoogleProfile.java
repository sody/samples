package com.example.internal.google;

import java.io.Serializable;
import java.util.Locale;

/**
 * This class represents model containing a Google user's profile information.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GoogleProfile implements Serializable {
	private final String id;
	private String name;
	private String givenName;
	private String familyName;
	private String gender;
	private Locale locale;
	private String link;
	private String pictureUrl;

	public GoogleProfile(final String id) {
		this.id = id;
	}

	/**
	 * Gets user's Google ID.
	 *
	 * @return user's Google ID, not {@code null}
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets user's full name.
	 *
	 * @return user's full name, can be {@code null}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets user's first name.
	 *
	 * @return user's first name, can be {@code null}
	 */
	public String getGivenName() {
		return givenName;
	}

	/**
	 * Gets user's last name.
	 *
	 * @return user's last name, can be {@code null}
	 */
	public String getFamilyName() {
		return familyName;
	}

	/**
	 * Gets user's gender.
	 *
	 * @return user's gender, can be {@code null}
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Gets user's locale.
	 *
	 * @return user's locale, can be {@code null}
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Gets link to the user's profile on Google.
	 *
	 * @return user's profile link, can be {@code null}
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Gets user's profile picture as a url.
	 *
	 * @return user's profile picture url, can be {@code null}
	 */
	public String getPictureUrl() {
		return pictureUrl;
	}
}
