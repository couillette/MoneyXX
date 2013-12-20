package com.moneyxx;

import com.entity.SignedUser;
import com.moneyxx.R.id;
import com.stackmob.android.sdk.common.StackMobAndroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Sign_upActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		//Store User settings on the application data
//		setSharedPreferences();
		
		//connection to the Stackmob Backend services
		StackMobAndroid.init(getApplicationContext(), 0, "2e24fee1-d4e5-4465-85cd-c7623de7cf71");
		
		

	}
	
	
	/* Sign_up activity should be display only at the first time. For this we need to store preference
	 * the first time is going to get the value from a boolean (Sign_upActivated) saved on such 
	 * preferences (Sign_upPreferences)
	 * if it doesn't find any value it will return false and this activity will be display
	 * if it finds some value this MainActivity will be started and Sign_upActivity be closed
	 * 
	 * !! launcher activity ---> edit the the manifest !!
	 */
//	private void setSharedPreferences(){
//		SharedPreferences pref = getSharedPreferences("Sign_upPreferences", Context.MODE_PRIVATE);
//		if(pref.getBoolean("Sign_upActivated", false)){
//			Intent intent = new Intent(Sign_upActivity.this, MainActivity.class);
//			startActivity(intent);
//			finish();
//		}else {
//			Editor edit = pref.edit();
//			edit.putBoolean("Sign_upActivated", true);
//			// "edit.apply()" is asynch and "edit.commit()" is synchronous 
//			edit.apply();
//		}
//	}
	
	
	public void onClick(View view){
			if(view.getId() == R.id.button_SignUp) {
				
				//retrieve data from the Sign_up form
				String Username = ((EditText) findViewById(R.id.editText_Username)).getText().toString();
				String pwd = ((EditText) findViewById(R.id.editText_Password)).getText().toString();
				String email = ((EditText) findViewById(R.id.editText_Email)).getText().toString();
				int creditCard = Integer.parseInt(((EditText) findViewById(R.id.editText_CreditCard)).getText().toString());
				
				//Store User Information on Stackmob
				SignedUser user = new SignedUser(Username, pwd, email, creditCard);
				user.save();
				
				//Go to MainActivity
				Intent intent = new Intent(Sign_upActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}

}
