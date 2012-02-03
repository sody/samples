package com.example;

import org.spockframework.runtime.extension.AbstractMethodInterceptor;
import org.spockframework.runtime.extension.IMethodInvocation;

/**
 * @author Ivan Khalopik
 */
public class RepeatInterceptor extends AbstractMethodInterceptor{
  private final int count;

  public RepeatInterceptor(int count) {
    this.count = count;
  }

  @Override
  public void interceptFeatureExecution(IMethodInvocation invocation) throws Throwable {
    for (int i = 0; i < count; i++) {
      invocation.proceed();
    }
  }
}
