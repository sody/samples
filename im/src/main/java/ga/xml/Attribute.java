package ga.xml;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class Attribute implements Node {
	private final String namespace;
	private final String name;
	private final String value;

	Attribute(String name, String value) {
		this(null, name, value);
	}

	Attribute(String namespace, String name, String value) {
		this.namespace = namespace;
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public String toXml() {
		final StringBuilder builder = new StringBuilder(" ");
		if (namespace != null) {
			builder.append(namespace).append(":");
		}
		builder.append(name);
		builder.append("=\"").append(value).append("\"");
		return builder.toString();
	}
}
