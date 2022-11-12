package com.school42.ft_hangouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class MessageActivity extends AppCompatActivity {

	static private Contact _contact;
	private ImageButton send;
	private EditText msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		send = findViewById(R.id.sendButton);
		msg = findViewById(R.id.messageInput);

		send.setOnClickListener(new SendEvent(getApplicationContext(), _contact, msg));
	}

	static public void setContact(Contact contact) { _contact = contact; }

	static class SendEvent implements View.OnClickListener {
		private Context _context;
		private Contact _contact;
		private EditText _message;

		SendEvent(Context context, Contact contact, EditText msg) {
			_context = context;
			_contact = contact;
			_message = msg;
		}

		@Override
		public void onClick(View view) {
			if (_message.getText().toString().length() == 0)
				return ;
			String phone = _contact.getPhone();
			String msg = _message.getText().toString();
			_message.setText("");
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(phone,null, msg, null, null);
		}
	}
}