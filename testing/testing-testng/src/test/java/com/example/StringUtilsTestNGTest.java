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

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class StringUtilsTestNGTest {

	@DataProvider
	public Object[][] isEmptyData() {
		return new Object[][]{
				{null, true},
				{"", true},
				{" ", false},
				{"some string", false},
		};
	}

	@DataProvider
	public Object[][] toHexStringData() {
		return new Object[][]{
				{new byte[0], ""},
				{new byte[]{1, 2, 13, 17, 45, 127}, "01020d112d7f"},
				{new byte[]{0, -1, -14, 17, -128}, "00fff21180"},
		};
	}

	@DataProvider
	public Object[][] toHexStringWrongData() {
		return new Object[][]{
				{null},
		};
	}

	@Test(dataProvider = "isEmptyData")
	public void testIsEmpty(final String test, final boolean expected) {
		final boolean actual = StringUtils.isEmpty(test);
		Assert.assertEquals(actual, expected);
	}

	@Test(dataProvider = "toHexStringData")
	public void testToHexString(final byte[] test, final String expected) {
		final String actual = StringUtils.toHexString(test);
		Assert.assertEquals(actual, expected);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testToHexString() {
		StringUtils.toHexString(null);
	}
}
