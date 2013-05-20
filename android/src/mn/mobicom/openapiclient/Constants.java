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
	public static final String CLIENT_ID = "3fd1c68e834e7936";
	/**
	 * Бүртгүүлсэн хөгжүүлэгчийн нууц үг.
	 */
	public static final String CLIENT_SECRET = "12423671-e9a9-437b-9efb-aed4184699c8";

	/**
	 * Ашиглаж болох үйлчилгээний нөөцүүд. Дуут шуудан нээх.
	 */
	public static final String SCOPE_VOICEMAILON = "http://api.mobicom.mn:8080/OpenApiResourceCenter/resource/voicemail/on";
	/**
	 * Ашиглаж болох үйлчилгээний нөөцүүд. Дуут шуудан хаах.
	 */
	public static final String SCOPE_VOICEMAILOFF = "http://api.mobicom.mn:8080/OpenApiResourceCenter/resource/voicemail/off";

	/**
	 * Хэрэглэгч нэвтрэх URL.
	 */
	public static final String REQUEST_URL = "http://api.mobicom.mn:8080/OpenApiAuthorizer/endpoint/authz";
	/**
	 * OpenAPI-с token авах URL.
	 */
	public static final String ACCESS_URL = "http://api.mobicom.mn:8080/OpenApiTokenizer/endpoint/token";
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
