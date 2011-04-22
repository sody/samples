package ga.im.xmpp.parser;

import org.xmlpull.v1.XmlPullParser;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public abstract class AbstractPacketParser implements PacketParser {
	public boolean supports(XmlPullParser parser) {
		final String elementName = parser.getName();
		return getElementName().equalsIgnoreCase(elementName) &&
				(getElementNamespace() == null || getElementNamespace().equals(parser.getNamespace()));
	}

	protected String getElementNamespace() {
		return null;
	}

	protected abstract String getElementName();
}
