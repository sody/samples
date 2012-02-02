package com.example;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GuiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(String.class).annotatedWith(Names.named("test-symbol")).toInstance("guice");
  }
}
