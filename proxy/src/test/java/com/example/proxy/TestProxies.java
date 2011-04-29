package com.example.proxy;

import net.sf.cglib.proxy.Enhancer;
import org.apache.tapestry5.plastic.ClassInstantiator;
import org.apache.tapestry5.plastic.InstructionBuilder;
import org.apache.tapestry5.plastic.InstructionBuilderCallback;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
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
 * @since 8.0
 */
public class TestProxies extends Assert {

	@Test
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
		final PlasticManager manager = new PlasticManager();
		final ClassInstantiator proxy = manager.createProxy(builder.getObjectClass(), new PlasticClassTransformer() {
			public void transform(final PlasticClass plasticClass) {
				final PlasticField builderField =
						plasticClass.introduceField(ObjectBuilder.class, "builder").inject(builder);
				final Class<T> type = builder.getObjectClass();
				final PlasticField delegateField = plasticClass.introduceField(type, "delegate");

				final PlasticMethod delegate =
						plasticClass.introducePrivateMethod(type.getName(), "delegate", null, null);
				delegate.changeImplementation(new InstructionBuilderCallback() {
					public void doBuild(final InstructionBuilder builder) {
						builder.getField(delegateField);
						builder.ifNull(new InstructionBuilderCallback() {
							public void doBuild(final InstructionBuilder builder) {
								builder.loadThis().getField(builderField);
								builder.invoke(ObjectBuilder.class, Object.class, "build");
								builder.checkcast(type).returnResult();
							}
						}, new InstructionBuilderCallback() {
							public void doBuild(final InstructionBuilder builder) {
								builder.checkcast(type).returnResult();
							}
						});
					}
				});

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
