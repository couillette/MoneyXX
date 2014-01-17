package com.moneyxx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.adapter.TabsPagerAdapter;
import com.moneyxx.R;
import com.server.StackmobQuery;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.SimpleAdapter;

public class SettingsActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager mViewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	// tab titles
	private String[] tabs = { "Banking", "User" };
	private ArrayList<String[]> userList = new ArrayList<String[]>();
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		 
		


		// Initialization
		mViewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		// Set up the ViewPager with the sections adapter.
		mViewPager.setAdapter(mAdapter);
		// Set up the action bar.
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// Enable the callBack button
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the mviewpager make respective tab selected
		 * */
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						// on changing the page
						// make respected tab selected
						actionBar.setSelectedNavigationItem(position);
						
						

					}
					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
					}

					@Override
					public void onPageScrollStateChanged(int arg0) {
					}
				});
	}
	
	public void onClick(View view) {
		if (view.getId() == R.id.button_Settings_UserSave) {

		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		Intent myIntent;
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_money_beg:
            // action_money_beg
        	myIntent = new Intent(SettingsActivity.this, SendBegActivity.class);
        	SettingsActivity.this.startActivity(myIntent);
            return true;
        case R.id.action_money_send:
            // action_money_send
        	myIntent = new Intent(SettingsActivity.this, SendBegActivity.class);
        	SettingsActivity.this.startActivity(myIntent);
            return true;
        case R.id.action_jackpot:
            // action_jackpot
        	myIntent = new Intent(SettingsActivity.this, JackpotActivity.class);
        	SettingsActivity.this.startActivity(myIntent);
            return true;
        case R.id.action_wallet:
            // action_wallet
        	myIntent = new Intent(SettingsActivity.this, MyWalletActivity.class);
        	SettingsActivity.this.startActivity(myIntent);
            return true;
        case R.id.action_settings:
            // action_settings
        	myIntent = new Intent(SettingsActivity.this, SettingsActivity.class);
        	SettingsActivity.this.startActivity(myIntent);
            return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		
		
	}
}
