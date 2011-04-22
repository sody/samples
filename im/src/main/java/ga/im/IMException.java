package ga.im;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class IMException extends RuntimeException {

	public IMException() {
	}

	public IMException(String message) {
		super(message);
	}

	public IMException(String message, Throwable cause) {
		super(message, cause);
	}

	public IMException(Throwable cause) {
		super(cause);
	}

}
