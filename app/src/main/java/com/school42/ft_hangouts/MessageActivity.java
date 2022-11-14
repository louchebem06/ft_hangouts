package com.school42.ft_hangouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.snackbar.Snackbar;


public class MessageActivity extends AppCompatActivity {

	static private Contact _contact;
	private ImageButton send;
	private EditText msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);

		send = findViewById(R.id.sendButton);
		msg = findViewById(R.id.messageInput);

		send.setOnClickListener(new SendEvent(getApplicationContext(), _contact, msg));
	}

	static public void setContact(Contact contact) { _contact = contact; }

	class SendEvent implements View.OnClickListener {
		private Contact _contact;
		private EditText _message;
		private Context _context;

		SendEvent(Context context, Contact contact, EditText msg) {
			_contact = contact;
			_message = msg;
			_context = context;
		}

		@Override
		public void onClick(View view) {
			if (_message.getText().toString().length() == 0)
				return ;
			if (ActivityCompat.checkSelfPermission(_context, Manifest.permission.SEND_SMS) < 0) {
				Snackbar.make(findViewById(R.id.MessageLayout),
						"Please activate permission for send SMS",
						2000).show();
				return ;
			}
			String phone = _contact.getPhone();
			String msg = _message.getText().toString();
			_message.setText("");
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(phone, null, msg, null, null);
		}
	}
}