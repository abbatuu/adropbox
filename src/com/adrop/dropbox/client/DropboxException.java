package com.adrop.dropbox.client;

public class DropboxException extends Exception {

	private static final long serialVersionUID = -1969259827520002483L;

	private int errorCode;

	public DropboxException(int errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
