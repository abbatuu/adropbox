package com.adrop.dropbox.client;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;

import org.json.simple.JSONObject;

import com.adrop.dropbox.DropboxConstants;
import com.adrop.dropbox.JSONUtilities;
import com.adrop.dropbox.Utilities;

public class Adropbox {

	private static final Logger logger = Logger.getLogger(Adropbox.class.getName());

	@SuppressWarnings("unused")
	private String consumerKey;
	@SuppressWarnings("unused")
	private String consumerSecret;

	private OAuthConsumer oauthConsumer;
	@SuppressWarnings("unused")
	private OAuthProvider oauthProvider;

	@SuppressWarnings("unused")
	private String email;
	private boolean authenticated = false;

	private AccessToken accessToken;

	/**
	 * Constuctor for Adropbox
	 * 
	 * @param email
	 *            Account for logging into www.dropbox.com
	 * @param password
	 *            Account's password, Adropbox will not save the password
	 * @param consumerKey
	 *            App Key applied from www.dropbox.com
	 * @param consumerSecret
	 *            App Secret applied from www.dropbox.com
	 * @throws DropboxException
	 */
	public Adropbox(String email, String password, String consumerKey, String consumerSecret) throws DropboxException {
		super();
		this.email = email;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		oauthConsumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
		oauthProvider = new DefaultOAuthProvider(	DropboxConstants.REQUEST_TOKEN_URL,
													DropboxConstants.ACCESS_TOKEN_URL,
													DropboxConstants.AUTHORIZATION_URL);
		authenticate(email, password);
	}

	public Adropbox(String consumerKey, String consumerSecret, AccessToken accessToken) {
		super();
		this.accessToken = accessToken;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		oauthConsumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
		oauthProvider = new DefaultOAuthProvider(	DropboxConstants.REQUEST_TOKEN_URL,
													DropboxConstants.ACCESS_TOKEN_URL,
													DropboxConstants.AUTHORIZATION_URL);
		oauthConsumer.setTokenWithSecret(accessToken.getToken(), accessToken.getSecret());
		this.authenticated = true;
	}

	/**
	 * Get the metadata for a directory or file
	 * 
	 * @param hash
	 * @param path
	 * @return
	 * @throws DropboxException
	 */
	public File getMetadata(String hash, String path, boolean inDropbox) throws DropboxException {
		checkNotBlank(path);

		String metadataUrl = Utilities.getMetadataUrl(getRoot(inDropbox), path);

		Map<String, String> params = new HashMap<String, String>();
		if (!Utilities.isBlank(hash)) {
			params.put("hash", hash);
		}
		params.put("file_limit", "100"); // just list top 100 files, hardcode to 100, default is 10000
		params.put("list", "true"); // always list the contents of a folder
		DropboxGetOperation oper = new DropboxGetOperation(metadataUrl, params, oauthConsumer);
		oper.execute();

		return new File(oper.readResponseAsJSONObject());
	}

	/**
	 * Get the content of a file
	 * 
	 * @param path
	 * @return
	 * @throws DropboxException
	 */
	public InputStream getFile(String path, boolean inDropbox) throws DropboxException {
		checkNotBlank(path);

		return null;
	}

	/**
	 * Upload a file to dropbox
	 * 
	 * @param input
	 * @param path
	 * @throws DropboxException
	 */
	public void putFile(InputStream input, String path, boolean inDropbox) throws DropboxException {

	}

	/**
	 * Get a minimized thumbnail for a photo in a specified size
	 * 
	 * @param path
	 * @param size
	 * @return
	 * @throws DropboxException
	 */
	public InputStream thumbnails(String path, String size, boolean inDropbox) throws DropboxException {
		return null;
	}

	/**
	 * Copy a file
	 * 
	 * @param from
	 * @param to
	 * @return
	 * @throws DropboxException
	 */
	public File copy(String from, String to, boolean inDropbox) throws DropboxException {
		checkNotBlank(from);
		checkNotBlank(to);

		Map<String, String> params = new HashMap<String, String>();
		params.put("root", getRoot(inDropbox));
		params.put("from_path", from);
		params.put("to_path", to);

		DropboxGetOperation oper = null;
		oper = new DropboxGetOperation(DropboxConstants.URL_COPY, params, oauthConsumer);
		oper.execute();

		return new File(oper.readResponseAsJSONObject());
	}

	/**
	 * Create a new folder
	 * 
	 * @param path
	 * @return
	 * @throws DropboxException
	 */
	public File createFolder(String path, boolean inDropbox) throws DropboxException {
		checkNotBlank(path);

		Map<String, String> params = new HashMap<String, String>();
		params.put("root", getRoot(inDropbox));
		params.put("path", path);

		DropboxGetOperation oper = null;
		oper = new DropboxGetOperation(DropboxConstants.URL_CREATE_FOLDER, params, oauthConsumer);
		oper.execute();

		return new File(oper.readResponseAsJSONObject());
	}

	/**
	 * Delete a file or folder
	 * 
	 * @param path
	 * @throws DropboxException
	 */
	public void delete(String path, boolean inDropbox) throws DropboxException {
		checkNotBlank(path);

		Map<String, String> params = new HashMap<String, String>();
		params.put("root", getRoot(inDropbox));
		params.put("path", path);

		DropboxGetOperation oper = null;
		oper = new DropboxGetOperation(DropboxConstants.URL_DELETE, params, oauthConsumer);
		try {
			oper.execute();
		} finally {
			oper.cleanup();
		}
	}

	/**
	 * Move a file or folder to a new location
	 * 
	 * @param from
	 * @param to
	 * @return
	 * @throws DropboxException
	 */
	public File move(String from, String to, boolean inDropbox) throws DropboxException {
		return null;
	}

	/**
	 * Load an account's access token from persistence, or fetch it from dropbox
	 * 
	 * @param email
	 * @param password
	 * @throws DropboxException
	 */
	public void authenticate(String email, String password) throws DropboxException {
		if (authenticated) return;
		checkNotBlank(email);
		checkNotBlank(password);

		Map<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("password", password);

		AccessTokenStore ats = AccessTokenStoreFactory.get();
		accessToken = ats.findBy(email);
		if (accessToken == null) {
			DropboxGetOperation oper = new DropboxGetOperation(DropboxConstants.URL_TOKEN, params, oauthConsumer);
			oper.execute();
			JSONObject json = oper.readResponseAsJSONObject();
			accessToken = new AccessToken(	email,
											JSONUtilities.stringValue(json, "token"),
											JSONUtilities.stringValue(json, "secret"));
			ats.save(accessToken);
			logger.info("Got new access token: " + accessToken.toString());
		} else {
			logger.info("Load access token: " + accessToken.toString());
		}
		oauthConsumer.setTokenWithSecret(accessToken.getToken(), accessToken.getSecret());
		authenticated = true;

	}

	/**
	 * Get an account's information
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws DropboxException
	 */
	public Account getAccountInfo() throws DropboxException {

		DropboxGetOperation oper = new DropboxGetOperation(DropboxConstants.URL_ACCOUNT_INFO, oauthConsumer);
		oper.execute();
		JSONObject accountJson = oper.readResponseAsJSONObject();

		return new Account(accountJson);

	}

	private String getRoot(boolean inDropbox) {
		return inDropbox ? "dropbox" : "sandbox";
	}

	private void checkNotBlank(String param) {
		if (Utilities.isBlank(param)) throw new java.lang.IllegalArgumentException();
	}

	public AccessToken getAccessToken() {
		return accessToken;
	}

}
