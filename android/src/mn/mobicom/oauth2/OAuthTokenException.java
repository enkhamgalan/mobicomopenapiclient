package mn.mobicom.oauth2;

public class OAuthTokenException extends OAuthException {
	/**
	 * Token буруу эсвэл ашиглах боломжгүй болсон үед буцаана
	 */
	private static final long serialVersionUID = 7316819690371973011L;

	public OAuthTokenException() {
	}
	
	public OAuthTokenException(String msg){
		super(msg);
	}
	
	public OAuthTokenException(String msg, Throwable t){
		super(msg, t);
	}
}
