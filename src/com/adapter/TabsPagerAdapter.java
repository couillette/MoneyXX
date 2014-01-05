package com.adapter;

import com.moneyxx.BankSettingsFragment;
import com.moneyxx.UserSettingsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter{

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int index) {
		switch(index) {
		case 0:
			//Bank Settings Activity
			return new BankSettingsFragment();
		case 1:
			//User Settings Activity
			return new UserSettingsFragment();
		}
		return null;
	}

	@Override
	public int getCount() {
		// equal to tab number
		return 2;
	}
	

}
