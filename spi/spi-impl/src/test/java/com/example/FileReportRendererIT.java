package com.example;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class FileReportRendererIT extends Assert {

	@Test
	public void test() {
		ReportRenderer.main(null);
	}
}
