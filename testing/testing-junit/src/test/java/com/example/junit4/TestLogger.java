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

package com.example.junit4;

import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

import java.util.logging.Logger;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestLogger extends TestWatchman {
	private final Logger logger = Logger.getLogger(TestLogger.class.getName());

	@Override
	public void starting(final FrameworkMethod method) {
		logger.info(String.format("Starting method %s", method.getName()));
	}

	@Override
	public void finished(final FrameworkMethod method) {
		logger.info(String.format("Finishing method %s", method.getName()));
	}

	@Override
	public void succeeded(final FrameworkMethod method) {
		logger.info(String.format("Succeeded test %s", method.getName()));
	}

	@Override
	public void failed(final Throwable e, final FrameworkMethod method) {
		logger.info(String.format("Failed test %s", method.getName()));
	}
}
