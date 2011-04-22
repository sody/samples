package ga.im;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public interface ConnectionConfiguration {

	String getHost();

	void setHost(String host);

	int getPort();

	void setPort(int port);

	SecurityMode getSecurityMode();

	void setSecurityMode(SecurityMode securityMode);

}
