package ga.im.xmpp.parser;

import ga.im.xmpp.Packet;
import ga.im.xmpp.packet.ChallengePacket;
import org.xmlpull.v1.XmlPullParser;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class ChallengeParser extends AbstractPacketParser {
	public String getElementName() {
		return ChallengePacket.CHALLENGE_TAG;
	}

	public Packet parse(XmlPullParser parser) throws Exception {
		final ChallengePacket result = new ChallengePacket();
		result.setContent(parser.nextText());

		boolean done = false;
		while (!done) {
			final int eventType = parser.next();
			final String elementName = parser.getName();
			switch (eventType) {
				case XmlPullParser.END_TAG:
					if (ChallengePacket.CHALLENGE_TAG.equals(elementName)) {
						done = true;
					}
					break;
			}
		}
		return result;
	}
}
