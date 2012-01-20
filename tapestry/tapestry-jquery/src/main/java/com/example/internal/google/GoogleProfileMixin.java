package com.example.internal.google;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Locale;

/**
 * Annotated mixin to add Jackson annotations to GoogleProfile.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class GoogleProfileMixin {

	@JsonCreator
	GoogleProfileMixin(
			@JsonProperty("id") String id) {
	}

	@JsonProperty("name")
	private String name;

	@JsonProperty("given_name")
	private String givenName;

	@JsonProperty("family_name")
	private String familyName;

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("locale")
	private Locale locale;

	@JsonProperty("link")
	private String link;

	@JsonProperty("picture")
	private String pictureUrl;
}
