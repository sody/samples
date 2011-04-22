package ga.im.xmpp.packet;

import ga.im.xmpp.Packet;
import ga.im.xmpp.XMPPConstants;
import ga.xml.XmlWriter;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class ResponsePacket implements Packet {
	public static final String RESPONSE_TAG = "response";

	private String content;

	public ResponsePacket() {
	}

	public ResponsePacket(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String serialize() {
		final XmlWriter writer = new XmlWriter();
		writer.element(RESPONSE_TAG);
		writer.attribute(XMPPConstants.NAMESPACE_PREFIX, XMPPConstants.SASL_NAMESPACE);
		if (content != null) {
			writer.text(content);
		}
		writer.end();
		return writer.toString();
	}
}
