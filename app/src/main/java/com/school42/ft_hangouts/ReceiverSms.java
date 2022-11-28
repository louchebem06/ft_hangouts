package com.school42.ft_hangouts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Vector;

public class ReceiverSms extends BroadcastReceiver {
	private static final String TAG = ReceiverSms.class.getSimpleName();
	public static final String pdu_type = "pdus";

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Object[] pdus = (Object[]) bundle.get(pdu_type);
		Vector<Contact> newContacts = new Vector<>();
		if (pdus != null) {
			SmsMessage[] msgs = new SmsMessage[pdus.length];
			for (int i = 0; i < msgs.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				String phone = msgs[i].getOriginatingAddress();
				String msg = msgs[i].getMessageBody();
				Contact contact = new Contact();
				contact.setPhone(phone);
				contact.setFirstName(phone);
				contact.setSurname("");
				contact.setLastName("");
				contact.setMail("");
				newContacts.add(contact);
			}
		}
		Vector<Contact> existContact = MainActivity.getContacts();
		for (Contact newContact : newContacts) {
			boolean found = false;
			for (Contact exist : existContact) {
				if (newContact.getPhone().equals(exist.getPhone())) {
					found = true;
					break ;
				}
			}
			if (!found) {
				newContact.insert(context);
			}
		}
	}
}
