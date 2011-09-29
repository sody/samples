package com.example.components;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BindingSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Import(
		stylesheet = {
				"context:css/sunny/jquery-ui-1.8.16.css",
				"context:css/layout.css",
				"context:css/base.css"
		},
		library = {
				"context:js/jquery-1.6.2-noconflict.js",
				"context:js/jquery-ui-1.8.16.js",
				"context:js/base.js"
		})
@SupportsInformalParameters
public class Layout {

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@Inject
	private BindingSource bindingSource;

	@Inject
	private ComponentResources resources;

	@Inject
	private JavaScriptSupport support;

	Binding defaultTitle() {
		return bindingSource.newBinding("title", resources.getContainerResources(), BindingConstants.MESSAGE, "title");
	}

	@AfterRender
	void afterRender() {
		support.addScript("setTimeout(T5.Initializer[\"jquery.ui\"], %d)", 1500);
//		support.addInitializerCall("jquery.ui", "");
	}
}
