package com.adrop.dropbox.common.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ForwardModel extends AbstractModel {

	private Map<String, Object> modelData = new HashMap<String, Object>();
	private String target;

	public ForwardModel(String target) {
		super();
		this.target = target;
	}

	public void addRequestAttribute(String name, Object value) {
		modelData.put(name, value);
	}

	public void addRequestAttribute(Map<String, Object> attrs) {
		modelData.putAll(attrs);
	}

	public void removeRequestAttribute(String name) {
		modelData.remove(name);
	}

	public void view(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		if (target != null) {
			for (Entry<String, Object> iter : modelData.entrySet()) {
				req.setAttribute(iter.getKey(), iter.getValue());
			}
			RequestDispatcher rd = req.getRequestDispatcher(target);
			rd.forward(req, resp);
		}
	}

}
