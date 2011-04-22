package ga.im.xmpp.packet;

import ga.im.xmpp.XMPPConstants;
import ga.xml.XmlWriter;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class OpenStreamPacket extends StreamPacket {
	public static final String XML_HEADER = "<?xml version='1.0'?>";
	public static final String XML_PREFIX = "xml";

	public static final String FROM_ATTRIBUTE = "from";
	public static final String TO_ATTRIBUTE = "to";
	public static final String ID_ATTRIBUTE = "id";
	public static final String LANG_ATTRIBUTE = "lang";
	public static final String VERSION_ATTRIBUTE = "version";

	private final boolean server;

	private String from;
	private String to;
	private String id;
	private String lang;
	private String version = "1.0";

	public OpenStreamPacket() {
		this(false);
	}

	public OpenStreamPacket(boolean server) {
		this.server = server;
	}

	public boolean isServer() {
		return server;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String serialize() {
		final XmlWriter writer = new XmlWriter();
		writer.elementNS(STREAM_PREFIX, STREAM_TAG);
		writer.attribute(XMPPConstants.NAMESPACE_PREFIX, STREAM_PREFIX, XMPPConstants.STREAMS_NAMESPACE);
		writer.attribute(XMPPConstants.NAMESPACE_PREFIX, server ? XMPPConstants.SERVER_NAMESPACE : XMPPConstants.CLIENT_NAMESPACE);
		if (from != null) {
			writer.attribute(FROM_ATTRIBUTE, from);
		}
		if (to != null) {
			writer.attribute(TO_ATTRIBUTE, to);
		}
		if (id != null) {
			writer.attribute(ID_ATTRIBUTE, id);
		}
		if (lang != null) {
			writer.attribute(XML_PREFIX, LANG_ATTRIBUTE, lang);
		}
		writer.attribute(VERSION_ATTRIBUTE, version);
		return XML_HEADER + writer.toString();
	}
}
