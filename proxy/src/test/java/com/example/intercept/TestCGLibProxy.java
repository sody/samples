/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.intercept;

import net.sf.cglib.proxy.Enhancer;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestCGLibProxy extends Assert {

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
		final Class superClass = builder.getObjectClass().isInterface() ? Object.class : builder.getObjectClass();
		final CGLibInvocationHandler<T> handler = new CGLibInvocationHandler<T>(builder);

		final Object proxy = builder.getObjectClass().isInterface() ?
				Enhancer.create(superClass, new Class[] { builder.getObjectClass() }, handler) :
				Enhancer.create(superClass, handler);

		return builder.getObjectClass().cast(proxy);
	}
}
