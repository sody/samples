package com.example.intercept;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Proxy;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class TestJdkProxy extends Assert {

	@Test
	public void testCreateProxy() {
		final int times = 1000000;
		final ObjectBuilder<MockInterface> builder = new SilentBuilder();

		final long start = System.currentTimeMillis();
		for (int i = 0; i < times; i++) {
			final MockInterface proxy = createProxy(builder);
		}
		final long end = System.currentTimeMillis();
		final long delta = end - start;
		System.out.printf("Times: %d\n", times);
		System.out.printf("All time: %d ms\n", delta);
		System.out.printf("Avg time: %f ms\n", ((double) delta / times));
	}

	@Test
	public void testUseProxy() {
		final int times = 10000000;
		final ObjectBuilder<MockInterface> builder = new SilentBuilder();
		final MockInterface proxy = createProxy(builder);

		final long start = System.currentTimeMillis();
		for (int i = 0; i < times; i++) {
			final String say = proxy.say();
		}
		final long end = System.currentTimeMillis();
		final long delta = end - start;
		System.out.printf("Times: %d\n", times);
		System.out.printf("All time: %d ms\n", delta);
		System.out.printf("Avg time: %f ms\n", ((double) delta / times));
	}

	public static <T> T createProxy(final ObjectBuilder<T> builder) {
		final ClassLoader classLoader = builder.getObjectClass().getClassLoader();

		final Object proxyInstance = Proxy.newProxyInstance(classLoader,
				new Class<?>[] { builder.getObjectClass() },
				new JdkInvocationHandler<T>(builder));
		return builder.getObjectClass().cast(proxyInstance);
	}
}
