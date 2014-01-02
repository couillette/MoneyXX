package com.moneyxx;

import java.util.List;

import com.entity.UserAccount;
import com.entity.UserRegistered;
import com.server.BaseActivity;
import com.server.SendEmailAsyncTask;
import com.stackmob.android.sdk.common.StackMobAndroid;
import com.stackmob.sdk.api.StackMobQuery;
import com.stackmob.sdk.callback.StackMobModelCallback;
import com.stackmob.sdk.callback.StackMobQueryCallback;
import com.stackmob.sdk.exception.StackMobException;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.Spannable;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Sign_upActivity extends BaseActivity {

	TextView error_message;
	Intent intent;
	
	String usernameval;
	String email;
	String pwd;
	String phone;
	
	Boolean query;
	Boolean exist;

			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		//display this activity at first launch only
		loadPrefs();
		

	}
	
	
	public void onClick(View view) {
		if (view.getId() == R.id.button_SignUp) {

			// retrieve data from the Sign_up form
			usernameval = ((EditText) findViewById(R.id.editText_Username)).getText().toString();
			pwd = ((EditText) findViewById(R.id.editText_Password)).getText().toString();
			email = ((EditText) findViewById(R.id.editText_Email)).getText().toString();
			phone = ((EditText) findViewById(R.id.editText_Phone)).getText().toString();
			

			if (!(usernameval.trim().equals("") || pwd.trim().equals("")
					|| email.trim().equals("") || phone.trim().equals("")) ) {
				
				
				if (isValidEmail(email)) {
					
					if(!checkIfUsernameExist()){
						// Store User Information on Stackmob
						UserRegistered user = new UserRegistered(usernameval, pwd, email, phone);
						user.save();
						
						//Send a confirmation mail
						String[] mailAddress = {email.trim()};
						String sub = "Successfull registration";
						String message = "Hi "+usernameval+"," +
								"\n\nWe are pleased to announce you that you now have a MoneyXX Account " +
								"to send and receive money from ...blabla";
						new SendEmailAsyncTask(mailAddress, sub, message).execute();
						
						
						// Store settings on the application data
						// this will skip this activity at next start
						savePrefs("SINGUP", true);
						// this permit to save username to retrieve it from
						// stackmod
						savePrefs("USERNAME", usernameval);
						savePrefs("EMAIL", email);
						
						//StackMobCallbacks come back on a different thread
						runOnUiThread(new Runnable() {	
							@Override
							public void run() {
								// Go to MainActivity
								intent = new Intent(Sign_upActivity.this, MainActivity.class);
								Sign_upActivity.this.startActivity(intent);
								Sign_upActivity.this.finish();
							}
						});
							
					} else {
								// Display error message when email is not valid
								error_message = (TextView) findViewById(R.id.textView_singup);
								String text = "\n\nchoose another username this one is already used " +
										"if you don't want to split your money ";
								appendTextDifSize(error_message, text, 0.5f);
					}
						
//						user.save(new StackMobModelCallback() {
//						
//						@Override
//						public void failure(StackMobException arg0) {
//							//StackMobCallbacks come back on a different thread
//							runOnUiThread(new Runnable() {	
//								@Override
//								public void run() {
//									// Display error message when email is not valid
//									error_message = (TextView) findViewById(R.id.textView_singup);
//									String text = "\n\nchoose another username this one is already used " +
//											"if you don't want to split your money ";
//									appendTextDifSize(error_message, text, 0.5f);
//								}
//							});
//						}
//						
//						@Override
//						public void success() {
//							//Send a confirmation mail
//							String[] mailAddress = {email.trim()};
//							String sub = "Successfull registration";
//							String message = "Hi "+usernameval+"," +
//									"\n\nWe are pleased to announce you that you now have a MoneyXX Account " +
//									"to send and receive money from ...blabla";
//							new SendEmailAsyncTask(mailAddress, sub, message).execute();
//							
							
							// Store settings on the application data
							// this will skip this activity at next start
//							savePrefs("SINGUP", true);
//							// this permit to save username to retrieve it from
//							// stackmod
//							savePrefs("USERNAME", usernameval);
//							savePrefs("EMAIL", email);
							
							//StackMobCallbacks come back on a different thread
//							runOnUiThread(new Runnable() {	
//								@Override
//								public void run() {
//									// Go to MainActivity
//									Intent intent = new Intent(Sign_upActivity.this,
//											MainActivity.class);
//									startActivity(intent);
//									finish();
//								}
//							});
//						}
//					});
					
//					Account account = new Account("120974625", "333456787687", "1234356", "0");
//					account.save();
//					user.setUser_account(account);
//					account.setUser_Account(user);


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
	

	
	public Boolean checkIfUsernameExist(){
		 exist = false;
		 query = false;

		 // "while" because stackmob query are asynchronous
		 while(!query){
		UserRegistered.query(
				UserRegistered.class, 
				new StackMobQuery().fieldIsEqualTo("username", usernameval.trim()),
				new StackMobQueryCallback<UserRegistered>() {

					@Override
					public void failure(StackMobException e) {
						query=true;
					}

					@Override
					public void success(List<UserRegistered> user) {
						if(!user.isEmpty()){
							exist = true;
						}
						query=true;
					}
				});
		 }
		 return exist;
		
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
