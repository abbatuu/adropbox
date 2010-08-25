package com.adrop.dropbox.commands;

import java.util.logging.Logger;

import com.adrop.dropbox.client.Account;
import com.adrop.dropbox.client.Adropbox;
import com.adrop.dropbox.common.Context;
import com.adrop.dropbox.common.DispatchCommand;
import com.adrop.dropbox.common.ForwardModel;
import com.adrop.dropbox.common.Model;

public class AccountCommand extends DispatchCommand {

	private static final Logger logger = Logger.getLogger(AccountCommand.class.getName());

	public Model login(Context ctx) {

		String email = ctx.getParameter("email");
		String password = ctx.getParameter("password");
		Adropbox client = new Adropbox();
		Account account = null;
		ForwardModel model = new ForwardModel("/accountInfo.jsp");
		if (client.login(email, password)) {
			account = client.getAccountInfo(email, password);
			model.addRequestAttribute("account", account);
		}
		return model;
	}

	public Model metadata(Context ctx) {
		return null;
	}

}
