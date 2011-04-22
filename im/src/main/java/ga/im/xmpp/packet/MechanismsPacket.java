package ga.im.xmpp.packet;

import ga.im.xmpp.Packet;
import ga.im.xmpp.XMPPConstants;
import ga.xml.XmlWriter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class MechanismsPacket implements Packet {
	public static final String MECHANISMS_TAG = "mechanisms";
	public static final String MECHANISM_TAG = "mechanism";

	private Collection<String> mechanisms = new ArrayList<String>();

	public Collection<String> getMechanisms() {
		return mechanisms;
	}

	public void setMechanisms(Collection<String> mechanisms) {
		this.mechanisms.clear();
		this.mechanisms.addAll(mechanisms);
	}

	public void addMechanism(String mechanism) {
		mechanisms.add(mechanism);
	}

	public String serialize() {
		final XmlWriter writer = new XmlWriter();
		writer.element(MECHANISMS_TAG, XMPPConstants.NAMESPACE_PREFIX, XMPPConstants.SASL_NAMESPACE);
		for (String mechanism : mechanisms) {
			writer.element(MECHANISM_TAG).text(mechanism);
			writer.end();
		}
		writer.end();
		return writer.toString();
	}
}
