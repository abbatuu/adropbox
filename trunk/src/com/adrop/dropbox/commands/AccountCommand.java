package com.adrop.dropbox.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.adrop.dropbox.client.Account;
import com.adrop.dropbox.client.Adropbox;
import com.adrop.dropbox.client.DropboxException;
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
		try {
			if (client.login(email, password)) {
				account = client.getAccountInfo(email, password);
				model.addRequestAttribute("account", account);
			}
		} catch (DropboxException e) {
			logger.log(Level.WARNING, null, e);
		}
		return model;
	}

	public Model metadata(Context ctx) {
		return null;
	}

}
