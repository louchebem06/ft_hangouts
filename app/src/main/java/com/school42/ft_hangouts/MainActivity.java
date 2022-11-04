package com.school42.ft_hangouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

	private FloatingActionButton createContactBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS}, 1);

		createContactBtn = findViewById(R.id.createContactBtn);

		createContactBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent createContactActivity = new Intent(getApplicationContext(), CreateContactActivity.class);
				startActivity(createContactActivity);
				// finish();
			}
		});
	}

}