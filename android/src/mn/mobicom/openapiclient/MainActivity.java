package mn.mobicom.openapiclient;

import mn.mobicom.oauth2.OAuthClient;
import mn.mobicom.oauth2.OAuthException;
import mn.mobicom.oauth2.TokenListener;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * Үндсэн класс.
 * 
 * @author MobiCom
 * 
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final SharedPreferences settings = getSharedPreferences(
				Constants.OAUTH_CALLBACK_SCHEME, 0);
		OAuthClient.client_id = Constants.CLIENT_ID;
		OAuthClient.client_secret = Constants.CLIENT_SECRET;
		OAuthClient.redirect_uri = Constants.OAUTH_CALLBACK_URL;
		OAuthClient.tokenListener = new TokenListener() {

			@Override
			public void refreshToken(String refreshToken) {
				// refresh token-g хадгалаж байна
				settings.edit().putString("refresh_token", refreshToken)
						.commit();
			}

			@Override
			public void accessToken(String accessToken) {
				// access token-g хадгалаж байна
				settings.edit().putString("access_token", accessToken).commit();
			}
		};
		String rt = settings.getString("refresh_token", null);
		// refresh token хадгалагдсан бол
		if (rt != null) {

			OAuthClient.refresh_token = rt;
			startActivity(new Intent(this, OauthActivity.class));
			finish();
			return;
		}
		// Нэвтрэх товчийг дарангуут хэрэглэгчийн нэвтрэх вэб броузерийг
		// ачааллана.
		Button login = (Button) findViewById(R.id.changepassword);
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				userLogin();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Хэрэглэгчийн нэвтрэлтийг хийх. Хэрэглэгчийн нэвтрэлтийг вэб броузер дээр
	 * хийлгэнэ.
	 */
	public void userLogin() {
		String url;
		try {
			url = OAuthClient.authorize(Constants.SCOPE_SMS_SEND);
			Intent intent = null;
			if (WebViewActivity.class.isAnnotationPresent(Deprecated.class)) {
				intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			} else {
				intent = new Intent(this, WebViewActivity.class);
				intent.setData(Uri.parse(url));
			}
			startActivity(intent);// Хөгжүүлэгчийн хүсэлтийг шинэ вэб броузер
									// нээж байна.
			finish();
		} catch (OAuthException e) {
			Toast.makeText(getApplicationContext(), e.getMessage(),
					Toast.LENGTH_LONG).show();
		}
	}
}
