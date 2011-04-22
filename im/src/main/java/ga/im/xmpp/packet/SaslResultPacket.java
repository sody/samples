package ga.im.xmpp.packet;

import ga.im.xmpp.Packet;
import ga.im.xmpp.XMPPConstants;
import ga.xml.XmlWriter;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class SaslResultPacket implements Packet {
	public static final String SUCCESS_TAG = "success";
	public static final String FAILURE_TAG = "failure";

	private boolean success;
	private ConditionPacket condition;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ConditionPacket getCondition() {
		return condition;
	}

	public void setCondition(ConditionPacket condition) {
		this.condition = condition;
	}

	public String serialize() {
		final XmlWriter writer = new XmlWriter();
		writer.element(success ? SUCCESS_TAG : FAILURE_TAG);
		writer.attribute(XMPPConstants.NAMESPACE_PREFIX, XMPPConstants.SASL_NAMESPACE);
		if (condition != null) {
			writer.text(condition.serialize());
		}
		writer.end();
		return writer.toString();
	}
}
