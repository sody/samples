package com.example.components;

import com.example.internal.CSSConstants;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.MarkupWriterAdapter;
import org.apache.tapestry5.MarkupWriterListener;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.dom.Node;
import org.apache.tapestry5.dom.Text;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Button {

	private static final String[][][] BUTTON_TYPES = {
			{{CSSConstants.BUTTON_TEXT_ONLY, CSSConstants.BUTTON_ICON_ONLY}, {CSSConstants.BUTTON_ICON_ONLY, CSSConstants.BUTTON_ICONS_ONLY}},

			{{CSSConstants.BUTTON_TEXT_ONLY, CSSConstants.BUTTON_TEXT_ICON_SECONDARY}, {CSSConstants.BUTTON_TEXT_ICON_PRIMARY, CSSConstants.BUTTON_TEXT_ICONS}}
	};

	@Parameter(defaultPrefix = BindingConstants.LITERAL, name = "class")
	private String className;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String primary;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String secondary;

	private final MarkupWriterListener listener = new MarkupWriterAdapter() {
		@Override
		public void elementDidEnd(final Element element) {
			processElement(element);
		}
	};

	@BeginRender
	void beginRender(final MarkupWriter writer) {
		writer.addListener(listener);
	}


	@AfterRender
	void afterRender(final MarkupWriter writer) {
		writer.removeListener(listener);
	}

	private void processElement(final Element element) {
		final String elementName = element.getName();
		if (elementName.equals("input")) {
			final String type = element.getAttribute("type");
			if (type != null && type.equals("submit")) {
				addButtonStyle(element);
			} else {
				element.addClassName(CSSConstants.HELPER_HIDDEN_ACCESSIBLE);
			}
		} else {
			addButtonStyle(element);
			addButtonContent(element);
		}
	}

	private void addButtonStyle(final Element element) {
		if (className != null) {
			element.addClassName(className);
		}
		element.addClassName(
				CSSConstants.BUTTON,
				CSSConstants.WIDGET,
				CSSConstants.STATE_DEFAULT,
				CSSConstants.CORNER_ALL);
	}

	private void addButtonContent(final Element button) {
		Element text = getButtonText(button);
		final String buttonType = BUTTON_TYPES[text != null ? 1 : 0][primary != null ? 1 : 0][secondary != null ? 1 : 0];
		button.addClassName(buttonType);

		if (text == null) {
			text = button.element("span");
			text.text("_PLACEHOLDER_");
		}
		text.addClassName(CSSConstants.BUTTON_TEXT);

		if (primary != null) {
			button.element("span")
					.addClassName(CSSConstants.BUTTON_ICON_PRIMARY, CSSConstants.ICON, primary)
					.moveBefore(text);
		}
		if (secondary != null) {
			button.element("span")
					.addClassName(CSSConstants.BUTTON_ICON_SECONDARY, CSSConstants.ICON, secondary)
					.moveAfter(text);
		}
	}

	private Element getButtonText(final Element button) {
		for (Node node : button.getChildren()) {
			if (node instanceof Text) {
				return node.wrap("span");
			}
		}
		return null;
	}
}
