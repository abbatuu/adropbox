package com.adrop.dropbox.client;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.adrop.dropbox.Utilities;

public class AccessTokenStoreImpl implements AccessTokenStore {

	private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");;

	public AccessTokenStoreImpl() {
	}

	public Query newGetQuery(PersistenceManager pm) {
		Query query = pm.newQuery(AccessToken.class, "email == emailParam");
		query.declareParameters("String emailParam");
		return query;
	}

	public boolean exists(String email) {
		List<AccessToken> tokens = (List<AccessToken>) newGetQuery(pmfInstance.getPersistenceManager()).execute(email);
		return !tokens.isEmpty();
	}

	public void save(AccessToken accessToken) {
		if (accessToken != null) {
			PersistenceManager pm = pmfInstance.getPersistenceManager();
			pm.makePersistent(accessToken);
		}

	}

	public AccessToken destroyBy(String email) {
		if (!Utilities.isBlank(email)) {
			PersistenceManager pm = pmfInstance.getPersistenceManager();
			List<AccessToken> tokens = (List<AccessToken>) newGetQuery(pm).execute(email);
			if (tokens.isEmpty()) return null;
			AccessToken removed = tokens.iterator().next();
			pm.deletePersistent(removed);
			return removed;
		}
		return null;
	}

	public AccessToken findBy(String email) {
		if (!Utilities.isBlank(email)) {
			List<AccessToken> tokens = (List<AccessToken>) newGetQuery(pmfInstance.getPersistenceManager()).execute(email);
			return tokens.isEmpty() ? null : tokens.iterator().next();
		}
		return null;
	}
}
