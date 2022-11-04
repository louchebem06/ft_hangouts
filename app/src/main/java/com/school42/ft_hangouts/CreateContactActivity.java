package com.school42.ft_hangouts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.net.URI;
import java.util.ArrayList;

public class CreateContactActivity extends AppCompatActivity {

	private Button saveBtn;
	private TextInputEditText nameInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_contact);

		ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_CONTACTS}, 1);

		saveBtn = findViewById(R.id.saveBtn);
		nameInput = findViewById(R.id.nameInput);

		saveBtn.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("WorldWriteableFiles")
			@Override
			public void onClick(View view) {
				Contact contact = new Contact();
				contact.setFirstName(nameInput.getText().toString());

				writeContact(contact.getFirstName(), "+33650489457");

				finish();
			}
		});
	}

	private void writeContact(String name, String number) {
		ArrayList < ContentProviderOperation > ops = new ArrayList < ContentProviderOperation > ();

		ops.add(ContentProviderOperation.newInsert(
						ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
				.build());

		//------------------------------------------------------ Names
		if (name != null) {
			ops.add(ContentProviderOperation.newInsert(
							ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(ContactsContract.Data.MIMETYPE,
							ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
					.withValue(
							ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
							name).build());
		}

		//------------------------------------------------------ Mobile Number
		if (number != null) {
			ops.add(ContentProviderOperation.
					newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(ContactsContract.Data.MIMETYPE,
							ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number)
					.withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
							ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
					.build());
		}

//		//------------------------------------------------------ Home Numbers
//		if (HomeNumber != null) {
//			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//					.withValue(ContactsContract.Data.MIMETYPE,
//							ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//					.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, HomeNumber)
//					.withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
//							ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
//					.build());
//		}
//
//		//------------------------------------------------------ Work Numbers
//		if (WorkNumber != null) {
//			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//					.withValue(ContactsContract.Data.MIMETYPE,
//							ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//					.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, WorkNumber)
//					.withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
//							ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
//					.build());
//		}
//
//		//------------------------------------------------------ Email
//		if (emailID != null) {
//			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//					.withValue(ContactsContract.Data.MIMETYPE,
//							ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
//					.withValue(ContactsContract.CommonDataKinds.Email.DATA, emailID)
//					.withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
//					.build());
//		}
//
//		//------------------------------------------------------ Organization
//		if (!company.equals("") && !jobTitle.equals("")) {
//			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//					.withValue(ContactsContract.Data.MIMETYPE,
//							ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
//					.withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
//					.withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
//					.withValue(ContactsContract.CommonDataKinds.Organization.TITLE, jobTitle)
//					.withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
//					.build());
//		}

		// Asking the Contact provider to create a new contact
		try {
			getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}