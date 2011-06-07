package com.example;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class FactoryTest {

	@DataProvider
	public Object[][] tablesData() {
		return new Object[][] {
				{"FIRST_TABLE"},
				{"SECOND_TABLE"},
				{"THIRD_TABLE"},
		};
	}

	@Factory(dataProvider = "tablesData")
	public Object[] createTest(String table) {
		return new Object[] { new GenericTableTest(table) };
	}

	@Parameters("table")
	@Factory
	public Object[] createParameterizedTest(@Optional("SOME_TABLE") String table) {
		return new Object[] { new GenericTableTest(table) };
	}
}
