package ga.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class Element implements Node {
	private final List<Node> children = new ArrayList<Node>();
	private final List<Node> attributes = new ArrayList<Node>();
	private final Element parent;
	private final String name;
	private final String namespace;

	private boolean closed;

	Element(String namespace, String name) {
		this(null, namespace, name);
	}

	Element(Element parent, String namespace, String name) {
		this.parent = parent;
		this.namespace = namespace;
		this.name = name;
	}

	public Element getParent() {
		return parent;
	}

	public String getName() {
		return name;
	}

	public String getNamespace() {
		return namespace;
	}

	public Element attribute(String name, String value) {
		return attribute(null, name, value);
	}

	public Element attribute(String namespace, String name, String value) {
		attributes.add(new Attribute(namespace, name, value));
		return this;
	}

	public Element attributes(String... attributes) {
		int i = 0;
		while (i < attributes.length) {
			final String name = attributes[i++];
			final String value = attributes[i++];
			attribute(name, value);
		}
		return this;
	}

	public Element element(String name, String... attributes) {
		return elementNS(null, name, attributes);
	}

	public Element elementNS(String namespace, String name, String... attributes) {
		final Element child = new Element(this, namespace, name);
		child.attributes(attributes);
		children.add(child);
		return child;
	}

	public Element text(String text) {
		children.add(new Text(text));
		return this;
	}

	public Element end() {
		closed = true;
		return parent;
	}

	public String toXml() {
		final StringBuilder builder = new StringBuilder("<");
		if (namespace != null) {
			builder.append(namespace).append(":");
		}
		builder.append(name);
		for (Node attribute : attributes) {
			builder.append(attribute.toXml());
		}
		if (!closed) {
			builder.append(">");
		} else {
			if (children.isEmpty()) {
				builder.append("/>");
			} else {
				builder.append(">");
				for (Node child : children) {
					builder.append(child.toXml());
				}
				builder.append("</");
				if (namespace != null) {
					builder.append(namespace).append(":");
				}
				builder.append(name).append(">");
			}
		}
		return builder.toString();
	}
}
