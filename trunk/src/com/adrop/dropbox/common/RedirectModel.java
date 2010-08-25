package com.adrop.dropbox.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectModel extends AbstractModel {

	private String target;

	public RedirectModel(String target) {
		super();
		this.target = target;
	}

	public void view(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.sendRedirect(target);
	}

}
