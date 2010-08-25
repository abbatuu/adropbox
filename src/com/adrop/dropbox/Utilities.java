package com.adrop.dropbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Utilities {

	private static final Logger logger = Logger.getLogger(Utilities.class.getName());

	public static JSONObject readResponseAsJSONObject(URLConnection tokenURLConn) throws IOException, ParseException {
		StringBuilder buf = readResponseAsString(tokenURLConn);
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(buf.toString());
		return json;
	}

	private static StringBuilder readResponseAsString(URLConnection tokenURLConn) throws IOException {
		StringBuilder buf = new StringBuilder();
		BufferedReader tokenReader = new BufferedReader(new InputStreamReader(tokenURLConn.getInputStream()));
		String tokenLine = null;
		while ((tokenLine = tokenReader.readLine()) != null) {
			buf.append(tokenLine);
		}
		logger.info(buf.toString());
		return buf;
	}

	public static String getTokenUrl(String email, String password) {
		return MessageFormat.format(DropboxConstants.URL_TOKEN_PATTERN, email, password);
	}

	public static boolean isEmpty(String str) {
		return null == str || "".equals(str);
	}

	public static boolean isBlank(String str) {
		return null == str || "".equals(str.trim());
	}
}
