package ga.im.xmpp.parser;

import ga.im.xmpp.Packet;
import ga.im.xmpp.XMPPConstants;
import ga.im.xmpp.packet.ConditionPacket;
import ga.im.xmpp.packet.SaslResultPacket;
import org.xmlpull.v1.XmlPullParser;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class SaslResultParser extends CompositeParser {
	public final boolean success;

	public SaslResultParser(boolean success) {
		this.success = success;
		registerParser(new ConditionParser());
	}

	public String getElementName() {
		return success ? SaslResultPacket.SUCCESS_TAG : SaslResultPacket.FAILURE_TAG;
	}

	@Override
	protected String getElementNamespace() {
		return XMPPConstants.SASL_NAMESPACE;
	}

	public Packet parse(XmlPullParser parser) throws Exception {
		final SaslResultPacket result = new SaslResultPacket();
		result.setSuccess(success);

		boolean done = false;
		while (!done) {
			final int eventType = parser.next();
			final String elementName = parser.getName();
			switch (eventType) {
				case XmlPullParser.START_TAG:
					final Packet packet = parsePacket(parser);
					if (packet != null && packet instanceof ConditionPacket) {
						result.setCondition((ConditionPacket) packet);
					}
					break;
				case XmlPullParser.END_TAG:
					if (getElementName().equals(elementName)) {
						done = true;
					}
					break;
			}
		}
		return result;
	}
}
