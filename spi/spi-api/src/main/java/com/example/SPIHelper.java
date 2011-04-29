package com.example;

import sun.misc.Service;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class SPIHelper {

	public static <T> T resolveImplementation(final Class<T> serviceClass) {
		assert serviceClass != null;

		final Iterator<T> providers = ServiceLoader.load(serviceClass).iterator();
		if (providers.hasNext()) {
			return providers.next();
		}

		return null;
	}

	public static <T> T obtainImplementation(final Class<T> serviceClass) {
		assert serviceClass != null;

		final Iterator providers = Service.providers(serviceClass);
		if (providers.hasNext()) {
			return serviceClass.cast(providers.next());
		}

		return null;
	}
}
