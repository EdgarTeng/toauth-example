package com.tenchael.toauth2.provider.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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

	public static String readRequestBodyAsString(HttpServletRequest request)
			throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream in = request.getInputStream();
			if (in != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(in));

				int bufferSize = 128;
				char[] buf = new char[bufferSize];
				while (true) {
					int read = 0;
					if (in != null) {
						read = bufferedReader.read(buf);
					}
					if (read == -1) {
						break;
					}
					stringBuilder.append(buf, 0, read);
				}

			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}
		return stringBuilder.toString();
	}

	public static void writeRequestBodyToFile(HttpServletRequest request,
			OutputStream out) throws IOException {
		InputStream in = request.getInputStream();
		FileUtils.readAndWrite(in, out);
	}

}
