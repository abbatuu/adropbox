package com.adrop.dropbox.common;

public interface Mapping {

	public static final String TO_TYPE_FORWARD = "forward";
	public static final String TO_TYPE_REDIRECT = "redirect";

	public abstract Command createCommand();
}
