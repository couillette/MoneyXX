package com.moneyxx;

import com.entity.SignedUser;
import com.stackmob.android.sdk.common.StackMobAndroid;
import com.stackmob.sdk.callback.StackMobModelCallback;
import com.stackmob.sdk.exception.StackMobException;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.Spannable;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Sign_upActivity extends Activity {
	
	EditText et_Username; 
	EditText et_Pwd;
	EditText et_Email;
	EditText et_CreditCard;
	TextView error_message;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		et_Username = (EditText) findViewById(R.id.editText_Username);
		et_Pwd = (EditText) findViewById(R.id.editText_Password);
		et_Email = (EditText) findViewById(R.id.editText_Email);
		et_CreditCard = (EditText) findViewById(R.id.editText_CreditCard);
		
		//connection to the Stackmob Backend services
		StackMobAndroid.init(getApplicationContext(), 0, "2e24fee1-d4e5-4465-85cd-c7623de7cf71");
		
		//display this activity at first launch only
		loadPrefs();

	}
	
	
	public void onClick(View view) {
		if (view.getId() == R.id.button_SignUp) {

			// retrieve data from the Sign_up form
			String usernameval = et_Username.getText().toString();
			String pwd = et_Pwd.getText().toString();
			String email = et_Email.getText().toString();
			String creditCard = et_CreditCard.getText().toString();

			if (!(usernameval.trim().equals("") || pwd.trim().equals("")
					|| email.trim().equals("") || creditCard.trim().equals(""))) {
				
				
				
				
				
				
				
				if (isValidEmail(email)) {
					// Store User Information on Stackmob
					final SignedUser user = new SignedUser(usernameval, pwd, email,Integer.parseInt(creditCard));
					user.save(new StackMobModelCallback() {
						
						@Override
						public void failure(StackMobException arg0) {
							//StackMobCallbacks come back on a different thread
							runOnUiThread(new Runnable() {	
								@Override
								public void run() {
									// Display error message when email is not valid
									error_message = (TextView) findViewById(R.id.textView_singup);
									String text = "\n\nchoose another username this one is already used" +
											"if you don't want to split your money ";
									appendTextDifSize(error_message, text, 0.5f);
								}
							});
						}
						
						@Override
						public void success() {
							// Store settings on the application data
							// this will skip this activity at next start
//							savePrefs("SINGUP", true);
							// this permit to save username to retrieve it from
							// stackmod
//							savePrefs("USERNAME", usernameval);
//							savePrefs("EMAIL", email);
							
							//StackMobCallbacks come back on a different thread
							runOnUiThread(new Runnable() {	
								@Override
								public void run() {
									// Go to MainActivity
									Intent intent = new Intent(Sign_upActivity.this,
											MainActivity.class);
									startActivity(intent);
									finish();
								}
							});
						}
					});


				} else {
					// Display error message when email is not valid
					error_message = (TextView) findViewById(R.id.textView_singup);
					String text = "\n\nemail not valid if you want to earn money";
					appendTextDifSize(error_message, text, 0.5f);
				}

			} else {
				// Display error message when required field are not complete
				error_message = (TextView) findViewById(R.id.textView_singup);
				String text = "\n\nYou need to fill the required field to earn money";
				appendTextDifSize(error_message, text, 0.5f);
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}
	
	
	public void appendTextDifSize(TextView tv, String text, float size){
		int start = tv.getText().length();
		
		if(start>6){
			char[] charList = tv.getText().toString().toCharArray();
			int i = 0;
			while (charList[i] !='\n'){
				i++; }
			start = i;
			CharSequence prevText = tv.getText().subSequence(0, i);
			tv.setText(prevText);
		}
		
		tv.append(text);
		int end = tv.getText().length();
			
		Spannable span = (Spannable) tv.getText();
		span.setSpan(new RelativeSizeSpan(size), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	}
	
	
	public boolean isValidEmail(CharSequence email){
		if(email == null){
			return false;
		}else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
		}
	}
	
	
	
	private void loadPrefs(){
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		boolean signupIsAlreadyDone = pref.getBoolean("SINGUP", false);
		String username = pref.getString("USERNAME", null);
		
		// Sign_up activity should be display only at the first time. For this we need to store preference
		if(signupIsAlreadyDone){
			Intent intent = new Intent(Sign_upActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}
	
	
	private void savePrefs(String key, boolean value){
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		Editor edit = pref.edit();
		edit.putBoolean(key, value);
		edit.apply();
	}
	
	private void savePrefs(String key, String value){
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		Editor edit = pref.edit();
		edit.putString(key, value);
		edit.apply();
	}

}
