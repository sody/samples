package com.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class JdkInvocationHandler<T> implements InvocationHandler {
	private final ObjectBuilder<T> builder;

	private T delegate;

	public JdkInvocationHandler(final ObjectBuilder<T> builder) {
		this.builder = builder;
	}

	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		return method.invoke(getDelegate(), args);
	}

	private T getDelegate() {
		if (delegate == null) {
			delegate = builder.build();
		}
		return delegate;
	}
}
