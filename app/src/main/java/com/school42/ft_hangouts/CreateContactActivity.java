package com.school42.ft_hangouts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class CreateContactActivity extends AppCompatActivity {

	private Button saveBtn;
	private TextInputEditText firstName;
	private TextInputEditText lastName;
	private TextInputEditText surName;
	private TextInputEditText mail;
	private TextInputEditText phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_contact);

		getElements();

		saveBtn.setOnClickListener(new BtnEvent());
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

	private void getElements() {
		saveBtn = findViewById(R.id.saveBtn);
		firstName = findViewById(R.id.firstName);
		lastName = findViewById(R.id.lastName);
		surName = findViewById(R.id.surname);
		mail = findViewById(R.id.mail);
		phone = findViewById(R.id.phone);
	}

	class BtnEvent implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			Contact contact = new Contact();

			contact.setFirstName(String.valueOf(firstName.getText()));
			contact.setLastName(String.valueOf(lastName.getText()));
			contact.setSurname(String.valueOf(surName.getText()));
			contact.setMail(String.valueOf(mail.getText()));
			contact.setPhone(String.valueOf(phone.getText()));

			if (!contact.isValid()) {
				Snackbar.make(findViewById(R.id.CreateContactLayout),
								R.string.errorCreateContact,
								1000).show();
				return ;
			}

			boolean state = contact.insert(getApplicationContext());

			if (state) {
				Snackbar.make(findViewById(R.id.CreateContactLayout),
								R.string.contactCreate,
								2000).show();
				firstName.setText("");
				lastName.setText("");
				surName.setText("");
				mail.setText("");
				phone.setText("");
				finish();
			} else {
				Snackbar.make(findViewById(R.id.CreateContactLayout),
									R.string.createContactFail,
									2000).show();
			}
		}
	}

}