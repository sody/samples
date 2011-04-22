package ga.im.xmpp.parser;

import ga.im.xmpp.Packet;
import ga.im.xmpp.XMPPConstants;
import ga.im.xmpp.packet.StartTlsPacket;
import org.xmlpull.v1.XmlPullParser;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class StartTlsParser extends AbstractPacketParser {
	public String getElementName() {
		return StartTlsPacket.START_TLS_TAG;
	}

	@Override
	protected String getElementNamespace() {
		return XMPPConstants.TLS_NAMESPACE;
	}

	public Packet parse(XmlPullParser parser) throws Exception {
		final StartTlsPacket result = new StartTlsPacket();

		boolean done = false;
		while (!done) {
			final int eventType = parser.next();
			final String elementName = parser.getName();
			switch (eventType) {
				case XmlPullParser.END_TAG:
					if (StartTlsPacket.START_TLS_TAG.equals(elementName)) {
						done = true;
					} else if (StartTlsPacket.REQUIRED_TAG.equals(elementName)) {
						result.setRequired(true);
					}
					break;
			}
		}
		return result;
	}
}
