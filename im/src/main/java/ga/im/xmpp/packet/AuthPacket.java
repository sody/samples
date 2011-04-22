package ga.im.xmpp.packet;

import ga.im.xmpp.Packet;
import ga.im.xmpp.XMPPConstants;
import ga.xml.XmlWriter;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class AuthPacket implements Packet {
	public static final String AUTH_TAG = "auth";
	public static final String MECHANISM_ATTRIBUTE = "mechanism";

	private String mechanism;
	private String content;

	public AuthPacket() {
	}

	public AuthPacket(String mechanism) {
		this.mechanism = mechanism;
	}

	public String getMechanism() {
		return mechanism;
	}

	public void setMechanism(String mechanism) {
		this.mechanism = mechanism;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String serialize() {
		final XmlWriter writer = new XmlWriter();
		writer.element(AUTH_TAG);
		writer.attribute(XMPPConstants.NAMESPACE_PREFIX, XMPPConstants.SASL_NAMESPACE);
		writer.attribute(MECHANISM_ATTRIBUTE, mechanism);
		if (content != null) {
			writer.text(content);
		}
		writer.end();
		return writer.toString();
	}
}
