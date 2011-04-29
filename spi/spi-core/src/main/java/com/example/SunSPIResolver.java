package com.example;

import sun.misc.Service;

import java.util.Iterator;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class SunSPIResolver implements SPIResolver {

	public <T> T resolveImplementation(final Class<T> serviceClass) {
		assert serviceClass != null;

		final Iterator providers = Service.providers(serviceClass);
		if (providers.hasNext()) {
			return serviceClass.cast(providers.next());
		}

		return null;
	}
}
