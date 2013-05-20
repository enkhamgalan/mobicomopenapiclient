package mn.mobicom.openapiclient;

import net.smartam.leeloo.client.request.OAuthClientRequest;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Үндсэн класс.
 * 
 * @author MobiCom
 * 
 */
public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Нэвтрэх товчийг дарангуут хэрэглэгчийн нэвтрэх вэб броузерийг
		// ачааллана.
		Button login = (Button) findViewById(R.id.button1);
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
		OAuthClientRequest request = null;
		try {
			// Хөгжүүлэлэгчийн хүсэлтийг угсарч байна.
			request = OAuthClientRequest
					.authorizationLocation(Constants.REQUEST_URL)
					.setClientId(Constants.CLIENT_ID)
					.setRedirectURI(Constants.OAUTH_CALLBACK_URL)
					.setScope(
							Constants.SCOPE_VOICEMAILOFF + " "
									+ Constants.SCOPE_VOICEMAILON)
					.buildQueryMessage();
		} catch (OAuthSystemException oase) {
			Log.i(TAG, "error", oase);
		}
		// Хөгжүүлэгчийн хүсэлтийг шинэ вэб броузер нээж байна.
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(request
				.getLocationUri() + "&response_type=code"));
		startActivity(intent);
		finish();
	}
}
