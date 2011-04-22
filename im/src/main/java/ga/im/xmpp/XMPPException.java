package ga.im.xmpp;

import ga.im.IMException;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class XMPPException extends IMException {
	public XMPPException() {
	}

	public XMPPException(String message) {
		super(message);
	}

	public XMPPException(String message, Throwable cause) {
		super(message, cause);
	}

	public XMPPException(Throwable cause) {
		super(cause);
	}
}
