package com.agilejerry.orm.model;

public class FunnyEvent extends Event {
	private String message = "hello";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
