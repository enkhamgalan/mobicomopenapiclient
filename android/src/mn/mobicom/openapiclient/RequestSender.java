package mn.mobicom.openapiclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * HTTP хүсэлт шидэгч класс.
 * 
 * @author MobiCom
 * 
 */
public class RequestSender {

	/**
	 * HTTP хүсэлт шидэх.
	 * 
	 * @param url
	 *            HTTP хүсэлт шидэх URL
	 * @param request
	 *            Хэрэглэгчийн хүсэлт
	 * @param header
	 *            HTTP хүсэлтийн header
	 * @return HTTP хүсэлтийн хариу
	 * @throws IOException
	 *             гарч болзошгүй гажуудал
	 */
	public static String send(String url, String request,
			Map<String, String> header) throws IOException {
		String response;
		HttpURLConnection con = null;

		try {
			URL url1 = new URL(url);
			con = (HttpURLConnection) url1.openConnection();
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setConnectTimeout(20000);
			con.setReadTimeout(20000);
			con.setRequestMethod("POST");
			// Header дээр content-type нь заавал ийм байна.
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
			out.flush();
			out.close();
			con.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = null;
			StringBuilder res = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				res.append(line);
				res.append("\n");
			}
			response = res.toString();
			reader.close();
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
		return response;
	}
}
