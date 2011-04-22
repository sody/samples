package ga.im.xmpp.parser;

import ga.im.xmpp.Packet;
import ga.im.xmpp.XMPPConstants;
import ga.im.xmpp.packet.TlsResultPacket;
import org.xmlpull.v1.XmlPullParser;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class TlsResultParser extends AbstractPacketParser {
	public final boolean success;

	public TlsResultParser(boolean success) {
		this.success = success;
	}

	public String getElementName() {
		return success ? TlsResultPacket.PROCEED_TAG : TlsResultPacket.FAILURE_TAG;
	}

	@Override
	protected String getElementNamespace() {
		return XMPPConstants.TLS_NAMESPACE;
	}

	public Packet parse(XmlPullParser parser) throws Exception {
		final TlsResultPacket result = new TlsResultPacket();
		result.setSuccess(success);

		boolean done = false;
		while (!done) {
			final int eventType = parser.next();
			final String elementName = parser.getName();
			switch (eventType) {
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
