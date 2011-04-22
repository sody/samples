package com.example.intercept;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class SilentBuilder implements ObjectBuilder<MockInterface> {
	public Class<MockInterface> getObjectClass() {
		return MockInterface.class;
	}

	public List<Interceptor> getInterceptors() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public MockInterface build() {
		return new MockInterface() {
			public String say() {
				return "test";
			}
		};
	}
}
