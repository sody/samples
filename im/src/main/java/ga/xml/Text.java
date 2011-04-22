package ga.xml;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class Text implements Node {
	private final String text;

	Text(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public String toXml() {
		return text != null ? text : "";
	}
}
