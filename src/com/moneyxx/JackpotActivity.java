package com.moneyxx;

import com.entity.Project;
import com.entity.ProjectAccount;
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
import android.view.View;

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
	
	
	public void onClick(View view) {
		
		switch (view.getId()) {
		case R.id.imageButton_jackpot_1:
			Intent i1 = new Intent(JackpotActivity.this,JackpotProjectActivity.class);
			Bundle b1 = new Bundle();
			b1.putInt("key",1);
			i1.putExtras(b1);
			JackpotActivity.this.startActivity(i1);
		break;
		case R.id.imageButton_jackpot_2:
			Intent i2 = new Intent(JackpotActivity.this,JackpotProjectActivity.class);
			Bundle b2 = new Bundle();
			b2.putInt("key",2);
			i2.putExtras(b2);
			JackpotActivity.this.startActivity(i2);
		break;
		case R.id.imageButton_jackpot_3:
			Intent i3 = new Intent(JackpotActivity.this,JackpotProjectActivity.class);
			Bundle b3 = new Bundle();
			b3.putInt("key",3);
			i3.putExtras(b3);
			JackpotActivity.this.startActivity(i3);
		break;
		case R.id.imageButton_jackpot_4:
			Intent i4 = new Intent(JackpotActivity.this,JackpotProjectActivity.class);
			Bundle b4 = new Bundle();
			b4.putInt("key",4);
			i4.putExtras(b4);
			JackpotActivity.this.startActivity(i4);
		break;
		case R.id.button_jackpot_1:
			
//			ProjectAccount paccount = new ProjectAccount("11111524213", "0");
//			paccount.save();
//			Project p = new Project("Le schisme littéraire des témoignages de la Grande Guerre", "project1@gmail.com");
//			p.setID("1");
//			p.setProject_account(paccount);
//			p.save();
//			
//			
//			ProjectAccount p2account = new ProjectAccount("2222435465", "0");
//			p2account.save();
//			Project p2 = new Project("Tant va la cruche à l'eau qu'à la fin ...", "project2@gmail.com");
//			p2.setID("2");
//			p2.setProject_account(paccount);
//			p2.save();
//			
//			
//			ProjectAccount p3account = new ProjectAccount("333365424213", "0");
//			p3account.save();
//			Project p3 = new Project("Les élites françaises ont honte de la France", "project3@gmail.com");
//			p3.setID("3");
//			p3.setProject_account(paccount);
//			p3.save();
//			
//			
//			ProjectAccount p4account = new ProjectAccount("4444436524213", "0");
//			p4account.save();
//			Project p4 = new Project("A ciel ouvert : La folie à l'âge tendre", "project4@gmail.com");
//			p4.setID("4");
//			p3.setProject_account(paccount);
//			p4.save();
			
			
			Intent intent = new Intent(JackpotActivity.this,JackpotFormActivity.class);
			JackpotActivity.this.startActivity(intent);
			finish();
		break;

		}
	}
	
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
        	this.finish();
            return true;
        case R.id.action_money_send:
            // action_money_send
        	myIntent = new Intent(JackpotActivity.this, SendBegActivity.class);
        	JackpotActivity.this.startActivity(myIntent);
        	this.finish();
            return true;
        case R.id.action_jackpot:
            // action_jackpot
        	myIntent = new Intent(JackpotActivity.this, JackpotActivity.class);
        	JackpotActivity.this.startActivity(myIntent);
        	this.finish();
            return true;
        case R.id.action_wallet:
            // action_wallet
        	myIntent = new Intent(JackpotActivity.this, MyWalletActivity.class);
        	JackpotActivity.this.startActivity(myIntent);
        	this.finish();
            return true;
        case R.id.action_settings:
            // action_settings
        	myIntent = new Intent(JackpotActivity.this, SettingsActivity.class);
        	JackpotActivity.this.startActivity(myIntent);
        	this.finish();
            return true;      
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
