package ga.im.xmpp.packet;

import ga.im.xmpp.Packet;
import ga.im.xmpp.XMPPConstants;
import ga.xml.XmlWriter;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class ChallengePacket implements Packet {
	public static final String CHALLENGE_TAG = "challenge";

	private String content;

	public ChallengePacket() {
	}

	public ChallengePacket(String content) {
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
		writer.element(CHALLENGE_TAG);
		writer.attribute(XMPPConstants.NAMESPACE_PREFIX, XMPPConstants.SASL_NAMESPACE);
		if (content != null) {
			writer.text(content);
		}
		writer.end();
		return writer.toString();
	}
}
