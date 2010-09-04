package com.adrop.dropbox.common.command;

import com.adrop.dropbox.common.Command;
import com.adrop.dropbox.common.Context;
import com.adrop.dropbox.common.Mapping;
import com.adrop.dropbox.common.Model;
import com.adrop.dropbox.common.model.ForwardModel;
import com.adrop.dropbox.common.model.RedirectModel;

public class PageCommand implements Command {

	private String type;
	private String target;

	public PageCommand(String type, String target) {
		super();
		this.type = type;
		this.target = target;
	}

	public Model execute(Context context) throws Exception {
		if (Mapping.TO_TYPE_REDIRECT.equals(type)) {
			return new RedirectModel(target);
		} else if (Mapping.TO_TYPE_FORWARD.equals(type)) {
			return new ForwardModel(target);
		}
		return new ForwardModel(target);
	}
}
