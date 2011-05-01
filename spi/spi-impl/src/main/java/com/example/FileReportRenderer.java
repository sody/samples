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

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FileReportRenderer extends ReportRenderer {

	@Override
	public void generateReport() {
		final List<String> music = findMusic();
		try {
			final FileWriter writer = new FileWriter("music.txt");
			for (String composition : music) {
				writer.append(composition);
				writer.append("\n");
			}
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
