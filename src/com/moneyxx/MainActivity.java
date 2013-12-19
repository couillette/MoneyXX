package com.moneyxx;

import com.moneyxx.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;


public class MainActivity extends Activity {
	
	Intent myIntent;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
	}
	
	//method called at button click
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.button_Send:
			myIntent = new Intent(MainActivity.this, SendBegActivity.class);
			MainActivity.this.startActivity(myIntent);
//		case
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
