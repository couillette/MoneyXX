package com.moneyxx;

import com.moneyxx.R;
import com.server.Mail;
import com.server.SendEmailAsyncTask;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WalletActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wallet);
		
		
		
		//Enable the callBack button
		getActionBar().setDisplayHomeAsUpEnabled(true);;
		
	}
	
	public void onClick(View view){
		
		String[] mailAddress = {"s_couillette@hotmail.com"};
		String sub = "Android Application";
		String message = "you just receive a lot of money";
		new SendEmailAsyncTask(mailAddress, sub, message).execute();
	}


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wallet, menu);
		return true;
	}
	
	//Set the call back to return to previous activity
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
