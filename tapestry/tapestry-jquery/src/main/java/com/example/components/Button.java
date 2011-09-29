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
 * Corresponds to jquery.ui button widgets. It is responsible for correct button rendering with all needed content and
 * styles that will prevent content flashing during applying styles on client-side.
 *
 * @tapestrydoc
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Button {
	private static final String EMPTY_TEXT_PLACEHOLDER = "_EMPTY_";
	private static final String[][][] BUTTON_TYPES = {
			{
					{CSSConstants.BUTTON_TEXT_ONLY, CSSConstants.BUTTON_ICON_ONLY},
					{CSSConstants.BUTTON_ICON_ONLY, CSSConstants.BUTTON_ICONS_ONLY}
			},
			{
					{CSSConstants.BUTTON_TEXT_ONLY, CSSConstants.BUTTON_TEXT_ICON_SECONDARY},
					{CSSConstants.BUTTON_TEXT_ICON_PRIMARY, CSSConstants.BUTTON_TEXT_ICONS}
			}
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

	/**
	 * Adds markup writer listener that processes child markup with {@link #processElement(org.apache.tapestry5.dom.Element)}
	 * method.
	 *
	 * @param writer markup writer, not {@code null}
	 */
	@BeginRender
	void beginRender(final MarkupWriter writer) {
		writer.addListener(listener);
	}

	/**
	 * Removes markup writer listener after render component.
	 *
	 * @param writer markup writer, not {@code null}
	 */
	@AfterRender
	void afterRender(final MarkupWriter writer) {
		writer.removeListener(listener);
	}

	/**
	 * Processes every child element inside this component with applying all necessary styles and adding all needed
	 * content.
	 *
	 * @param element element to process, not {@code null}
	 */
	private void processElement(final Element element) {
		// get element name
		final String elementName = element.getName();
		// input elements processing differs from others
		if (elementName.equals("input")) {
			// get input type
			final String type = element.getAttribute("type");
			if (type != null && type.equals("submit")) {
				// submit buttons can have only styles
				addButtonStyle(element);
			} else {
				// other input types should be hided
				// all styles will be applied to their label elements
				element.addClassName(CSSConstants.HELPER_HIDDEN_ACCESSIBLE);
			}
		} else {
			// other elements will have all needed styles and all necessary content
			addButtonStyle(element);
			addButtonContent(element);
		}
	}

	/**
	 * Adds special ui button styles to specified element.
	 *
	 * @param element element to add styles for, not {@code null}
	 */
	private void addButtonStyle(final Element element) {
		// add specified class if bounded
		if (className != null) {
			element.addClassName(className);
		}
		// add usual button styles
		element.addClassName(
				CSSConstants.BUTTON,
				CSSConstants.WIDGET,
				CSSConstants.STATE_DEFAULT,
				CSSConstants.CORNER_ALL);
	}

	/**
	 * Adds generated content to specified element. It includes button type class, label, primary and secondary items.
	 * NOTE: It should not be applied to input elements, only for their labels.
	 *
	 * @param button button to generate content for, not {@code null}
	 */
	private void addButtonContent(final Element button) {
		// get label element
		Element label = getText(button);
		// determine button type according to hasText, hasPrimaryIcon, hasSecondaryIcon options
		final String buttonType = BUTTON_TYPES[label != null ? 1 : 0][primary != null ? 1 : 0][secondary != null ? 1 : 0];
		// add button type to class attribute
		button.addClassName(buttonType);
		// if label is not found, create empty placeholder
		if (label == null) {
			label = button.element("span");
			label.text(EMPTY_TEXT_PLACEHOLDER);
		}
		label.addClassName(CSSConstants.BUTTON_TEXT);

		// add span for primary icon
		if (primary != null) {
			button.element("span")
					.addClassName(CSSConstants.BUTTON_ICON_PRIMARY, CSSConstants.ICON, primary)
					.moveBefore(label);
		}
		// add span for secondary icon
		if (secondary != null) {
			button.element("span")
					.addClassName(CSSConstants.BUTTON_ICON_SECONDARY, CSSConstants.ICON, secondary)
					.moveAfter(label);
		}
	}

	/**
	 * Looks for text node inside the specified element and wraps it with {@code 'span'} element.
	 *
	 * @param button element to find text for, not {@code null}
	 * @return {@code 'span'} wrapper element around founded text or {@code null} if not found
	 */
	private Element getText(final Element button) {
		for (Node node : button.getChildren()) {
			if (node instanceof Text) {
				// return wrapped with span text
				return node.wrap("span");
			}
		}
		return null;
	}
}
