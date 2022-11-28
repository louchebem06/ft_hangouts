package com.school42.ft_hangouts;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.provider.Telephony;
import android.widget.ListView;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.util.Vector;


public class MessageActivity extends AppCompatActivity {

	static private Contact _contact;
	static private final Vector<Message> messages = new Vector<>();
	static private MessageAdapter messageAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		if (messages != null)
			messages.clear();

		ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);

		ImageButton send = findViewById(R.id.sendButton);
		EditText msg = findViewById(R.id.messageInput);

		readSms();

		ListView listMessage = findViewById(R.id.listMessage);
		messageAdapter = new MessageAdapter(this, messages);
		listMessage.setAdapter(messageAdapter);
		listMessage.setSelection(messageAdapter.getCount() - 1);

		send.setOnClickListener(new SendEvent(getApplicationContext(), _contact, msg, messageAdapter));

		String name = _contact.getSurname();
		if (name.length() == 0)
			name = _contact.getFullName();
		setTitle(name);

		ActionBar actionBar = getSupportActionBar();
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);
		if (MainActivity.color.length() > 0) {
			ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(MainActivity.color));
			actionBar.setBackgroundDrawable(colorDrawable);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	static public void newMessage(Message message) {
		addMessage(message);
		messageAdapter.notifyDataSetChanged();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		ActivityBackground.setResume(this);
	}

	@Override
	public void onPause()
	{
		super.onPause();
		ActivityBackground.setPause();
	}

	public void readSms() {
		String number = Telephony.TextBasedSmsColumns.ADDRESS;
		String msg = Telephony.TextBasedSmsColumns.BODY;
		String type = Telephony.TextBasedSmsColumns.TYPE;

		Cursor cursor = getContentResolver().query(
			Telephony.Sms.CONTENT_URI,
			new String[]{number, msg, type},
		number + "=?",
			new String[]{_contact.getPhone()},
		"_id ASC"
		);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			int numberColId = cursor.getColumnIndex(number);
			int msgColId = cursor.getColumnIndex(msg);
			int typeColId = cursor.getColumnIndex(type);

			Message message = new Message(
					cursor.getString(numberColId),
					cursor.getString(msgColId),
					cursor.getString(typeColId)
			);

			messages.add(message);
			cursor.moveToNext();
		}
		cursor.close();
	}

	static public void setContact(Contact contact) { _contact = contact; }
	static public void addMessage(Message message) {
		if (!(_contact.getPhone().equals(message.getNumber())))
			return ;
		messages.add(message);
		messageAdapter.notifyDataSetChanged();
	}

	class SendEvent implements View.OnClickListener {
		private final Contact _contact;
		private final EditText _message;
		private final Context _context;
		private final MessageAdapter _messageAdapter;

		SendEvent(Context context, Contact contact, EditText msg, MessageAdapter adaptater) {
			_contact = contact;
			_message = msg;
			_context = context;
			_messageAdapter = adaptater;
		}

		@Override
		public void onClick(View view) {
			if (_message.getText().toString().length() == 0)
				return ;
			if (ActivityCompat.checkSelfPermission(_context, Manifest.permission.SEND_SMS) < 0) {
				Snackbar.make(findViewById(R.id.MessageLayout),
						R.string.addPermissionSendSMS,
						2000).show();
				return ;
			}
			String phone = _contact.getPhone();
			String msg = _message.getText().toString();
			_message.setText("");
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(phone, null, msg, null, null);
			addMessage(new Message(phone, msg, "2"));
			Snackbar.make(findViewById(R.id.MessageLayout),
					R.string.msgSendSuccess,
					2000).show();
		}
	}
}