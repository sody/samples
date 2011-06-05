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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LocaleUtilsOldStyleTest extends Assert {
	private Map<String, Locale> parseLocaleData = new HashMap<String, Locale>();

	@BeforeClass
	private void setUp() {
		parseLocaleData.put(null, null);
		parseLocaleData.put("", LocaleUtils.ROOT_LOCALE);
		parseLocaleData.put("en", Locale.ENGLISH);
		parseLocaleData.put("en_US", Locale.US);
		parseLocaleData.put("en_GB", Locale.UK);
		parseLocaleData.put("ru", new Locale("ru"));
		parseLocaleData.put("ru_RU_xxx", new Locale("ru", "RU", "xxx"));
	}

	@AfterTest
	void tearDown() {
		parseLocaleData.clear();
	}

	@Test
	public void testParseLocale() {
		for (Map.Entry<String, Locale> entry : parseLocaleData.entrySet()) {
			final Locale actual = LocaleUtils.parseLocale(entry.getKey());
			final Locale expected = entry.getValue();
			assertEquals(actual, expected);
		}
	}
}
