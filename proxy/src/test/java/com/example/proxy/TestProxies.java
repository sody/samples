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

package com.example.proxy;

import net.sf.cglib.proxy.Enhancer;
import org.apache.tapestry5.plastic.ClassInstantiator;
import org.apache.tapestry5.plastic.InstructionBuilder;
import org.apache.tapestry5.plastic.InstructionBuilderCallback;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticClassTransformer;
import org.apache.tapestry5.plastic.PlasticField;
import org.apache.tapestry5.plastic.PlasticManager;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestProxies extends Assert {

	@Test(enabled = false)
	public void testPlasticProxy() {
		System.out.println("Create proxy builder");
		final ObjectBuilder<MockInterface> builder = new SimpleBuilder();

		System.out.println("Create proxy");
		final MockInterface proxy = createPlasticProxy(builder);
		testProxy(proxy);
	}

	@Test
	public void testJdkProxy() {
		System.out.println("Create proxy builder");
		final ObjectBuilder<MockInterface> builder = new SimpleBuilder();

		System.out.println("Create proxy");
		final MockInterface proxy = createJdkProxy(builder);
		testProxy(proxy);
	}

	@Test
	public void testCGLibProxy() {
		System.out.println("Create proxy builder");
		final ObjectBuilder<MockInterface> builder = new SimpleBuilder();

		System.out.println("Create proxy");
		final MockInterface proxy = createCGLibProxy(builder);
		testProxy(proxy);
	}

	private void testProxy(final MockInterface proxy) {
		System.out.println("Invoke proxy method #1");
		String result = proxy.say();
		System.out.println("Result = " + result);

		System.out.println("Invoke proxy method #2");
		result = proxy.say();
		System.out.println("Result = " + result);
	}

	private static <T> T createPlasticProxy(final ObjectBuilder<T> builder) {
		final PlasticManager manager = PlasticManager.withContextClassLoader().create();
		final ClassInstantiator proxy = manager.createProxy(builder.getObjectClass(), new PlasticClassTransformer() {
			public void transform(final PlasticClass plasticClass) {
				final PlasticField builderField =
						plasticClass.introduceField(ObjectBuilder.class, "builder").inject(builder);
				final Class<T> type = builder.getObjectClass();
				final PlasticField delegateField = plasticClass.introduceField(type, "delegate");

				final PlasticMethod delegate =
						plasticClass.introducePrivateMethod(type.getName(), "delegate", null, null);
//				delegate.changeImplementation(new InstructionBuilderCallback() {
//					public void doBuild(final InstructionBuilder builder) {
//						builder.getField(delegateField);
//						builder.ifNull(new InstructionBuilderCallback() {
//							public void doBuild(final InstructionBuilder builder) {
//								builder.invoke(ObjectBuilder.class, Object.class, "build");
//								builder.loadThis().putField(builderField.getPlasticClass().getClassName(),
//										builderField.getName(),
//										builderField.getTypeName());
//								builder.checkcast(type).returnResult();
//							}
//						}, null);
//						builder.checkcast(type).returnResult();
//					}
//				});

				for (Method method : type.getMethods()) {
					plasticClass.introduceMethod(method).delegateTo(delegate);
				}

				plasticClass.addToString("<Proxy>");
			}
		});

		return builder.getObjectClass().cast(proxy.newInstance());
	}

	private static <T> T createJdkProxy(final ObjectBuilder<T> builder) {
		final ClassLoader classLoader = builder.getObjectClass().getClassLoader();

		final Object proxyInstance = Proxy.newProxyInstance(classLoader,
				new Class<?>[] { builder.getObjectClass() },
				new JdkInvocationHandler<T>(builder));
		return builder.getObjectClass().cast(proxyInstance);
	}

	private static <T> T createCGLibProxy(final ObjectBuilder<T> builder) {
		final Class superClass = builder.getObjectClass().isInterface() ? Object.class : builder.getObjectClass();
		final CGLibInvocationHandler<T> handler = new CGLibInvocationHandler<T>(builder);

		final Object proxy = builder.getObjectClass().isInterface() ?
				Enhancer.create(superClass, new Class[] { builder.getObjectClass() }, handler) :
				Enhancer.create(superClass, handler);

		return builder.getObjectClass().cast(proxy);
	}
}
