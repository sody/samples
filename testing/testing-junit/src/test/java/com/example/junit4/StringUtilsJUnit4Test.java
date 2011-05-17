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

package com.example.junit4;

import com.example.StringUtils;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class StringUtilsJUnit4Test extends Assert {
	private static final Map<CharSequence, Boolean> isEmptyData = new HashMap<CharSequence, Boolean>();
	private static final Map<String, byte[]> toHexStringData = new HashMap<String, byte[]>();

	@BeforeClass
	public static void setUpIsEmptyData() {
		isEmptyData.put("", true);
		isEmptyData.put(" ", false);
		isEmptyData.put("some string", false);
	}

	@BeforeClass
	public static void setUpToHexStringData() {
		toHexStringData.put("", new byte[0]);
		toHexStringData.put("01020d112d7f", new byte[] { 1, 2, 13, 17, 45, 127 });
		toHexStringData.put("00fff21180", new byte[] { 0, -1, -14, 17, -128 });
	}

	@AfterClass
	public static void tearDownIsEmptyData() {
		isEmptyData.clear();
	}

	@AfterClass
	public static void tearDownToHexStringData() {
		toHexStringData.clear();
	}

	@Test
	public void testIsEmpty() {
		final boolean empty = StringUtils.isEmpty(null);
		assertTrue(empty);

		for (Map.Entry<CharSequence, Boolean> entry : isEmptyData.entrySet()) {
			final CharSequence testString = entry.getKey();
			final boolean expected = entry.getValue();
			final boolean actual = StringUtils.isEmpty(testString);
			assertEquals(expected, actual);
		}
	}

	@Test
	public void testToHexString() {
		for (Map.Entry<String, byte[]> entry : toHexStringData.entrySet()) {
			final byte[] testData = entry.getValue();
			final String expected = entry.getKey();
			final String actual = StringUtils.toHexString(testData);
			assertEquals(expected, actual);
		}
	}

	@Test(expected = NullPointerException.class)
	public void testToHexStringWrong() {
		StringUtils.toHexString(null);
	}
}
