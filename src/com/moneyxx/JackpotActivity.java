package com.moneyxx;

import com.entity.UserAccount;
import com.entity.UserRegistered;
import com.moneyxx.R;
import com.server.StackmobQuery;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

public class JackpotActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jackpot);

		// Enable the callBack button
		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jackpot, menu);
		return true;
	}

	
	//Set the call back to return to previous activity
		@Override
		public boolean onOptionsItemSelected(MenuItem item){
			Intent myIntent ;
			switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
			case R.id.action_money_beg:
	            // action_money_beg
	        	myIntent = new Intent(JackpotActivity.this, SendBegActivity.class);
	        	JackpotActivity.this.startActivity(myIntent);
	            return true;
	        case R.id.action_money_send:
	            // action_money_send
	        	myIntent = new Intent(JackpotActivity.this, SendBegActivity.class);
	        	JackpotActivity.this.startActivity(myIntent);
	            return true;
	        case R.id.action_jackpot:
	            // action_jackpot
	        	myIntent = new Intent(JackpotActivity.this, JackpotActivity.class);
	        	JackpotActivity.this.startActivity(myIntent);
	            return true;
	        case R.id.action_wallet:
	            // action_wallet
	        	myIntent = new Intent(JackpotActivity.this, MyWalletActivity.class);
	        	JackpotActivity.this.startActivity(myIntent);
	            return true;
	        case R.id.action_settings:
	            // action_settings
	        	myIntent = new Intent(JackpotActivity.this, SettingsActivity.class);
				JackpotActivity.this.startActivity(myIntent);
	            return true;      
	            
			default:
				return super.onOptionsItemSelected(item);
			}
		}

}
