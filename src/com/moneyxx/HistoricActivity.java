package com.moneyxx;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

public class HistoricActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historic);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.historic, menu);
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
		        	myIntent = new Intent(HistoricActivity.this, SendBegActivity.class);
		        	HistoricActivity.this.startActivity(myIntent);
		            return true;
		        case R.id.action_money_send:
		            // action_money_send
		        	myIntent = new Intent(HistoricActivity.this, SendBegActivity.class);
		        	HistoricActivity.this.startActivity(myIntent);
		            return true;
		        case R.id.action_jackpot:
		            // action_jackpot
		        	myIntent = new Intent(HistoricActivity.this, JackpotActivity.class);
		        	HistoricActivity.this.startActivity(myIntent);
		            return true;
		        case R.id.action_wallet:
		            // action_wallet
		        	myIntent = new Intent(HistoricActivity.this, MyWalletActivity.class);
		        	HistoricActivity.this.startActivity(myIntent);
		            return true;
				default:
					return super.onOptionsItemSelected(item);
				}
			}

}
