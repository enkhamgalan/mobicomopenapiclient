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
	public static final String CLIENT_ID = "ТАНЫ АПП АйДи";
	/**
	 * Бүртгүүлсэн хөгжүүлэгчийн нууц үг.
	 */
	public static final String CLIENT_SECRET = "ТАНЫ АПП КЕЙ";

	/**
	 * Ашиглаж болох үйлчилгээний нөөцүүд. Дуут шуудан нээх.
	 */
	public static final String SCOPE_VOICEMAILON = "http://api.mobicom.mn/oauth/v1/resource/voicemail/on";
	/**
	 * Ашиглаж болох үйлчилгээний нөөцүүд. Дуут шуудан хаах.
	 */
	public static final String SCOPE_VOICEMAILOFF = "http://api.mobicom.mn/oauth/v1/resource/voicemail/off";

	/**
	 * Хэрэглэгч нэвтрэх URL.
	 */
	public static final String REQUEST_URL = "http://api.mobicom.mn/oauth/v1/authorizer/authz";
	/**
	 * OpenAPI-с token авах URL.
	 */
	public static final String ACCESS_URL = "http://api.mobicom.mn/oauth/v1/tokenizer/token";
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