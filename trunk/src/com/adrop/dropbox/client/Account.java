package com.adrop.dropbox.client;

import org.json.simple.JSONObject;

import com.adrop.dropbox.JSONUtilities;

public class Account {
	private String country;
	private String displayName;
	private long quotaQuota;
	private long quotaNormal;
	private long quotaShared;
	private long uid;
	private String email;
	private String referralLink;
	private JSONObject json;

	@SuppressWarnings("unchecked")
	public Account(JSONObject json) {
		this.json = json;

		this.country = JSONUtilities.stringValue(json, "country");
		this.displayName = JSONUtilities.stringValue(json, "display_name");
		this.uid = JSONUtilities.longValue(json, "uid");
		this.referralLink = JSONUtilities.stringValue(json, "referralLink");

		JSONObject quota = (JSONObject) json.get("quota_info");
		this.quotaQuota = JSONUtilities.longValue(quota, "quota");
		this.quotaNormal = JSONUtilities.longValue(quota, "normal");
		this.quotaShared = JSONUtilities.longValue(quota, "shared");
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public long getQuotaQuota() {
		return quotaQuota;
	}

	public void setQuotaQuota(long quotaQuota) {
		this.quotaQuota = quotaQuota;
	}

	public long getQuotaNormal() {
		return quotaNormal;
	}

	public void setQuotaNormal(long quotaNormal) {
		this.quotaNormal = quotaNormal;
	}

	public long getQuotaShared() {
		return quotaShared;
	}

	public void setQuotaShared(long quotaShared) {
		this.quotaShared = quotaShared;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReferralLink() {
		return referralLink;
	}

	public void setReferralLink(String referralLink) {
		this.referralLink = referralLink;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

}