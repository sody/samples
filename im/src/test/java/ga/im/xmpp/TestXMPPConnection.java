package ga.im.xmpp;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class TestXMPPConnection {

	public static void main(String[] args) throws InterruptedException {
		XMPPConnection connection = new XMPPConnection();
		connection.getConfiguration().setHost("jabber.ru");
		connection.getConfiguration().setUser("sody");
		connection.getConfiguration().setPassword("phuene");
		connection.connect();//todo: add pause until connection established
		Thread.sleep(10000);
		connection.login();//todo: add pause until login result received
		Thread.sleep(50000);
		connection.disconnect();
	}
}
