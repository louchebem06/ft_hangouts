package com.school42.ft_hangouts.database;

import android.provider.BaseColumns;

public final class ContactReaderContract {

	private ContactReaderContract() {}

	public static class ContactEntry implements BaseColumns {
		public static final String TABLE_NAME = "contact";
		public static final String COLUMN_NAME_FIRSTNAME = "firstname";
		public static final String COLUMN_NAME_LASTNAME = "lastname";
		public static final String COLUMN_NAME_SURNAME = "surname";
		public static final String COLUMN_NAME_MAIL = "mail";
		public static final String COLUMN_NAME_PHONE = "phone";
	}

}
