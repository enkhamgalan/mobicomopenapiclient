package mn.mobicom.oauth2;

public class OAuthApplicationException  extends OAuthException{
	/**
	 * Програм алдаатай үед буцна
	 */
	private static final long serialVersionUID = -5991537236070905393L;

	public OAuthApplicationException() {

	}

	public OAuthApplicationException(String msg) {
		super(msg);
	}

	public OAuthApplicationException(String msg, Throwable t) {
		super(msg, t);
	}
}
