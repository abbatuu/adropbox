package com.adrop.dropbox.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Model {

	public void view(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;

}
