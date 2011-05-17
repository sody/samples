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

package com.example.junit3;

import com.example.StringUtils;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class StringUtilsJUnit3Test extends TestCase {
	private final Map isEmptyData = new HashMap();
	private final Map toHexStringData = new HashMap();

	protected void setUp() throws Exception {
		isEmptyData.put("", Boolean.TRUE);
		isEmptyData.put(" ", Boolean.FALSE);
		isEmptyData.put("some string", Boolean.FALSE);

		toHexStringData.put("", new byte[0]);
		toHexStringData.put("01020d112d7f", new byte[] { 1, 2, 13, 17, 45, 127 });
		toHexStringData.put("00fff21180", new byte[] { 0, -1, -14, 17, -128 });
	}

	protected void tearDown() throws Exception {
		isEmptyData.clear();
		toHexStringData.clear();
	}

	public static void testIsEmpty0() {
		boolean actual = StringUtils.isEmpty(null);
		assertTrue(actual);

		actual = StringUtils.isEmpty("");
		assertTrue(actual);

		actual = StringUtils.isEmpty(" ");
		assertFalse(actual);

		actual = StringUtils.isEmpty("some string");
		assertFalse(actual);
	}

	public void testIsEmpty() {
		final boolean empty = StringUtils.isEmpty(null);
		assertTrue(empty);

		for (Iterator iterator = isEmptyData.keySet().iterator(); iterator.hasNext();) {
			final CharSequence testString = (CharSequence) iterator.next();
			final boolean actual = StringUtils.isEmpty(testString);
			final boolean expected = ((Boolean) isEmptyData.get(testString)).booleanValue();
			assertEquals(expected, actual);
		}
	}

	public void testToHexString() {
		try {
			StringUtils.toHexString(null);
			fail();
		} catch (Exception e) {
			//all is ok
		}

		for (Iterator iterator = toHexStringData.keySet().iterator(); iterator.hasNext();) {
			final String expected = (String) iterator.next();
			final byte[] testData = (byte[]) toHexStringData.get(expected);
			final String actual = StringUtils.toHexString(testData);
			assertEquals(expected, actual);
		}
	}
}
