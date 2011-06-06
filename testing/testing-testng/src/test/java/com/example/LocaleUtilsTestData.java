package com.example;

import org.testng.annotations.DataProvider;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class LocaleUtilsTestData {

	@DataProvider(name = "getCandidateLocalesData", parallel = true)
	public static Object[][] getCandidateLocalesData() {
		return new Object[][]{
				{null, Arrays.asList(LocaleUtils.ROOT_LOCALE)},
				{LocaleUtils.ROOT_LOCALE, Arrays.asList(LocaleUtils.ROOT_LOCALE)},
				{ Locale.ENGLISH, Arrays.asList(Locale.ENGLISH, LocaleUtils.ROOT_LOCALE)},
				{Locale.US, Arrays.asList(Locale.US, Locale.ENGLISH, LocaleUtils.ROOT_LOCALE)},
				{new Locale("en", "US", "xxx"), Arrays.asList(
						new Locale("en", "US", "xxx"), Locale.US, Locale.ENGLISH, LocaleUtils.ROOT_LOCALE)
				},
		};
	}
}
