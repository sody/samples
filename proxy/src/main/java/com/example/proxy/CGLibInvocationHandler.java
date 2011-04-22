package com.example.proxy;

import net.sf.cglib.proxy.InvocationHandler;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class CGLibInvocationHandler<T> extends JdkInvocationHandler<T> implements InvocationHandler {
	public CGLibInvocationHandler(final ObjectBuilder<T> builder) {
		super(builder);
	}
}
