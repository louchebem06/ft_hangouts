package com.school42.ft_hangouts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
								"Please input firstname and phone",
								1000).show();
				return ;
			}

			boolean state = contact.insert(getApplicationContext());

			if (state) {
				Snackbar.make(findViewById(R.id.CreateContactLayout),
								"Contact create",
								2000).show();
				firstName.setText("");
				lastName.setText("");
				surName.setText("");
				mail.setText("");
				phone.setText("");
			} else {
				Snackbar.make(findViewById(R.id.CreateContactLayout),
									"Create contact fail",
									2000).show();
			}
		}
	}

}