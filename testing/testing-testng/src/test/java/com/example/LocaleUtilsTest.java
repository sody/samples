/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LocaleUtilsTest extends Assert {

	@DataProvider
	public Object[][] parseLocaleData() {
		return new Object[][] {
				{ null, null },
				{ "", LocaleUtils.ROOT_LOCALE },
				{ "en", Locale.ENGLISH },
				{ "en_US", Locale.US },
				{ "en_GB", Locale.UK },
				{ "ru", new Locale("ru") },
				{ "ru_RU_xxx", new Locale("ru", "RU", "xxx") },
		};
	}

	@Test(dataProvider = "parseLocaleData")
	public void testParseLocale(String locale, Locale expected) {
		final Locale actual = LocaleUtils.parseLocale(locale);
		assertEquals(actual, expected);
	}

	@Test(dataProvider = "getCandidateLocalesData", dataProviderClass = LocaleUtilsTestData.class)
	public void testGetCandidateLocales(Locale locale, List<Locale> expected) {
		final List<Locale> actual = LocaleUtils.getCandidateLocales(locale);
		assertEquals(actual, expected);
	}
}
