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
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.Timeout;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class OtherJUnit4Test {

	@Rule
	public final TemporaryFolder folder = new TemporaryFolder();

	@Rule
	public final Timeout timeout = new Timeout(1000);

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Rule
	public final LogWatchman logger = new LogWatchman();

	@Ignore
	@Test
	public void anotherInfinity() {
		while (true);
	}

	@Test
	public void testFileWriting() throws IOException {
		final File log = folder.newFile("debug.log");
		final FileWriter logWriter = new FileWriter(log);
		logWriter.append("Hello, ");
		logWriter.append("World!!!");
		logWriter.flush();
		logWriter.close();
	}

	@Test
	public void testExpectedException() throws IOException {
		thrown.expect(NullPointerException.class);
		StringUtils.toHexString(null);
	}

	@Ignore
	@Test(timeout = 1000)
	public void infinity() {
		while (true);
	}
}
