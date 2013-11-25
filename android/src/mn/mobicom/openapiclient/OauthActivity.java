package mn.mobicom.openapiclient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import mn.mobicom.oauth2.OAuthAuthorizationException;
import mn.mobicom.oauth2.OAuthClient;
import mn.mobicom.oauth2.OAuthException;
import mn.mobicom.oauth2.OAuthTokenException;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * OpenAPI руу холбогдох класс.
 * 
 * @author MobiCom
 * 
 */
public class OauthActivity extends Activity {

	private static final String TAG = "OAUTH";
	public static String refresh_token = null;
	public static String access_token = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		SharedPreferences settings = getSharedPreferences(
				Constants.OAUTH_CALLBACK_SCHEME, 0);
		OauthActivity.refresh_token = settings.getString("refresh_token", null);
		OauthActivity.access_token = settings.getString("access_token", null);
		setContentView(R.layout.activity_oauth);
		// message илгээх товч
		Button button = (Button) findViewById(R.id.sendButton);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String phone = ((EditText) findViewById(R.id.phoneNumber))
						.getText().toString();
				String text = ((EditText) findViewById(R.id.messageField))
						.getText().toString();
				String content = "text=#text&to=#phone";
				try {
					content = content.replace("#text",
							URLEncoder.encode(text, "UTF-8")).replace("#phone",
							URLEncoder.encode(phone, "UTF-8"));
				} catch (UnsupportedEncodingException e2) {
				}
				while (true) {
					try {
						//sms илгээж байна
						String res = OAuthClient
								.useResource(Constants.URL_SMS_SEND + "?"
										+ content);
						Toast.makeText(getApplicationContext(), res,
								Toast.LENGTH_LONG).show();
						break;
					} catch (OAuthException e) {
						if (e instanceof OAuthTokenException) {
							try {
								OAuthClient.refreshToken();
								continue;
							} catch (OAuthException e1) {
								e = e1;
							}
						}
						handleOAuthException(e);
						break;
					}
				}
			}
		});
		// хэрэглэгч logout хийх товч
		Button logout = (Button) findViewById(R.id.logout);
		logout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SharedPreferences settings = getSharedPreferences(
						Constants.OAUTH_CALLBACK_SCHEME, 0);
				// refresh token, access token-ыг устгана
				settings.edit().clear().commit();
				startActivity(new Intent(OauthActivity.this, MainActivity.class));
				finish();
			}
		});
		Intent intent = getIntent();
		if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			Uri uri = intent.getData();
			Log.i(TAG, "URI=" + uri.toString());
			// OpenAPI-с redirect хийж authorization кодыг илгээсэн бол
			String code = uri.getQueryParameter("code");
			Log.i(TAG, "CODE=" + code);
			if (code != null) {
				try {
					OAuthClient.getTokens(code);
				} catch (OAuthException e) {
					handleOAuthException(e);
				}
			}
			// OpenAPI-с redirect хийж ямар нэг алдаа буцсан бол
			String error = uri.getQueryParameter("error");
			if (error != null) {
				handleOAuthException(new OAuthAuthorizationException(
						"Хүсэлтийг биелүүлэхэд алдаа гарлаа. " + error));
			}
		}
		return;
	}

	/*
	 * Exception-с хамаарч юу хийхийг шийдэнэ
	 * 
	 * @param e OAuthException
	 */
	private void handleOAuthException(OAuthException e) {
		Toast.makeText(getApplicationContext(), e.getMessage(),
				Toast.LENGTH_LONG).show();
		if (e instanceof OAuthAuthorizationException) {
			
			OAuthClient.refresh_token = null;
			OAuthClient.access_token = null;
			startActivity(new Intent(this, MainActivity.class));
		}
	}

	@Override
	public void onBackPressed() {
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.oauth, menu);
		return true;
	}
}
