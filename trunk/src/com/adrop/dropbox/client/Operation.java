package com.adrop.dropbox.client;

import java.util.Map;

public interface Operation {

	public abstract Object execute(Map<String, Object> parameters, Object data) throws DropboxException;

	public abstract Object execute() throws DropboxException;

	public abstract Object execute(Map<String, Object> parameters) throws DropboxException;

}