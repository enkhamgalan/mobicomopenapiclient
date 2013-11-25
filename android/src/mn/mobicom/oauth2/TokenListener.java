package mn.mobicom.oauth2;

public interface TokenListener {
	/*
	 * @param accessToken шинэ access_token access token шинэчилэгдэхэд
	 * дуудагдах функц
	 */
	public void accessToken(String accessToken);

	/*
	 * @param refreshToken шинэ refresh_token refresh token шинэчилэгдэхэд
	 * дуудагдах функц
	 */
	public void refreshToken(String refreshToken);
}
