package com.example;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public interface SPIResolver {

	SPIResolver DEFAULT_SPI_RESOLVER = new DefaultSPIResolver();
	SPIResolver SUN_SPI_RESOLVER = new SunSPIResolver();

	<T> T resolveImplementation(Class<T> serviceClass);
}
