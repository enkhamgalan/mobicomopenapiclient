package mn.mobicom.openapiclient;

import java.io.IOException;

import net.smartam.leeloo.client.OAuthClient;
import net.smartam.leeloo.client.URLConnectionClient;
import net.smartam.leeloo.client.request.OAuthClientRequest;
import net.smartam.leeloo.client.response.OAuthAccessTokenResponse;
import net.smartam.leeloo.client.response.OAuthJSONAccessTokenResponse;
import net.smartam.leeloo.common.message.types.GrantType;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

/**
 * OpenAPI руу холбогдох класс.
 * 
 * @author MobiCom
 * 
 */
public class OauthActivity extends Activity {

	private static final String TAG = "OAUTH";

	/**
	 * Хэрэглэгчийн нэвтрэлтийг баталгаажуулсан код.
	 */
	String code = null;
	/**
	 * OpenAPI-с ирэх token. Хэрэглэгчийн үйлчилгээ идэвхжүүлэх хүсэлт илгээхдээ
	 * ашиглана.
	 */
	String token = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_oauth);

		// Үйлчилгээ идэвхжүүлэх товч
		ToggleButton button = (ToggleButton) findViewById(R.id.toggleButton1);
		button.setChecked(true);
		button.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (token != null) {
					sendUserRequest(isChecked);
				} else {
					getToken();
					sendUserRequest(isChecked);
				}
			}
		});

		// Redirect хийсэн хариунаас кодыг салгаж авах
		parseCode();

		// Token авах
		getToken();
	}

	/**
	 * Хэрэглэгчийн хүсэлтийг илгээхэд ашиглах token авах.
	 */
	private void getToken() {
		OAuthClientRequest request = null;
		if (code != null) {
			try {
				request = OAuthClientRequest
						.tokenLocation(Constants.ACCESS_URL)
						.setClientId(Constants.CLIENT_ID)
						.setClientSecret(Constants.CLIENT_SECRET).setCode(code)
						.setGrantType(GrantType.AUTHORIZATION_CODE)
						.setRedirectURI(Constants.OAUTH_CALLBACK_URL)
						.buildBodyMessage();
				OAuthClient client = new OAuthClient(new URLConnectionClient());

				Class<? extends OAuthAccessTokenResponse> cl = OAuthJSONAccessTokenResponse.class;

				OAuthAccessTokenResponse oauthResponse = client.accessToken(
						request, cl);

				token = oauthResponse.getAccessToken();
				Log.i(TAG, "token=" + token);
			} catch (Exception e) {
				Log.e(TAG, "error on token", e);
			}
		} else {
			startActivity(new Intent(this, MainActivity.class));
			finish();
		}
	}

	/**
	 * Хэрэглэгчийн үйлчилгээ идэвхжүүлэх хүсэлтийг илгээх.
	 * 
	 * @param isChecked
	 *            Үйлчилгээг нээх эсвэл хаахыг заана.
	 */
	private void sendUserRequest(boolean isChecked) {
		try {
			String res = RequestSender.send(
					isChecked ? Constants.SCOPE_VOICEMAILON
							: Constants.SCOPE_VOICEMAILOFF, "access_token="
							+ token, null);
			if (res.contains("\"Code\":0")) {
				Toast.makeText(getApplicationContext(),
						"Хүсэлт амжилттай бүртгэгдлээ", Toast.LENGTH_LONG)
						.show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Хүсэлтийг биелүүлэхэд алдаа гарлаа.",
						Toast.LENGTH_LONG).show();
			}
			Log.i(TAG, "RES=" + res);
		} catch (IOException e) {
			Log.i(TAG, "Sending user's request failed.", e);
		}
	}

	/**
	 * Хэрэглэгчийн нэвтрэлтийн хариуг OAUTH_CALLBACK_URL-р авах бөгөөд
	 * хариунаас кодыг нь салгаж авах.
	 */
	private void parseCode() {
		Intent intent = getIntent();
		if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			Uri uri = intent.getData();
			Log.i(TAG, "URI=" + uri.toString());
			code = uri.getQueryParameter("code");
			Log.i(TAG, "CODE=" + code);
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
