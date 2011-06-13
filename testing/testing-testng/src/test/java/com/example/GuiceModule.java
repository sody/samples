package com.example;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GuiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(String.class).annotatedWith(Names.named("guice-string-0")).toInstance("Hello, ");
	}

	@Named("guice-string-1")
	@Inject
	@Singleton
	@Provides
	public String provideGuiceString() {
		return "World!!!";
	}
}
