package com.example;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class DefaultSPIResolver implements SPIResolver {
	public <T> T resolveImplementation(final Class<T> serviceClass) {
		assert serviceClass != null;

		final Iterator<T> providers = ServiceLoader.load(serviceClass).iterator();
		if (providers.hasNext()) {
			return providers.next();
		}

		return null;
	}
}
