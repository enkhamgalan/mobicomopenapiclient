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
	public static final String CLIENT_ID = "3fe5cf7d2fbc085b";
	/**
	 * Бүртгүүлсэн хөгжүүлэгчийн нууц үг.
	 */
	public static final String CLIENT_SECRET = "293688b6-71c5-4968-8a8c-78ed12e74dcc";

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
}