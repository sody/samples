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

import java.util.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ReportRenderer {
	public void generateReport() {
		final List<String> music = findMusic();
		for (String composition : music) {
			System.out.println(composition);
		}
	}

	public List<String> findMusic() {
		final List<String> music = new ArrayList<String>();
		for (final MusicFinder finder : SPIUtils.resolveAll(MusicFinder.class)) {
			music.addAll(finder.getMusic());
		}
		Collections.sort(music);
		return music;
	}

	public static ReportRenderer getInstance() {
		final ReportRenderer renderer = SPIUtils.resolve(ReportRenderer.class);
		return renderer != null ? renderer : new ReportRenderer();
	}

	public static void main(String[] args) {
		final ReportRenderer renderer = getInstance();
		renderer.generateReport();
	}
}
