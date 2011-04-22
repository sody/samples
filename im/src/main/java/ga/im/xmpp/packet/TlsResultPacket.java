package ga.im.xmpp.packet;

import ga.im.xmpp.Packet;
import ga.im.xmpp.XMPPConstants;
import ga.xml.XmlWriter;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class TlsResultPacket implements Packet {
	public static final String PROCEED_TAG = "proceed";
	public static final String FAILURE_TAG = "failure";

	private boolean success;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String serialize() {
		final XmlWriter writer = new XmlWriter();
		writer.element(success ? PROCEED_TAG : FAILURE_TAG);
		writer.attribute(XMPPConstants.NAMESPACE_PREFIX, XMPPConstants.TLS_NAMESPACE);
		writer.end();
		return writer.toString();
	}
}
