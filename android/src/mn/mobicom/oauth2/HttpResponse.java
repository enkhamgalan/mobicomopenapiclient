package mn.mobicom.oauth2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponse {
	/*
	 * @
	 */
	private int code;
	private String body;
	private Map<String, List<String>> header = new HashMap<String, List<String>>();

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public Map<String, List<String>> getHeader() {
		return header;
	}
}
