package com.example.internal.google;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;

/**
 * Jackson module for setting up mixin annotations on Google model types. This enables the use of Jackson annotations
 * without directly annotating the model classes themselves.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GoogleModule extends SimpleModule {
	public GoogleModule() {
		super("GoogleModule", new Version(1, 0, 0, "alpha-1"));
	}

	@Override
	public void setupModule(final SetupContext context) {
		context.setMixInAnnotations(GoogleProfile.class, GoogleProfileMixin.class);
	}
}
