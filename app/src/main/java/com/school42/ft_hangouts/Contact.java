package com.school42.ft_hangouts;

public class Contact {

	private String _firstName;
	private String _lastName;
	private String _phone;
	private String _mail;

	Contact() {}

	public String toString() { return (_firstName); }

	public void setFirstName(String firstName) { _firstName = firstName; }
	public String getFirstName() { return (_firstName); }
	public void setPhone(String phone) { _phone = phone; }
	public String getPhone() { return (_phone); }
}
