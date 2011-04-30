package com.example.utils;

import sun.misc.Service;

import java.util.*;

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

	public static <T> List<T> resolveImplementations(final Class<T> serviceClass) {
		assert serviceClass != null;

		final ArrayList<T> services = new ArrayList<T>();
		for (final T service : ServiceLoader.load(serviceClass)) {
			services.add(service);
		}

		return services;
	}
}
