package ga.im.xmpp.parser;

import ga.im.xmpp.Packet;
import ga.im.xmpp.packet.OpenStreamPacket;
import org.xmlpull.v1.XmlPullParser;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class OpenStreamParser extends AbstractPacketParser {
	public String getElementName() {
		return OpenStreamPacket.STREAM_TAG;
	}

	public Packet parse(XmlPullParser parser) throws Exception {
		final String id = parser.getAttributeValue("", OpenStreamPacket.ID_ATTRIBUTE);
		final String from = parser.getAttributeValue("", OpenStreamPacket.FROM_ATTRIBUTE);
		final String version = parser.getAttributeValue("", OpenStreamPacket.VERSION_ATTRIBUTE);
		final String lang = parser.getAttributeValue(OpenStreamPacket.XML_PREFIX, OpenStreamPacket.LANG_ATTRIBUTE);
		final OpenStreamPacket packet = new OpenStreamPacket();
		packet.setFrom(from);
		packet.setId(id);
		packet.setLang(lang);
		packet.setVersion(version);
		return packet;
	}
}
