package mn.mobicom.oauth2;

public class OAuthException extends Exception {

	/**
	 * Ямар нэг алдаа гарсан үед
	 */
	private static final long serialVersionUID = 8189277162890528385L;

	public OAuthException() {

	}

	public OAuthException(String msg) {
		super(msg);
	}

	public OAuthException(String msg, Throwable t) {
		super(msg, t);
	}

}
