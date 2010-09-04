package com.adrop.dropbox.common.mapping;

import com.adrop.dropbox.common.Command;
import com.adrop.dropbox.common.Mapping;
import com.adrop.dropbox.common.command.MethodCallCommand;

public class ActionMapping implements Mapping {

	private String path;
	private String className;
	private String method;

	public ActionMapping(String path, String className, String method) {
		super();
		this.path = path;
		this.className = className;
		this.method = method;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String requestPath) {
		this.path = requestPath;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String methodName) {
		this.method = methodName;
	}

	public Command createCommand() {
		return new MethodCallCommand(className, method);
	}

}
