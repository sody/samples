package ga.im.xmpp.packet;

import ga.im.xmpp.Packet;
import ga.im.xmpp.XMPPConstants;
import ga.xml.XmlWriter;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class FeaturesPacket implements Packet {
	public static final String FEATURES_TAG = "features";
	public static final String REGISTER_TAG = "register";
	public static final String SESSION_TAG = "session";
	public static final String BIND_TAG = "bind";

	private StartTlsPacket startTls;
	private MechanismsPacket mechanisms;
	private CompressionPacket compression;
	private boolean registerSupported;
	private boolean sessionSupported;
	private boolean bindRequired;

	public StartTlsPacket getStartTls() {
		return startTls;
	}

	public void setStartTls(StartTlsPacket startTls) {
		this.startTls = startTls;
	}

	public MechanismsPacket getMechanisms() {
		return mechanisms;
	}

	public void setMechanisms(MechanismsPacket mechanisms) {
		this.mechanisms = mechanisms;
	}

	public CompressionPacket getCompression() {
		return compression;
	}

	public void setCompression(CompressionPacket compression) {
		this.compression = compression;
	}

	public boolean isRegisterSupported() {
		return registerSupported;
	}

	public void setRegisterSupported(boolean registerSupported) {
		this.registerSupported = registerSupported;
	}

	public boolean isSessionSupported() {
		return sessionSupported;
	}

	public void setSessionSupported(boolean sessionSupported) {
		this.sessionSupported = sessionSupported;
	}

	public boolean isBindRequired() {
		return bindRequired;
	}

	public void setBindRequired(boolean bindRequired) {
		this.bindRequired = bindRequired;
	}

	public String serialize() {
		final XmlWriter writer = new XmlWriter();
		writer.elementNS(StreamPacket.STREAM_PREFIX, FEATURES_TAG);
		if (startTls != null) {
			writer.text(startTls.serialize());
		}
		if (mechanisms != null) {
			writer.text(mechanisms.serialize());
		}
		if (compression != null) {
			writer.text(compression.serialize());
		}
		if (registerSupported) {
			writer.element(REGISTER_TAG, XMPPConstants.NAMESPACE_PREFIX, XMPPConstants.IQ_REGISTER_NAMESPACE);
			writer.end();
		}
		if (sessionSupported) {
			writer.element(SESSION_TAG, XMPPConstants.NAMESPACE_PREFIX, XMPPConstants.SESSION_NAMESPACE);
			writer.end();
		}
		if (bindRequired) {
			writer.element(BIND_TAG, XMPPConstants.NAMESPACE_PREFIX, XMPPConstants.BIND_NAMESPACE);
			writer.end();
		}
		writer.end();
		return writer.toString();
	}
}
