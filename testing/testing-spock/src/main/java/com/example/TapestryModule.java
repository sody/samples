package com.example;

import org.apache.tapestry5.ioc.MappedConfiguration;

/**
 * @author Ivan Khalopik
 */
public class TapestryModule {

  public void contributeApplicationDefaults(final MappedConfiguration<String, String> configuration) {
    configuration.add("test-symbol", "tapestry");
  }
}
