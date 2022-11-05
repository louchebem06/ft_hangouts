package com.school42.ft_hangouts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.school42.ft_hangouts.database.ContactReaderContract.ContactEntry;

public class ContactReaderDbHelper extends SQLiteOpenHelper {

	private static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + ContactEntry.TABLE_NAME + " (" +
					ContactEntry._ID + " INTEGER PRIMARY KEY," +
					ContactEntry.COLUMN_NAME_FIRSTNAME + " TEXT," +
					ContactEntry.COLUMN_NAME_LASTNAME + " TEXT," +
					ContactEntry.COLUMN_NAME_SURNAME + " TEXT," +
					ContactEntry.COLUMN_NAME_MAIL + " TEXT," +
					ContactEntry.COLUMN_NAME_PHONE + " TEXT)";

	private static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + ContactReaderContract.ContactEntry.TABLE_NAME;

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "contact.db";

	public ContactReaderDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

}