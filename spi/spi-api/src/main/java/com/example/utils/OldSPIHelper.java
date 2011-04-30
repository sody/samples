package com.example.utils;

import sun.misc.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class OldSPIHelper {

	public static <T> T resolveImplementation(final Class<T> serviceClass) {
		assert serviceClass != null;

		final Iterator providers = Service.providers(serviceClass);
		if (providers.hasNext()) {
			return serviceClass.cast(providers.next());
		}

		return null;
	}

	public static <T> List<T> resolveImplementations(final Class<T> serviceClass) {
		assert serviceClass != null;

		final ArrayList<T> services = new ArrayList<T>();
		final Iterator providers = Service.providers(serviceClass);
		while (providers.hasNext()) {
			services.add(serviceClass.cast(providers.next()));
		}

		return services;
	}
}
