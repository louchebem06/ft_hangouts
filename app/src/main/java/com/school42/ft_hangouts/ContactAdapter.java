package com.school42.ft_hangouts;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		view = _inflater.inflate(R.layout.adapter_contact, null);

		Contact current = getItem(i);
		String fullname = !current.getSurname().isEmpty() ? current.getSurname() : current.getFullName();

		TextView fullnameText = view.findViewById(R.id.fullname);
		fullnameText.setText(fullname);

		ImageButton delete = view.findViewById(R.id.delete);
		ImageButton msg = view.findViewById(R.id.sms);
		ImageButton call = view.findViewById(R.id.call);
		ImageButton edit = view.findViewById(R.id.edit);

		delete.setOnClickListener(new DeleteListener(current));
		msg.setOnClickListener(new MsgListener());
		call.setOnClickListener(new CallListener(current));
		edit.setOnClickListener(new EditListener());

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

	static class EditListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {

		}
	}

	static class MsgListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {

		}
	}

}
