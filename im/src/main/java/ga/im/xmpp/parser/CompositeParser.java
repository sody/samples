package ga.im.xmpp.parser;

import ga.im.xmpp.Packet;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public abstract class CompositeParser extends AbstractPacketParser {
	private final List<PacketParser> parsers = new ArrayList<PacketParser>();

	protected void registerParser(PacketParser parser) {
		parsers.add(parser);
	}

	protected Packet parsePacket(XmlPullParser parser) throws Exception {
		final PacketParser packetParser = getPacketParser(parser);
		return packetParser != null ? packetParser.parse(parser) : null;
	}

	private PacketParser getPacketParser(XmlPullParser parser) {
		for (PacketParser packetParser : parsers) {
			if (packetParser.supports(parser)) {
				return packetParser;
			}
		}
		return null;
	}
}
