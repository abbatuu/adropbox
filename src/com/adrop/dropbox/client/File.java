package com.adrop.dropbox.client;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.adrop.dropbox.JSONUtilities;

public class File {
	private boolean thumbExists;
	private long bytes;
	private String modified;
	private String path;
	private boolean isDirectory;
	private String size;
	private String root;
	private String icon;
	private List<File> contents = new ArrayList<File>(0);
	private JSONObject json;

	public File(JSONObject json) {
		super();
		this.json = json;
		this.thumbExists = JSONUtilities.booleanValue(json, "thumb_exists");
		this.bytes = JSONUtilities.longValue(json, "bytes");
		this.modified = JSONUtilities.stringValue(json, "modified");
		this.path = JSONUtilities.stringValue(json, "path");
		this.isDirectory = JSONUtilities.booleanValue(json, "is_dir");
		this.size = JSONUtilities.stringValue(json, "size");
		this.root = JSONUtilities.stringValue(json, "root");
		this.icon = JSONUtilities.stringValue(json, "icon");

		JSONArray jsonContents = (JSONArray) json.get("contents");
		if (jsonContents != null) {
			for (Object obj : jsonContents) {
				JSONObject jsonObj = (JSONObject) obj;
				contents.add(new File(jsonObj));
			}
		}
	}

	public boolean isThumbExists() {
		return thumbExists;
	}

	public void setThumbExists(boolean thumbExists) {
		this.thumbExists = thumbExists;
	}

	public long getBytes() {
		return bytes;
	}

	public void setBytes(long bytes) {
		this.bytes = bytes;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isDirectory() {
		return isDirectory;
	}

	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<File> getContents() {
		return contents;
	}

	public void setContents(List<File> contents) {
		this.contents = contents;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

}
