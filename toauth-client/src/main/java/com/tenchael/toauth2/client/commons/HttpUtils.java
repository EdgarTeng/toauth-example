package com.tenchael.toauth2.client.commons;

import static com.tenchael.toauth2.client.commons.Constants.SESSION_KEY;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

	// 检查sessionKey是否有效
	public static boolean checkSessionKey(HttpServletRequest request) {
		return request.getSession().getAttribute(SESSION_KEY) != null;
	}

	public static String getSessionKey(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object obj = session.getAttribute(SESSION_KEY);
		if (obj == null) {
			String sessionKey = UUID.randomUUID().toString();
			session.setAttribute(SESSION_KEY, sessionKey);
			return sessionKey;
		} else {
			return obj.toString();
		}
	}

}
