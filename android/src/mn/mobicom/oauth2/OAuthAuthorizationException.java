package mn.mobicom.oauth2;

public class OAuthAuthorizationException  extends OAuthException{
	/**
	 * Ахин login хийх хэрэгтэй үед буцна
	 */
	private static final long serialVersionUID = -5991537236070905393L;

	public OAuthAuthorizationException() {

	}

	public OAuthAuthorizationException(String msg) {
		super(msg);
	}

	public OAuthAuthorizationException(String msg, Throwable t) {
		super(msg, t);
	}
}
