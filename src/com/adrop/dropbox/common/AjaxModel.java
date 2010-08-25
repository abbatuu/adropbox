package com.adrop.dropbox.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxModel extends AbstractModel {

	private ByteArrayOutputStream output = new ByteArrayOutputStream();

	public void write(String data) throws IOException {
		if (data != null) {
			output.write(data.getBytes());
		}
	}

	public void view(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		ServletOutputStream servletOutput = resp.getOutputStream();
		output.writeTo(servletOutput);
		servletOutput.flush();
	}

}
