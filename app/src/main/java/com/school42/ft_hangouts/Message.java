package com.school42.ft_hangouts;

import androidx.annotation.NonNull;

public class Message {
	private final String number;
	private final String type;
	private final String msg;

	public Message(String n, String m, String t) {
		number = n;
		msg = m;
		type = t;
	}

	public String getType() { return(type); }
	public String getMsg() { return(msg); }
	public String getNumber() { return(number); };

	@NonNull
	@Override
	public String toString() {
		return "Message{" +
				"number='" + number + '\'' +
				", type=" + type +
				", msg='" + msg +
				'}';
	}
}
