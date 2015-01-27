package com.tenchael.toauth2.client.commons;

import java.util.Map;
import java.util.Set;

public class HttpUtils {

	public static String jointParams(String baseUrl, Map<String, String> params) {
		StringBuffer buf = new StringBuffer(baseUrl);
		// 拼接参数
		Set<String> keys = params.keySet();
		if (keys != null && !keys.isEmpty()) {
			buf.append("?");
			for (String key : keys) {
				String value = params.get(key);
				buf.append(key + "=" + value + "&");
			}
			buf.deleteCharAt(buf.lastIndexOf("&"));
		}
		return buf.toString();
	}


}
