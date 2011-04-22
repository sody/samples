package ga.im.xmpp.packet;

import ga.im.xmpp.Packet;
import ga.xml.XmlWriter;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class ConditionPacket implements Packet {
	private final String condition;

	public ConditionPacket(String condition) {
		this.condition = condition;
	}

	public String getCondition() {
		return condition;
	}

	public String serialize() {
		final XmlWriter writer = new XmlWriter();
		writer.element(condition).end();
		return writer.toString();
	}
}
