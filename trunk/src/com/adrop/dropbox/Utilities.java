package com.adrop.dropbox;

import java.text.MessageFormat;
import java.util.logging.Logger;

public class Utilities {

	private static final Logger logger = Logger.getLogger(Utilities.class.getName());

	public static String getMetadataUrl(String root, String path) {
		return MessageFormat.format(DropboxConstants.URL_METADATA, root, path);
	}

	public static boolean isEmpty(String str) {
		return null == str || "".equals(str);
	}

	public static boolean isBlank(String str) {
		return null == str || "".equals(str.trim());
	}

}
