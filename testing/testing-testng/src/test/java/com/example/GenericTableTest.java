package com.example;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class GenericTableTest extends Assert {
	private final String table;

	public GenericTableTest(final String table) {
		this.table = table;
	}

	@Test
	public void testTable() {
		System.out.println(table);
		// do some testing staff here
	}
}
