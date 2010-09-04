package com.adrop.dropbox.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

	public HttpServletRequest getRequest() {
		return req;
	}

	public HttpServletResponse getResponse() {
		return resp;
	}

	public HttpSession getSession() {
		return req.getSession();
	}

	public Object getValueFromSession(String name) {
		return getSession().getAttribute(name);
	}

	public void setValueToSession(String name, Object value) {
		getSession().setAttribute(name, value);
	}

	public void setRequestValue(String name, Object value) {
		req.setAttribute(name, value);
	}
}
