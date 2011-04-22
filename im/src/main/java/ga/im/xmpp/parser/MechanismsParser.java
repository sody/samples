package ga.im.xmpp.parser;

import ga.im.xmpp.Packet;
import ga.im.xmpp.XMPPConstants;
import ga.im.xmpp.packet.MechanismsPacket;
import org.xmlpull.v1.XmlPullParser;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class MechanismsParser extends AbstractPacketParser {
	public String getElementName() {
		return MechanismsPacket.MECHANISMS_TAG;
	}

	@Override
	protected String getElementNamespace() {
		return XMPPConstants.SASL_NAMESPACE;
	}

	public Packet parse(XmlPullParser parser) throws Exception {
		final MechanismsPacket result = new MechanismsPacket();
		boolean done = false;
		while (!done) {
			final int eventType = parser.next();
			final String elementName = parser.getName();
			switch (eventType) {
				case XmlPullParser.START_TAG:
					if (MechanismsPacket.MECHANISM_TAG.equals(elementName)) {
						result.addMechanism(parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					if (MechanismsPacket.MECHANISMS_TAG.equals(parser.getName())) {
						done = true;
					}
					break;
			}
		}
		return result;
	}
}
