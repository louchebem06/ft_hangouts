package com.school42.ft_hangouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/*
* for remove header, place this function in onCreate()
* getSupportActionBar().hide();
*/

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS}, 1);

		createContactBtn();
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

}