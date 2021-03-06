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
						// sms илгээж байна
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
				String url = Constants.LOGOUT_URL;
				Intent intent = null;
				if (WebViewActivity.class.isAnnotationPresent(Deprecated.class)) {
					intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					startActivity(intent);
				} else {
					intent = new Intent(OauthActivity.this,
							WebViewActivity.class);
					intent.setData(Uri.parse(url));
					startActivity(intent);
					finish();
				}
			}
		});
		// Нууц үг солих
		Button changepassword = (Button) findViewById(R.id.changepassword);
		changepassword.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String url = Constants.CP_URL;
				Intent intent = null;
				if (WebViewActivity.class.isAnnotationPresent(Deprecated.class)) {
					intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					startActivity(intent);
				} else {
					intent = new Intent(OauthActivity.this,
							WebViewActivity.class);
					intent.setData(Uri.parse(url));
					startActivity(intent);
				}
			}
		});
		Intent intent = getIntent();
		if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			Uri uri = intent.getData();
			Log.i(TAG, "URI=" + uri.toString());
			// OpenAPI-с redirect хийж authorization кодыг илгээсэн бол
			String code = uri.getQueryParameter("code");
			Log.i(TAG, "CODE=" + code);
			try {
				if (code != null) {
					OAuthClient.getTokens(code);
				}
				// OpenAPI-с redirect хийж ямар нэг алдаа буцсан бол
				String error = uri.getQueryParameter("error");
				String info = uri.getQueryParameter("info");
				if (error != null) {
					if (error.contains("invalid_scope")) {
						throw new OAuthAuthorizationException(
								"Програмын эрх хүрэлцэхгүй байна");
					} else if (error.contains("unsupported")) {
						throw new OAuthAuthorizationException(
								"Алдаатай програм байна");
					} else if (error.contains("server_error")) {
						throw new OAuthAuthorizationException("Та ахин үзнэ үү");
					} else if (error.contains("cancel")) {
						throw new OAuthAuthorizationException(
								"Хэрэглэгч нэврэхээс татгалзлаа");
					} else if (error.contains("access_denied")) {
						throw new OAuthAuthorizationException(
								"Хэрэглэгч нэврэхээс татгалзлаа");
					} else if (error.contains("logout")) {
						throw new OAuthAuthorizationException("Гарлаа");
					} else {
						throw new OAuthAuthorizationException(
								"Интернет холбоосоо шалгана уу");
					}
				}
				if (info != null) {
					throw new OAuthAuthorizationException(info);
				}
			} catch (OAuthException ex) {
				handleOAuthException(ex);
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
			finish();
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
