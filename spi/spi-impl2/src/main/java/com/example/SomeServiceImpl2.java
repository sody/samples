package com.example;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class SomeServiceImpl2 implements SomeService {
	private final String message;

	public SomeServiceImpl2(final String message) {
		this.message = message;
	}

	public String doSomeThing() {
		System.out.println("This is implementation#2");
		return message;
	}
}
