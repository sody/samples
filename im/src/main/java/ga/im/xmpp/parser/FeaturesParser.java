package ga.im.xmpp.parser;

import ga.im.xmpp.Packet;
import ga.im.xmpp.packet.CompressionPacket;
import ga.im.xmpp.packet.FeaturesPacket;
import ga.im.xmpp.packet.MechanismsPacket;
import ga.im.xmpp.packet.StartTlsPacket;
import org.xmlpull.v1.XmlPullParser;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class FeaturesParser extends CompositeParser {

	public FeaturesParser() {
		registerParser(new StartTlsParser());
		registerParser(new MechanismsParser());
		registerParser(new CompressionParser());
	}

	public String getElementName() {
		return FeaturesPacket.FEATURES_TAG;
	}

	public Packet parse(XmlPullParser parser) throws Exception {
		final FeaturesPacket result = new FeaturesPacket();

		boolean done = false;
		while (!done) {
			final int eventType = parser.next();
			final String elementName = parser.getName();
			switch (eventType) {
				case XmlPullParser.START_TAG:
					final Packet packet = parsePacket(parser);
					if (packet != null) {
						if (packet instanceof StartTlsPacket) {
							result.setStartTls((StartTlsPacket) packet);
						} else if (packet instanceof MechanismsPacket) {
							result.setMechanisms((MechanismsPacket) packet);
						} else if (packet instanceof CompressionPacket) {
							result.setCompression((CompressionPacket) packet);
						}
					} else {
						if (FeaturesPacket.REGISTER_TAG.equals(elementName)) {
							result.setRegisterSupported(true);
						} else if (FeaturesPacket.SESSION_TAG.equals(elementName)) {
							result.setSessionSupported(true);
						} else if (FeaturesPacket.BIND_TAG.equals(elementName)) {
							result.setBindRequired(true);
						}
					}
					break;
				case XmlPullParser.END_TAG:
					if (FeaturesPacket.FEATURES_TAG.equals(parser.getName())) {
						done = true;
					}
					break;
			}
		}
		return result;
	}
}
