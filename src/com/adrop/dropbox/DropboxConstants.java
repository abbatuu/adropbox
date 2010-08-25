package com.adrop.dropbox;

public final class DropboxConstants {
	public static final String DROPBOX_SERVER = "api.dropbox.com";
	public static final String DROPBOX_CONTENT_SERVER = "api-content.dropbox.com";

	public static final String APP_AdropDropbox_KEY = "fyz6fg4vu73lc7n";
	public static final String APP_AdropDropbox_SECRET = "76c3cjtwpwwm71u";

	public static final String HTTP_SCHEMA = "https";
	public static final String DROPBOX_API_VERSION = "0";

	public static final String REQUEST_TOKEN_URL = "https://api.dropbox.com/0/oauth/request_token";
	public static final String ACCESS_TOKEN_URL = "https://api.dropbox.com/0/oauth/access_token";
	public static final String AUTHORIZATION_URL = "https://api.dropbox.com/0/oauth/authorize";

	public static final String URL_TOKEN_PATTERN = HTTP_SCHEMA + "://" + DROPBOX_SERVER + "/" + DROPBOX_API_VERSION + "/token?email={0}&password={1}";

	public static final String URL_ACCOUNT_INFO = HTTP_SCHEMA + "://" + DROPBOX_SERVER + "/" + DROPBOX_API_VERSION + "/account/info";
	public static final String URL_METADATA = HTTP_SCHEMA + "://" + DROPBOX_SERVER + "/" + DROPBOX_API_VERSION + "/account/metadata/{0}/{1}";

}
