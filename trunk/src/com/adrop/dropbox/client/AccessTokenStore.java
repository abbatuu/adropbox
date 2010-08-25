package com.adrop.dropbox.client;

public interface AccessTokenStore {

	public boolean exists(String email);

	public void save(AccessToken accessToken);

	public AccessToken findBy(String email);

	public AccessToken destroyBy(String email);

}
