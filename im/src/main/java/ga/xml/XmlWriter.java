package ga.xml;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class XmlWriter {
	private Element rootElement;
	private Element currentElement;

	public Element getElement() {
		return currentElement;
	}

	public Element element(String name, String... attributes) {
		return elementNS(null, name, attributes);
	}

	public Element elementNS(String namespace, String name, String... attributes) {
		if (currentElement == null) {
			rootElement = new Element(namespace, name);
			rootElement.attributes(attributes);
			currentElement = rootElement;
		} else {
			currentElement = currentElement.elementNS(namespace, name, attributes);
		}
		return currentElement;
	}

	public Element attribute(String name, String value) {
		return attribute(null, name, value);
	}

	public Element attribute(String namespace, String name, String value) {
		if (currentElement != null) {
			currentElement.attribute(namespace, name, value);
		}
		return currentElement;
	}

	public Element attributes(String... attributes) {
		if (currentElement != null) {
			currentElement.attributes(attributes);
		}
		return currentElement;
	}

	public Element text(String text) {
		if (currentElement != null) {
			currentElement.text(text);
		}
		return currentElement;
	}

	public Element end() {
		currentElement = currentElement.end();
		if (currentElement == null) {
			currentElement = rootElement;
		}
		return currentElement;
	}

	@Override
	public String toString() {
		return rootElement != null ? rootElement.toXml() : "";
	}
}
