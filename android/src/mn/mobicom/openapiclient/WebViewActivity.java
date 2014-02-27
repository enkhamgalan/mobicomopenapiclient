package mn.mobicom.openapiclient;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 
 * @author Developer Deprecated-g авбал Login хэсэг Webview-ээр дуудагдана.
 */
//@Deprecated
public class WebViewActivity extends Activity {

	private static final String TAG = "WEBVIEW";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		WebView web = (WebView) findViewById(R.id.webView1);
		String url = this.getIntent().getData().toString();
		if (url != null) {
			Log.i(TAG, url);
			web.loadUrl(url);
			// web.postUrl(url, "".getBytes());
			WebSettings settings = web.getSettings();
			settings.setDefaultTextEncodingName("utf-8");
			web.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					Log.i(TAG, url);
					if (url.startsWith(Constants.OAUTH_CALLBACK_SCHEME)) {
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri
								.parse(url));
						startActivity(intent);
						finish();
					} else if (url.startsWith(Constants.LOGOUTOK_URL)) {
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri
								.parse(Constants.OAUTH_CALLBACK_URL
										+ "?error=logout"));
						startActivity(intent);
						finish();
					} else if (url.startsWith(Constants.CPOK_URL)) {
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri
								.parse(Constants.OAUTH_CALLBACK_URL
										+ "?error=back"));
						startActivity(intent);
						finish();
					} else {
						view.loadUrl(url);
					}
					return true;
				}

				@Override
				public void onReceivedSslError(WebView view,
						SslErrorHandler handler, SslError error) {
					// super.onReceivedSslError(view, handler, error);
					handler.proceed();
				}

				@Override
				public void onReceivedError(WebView view, int errorCode,
						String description, String failingUrl) {
					Log.i(TAG, "error code: "+errorCode);
					Log.i(TAG, "description: "+description);
					Log.i(TAG, "failingUrl: "+failingUrl);
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.web_view, menu);
		return true;
	}

}
