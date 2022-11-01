package com.chatbot.contentservice;

public class Action {
	
	private String type;
	
	private String title;
	
	private String value;
	
	public Action() {}
	
	public Action(String type, String title, String value) {
		this.type = type;
		this.title = title;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Action [type=" + type + ", title=" + title + ", value=" + value + "]";
	}
	
	

}
