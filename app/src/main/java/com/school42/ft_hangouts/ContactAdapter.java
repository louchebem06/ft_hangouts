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

public class ContactAdapter extends BaseAdapter {

	private Context _context;
	private Vector<Contact> _vectorContact;
	private LayoutInflater _inflater;
	private Contact _currentContact;

	ContactAdapter(Context context, Vector<Contact> contacts) {
		_context = context;
		_vectorContact = contacts;
		_inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() { return (_vectorContact.size()); }

	@Override
	public Contact getItem(int i) { return (_vectorContact.get(i)); }

	@Override
	public long getItemId(int i) { return 0; }

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		view = _inflater.inflate(R.layout.adapter_contact, null);

		_currentContact = getItem(i);
		String fullname = !_currentContact.getSurname().isEmpty() ? _currentContact.getSurname() : _currentContact.getFullName();

		TextView fullnameText = view.findViewById(R.id.fullname);
		fullnameText.setText(fullname);

		ImageButton delete = view.findViewById(R.id.delete);
		ImageButton msg = view.findViewById(R.id.sms);
		ImageButton call = view.findViewById(R.id.call);
		ImageButton edit = view.findViewById(R.id.edit);

		delete.setOnClickListener(new DeleteListener(_currentContact));
		msg.setOnClickListener(new MsgListener());
		call.setOnClickListener(new CallListener());
		edit.setOnClickListener(new EditListener());

		return (view);
	}

	class DeleteListener implements View.OnClickListener {
		private Contact current;

		DeleteListener(Contact r) { current = r; }
		@Override
		public void onClick(View view) {
			if (!current.delete(_context))
				return ;
		}
	}

	class CallListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {

		}
	}

	class EditListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {

		}
	}

	class MsgListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {

		}
	}

}
