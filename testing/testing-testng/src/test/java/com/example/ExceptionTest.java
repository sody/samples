package com.example;

import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ExceptionTest {

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "^Wrong (.*)$")
	public void testException() {
		throw new IllegalArgumentException("Wrong execution");
	}
}
