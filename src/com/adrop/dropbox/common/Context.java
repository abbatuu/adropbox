package com.adrop.dropbox.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Context {

	private HttpServletRequest req;
	private HttpServletResponse resp;

	public Context(HttpServletRequest req, HttpServletResponse resp) {
		super();
		this.req = req;
		this.resp = resp;
	}

	public String getParameter(String name) {
		return req.getParameter(name);
	}

	public String[] getParameterValues(String name) {
		return req.getParameterValues(name);
	}
}
