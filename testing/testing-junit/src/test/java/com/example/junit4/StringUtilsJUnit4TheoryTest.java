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
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@RunWith(Theories.class)
public class StringUtilsJUnit4TheoryTest extends Assert {

	@DataPoints
	public static Object[][] isEmptyData = new Object[][] {
			{ "", true },
			{ " ", false },
			{ "some string", false },
	};

	@DataPoint
	public static Object[] nullData = new Object[] {
			null, true
	};

	@Theory
	public void testEmpty(final Object... testData) {
		final boolean actual = StringUtils.isEmpty((CharSequence) testData[0]);
		assertEquals(testData[1], actual);
	}
}
