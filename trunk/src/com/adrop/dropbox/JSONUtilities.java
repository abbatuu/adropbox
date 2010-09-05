package com.adrop.dropbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.beanutils.BeanUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public final class JSONUtilities {

	private static final Logger logger = Logger.getLogger(JSONUtilities.class.getName());

	public static String stringValue(JSONObject json, String name) {
		return json.containsKey(name) && json.get(name) instanceof String ? (String) json.get(name) : null;
	}

	public static long longValue(JSONObject json, String name) {
		return json.containsKey(name) && json.get(name) instanceof Number ? ((Number) json.get(name)).longValue() : 0;
	}

	public static boolean booleanValue(JSONObject json, String name) {
		return json.containsKey(name) && json.get(name) instanceof Boolean ? ((Boolean) json.get(name)).booleanValue() : false;
	}

	public static void toBean(Object target, JSONObject json, Map<String, String> mapping) {
		for (Iterator iter = json.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			if (mapping.containsKey(name)) {
				try {
					BeanUtils.setProperty(target, mapping.get(name), json.get(name));
				} catch (IllegalAccessException e) {
					logger.log(Level.WARNING, null, e);
				} catch (InvocationTargetException e) {
					logger.log(Level.WARNING, null, e);
				}
			}
		}

	}

	public static void toBean(Object target, JSONObject json) {
		for (Iterator iter = json.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			try {
				BeanUtils.setProperty(target, name, json.get(name));
			} catch (IllegalAccessException e) {
				logger.log(Level.WARNING, null, e);
			} catch (InvocationTargetException e) {
				logger.log(Level.WARNING, null, e);
			}
		}

	}

	public static JSONObject readAsJSONObject(InputStream inputStream) throws IOException, ParseException {
		StringBuilder buf = JSONUtilities.readAsStringBuilder(inputStream);
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(buf.toString());
		return json;
	}

	public static StringBuilder readAsStringBuilder(InputStream inputStream) throws IOException {
		StringBuilder buf = new StringBuilder();
		BufferedReader tokenReader = new BufferedReader(new InputStreamReader(inputStream));
		String tokenLine = null;
		while ((tokenLine = tokenReader.readLine()) != null) {
			buf.append(tokenLine);
		}
		logger.info(buf.toString());
		return buf;
	}
}
