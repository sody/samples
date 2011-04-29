package com.example;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ServiceConfigurationError;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class SPITest extends Assert {

	@DataProvider
	public Object[][] testResolverData() {
		return new Object[][] {
				{ SPIResolver.DEFAULT_SPI_RESOLVER },
				{ SPIResolver.SUN_SPI_RESOLVER },
		};
	}

	@Test(dataProvider = "testResolverData", expectedExceptions = {
			ServiceConfigurationError.class, sun.misc.ServiceConfigurationError.class
	})
	public void testResolver(final SPIResolver resolver) {
		final SomeService service = resolver.resolveImplementation(SomeService.class);
	}
}
