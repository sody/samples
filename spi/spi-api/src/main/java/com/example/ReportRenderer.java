package com.example;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class ReportRenderer {
	public void generateReport() {
		final List<String> music = MusicFinder.getMusic();
		for (String composition : music) {
			System.out.println(composition);
		}
	}

	public static ReportRenderer getImplementation() {
		final Iterator<ReportRenderer> providers = ServiceLoader.load(ReportRenderer.class).iterator();
		if (providers.hasNext()) {
			return providers.next();
		}

		return new ReportRenderer();
	}

	public static void main(String[] args) {
		final ReportRenderer renderer = getImplementation();
		renderer.generateReport();
	}
}
