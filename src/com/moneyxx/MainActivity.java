package com.moneyxx;

import com.entity.UserRegistered;
import com.moneyxx.R;
import com.stackmob.android.sdk.common.StackMobAndroid;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	// blop2 jIB
	Intent myIntent;

	private String username;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loadPrefs();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.action_money_beg:
            // action_money_beg
        	myIntent = new Intent(MainActivity.this, SendBegActivity.class);
			MainActivity.this.startActivity(myIntent);
            return true;
        case R.id.action_money_send:
            // action_money_send
        	myIntent = new Intent(MainActivity.this, SendBegActivity.class);
			MainActivity.this.startActivity(myIntent);
            return true;
        case R.id.action_jackpot:
            // action_jackpot
        	myIntent = new Intent(MainActivity.this, JackpotActivity.class);
			MainActivity.this.startActivity(myIntent);
            return true;
        case R.id.action_wallet:
            // action_wallet
        	myIntent = new Intent(MainActivity.this, MyWalletActivity.class);
			MainActivity.this.startActivity(myIntent);
            return true;
        case R.id.action_settings:
            // action_settings
        	myIntent = new Intent(MainActivity.this, SettingsActivity.class);
			MainActivity.this.startActivity(myIntent);
            return true;
        
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	
	// method called at button click
	public void onClick(View view) {
		switch (view.getId()) {

		// if user click on "Send Money"
		case R.id.button_Send:
		case R.id.button_Beg:
			myIntent = new Intent(MainActivity.this, SendBegActivity.class);
			MainActivity.this.startActivity(myIntent);
			break;

		// if user click on "Beg Money"
		case R.id.button_Jackpot:
			myIntent = new Intent(MainActivity.this, JackpotActivity.class);
			MainActivity.this.startActivity(myIntent);
			break;

		// if user click on "Wallet"
		case R.id.button_Wallet:
			myIntent = new Intent(MainActivity.this, MyWalletActivity.class);
			MainActivity.this.startActivity(myIntent);
			break;

		}
	}

	private void loadPrefs() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		String username = pref.getString("USERNAME", null);
		TextView user_info = (TextView) findViewById(R.id.textView_User_Info);
		user_info.append(username + " !");
	}

}
