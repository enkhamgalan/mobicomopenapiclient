package mn.mobicom.openapiclient;

/**
 * Хөгжүүлэгчийн тогтмолууд.
 * 
 * @author MobiCom
 * 
 */
public class Constants {

	/**
	 * Вэб-д бүртгүүлсэн хөгжүүлэгчийн дугаар.
	 */
	public static final String CLIENT_ID = "3fea78052825a63f";
	/**
	 * Бүртгүүлсэн хөгжүүлэгчийн нууц үг.
	 */
	public static final String CLIENT_SECRET = "38eb9920-d5c1-484b-ac47-8611b289685a";

	/**
	 * Ашиглаж болох үйлчилгээний нөөцүүд. Message ilgeeh.
	 */
	public static final String SCOPE_SMS_SEND = "sms/send";
	public static final String URL_SMS_SEND = "https://api.mobicom.mn/oauth/resource/sms/v1/send";
	/**
	 * Хэрэглэгч нэвтрэх URL.
	 */
	public static final String REQUEST_URL = "https://api.mobicom.mn/oauth/authorization/auth";
	/**
	 * OpenAPI-с token авах URL.
	 */
	public static final String ACCESS_URL = "https://api.mobicom.mn/oauth/authorization/token";
	/**
	 * Хэрэглэгчийн нэвтрэлтийн хариуг хүлээж авах програмын хэсэг.
	 */
	public static final String OAUTH_CALLBACK_SCHEME = "mn.mobicom.openapiclient";
	/**
	 * Хэрэглэгчийн нэвтрэлтийн хариуг тодорхойлох үг.
	 */
	public static final String OAUTH_CALLBACK_HOST = "oauthresponse";
	/**
	 * Хэрэглэгч нэвтрэлтийн хариуг буцааж авах URL.
	 */
	public static final String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME
			+ "://" + OAUTH_CALLBACK_HOST;
	
	public static final String LOGOUT_URL = "https://accounts.mobicom.mn/logout.html#app";
	public static final String LOGOUTOK_URL = "https://accounts.mobicom.mn/login.html#app";
	public static final String CP_URL = "https://accounts.mobicom.mn/security.html#app";
	public static final String CPOK_URL = "https://accounts.mobicom.mn/account.html#app";
}