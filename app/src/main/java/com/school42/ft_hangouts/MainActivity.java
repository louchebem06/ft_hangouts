package com.school42.ft_hangouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.school42.ft_hangouts.database.ContactReaderDbHelper;
import com.school42.ft_hangouts.database.ContactReaderContract.ContactEntry;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

	static final private Vector<Contact> contacts = new Vector<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		createContactBtn();
		readDB();
	}

	private void createContactBtn() {
		FloatingActionButton createContactBtn;
		createContactBtn = findViewById(R.id.createContactBtn);
		createContactBtn.setOnClickListener(new createContactBtnListener());
	}

	class createContactBtnListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			Intent createContactActivity = new Intent(getApplicationContext(), CreateContactActivity.class);
			startActivity(createContactActivity);
		}
	}

	static public void updateContact(Contact contact) {
		removeContact(contact);
		insertContact(contact);
	}

	static public void removeContact(Contact contact) {
		for (int i = 0; i < contacts.size(); i++) {
			if (contacts.get(i).getId().equals(contact.getId())) {
				contacts.remove(i);
				break ;
			}
		}
		Log.e("42", "Remove\n" + contact.toString());
	}

	static public void insertContact(Contact contact) {
		contacts.add(contact);
		Log.e("42", "Insert\n" + contact.toString());
	}

	private void readDB() {
		ContactReaderDbHelper dbHelper = new ContactReaderDbHelper(getApplicationContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		String [] projection = {
				ContactEntry._ID,
				ContactEntry.COLUMN_NAME_FIRSTNAME,
				ContactEntry.COLUMN_NAME_LASTNAME,
				ContactEntry.COLUMN_NAME_SURNAME,
				ContactEntry.COLUMN_NAME_MAIL,
				ContactEntry.COLUMN_NAME_PHONE
		};

		Cursor cursor = db.query(ContactEntry.TABLE_NAME,projection,null, null, null,null,null);

		while(cursor.moveToNext()) {
			String id = ""+cursor.getLong(cursor.getColumnIndexOrThrow(ContactEntry._ID));
			String firstName = cursor.getString(cursor.getColumnIndexOrThrow(ContactEntry.COLUMN_NAME_FIRSTNAME));
			String lastName = cursor.getString(cursor.getColumnIndexOrThrow(ContactEntry.COLUMN_NAME_LASTNAME));
			String surname = cursor.getString(cursor.getColumnIndexOrThrow(ContactEntry.COLUMN_NAME_SURNAME));
			String mail = cursor.getString(cursor.getColumnIndexOrThrow(ContactEntry.COLUMN_NAME_MAIL));
			String phone = cursor.getString(cursor.getColumnIndexOrThrow(ContactEntry.COLUMN_NAME_PHONE));
			Contact contact = new Contact(id, firstName, lastName, surname, mail, phone);
			contacts.add(contact);
		}
		cursor.close();

		for (Contact contact : contacts)
			Log.e("42", contact.toString());
	}

}