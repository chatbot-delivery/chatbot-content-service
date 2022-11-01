package com.chatbot.contentservice;

public class DeliveryOption {
	
	private String value;
	
	private Action action;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "DeliveryOption [value=" + value + ", action=" + action + "]";
	}
	
}
