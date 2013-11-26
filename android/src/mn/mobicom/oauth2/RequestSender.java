package mn.mobicom.oauth2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import android.util.Log;

/**
 * HTTP хүсэлт шидэгч класс.
 * 
 * @author MobiCom
 * 
 */
public class RequestSender {

	public static final String TAG = "HTTPS";

	private final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	/**
	 * Trust every server - dont check for any certificate
	 */
	private static void trustAllHosts() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}

			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * HTTPS POST хүсэлт илгээхэд хэрэглэгдэн
	 * @param url хүсэлт илгээх url
	 * @param request хүсэлтийг бие
	 * @param header хүсэлтийн header-үүд
	 * @exception IOException холболттой холбоотой алдаа
	 */
	public static HttpResponse POST(String url, String request,
			Map<String, String> header) throws IOException {
		HttpsURLConnection con = null;
		Log.i(TAG, "Sending POST: " + url);
		try {
			trustAllHosts();
			URL url1 = new URL(url);
			con = (HttpsURLConnection) url1.openConnection();
			con.setHostnameVerifier(DO_NOT_VERIFY);
			con.setUseCaches(true);
			con.setDoOutput(true);
			con.setDoInput(true);
			HttpsURLConnection.setFollowRedirects(false);
			con.setConnectTimeout(20000);
			con.setReadTimeout(20000);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			con.setRequestProperty("Content-Length",
					Integer.toString(request.getBytes().length));
			if (header != null)
				for (String key : header.keySet()) {
					con.setRequestProperty(key, header.get(key));
				}
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.write(request.getBytes());
			Log.i(TAG, "request: " + request);
			out.flush();
			out.close();
			con.connect();
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			} catch (IOException ex){
				reader = new BufferedReader(new InputStreamReader(
						con.getErrorStream()));
			}
			String line = null;
			StringBuilder res = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				res.append(line);
				res.append("\n");
			}
			HttpResponse response = new HttpResponse();
			response.setCode(con.getResponseCode());
			response.setBody(res.toString());
			response.getHeader().putAll(con.getHeaderFields());
			reader.close();
			Log.i(TAG,
					"response (" + response.getCode() + "): " + res.toString());
			return response;
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}

	/*
	 * HTTPS GET хүсэлт илгээхэд хэрэглэгдэн
	 * @param url хүсэлт илгээх url
	 * @param header хүсэлтийн header-үүд
	 * @exception IOException холболттой холбоотой алдаа
	 */
	public static HttpResponse GET(String url, Map<String, String> header)
			throws IOException {
		HttpsURLConnection con = null;
		Log.i(TAG, "Sending GET request: " + url);
		try {
			trustAllHosts();
			URL url1 = new URL(url);
			con = (HttpsURLConnection) url1.openConnection();
			con.setHostnameVerifier(DO_NOT_VERIFY);
			con.setUseCaches(true);
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setConnectTimeout(20000);
			con.setReadTimeout(20000);
			con.setRequestMethod("GET");
			if (header != null)
				for (String key : header.keySet()) {
					con.setRequestProperty(key, header.get(key));
				}
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.flush();
			out.close();
			con.connect();
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			} catch (Exception ex){
				reader = new BufferedReader(new InputStreamReader(
						con.getErrorStream()));
			}
			String line = null;
			StringBuilder res = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				res.append(line);
				res.append("\n");
			}
			HttpResponse response = new HttpResponse();
			response.setCode(con.getResponseCode());
			response.setBody(res.toString());
			response.getHeader().putAll(con.getHeaderFields());
			reader.close();
			Log.i(TAG,
					"response (" + response.getCode() + "): " + res.toString());
			return response;
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}
}
