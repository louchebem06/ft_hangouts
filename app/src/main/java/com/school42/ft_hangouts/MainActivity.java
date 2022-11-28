package com.school42.ft_hangouts;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuItemWrapperICS;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.school42.ft_hangouts.database.ContactReaderContract.ContactEntry;
import com.school42.ft_hangouts.database.ContactReaderDbHelper;

import java.util.Collections;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

	static final private Vector<Contact> contacts = new Vector<>();
	@SuppressLint("StaticFieldLeak")
	static private ContactAdapter contactAdapter = null;
	static public String color = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getPermission();

		createContactBtn();
		readDB();

		ListView listContact = findViewById(R.id.listContact);
		contactAdapter = new ContactAdapter(this, contacts);
		Collections.sort(contacts);
		listContact.setAdapter(contactAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@SuppressLint("NonConstantResourceId")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.green:
				color = "#00FF00";
				break ;
			case R.id.red:
				color = "#FF0000";
				break ;
			case R.id.blue:
				color = "#0000FF";
				break ;
			case R.id.reset:
				color = "#6200EE";
				break ;
		}
		ActionBar actionBar = getSupportActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(color));
		assert actionBar != null;
		actionBar.setBackgroundDrawable(colorDrawable);
		return (true);
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

	private void getPermission() {
		if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED ||
				ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED ||
				ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
				ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ) {
			ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.READ_SMS,
							Manifest.permission.RECEIVE_SMS,
							Manifest.permission.SEND_SMS,
							Manifest.permission.CALL_PHONE}, PackageManager.PERMISSION_GRANTED);
		}
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
		Collections.sort(contacts);
		contactAdapter.notifyDataSetChanged();
	}

	static public void insertContact(Contact contact) {
		contacts.add(contact);
		Collections.sort(contacts);
		contactAdapter.notifyDataSetChanged();
	}

	static public Vector<Contact> getContacts() {
		return (contacts);
	}

	private void readDB() {
		if (contacts.size() != 0)
			return ;
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
	}

}
