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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@RunWith(Parameterized.class)
public class StringUtilsJUnit4ParameterizedTest extends Assert {
	private final CharSequence testData;
	private final boolean expected;

	public StringUtilsJUnit4ParameterizedTest(final CharSequence testData, final boolean expected) {
		this.testData = testData;
		this.expected = expected;
	}

	@Parameterized.Parameters
	public static List<Object[]> isEmptyData() {
		return Arrays.asList(new Object[][] {
				{ null, true },
				{ "", true },
				{ " ", false },
				{ "some string", false },
		});
	}

	@Test
	public void testIsEmpty() {
		final boolean actual = StringUtils.isEmpty(testData);
		assertEquals(expected, actual);
	}
}
