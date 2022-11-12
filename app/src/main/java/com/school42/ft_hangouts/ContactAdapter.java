package com.school42.ft_hangouts;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import java.util.Vector;

public class ContactAdapter extends BaseAdapter {

	private final Context _context;
	private final Vector<Contact> _vectorContact;
	private final LayoutInflater _inflater;

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

	@SuppressLint({"ViewHolder", "InflateParams"})
	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		view = _inflater.inflate(R.layout.adapter_contact, null);

		Contact current = getItem(i);
		String fullName = !current.getSurname().isEmpty() ? current.getSurname() : current.getFullName();

		TextView fullNameText = view.findViewById(R.id.fullname);
		fullNameText.setText(fullName);

		ImageButton delete = view.findViewById(R.id.delete);
		ImageButton msg = view.findViewById(R.id.sms);
		ImageButton call = view.findViewById(R.id.call);
		ImageButton edit = view.findViewById(R.id.edit);

		delete.setOnClickListener(new DeleteListener(current));
		msg.setOnClickListener(new MsgListener(current));
		call.setOnClickListener(new CallListener(current));
		edit.setOnClickListener(new EditListener(current));

		return (view);
	}

	class DeleteListener implements View.OnClickListener {
		private final Contact current;

		DeleteListener(Contact r) { current = r; }

		@Override
		public void onClick(View view) {
			current.delete(_context);
		}
	}

	class CallListener implements View.OnClickListener {
		private final Contact current;

		CallListener(Contact r) { current = r; }

		@Override
		public void onClick(View view) {
			String phone = current.getPhone();
			Intent call = new Intent(Intent.ACTION_CALL);
			call.setData(Uri.parse("tel:" + phone));
			try {
				_context.startActivity(call);
			} catch (Exception e) {
				ActivityCompat.requestPermissions((Activity) _context,new String[]{Manifest.permission.CALL_PHONE}, 1);
			}
		}
	}

	class EditListener implements View.OnClickListener {
		private final Contact current;

		EditListener(Contact contact) { current = contact; }

		@Override
		public void onClick(View view) {
			EditContactActivity.setContact(current);
			Intent Edit = new Intent(_context, EditContactActivity.class);
			try {
				_context.startActivity(Edit);
			} catch (Exception e) {
				Log.e("42", e.getMessage());
			}
		}
	}

	class MsgListener implements View.OnClickListener {
		private final Contact current;

		MsgListener(Contact contact) { current = contact; }

		@Override
		public void onClick(View view) {
			MessageActivity.setContact(current);
			Intent Msg = new Intent(_context, MessageActivity.class);
			try {
				_context.startActivity(Msg);
			} catch (Exception e) {
				Log.e("42", e.getMessage());
			}
		}
	}

}
