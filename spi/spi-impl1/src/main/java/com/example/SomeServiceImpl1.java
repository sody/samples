package com.example;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class SomeServiceImpl1 implements SomeService {

	public String doSomeThing() {
		System.out.println("This is implementation#1");
		return "test";
	}
}
