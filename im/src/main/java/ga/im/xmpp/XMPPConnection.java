package ga.im.xmpp;

import ga.im.Connection;
import ga.im.xmpp.packet.CloseStreamPacket;
import ga.im.xmpp.packet.OpenStreamPacket;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class XMPPConnection implements Connection<XMPPConnectionConfiguration>, PacketListener {
	private final XMPPReader reader = new XMPPReader(this);
	private final XMPPWriter writer = new XMPPWriter(this);

	private final XMPPConnectionConfiguration configuration;
	private final XMPPConnectionManager connectionManager;
	private final XMPPAuthenticationManager authenticationManager;

	public XMPPConnection() {
		this(new XMPPConnectionConfiguration());
	}

	public XMPPConnection(XMPPConnectionConfiguration configuration) {
		this.configuration = configuration;
		connectionManager = new XMPPConnectionManager(this);
		authenticationManager = new XMPPAuthenticationManager(this);
	}

	public XMPPConnectionConfiguration getConfiguration() {
		return configuration;
	}

	public boolean isConnected() {
		return connectionManager.isConnected();
	}

	public void connect() {
		connectionManager.connect();
	}

	public void disconnect() {
		connectionManager.disconnect();
	}

	public boolean isAuthenticated() {
		return authenticationManager.isAuthenticated();
	}

	public void login() {
		authenticationManager.login();
	}

	public void logout() {
		authenticationManager.logout();
	}

	public void send(Packet packet) {
		writer.addPacket(packet);
	}

	public void packetReceived(Packet packet) {
		connectionManager.packetReceived(packet);
		authenticationManager.packetReceived(packet);
	}

	void start() throws Exception {
		reader.start(connectionManager.getSocket());
		writer.start(connectionManager.getSocket());
		final OpenStreamPacket packet = new OpenStreamPacket();
		packet.setTo(configuration.getHost());
		send(packet);
	}

	void stop() {
		if (isConnected()) {
			send(new CloseStreamPacket());
		}
		reader.stop();
		writer.stop();
	}
}
