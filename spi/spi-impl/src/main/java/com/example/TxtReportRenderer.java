package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class TxtReportRenderer extends ReportRenderer {

	@Override
	public void generateReport() {
		final List<String> music = MusicFinder.getMusic();
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
