package com.adrop.dropbox.common.command;

import java.lang.reflect.Method;

import com.adrop.dropbox.common.Command;
import com.adrop.dropbox.common.Context;
import com.adrop.dropbox.common.Model;

public class DispatchCommand implements Command {

	private static final String PARAMETER_NAME = "action";

	public Model execute(Context context) throws Exception {
		String methodName = context.getParameter(PARAMETER_NAME);
		if (methodName != null && !"".equals(methodName.trim())) {
			Method method = this.getClass().getMethod(methodName, Context.class);
			if (method != null) {
				Object returnValue = method.invoke(this, context);
				if (returnValue instanceof Model) {
					return (Model) returnValue;
				} else {
					return null;
				}
			}
		}
		return null;
	}

}
