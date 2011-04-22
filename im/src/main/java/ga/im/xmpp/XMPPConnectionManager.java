package ga.im.xmpp;

import ga.im.SecurityMode;
import ga.im.xmpp.packet.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class XMPPConnectionManager implements PacketListener {
	private final XMPPConnectionConfiguration configuration;
	private final XMPPConnection connection;

	private boolean connected;
	private Socket socket;

	XMPPConnectionManager(XMPPConnection connection) {
		assert connection != null;

		this.connection = connection;
		configuration = connection.getConfiguration();
	}

	boolean isConnected() {
		return connected;
	}

	Socket getSocket() {
		return socket;
	}

	void connect() {
		if (connected) {
			throw new XMPPException("Already connected to server.");
		}
		try {
			socket = new Socket(configuration.getHost(), configuration.getPort());
			connection.start();
		} catch (Exception e) {
			disconnect();
			throw new XMPPException("Could not connect to " + configuration.getHost() + ":" + configuration.getPort(), e);
		}
	}

	void disconnect() {
		connection.stop();
		connected = false;
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				throw new XMPPException("Could not close socket", e);
			} finally {
				socket = null;
			}
		}
	}

	public void packetReceived(Packet packet) {
		if (packet instanceof OpenStreamPacket) {
			connected = true;
		} else if (packet instanceof CloseStreamPacket) {
			disconnect();
		} else if (packet instanceof FeaturesPacket) {
			final StartTlsPacket tls = ((FeaturesPacket) packet).getStartTls();
			if (tls == null && configuration.getSecurityMode() == SecurityMode.ALWAYS ||
					(tls != null && tls.isRequired() && configuration.getSecurityMode() == SecurityMode.NEVER)) {
				throw new XMPPException("Connection security type not supported");
			}
			if (tls != null && configuration.getSecurityMode() != SecurityMode.NEVER) {
				connection.send(new StartTlsPacket());
			}
		} else if (packet instanceof TlsResultPacket) {
			if (((TlsResultPacket) packet).isSuccess()) {
				startTlsConnection();
			}
		}
	}

	private void startTlsConnection() {
		try {
			connected = false;
			connection.stop();
			final SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new TrustManager[]{new FakeTrustManager()}, null);
			Socket plain = socket;
			socket = context.getSocketFactory().createSocket(plain,
					plain.getInetAddress().getHostName(), plain.getPort(), true);
			socket.setSoTimeout(0);
			socket.setKeepAlive(true);
			((SSLSocket) socket).startHandshake();
			Thread.sleep(1000);
			connection.start();
		} catch (Exception e) {
			throw new XMPPException("Can not start TLS connection", e);
		}
	}
}
