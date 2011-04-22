package ga.im.xmpp.packet;

import ga.im.xmpp.Packet;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class GenericPacket implements Packet {
	private String from;
	private String to;
	private String id;
	private String lang;

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

	public String serialize() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
