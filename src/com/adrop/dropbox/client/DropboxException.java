package com.adrop.dropbox.client;

public class DropboxException extends Exception {

	private static final long serialVersionUID = -1969259827520002483L;

	private int errorCode = -1;

	public DropboxException() {
		super();
	}

	public DropboxException(String message, Throwable cause) {
		super(message, cause);
	}

	public DropboxException(String message) {
		super(message);
	}

	public DropboxException(Throwable cause) {
		super(cause);
	}

	public DropboxException(int errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
