package ga.im.xmpp.packet;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class CloseStreamPacket extends StreamPacket {
	public String serialize() {
		return "</" + STREAM_PREFIX + ":" + STREAM_TAG + ">";
	}
}
