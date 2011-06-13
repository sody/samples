package com.example;

import org.testng.Assert;
import org.testng.TestNG;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AdditionalTest extends Assert {
	private Random generator;

	@BeforeClass
	public void setUpGenerator() {
		generator = new Random();
	}

	@Test(successPercentage = 50, invocationCount = 100, enabled = false)
	public void testAdditionalFunctionality() {
		if (generator.nextBoolean()) {
			fail();
		}
	}

	public static void main(String[] args) throws Exception{
		final TestNG testNG = new TestNG(true);
//		testNG.setTestClasses(new Class[] { AdditionalTest.class });
//		testNG.setExcludedGroups("optional");
		final Parser parser = new Parser("testing/testing-testng/src/test/resources/testng.yaml");
		final List<XmlSuite> suites = parser.parseToList();
		testNG.setXmlSuites(suites);
		testNG.run();
	}
}
