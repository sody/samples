package ga.im.xmpp;

import ga.im.ConnectionConfiguration;
import ga.im.SecurityMode;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class XMPPConnectionConfiguration implements ConnectionConfiguration {
	private static final int DEFAULT_PORT = 5222;

	private String host;
	private int port = DEFAULT_PORT;
	private SecurityMode securityMode = SecurityMode.DEFAULT;

	private String user;
	private String domain;
	private String resource;
	private String password;


	public XMPPConnectionConfiguration() {
		this(null);
	}

	public XMPPConnectionConfiguration(String host) {
		this.host = host;
	}

	public XMPPConnectionConfiguration(String host, int port) {
		this(host);
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public SecurityMode getSecurityMode() {
		return securityMode;
	}

	public void setSecurityMode(SecurityMode securityMode) {
		this.securityMode = securityMode;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
