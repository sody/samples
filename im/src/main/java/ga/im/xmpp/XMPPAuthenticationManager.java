package ga.im.xmpp;

import ga.im.xmpp.packet.*;
import org.jivesoftware.smack.util.Base64;

import javax.security.auth.callback.*;
import javax.security.sasl.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class XMPPAuthenticationManager implements PacketListener, CallbackHandler {
	private final XMPPConnectionConfiguration configuration;
	private final XMPPConnection connection;
	private final List<String> supportedMechanisms = new ArrayList<String>();

	private boolean authenticated;
	private String mechanism;
	private SaslClient client;

	XMPPAuthenticationManager(XMPPConnection connection) {
		assert connection != null;

		this.connection = connection;
		configuration = connection.getConfiguration();
		supportedMechanisms.add("PLAIN");
		supportedMechanisms.add("DIGEST-MD5");
	}

	boolean isAuthenticated() {
		return authenticated;
	}

	void login() {
		if (!connection.isConnected()) {
			throw new XMPPException("Not connected to server.");
		}
		if (authenticated) {
			throw new XMPPException("Already logged in to server.");
		}
		try {
			client = Sasl.createSaslClient(new String[]{mechanism},
					configuration.getUser(), "xmpp", configuration.getHost(),
					new HashMap<String, Object>(), this);
			final AuthPacket packet = new AuthPacket(mechanism);
			if (client.hasInitialResponse()) {
				byte[] response = client.evaluateChallenge(new byte[0]);
				final String authenticationText = Base64.encodeBytes(response, Base64.DONT_BREAK_LINES);
				packet.setContent(authenticationText);
			}
			connection.send(packet);
		} catch (SaslException e) {
			logout();
			throw new XMPPException("Could not log on", e);
		}
	}

	void logout() {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void packetReceived(Packet packet) {
		if (packet instanceof FeaturesPacket) {
			final MechanismsPacket mechanisms = ((FeaturesPacket) packet).getMechanisms();
			if (mechanisms != null) {
				mechanism = getPreferredMechanism(mechanisms.getMechanisms());
			}
		} else if (packet instanceof ChallengePacket) {
			final String content = ((ChallengePacket) packet).getContent();
			if (content != null) {
				try {
					final byte[] bytes = client.evaluateChallenge(Base64.decode(content));
					final String authenticationText = Base64.encodeBytes(bytes, Base64.DONT_BREAK_LINES);
					connection.send(new ResponsePacket(authenticationText));
				} catch (SaslException e) {
					throw new XMPPException("Could not log on", e);
				}
			}
		} else if (packet instanceof SaslResultPacket) {
			if (((SaslResultPacket) packet).isSuccess()) {
				try {
					connection.start();
					authenticated = true;
				} catch (Exception e) {
					throw new XMPPException("Could not start stream", e);
				}
			}
		}
	}

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for (Callback callback : callbacks) {
			if (callback instanceof NameCallback) {
				((NameCallback) callback).setName(configuration.getUser());
			} else if (callback instanceof PasswordCallback) {
				((PasswordCallback) callback).setPassword(configuration.getPassword().toCharArray());
			} else if (callback instanceof RealmCallback) {
				((RealmCallback) callback).setText(configuration.getHost());
			} else if (!(callback instanceof RealmChoiceCallback)) {
				throw new UnsupportedCallbackException(callback);
			}
		}
	}

	private String getPreferredMechanism(Collection<String> mechanisms) {
		for (String supportedMechanism : supportedMechanisms) {
			if (mechanisms.contains(supportedMechanism)) {
				return supportedMechanism;
			}
		}
		return null;
	}
}
