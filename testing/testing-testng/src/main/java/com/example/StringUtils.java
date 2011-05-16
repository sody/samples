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

/**
 * This class represents utility methods for working with java strings.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class StringUtils {
	private static final int HI_BYTE_MASK = 0xf0;
	private static final int LOW_BYTE_MASK = 0x0f;

	private static final char[] HEX_SYMBOLS = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
	};

	/**
	 * Checks if char sequence is empty(null or has no length).
	 *
	 * @param sequence sequence to check empty state
	 * @return true if sequence is empty, false otherwise
	 */
	public static boolean isEmpty(final CharSequence sequence) {
		return sequence == null || sequence.length() <= 0;
	}

	/**
	 * Converts binary data to string hex representation
	 *
	 * @param data binary data
	 * @return binary data hex representation
	 */
	public static String toHexString(final byte[] data) {
		final StringBuffer builder = new StringBuffer(2 * data.length);
		for (byte item : data) {
			builder.append(HEX_SYMBOLS[(HI_BYTE_MASK & item) >>> 4]);
			builder.append(HEX_SYMBOLS[(LOW_BYTE_MASK & item)]);
		}
		return builder.toString();
	}
}