package ga.im.xmpp.packet;

import ga.im.xmpp.Packet;
import ga.im.xmpp.XMPPConstants;
import ga.xml.XmlWriter;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class StartTlsPacket implements Packet {
	public static final String START_TLS_TAG = "starttls";
	public static final String REQUIRED_TAG = "required";

	private boolean required;

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String serialize() {
		final XmlWriter writer = new XmlWriter();
		writer.element(START_TLS_TAG);
		writer.attribute(XMPPConstants.NAMESPACE_PREFIX, XMPPConstants.TLS_NAMESPACE);
		if (required) {
			writer.element(REQUIRED_TAG).end();
		}
		writer.end();
		return writer.toString();
	}
}
