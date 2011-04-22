package ga.im.xmpp;

import ga.im.ConnectionSupport;
import ga.im.xmpp.packet.CloseStreamPacket;
import ga.im.xmpp.parser.*;
import org.xmlpull.mxp1.MXParser;
import org.xmlpull.v1.XmlPullParser;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class XMPPReader extends ConnectionSupport {
	private List<PacketParser> packetParsers = new ArrayList<PacketParser>();
	private XmlPullParser parser;
	private XMPPConnection connection;

	public XMPPReader(XMPPConnection connection) {
		super(connection, "XMPPReader");
		this.connection = connection;
		registerParser(new OpenStreamParser());
		registerParser(new FeaturesParser());
		registerParser(new TlsResultParser(true));
		registerParser(new TlsResultParser(false));
		registerParser(new ChallengeParser());
		registerParser(new SaslResultParser(true));
		registerParser(new SaslResultParser(false));
	}

	@Override
	protected void init(Socket socket) throws Exception {
		parser = new MXParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
		parser.setInput(createReader(socket));
	}

	@Override
	protected void process() throws Exception {
		final int eventType = parser.next();
		switch (eventType) {
			case XmlPullParser.START_TAG:
				final PacketParser packetParser = getPacketParser(parser);
				final Packet packet = packetParser.parse(parser);
				processPacket(packet);
				break;
			case XmlPullParser.END_TAG:
				if (CloseStreamPacket.STREAM_TAG.equals(parser.getName())) {
					processPacket(new CloseStreamPacket());
				}
				break;
		}
	}

	private void processPacket(Packet packet) {
		if (packet != null) {
			System.out.println("[READER] " + packet.serialize());
			connection.packetReceived(packet);
		}
	}

	private void registerParser(PacketParser parser) {
		packetParsers.add(parser);
	}

	private PacketParser getPacketParser(XmlPullParser parser) {
		for (PacketParser packetParser : packetParsers) {
			if (packetParser.supports(parser)) {
				return packetParser;
			}
		}
		return null;
	}
}
