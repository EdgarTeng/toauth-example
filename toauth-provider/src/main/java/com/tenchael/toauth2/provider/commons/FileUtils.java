package com.tenchael.toauth2.provider.commons;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

	public static void readAndWrite(InputStream in, OutputStream out)
			throws IOException {
		int bufferSize = 8192;
		byte[] buf = new byte[bufferSize];
		while (true) {
			int read = 0;
			if (in != null) {
				read = in.read(buf);
			}
			if (read == -1) {
				break;
			}
			out.write(buf, 0, read);
		}
		out.flush();
	}

}
