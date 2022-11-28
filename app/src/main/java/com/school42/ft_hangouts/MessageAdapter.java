package com.school42.ft_hangouts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Vector;

public class MessageAdapter extends BaseAdapter {

	private final Vector<Message> _vectorMessage;
	private final LayoutInflater _inflater;

	MessageAdapter(Context context, Vector<Message> messages) {
		_vectorMessage = messages;
		_inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() { return (_vectorMessage.size()); }

	@Override
	public Message getItem(int i) { return (_vectorMessage.get(i)); }

	@Override
	public long getItemId(int i) { return 0; }

	public String getContactName(String phone) {
		Vector<Contact> contacts = MainActivity.getContacts();
		String name = "";
		for (Contact contact: contacts) {
			if (contact.getPhone().equals(phone)) {
				name = contact.getSurname();
				if (name.length() == 0)
					name = contact.getFullName();
				break ;
			}
		}
		return (name);
	}

	@SuppressLint({"ViewHolder", "SetTextI18n"})
	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		view = _inflater.inflate(R.layout.adapter_message, null);

		Message current = getItem(i);
		TextView textMessage = view.findViewById(R.id.messageTextAdaptater);
		textMessage.setText(current.getType().equals("2") ? "You: " : getContactName(current.getNumber()) + ": ");
		textMessage.setText(textMessage.getText() + current.getMsg());
		return (view);
	}
}
