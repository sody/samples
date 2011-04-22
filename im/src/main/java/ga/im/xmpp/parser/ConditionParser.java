package ga.im.xmpp.parser;

import ga.im.xmpp.Packet;
import ga.im.xmpp.packet.ConditionPacket;
import org.xmlpull.v1.XmlPullParser;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class ConditionParser extends AbstractPacketParser {
	@Override
	public boolean supports(XmlPullParser parser) {
		return true;
	}

	@Override
	protected String getElementName() {
		return null;
	}

	public Packet parse(XmlPullParser parser) throws Exception {
		final ConditionPacket result = new ConditionPacket(parser.getName());
		boolean done = false;
		while (!done) {
			final int eventType = parser.next();
			switch (eventType) {
				case XmlPullParser.END_TAG:
					if (result.getCondition().equals(parser.getName())) {
						done = true;
					}
					break;
			}
		}
		return result;
	}
}
