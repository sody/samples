package ga.im.xmpp.parser;

import ga.im.xmpp.Packet;
import ga.im.xmpp.packet.CompressionPacket;
import org.xmlpull.v1.XmlPullParser;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class CompressionParser extends AbstractPacketParser {
	public String getElementName() {
		return CompressionPacket.COMPRESSION_TAG;
	}

	public Packet parse(XmlPullParser parser) throws Exception {
		final CompressionPacket result = new CompressionPacket();
		boolean done = false;
		while (!done) {
			final int eventType = parser.next();
			final String elementName = parser.getName();
			switch (eventType) {
				case XmlPullParser.START_TAG:
					if (CompressionPacket.METHOD_TAG.equals(elementName)) {
						result.addMethod(parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					if (CompressionPacket.COMPRESSION_TAG.equals(parser.getName())) {
						done = true;
					}
					break;
			}
		}
		return result;
	}
}
