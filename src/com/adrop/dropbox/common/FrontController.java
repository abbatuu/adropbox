package com.adrop.dropbox.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adrop.dropbox.commands.AccountCommand;

@SuppressWarnings("serial")
public class FrontController extends HttpServlet {

	private static final Logger logger = Logger.getLogger(FrontController.class.getName());

	private static final Map<String, Class<? extends Command>> commands = new HashMap<String, Class<? extends Command>>();
	static {
		commands.put("/account.do", AccountCommand.class);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.getRequestURI();
		String servletPath = req.getServletPath();
		if (commands.containsKey(servletPath)) {
			invokeCommand(req, resp, servletPath);
		} else {
			String requestURI = req.getRequestURI();
			logger.severe(requestURI + " not found.");
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, requestURI + " not found.");
		}
	}

	private void invokeCommand(HttpServletRequest req, HttpServletResponse resp, String servletPath) throws IOException,
																									ServletException {
		Class<? extends Command> commandClass = commands.get(servletPath);
		try {
			Command cmd = commandClass.newInstance();
			Context ctx = createContext(req, resp);
			Model model = cmd.execute(ctx);
			if (model != null) model.view(req, resp);
		} catch (InstantiationException e) {
			logger.log(Level.SEVERE, null, e);
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		} catch (IllegalAccessException e) {
			logger.log(Level.SEVERE, null, e);
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		} catch (Exception e) {
			logger.log(Level.SEVERE, null, e);
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	private Context createContext(HttpServletRequest req, HttpServletResponse resp) {
		return new Context(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		doGet(req, resp);
	}
}
