package com.adrop.dropbox.client;


public final class AccessTokenStoreFactory {

	public static AccessTokenStore get() {
		return new AccessTokenStoreImpl();
	}

}
