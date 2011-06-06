package com.example;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class AdditionalTest extends Assert {
	private Random generator;

	@BeforeClass
	public void setUpGenerator() {
		generator = new Random();
	}

	@Test(successPercentage = 50, invocationCount = 100)
	public void testAdditionalFunctionality() {
		if (generator.nextBoolean()) {
			fail();
		}
	}
}
