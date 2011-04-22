package ga.im;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public interface Connection<C extends ConnectionConfiguration> {

	C getConfiguration();

	boolean isConnected();

	boolean isAuthenticated();

	void connect();

	void disconnect();

	void login();

	void logout();

}
