package com.adrop.dropbox.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.adrop.dropbox.DropboxConstants;
import com.adrop.dropbox.client.AccessToken;
import com.adrop.dropbox.client.Account;
import com.adrop.dropbox.client.Adropbox;
import com.adrop.dropbox.client.DropboxException;
import com.adrop.dropbox.common.Context;
import com.adrop.dropbox.common.Model;
import com.adrop.dropbox.common.command.DispatchCommand;
import com.adrop.dropbox.common.model.ForwardModel;
import com.adrop.dropbox.common.model.RedirectModel;

public class AccountCommand extends DispatchCommand {

	private static final Logger logger = Logger.getLogger(AccountCommand.class.getName());

	public Model login(Context ctx) {

		String email = ctx.getParameter("email");
		String password = ctx.getParameter("password");

		RedirectModel model = new RedirectModel("/account/info");
		try {
			Adropbox client = new Adropbox(	email,
											password,
											DropboxConstants.APP_AdropDropbox_KEY,
											DropboxConstants.APP_AdropDropbox_SECRET);
			AccessToken token = client.getAccessToken();
			ctx.setValueToSession(DropboxConstants.SESSION_LEY_TOKEN, token);
		} catch (DropboxException e) {
			logger.log(Level.WARNING, null, e);
		}
		return model;
	}

	public Model getInfo(Context ctx) {
		AccessToken token = (AccessToken) ctx.getValueFromSession(DropboxConstants.SESSION_LEY_TOKEN);
		Adropbox client = new Adropbox(	DropboxConstants.APP_AdropDropbox_KEY,
										DropboxConstants.APP_AdropDropbox_SECRET,
										token);
		try {
			Account account = client.getAccountInfo();
			ctx.setRequestValue("account", account);
			return new ForwardModel("/accountInfo.jsp");
		} catch (DropboxException e) {
			logger.log(Level.WARNING, null, e);
		}
		return null;
	}

	public Model logout(Context ctx) {
		ctx.getRequest().getSession().invalidate();
		return new RedirectModel("/index.html");
	}

}
