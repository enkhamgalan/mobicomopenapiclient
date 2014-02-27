package mn.mobicom.oauth2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class OAuthClient {

	public static final String TAG = "OAUTH";

	public static String client_id; // APP_ID
	public static String client_secret; // APP_SECRET
	public static String redirect_uri; // REDIRECT_URI буцах холбоос
	public static String refresh_token; // refresh_token
	public static String access_token; // access_token
	public static TokenListener tokenListener = new TokenListener() {

		@Override
		public void refreshToken(String refreshToken) {
		}

		@Override
		public void accessToken(String accessToken) {
		}
	};

	/*
	 * @param scope ашиглах сэрвисүүд authorizaiton code авах функц
	 * 
	 * @return browser дуудах холбоос
	 * 
	 * @exception OAuthException OAuth 2.0 алдаа гарсан үед
	 * 
	 * @see OAuthException
	 */
	public static String authorize(String... scope) throws OAuthException {
		String authorization = "https://api.mobicom.mn/oauth/authorization/auth?client_id=%s&redirect_uri=%s&response_type=code&scope=%s";
		StringBuilder sc = new StringBuilder();
		for (String s : scope) {
			if (s != null) {
				if (sc.length() != 0)
					sc.append(" ");
				sc.append(s);
			}
		}
		try {
			return String
					.format(authorization,
							client_id == null ? "" : URLEncoder.encode(
									client_id, "UTF-8"),
							redirect_uri == null ? "" : URLEncoder.encode(
									redirect_uri, "UTF-8"), URLEncoder.encode(
									sc.toString(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, "", e);
			throw new OAuthApplicationException("Алдаатай програм байна");
		}
	}

	/*
	 * @param code authorization_code access token болон refresh token авах
	 * функц
	 * 
	 * @exception OAuthException OAuth 2.0 алдаа гарсан үед
	 * 
	 * @see OAuthException
	 */
	public static void getTokens(String code) throws OAuthException {
		String url = "https://api.mobicom.mn/oauth/authorization/token";
		String content = "client_id=%s&client_secret=%s&redirect_uri=%s&grant_type=authorization_code&code=%s";
		try {
			content = String.format(
					content,
					client_id == null ? "" : URLEncoder.encode(client_id,
							"UTF-8"),// ///////
					client_secret == null ? "" : URLEncoder.encode(
							client_secret, "UTF-8"),// /////////
					redirect_uri == null ? "" : URLEncoder.encode(redirect_uri,
							"UTF-8"),// //////////////////
					code == null ? "" : URLEncoder.encode(code, "UTF-8"));
			Map<String, String> header = new HashMap<String, String>();
			header.put("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			HttpResponse response = RequestSender.POST(url + "?" + content, "",
					header);
			if (response.getCode() == 200) {
				JSONObject json = new JSONObject(response.getBody());
				refresh_token = json.getString("refresh_token");
				if (tokenListener != null)
					tokenListener.refreshToken(refresh_token);
				access_token = json.getString("access_token");
				if (tokenListener != null)
					tokenListener.accessToken(access_token);
			} else if ((response.getCode() / 100) == 4) {
				JSONObject json = new JSONObject(response.getBody());
				String error = json.getString("error");
				Log.e(TAG, error);
				if (error != null) {
					if (error.contains("unauthorized_client")) {
						throw new OAuthApplicationException(
								"Алдаатай програм байна");
					} else if (error.contains("invalid_grant")) {
						throw new OAuthAuthorizationException(
								"Ахин нэвтрэх шаардлагатай");
					} else if (error.contains("invalid")) {
						throw new OAuthApplicationException(
								"Алдаатай програм байна");
					} else if (error.contains("unsupported")) {
						throw new OAuthApplicationException(
								"Алдаатай програм байна");
					}
				}
				throw new OAuthException("Та ахин оролдоно уу");
			} else {
				throw new OAuthException("Та интернет холболтоо шалгана уу");
			}
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, "", e);
			throw new OAuthApplicationException("Алдаатай програм байна");
		} catch (JSONException e) {
			Log.e(TAG, "Parsing json response", e);
			throw new OAuthException("Та интернет холболтоо шалгана уу");
		} catch (IOException e) {
			Log.e(TAG, "Sending authorization_code request", e);
			throw new OAuthException("Та интернет холболтоо шалгана уу");
		}
	}

	/*
	 * Token refresh хийх функц
	 * 
	 * @exception OAuthException OAuth 2.0 алдаа гарсан үед
	 * 
	 * @see OAuthException
	 */
	public static void refreshToken() throws OAuthException {
		String url = "https://api.mobicom.mn/oauth/authorization/token";
		String content = "client_id=%s&client_secret=%s&redirect_uri=%s&grant_type=refresh_token&refresh_token=%s";
		try {
			content = String.format(
					content,
					client_id == null ? "" : URLEncoder.encode(client_id,
							"UTF-8"),// ///////
					client_secret == null ? "" : URLEncoder.encode(
							client_secret, "UTF-8"),// /////////
					redirect_uri == null ? "" : URLEncoder.encode(redirect_uri,
							"UTF-8"),// //////////////////
					refresh_token == null ? "" : URLEncoder.encode(
							refresh_token, "UTF-8"));
			Map<String, String> header = new HashMap<String, String>();
			header.put("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			HttpResponse response = RequestSender.POST(url, content, header);
			if (response.getCode() == 200) {
				JSONObject json = new JSONObject(response.getBody());
				access_token = json.getString("access_token");
				if (tokenListener != null)
					tokenListener.accessToken(access_token);
			} else if ((response.getCode() / 100) == 4) {
				JSONObject json = new JSONObject(response.getBody());
				String error = json.getString("error");
				Log.e(TAG, error);
				if (error != null) {
					if (error.contains("unauthorized_client")) {
						throw new OAuthApplicationException(
								"Алдаатай програм байна");
					} else if (error.contains("invalid_grant")) {
						throw new OAuthAuthorizationException(
								"Алдаатай програм байна");
					} else if (error.contains("invalid")) {
						throw new OAuthApplicationException(
								"Алдаатай програм байна");
					} else if (error.contains("unsupported")) {
						throw new OAuthApplicationException(
								"Алдаатай програм байна");
					}
				}
				throw new OAuthException("Та ахин оролдоно уу");
			} else {
				throw new OAuthException("Та интернет холболтоо шалгана уу");
			}
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, "", e);
			throw new OAuthApplicationException("Алдаатай програм байна");
		} catch (JSONException e) {
			Log.e(TAG, "Parsing json response", e);
			throw new OAuthException("Та интернет холболтоо шалгана уу");
		} catch (IOException e) {
			Log.e(TAG, "Sending refresh_token request", e);
			throw new OAuthException("Та интернет холболтоо шалгана уу");
		}
	}

	/*
	 * Token refresh хийх функц
	 * 
	 * @exception OAuthException OAuth 2.0 алдаа гарсан үед
	 * 
	 * @see OAuthException
	 */
	public static String changePassword() {
		String url = "https://accounts.mobicom.mn/security.html";
		return url;
	}
	
	public static String logout() {
		String url = "https://accounts.mobicom.mn/logout.html";
		return url;
	}

	/*
	 * @param url resource-н url
	 * 
	 * @return resource resposne
	 */
	public static String useResource(String url) throws OAuthException {
		Map<String, String> header = new HashMap<String, String>();
		if (refresh_token == null) {
			throw new OAuthAuthorizationException(
					"Refresh token-g авах хэрэгтэй");
		}
		if (access_token == null) {
			throw new OAuthTokenException("Access token-g авах хэрэгтэй");
		}
		header.put("Authorization", "Bearer " + access_token);
		try {
			HttpResponse response = RequestSender.POST(url, "", header);
			if (response.getCode() == 200) {
				return response.getBody();
			} else if ((response.getCode() / 100) == 4) {
				JSONObject json = new JSONObject(response.getBody());
				String error = json.getString("error");
				String error_desc = json.getString("error_description");
				Log.e(TAG, error);
				if (error != null) {
					if (error.contains("insufficient_scope")) {
						throw new OAuthApplicationException(
								"Алдаатай програм байна");
					} else if (error.contains("quota_exceeded")) {
						throw new OAuthException(error_desc);
					} else if (error.contains("invalid_token")
							|| error.contains("expired_token")) {
						throw new OAuthTokenException(
								"Хандалтын хугацаа дууссан");
					} else if (error.contains("invalid")) {
						throw new OAuthApplicationException(
								"Алдаатай програм байна");
					}
				}
				throw new OAuthException("Та ахин оролдоно уу");
			} else {
				throw new OAuthException("Та интернет холболтоо шалгана уу");
			}
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, "", e);
			throw new OAuthApplicationException("Алдаатай програм байна");
		} catch (JSONException e) {
			Log.e(TAG, "Parsing json response", e);
			throw new OAuthException("Та интернет холболтоо шалгана уу");
		} catch (IOException e) {
			Log.e(TAG, "Sending resource request", e);
			throw new OAuthException("Та интернет холболтоо шалгана уу");
		} catch (OAuthException e) {
			throw e;
		} catch (Exception e) {
			Log.e(TAG, "Sending resource request", e);
			throw new OAuthException("Та интернет холболтоо шалгана уу");
		}
	}
}
