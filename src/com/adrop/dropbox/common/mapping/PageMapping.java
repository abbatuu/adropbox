package com.adrop.dropbox.common.mapping;

import com.adrop.dropbox.common.Command;
import com.adrop.dropbox.common.Mapping;
import com.adrop.dropbox.common.command.PageCommand;

public class PageMapping implements Mapping {

	private String target;
	private String type;

	public PageMapping(String target, String type) {
		super();
		this.target = target;
		this.type = type;
	}

	public Command createCommand() {
		return new PageCommand(type, target);
	}

}
