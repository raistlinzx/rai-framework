package com.rai.framework.exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class ErrorMessage {
	private static ErrorMessage errorMessage = null;
	private static HashMap errorMap = null;
	private static Properties config = null;

	static {
		InputStream in = ErrorMessage.class.getClassLoader()
				.getResourceAsStream("errorMessage_zh.properties");
		config = new Properties();
		try {
			config.load(in);
			in.close();
		} catch (IOException e) {
			System.out.println("No errorMessage.properties defined error");
		}
	}

	public static synchronized ErrorMessage getInstance() {
		if (errorMessage == null) {
			errorMessage = new ErrorMessage();
		}
		return errorMessage;
	}

	private ErrorMessage() {
		errorMap = new HashMap();
	}

	public HashMap loadErrorMessage() {
		Enumeration keys = config.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String value = config.getProperty(key);

			errorMap.put(key, value);
		}
		return errorMap;
	}
}
