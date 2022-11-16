package com.school42.ft_hangouts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class EditContactActivity extends AppCompatActivity {

	private static Contact _contact = null;

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
		setElements();

		saveBtn.setOnClickListener(new BtnEvent());
	}

	public static void setContact(Contact contact) { _contact = contact; }

	private void getElements() {
		saveBtn = findViewById(R.id.saveBtn);
		firstName = findViewById(R.id.firstName);
		lastName = findViewById(R.id.lastName);
		surName = findViewById(R.id.surname);
		mail = findViewById(R.id.mail);
		phone = findViewById(R.id.phone);
	}

	private void setElements() {
		if (_contact == null)
			return ;
		if (_contact.getFirstName() != null)
			firstName.setText(_contact.getFirstName());
		if (_contact.getLastName() != null)
			lastName.setText(_contact.getLastName());
		if (_contact.getSurname() != null)
			surName.setText(_contact.getSurname());
		if (_contact.getMail() != null)
			mail.setText(_contact.getMail());
		if (_contact.getPhone() != null)
			phone.setText(_contact.getPhone());
	}

	class BtnEvent implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			Contact contact = _contact;

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

			boolean state = contact.update(getApplicationContext());

			if (state) {
				Snackbar.make(findViewById(R.id.CreateContactLayout),
						R.string.updateContactSuccess,
						2000).show();
				firstName.setText("");
				lastName.setText("");
				surName.setText("");
				mail.setText("");
				phone.setText("");
				finish();
			} else {
				Snackbar.make(findViewById(R.id.CreateContactLayout),
						R.string.errorUpdate,
						2000).show();
			}
		}
	}

}
