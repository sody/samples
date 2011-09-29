package com.example.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class UIModule {

	public void contributeApplicationDefaults(final MappedConfiguration<String, String> configuration) {
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "ru,en");
		configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
	}

}
