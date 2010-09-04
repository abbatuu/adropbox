package com.adrop.dropbox.common.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.adrop.dropbox.common.Command;
import com.adrop.dropbox.common.Context;
import com.adrop.dropbox.common.Model;

public class MethodCallCommand implements Command {

	private static final Logger logger = Logger.getLogger(MethodCallCommand.class.getName());

	private String className;
	private String method;

	public MethodCallCommand(String className, String method) {
		super();
		this.className = className;
		this.method = method;
	}

	public Model execute(Context context) throws Exception {
		try {
			Class clazz = Class.forName(className);
			Command cmd = (Command) clazz.newInstance();
			Method mthd = clazz.getMethod(method, Context.class);
			return (Model) mthd.invoke(cmd, context);
		} catch (SecurityException e) {
			logger.log(Level.WARNING, null, e);
			return null;
		} catch (IllegalArgumentException e) {
			logger.log(Level.WARNING, null, e);
			return null;
		} catch (ClassNotFoundException e) {
			logger.log(Level.WARNING, null, e);
			return null;
		} catch (InstantiationException e) {
			logger.log(Level.WARNING, null, e);
			return null;
		} catch (IllegalAccessException e) {
			logger.log(Level.WARNING, null, e);
			return null;
		} catch (NoSuchMethodException e) {
			logger.log(Level.WARNING, null, e);
			return null;
		} catch (InvocationTargetException e) {
			logger.log(Level.WARNING, null, e);
			return null;
		} catch (Exception e) {
			throw e;
		}
	}
}
