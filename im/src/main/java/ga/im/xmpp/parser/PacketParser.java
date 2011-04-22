package ga.im.xmpp.parser;

import ga.im.xmpp.Packet;
import org.xmlpull.v1.XmlPullParser;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public interface PacketParser {

	boolean supports(XmlPullParser parser);

	Packet parse(XmlPullParser parser) throws Exception;

}
