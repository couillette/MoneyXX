package com.server;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.moneyxx.MainActivity;
import com.moneyxx.Sign_upActivity;

public class PhoneData {

	
	public void savePrefs(Context context, String key, boolean value){
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor edit = pref.edit();
		edit.putBoolean(key, value);
		edit.apply();
	}
	
	public void savePrefs(Context context, String key, String value){
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor edit = pref.edit();
		edit.putString(key, value);
		edit.apply();
	}

}
