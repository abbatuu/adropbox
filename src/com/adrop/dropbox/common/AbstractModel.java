package com.adrop.dropbox.common;

public abstract class AbstractModel implements Model {

	protected Throwable exception;

	public Throwable getException() {
		return exception;
	}

	public void caughtException(Throwable exception) {
		this.exception = exception;
	}

}
