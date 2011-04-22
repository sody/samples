package ga.im.xmpp.packet;

import ga.im.xmpp.Packet;
import ga.xml.XmlWriter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class CompressionPacket implements Packet {
	public static final String COMPRESSION_TAG = "compression";
	public static final String METHOD_TAG = "method";

	private Collection<String> methods = new ArrayList<String>();

	public Collection<String> getMethods() {
		return methods;
	}

	public void setMethods(Collection<String> methods) {
		this.methods.clear();
		this.methods.addAll(methods);
	}

	public void addMethod(String mechanism) {
		methods.add(mechanism);
	}

	public String serialize() {
		final XmlWriter writer = new XmlWriter();
		writer.element(COMPRESSION_TAG);
		for (String method : methods) {
			writer.element(METHOD_TAG).text(method);
			writer.end();
		}
		writer.end();
		return writer.toString();
	}
}
