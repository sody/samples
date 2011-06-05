package com.example;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ConcurrencyTest extends Assert {
	private Map<String, String> data;

	@BeforeClass
	void setUp() throws Exception {
		data = new HashMap<String, String>();
	}

	@AfterClass
	void tearDown() throws Exception {
		data = null;
	}

	@Test(threadPoolSize = 30, invocationCount = 100, invocationTimeOut = 10000)
	public void testMapOperations() throws Exception {
		data.put("1", "111");
		data.put("2", "111");
		data.put("3", "111");
		data.put("4", "111");
		data.put("5", "111");
		data.put("6", "111");
		data.put("7", "111");
		for (Map.Entry<String, String> entry : data.entrySet()) {
			System.out.println(entry);
		}
		data.clear();
	}

	@Test(singleThreaded = true, invocationCount = 100, invocationTimeOut = 10000)
	public void testMapOperationsSafe() throws Exception {
		data.put("1", "111");
		data.put("2", "111");
		data.put("3", "111");
		data.put("4", "111");
		data.put("5", "111");
		data.put("6", "111");
		data.put("7", "111");
		for (Map.Entry<String, String> entry : data.entrySet()) {
			System.out.println(entry);
		}
		data.clear();
	}
}
