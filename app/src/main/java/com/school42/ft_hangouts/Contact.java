package com.school42.ft_hangouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.school42.ft_hangouts.database.ContactReaderContract.ContactEntry;
import com.school42.ft_hangouts.database.ContactReaderDbHelper;

import java.util.Locale;

public class Contact implements Comparable<Contact> {

	private String _id = null;
	private String _firstName;
	private String _lastName;
	private String _surname;
	private String _mail;
	private String _phone;
	// TODO IMG

	public Contact() { init(); }

	public Contact(String id, String firstName, String lastName, String surname, String mail, String phone) {
		init();
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
		setSurname(surname);
		setMail(mail);
		setPhone(phone);
	}

	@NonNull
	public String toString() {
		return ("[ID]: " + getId() + " \n" +
				"[FIRSTNAME]: " + getFirstName() + " \n" +
				"[LASTNAME]: " + getLastName() + " \n" +
				"[SURNAME]: " + getSurname() + " \n" +
				"[MAIL]: " + getMail() + " \n" +
				"[PHONE]: " + getPhone() + " \n");
	}

	private void init() {}

	public void setId(String v) { _id = v; }
	public void setFirstName(String v) { _firstName = v; }
	public void setLastName(String v) { _lastName = v; }
	public void setSurname(String v) { _surname = v; }
	public void setMail(String v) { _mail = v; }
	public void setPhone(String v) { _phone = v; }

	public String getId() { return (_id); }
	public String getFirstName() { return (_firstName); }
	public String getLastName() { return (_lastName); }
	public String getSurname() { return (_surname); }
	public String getMail() { return (_mail); }
	public String getPhone() { return (_phone); }
	public String getFullName() { return (getFirstName() + " " + getLastName()); }

	public boolean isValid() { return (!(getFirstName().isEmpty()) && !(getPhone().isEmpty())); }

	public boolean insert(Context context) {
		if (getId() != null)
			return (false);
		ContactReaderDbHelper dbHelper = new ContactReaderDbHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(ContactEntry.COLUMN_NAME_FIRSTNAME, getFirstName());
		values.put(ContactEntry.COLUMN_NAME_LASTNAME, getLastName());
		values.put(ContactEntry.COLUMN_NAME_SURNAME, getSurname());
		values.put(ContactEntry.COLUMN_NAME_MAIL, getMail());
		values.put(ContactEntry.COLUMN_NAME_PHONE, getPhone());

		long newRowId = db.insert(ContactEntry.TABLE_NAME, null, values);
		if (newRowId == -1)
			return (false);
		setId(""+newRowId);
		MainActivity.insertContact(this);
		return (true);
	}

	public boolean update(Context context) {
		if (getId() == null)
			return (false);
		ContactReaderDbHelper dbHelper = new ContactReaderDbHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(ContactEntry.COLUMN_NAME_FIRSTNAME, getFirstName());
		values.put(ContactEntry.COLUMN_NAME_LASTNAME, getLastName());
		values.put(ContactEntry.COLUMN_NAME_SURNAME, getSurname());
		values.put(ContactEntry.COLUMN_NAME_MAIL, getMail());
		values.put(ContactEntry.COLUMN_NAME_PHONE, getPhone());

		String selection = ContactEntry._ID + " LIKE ?";
		String [] selectionArgs = { getId() };

		int count = db.update(
				ContactEntry.TABLE_NAME,
				values,
				selection,
				selectionArgs);
		MainActivity.updateContact(this);
		return (count > 0);
	}

	public void delete(Context context) {
		if (getId() == null)
			return ;
		MainActivity.removeContact(this);
		ContactReaderDbHelper dbHelper = new ContactReaderDbHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		String selection = ContactEntry._ID + " LIKE ?";
		String[] selectionArgs = { getId() };
		db.delete(ContactEntry.TABLE_NAME, selection, selectionArgs);
	}

	@Override
	public int compareTo(Contact contact) {
		String a = getSurname().length() != 0 ? getSurname() : getFullName();
		String b = contact.getSurname().length() != 0 ? contact.getSurname() : contact.getFullName();
		return (a.toLowerCase().compareTo(b.toLowerCase()));
	}
}
