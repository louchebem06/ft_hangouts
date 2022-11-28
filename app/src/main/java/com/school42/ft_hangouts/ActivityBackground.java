package com.school42.ft_hangouts;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

public class ActivityBackground {
	static private Date date;
	static private Context context;

	static public void setResume(Context c) {
		notification();
		context = c;
	}

	static public void setPause() {
		date = new Date();
		notification();
	}

	static public void notification() {
		if (!(date != null && new Date().getTime() - date.getTime() > 1000))
			return ;
		Activity activity = (Activity) context;
		View view = activity.getWindow().getDecorView().getRootView();
		Snackbar.make(view, date.toString(), 5000).show();
	}
}
