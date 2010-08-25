package com.adrop.dropbox.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.json.simple.JSONObject;

import com.adrop.dropbox.DropboxConstants;
import com.adrop.dropbox.JSONUtilities;
import com.adrop.dropbox.Utilities;

public class Adropbox {

	private static final Logger logger = Logger.getLogger(Adropbox.class.getName());

	private String consumerKey;
	private String consumerSecret;

	private OAuthConsumer oauthConsumer;
	private OAuthProvider oauthProvider;

	public Adropbox(String consumerKey, String consumerSecret) {
		super();
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
	}

	public Adropbox() {
		this.consumerKey = DropboxConstants.APP_AdropDropbox_KEY;
		this.consumerSecret = DropboxConstants.APP_AdropDropbox_SECRET;
		oauthConsumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
		oauthProvider = new DefaultOAuthProvider(	DropboxConstants.REQUEST_TOKEN_URL,
													DropboxConstants.ACCESS_TOKEN_URL,
													DropboxConstants.AUTHORIZATION_URL);
	}

	public void getMetadata() {

	}

	public void getFile() {

	}

	public void thumbnails() {

	}

	public void putFile() {

	}

	public void copy() {

	}

	public void createFolder() {

	}

	public void delete() {

	}

	public void move() {

	}

	public boolean login(String email, String password) {
		AccessToken accessToken = null;

		try {
			AccessTokenStore ats = AccessTokenStoreFactory.get();
			accessToken = ats.findBy(email);
			if (accessToken == null) {
				String tokenUrl = Utilities.getTokenUrl(email, password);
				URL tokenURL = new URL(tokenUrl);
				URLConnection tokenURLConn = tokenURL.openConnection();
				oauthConsumer.sign(tokenURLConn);
				tokenURLConn.connect();
				JSONObject json = Utilities.readResponseAsJSONObject(tokenURLConn);
				accessToken = new AccessToken(	email,
												JSONUtilities.stringValue(json, "token"),
												JSONUtilities.stringValue(json, "secret"));
				ats.save(accessToken);
				logger.info("save AccessToken " + email + ":" + accessToken.getToken() + "," + accessToken.getSecret());
			} else {
				logger.info("find AccessToken by email " + email + ":" + accessToken.getToken() + "," + accessToken.getSecret());
			}
			oauthConsumer.setTokenWithSecret(accessToken.getToken(), accessToken.getSecret());
			return true;
		} catch (MalformedURLException e) {
			logger.log(Level.WARNING, null, e);
		} catch (IOException e) {
			logger.log(Level.WARNING, null, e);
		} catch (OAuthMessageSignerException e) {
			logger.log(Level.WARNING, null, e);
		} catch (OAuthExpectationFailedException e) {
			logger.log(Level.WARNING, null, e);
		} catch (OAuthCommunicationException e) {
			logger.log(Level.WARNING, null, e);
		} catch (Exception e) {
			logger.log(Level.WARNING, null, e);
		}
		return false;
	}

	public Account getAccountInfo(String email, String password) {

		try {

			String accountInfoUrl = DropboxConstants.URL_ACCOUNT_INFO;
			URL url = new URL(accountInfoUrl);
			URLConnection urlConn = url.openConnection();
			oauthConsumer.sign(urlConn);
			urlConn.connect();
			JSONObject accountJson = Utilities.readResponseAsJSONObject(urlConn);

			Account account = new Account(accountJson);
			return account;

		} catch (MalformedURLException e) {
			logger.log(Level.WARNING, null, e);
		} catch (IOException e) {
			logger.log(Level.WARNING, null, e);
		} catch (OAuthMessageSignerException e) {
			logger.log(Level.WARNING, null, e);
		} catch (OAuthExpectationFailedException e) {
			logger.log(Level.WARNING, null, e);
		} catch (OAuthCommunicationException e) {
			logger.log(Level.WARNING, null, e);
		} catch (Exception e) {
			logger.log(Level.WARNING, null, e);
		}
		return null;
	}

}
