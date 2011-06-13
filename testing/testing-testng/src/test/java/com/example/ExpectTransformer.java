package com.example;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ExpectTransformer implements IAnnotationTransformer {
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		if (testMethod.getName().startsWith("expect")) {
			annotation.setExpectedExceptions(new Class[] {Exception.class});
		}
	}
}
