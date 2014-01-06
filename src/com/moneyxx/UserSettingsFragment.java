package com.moneyxx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.entity.UserAccount;
import com.entity.UserRegistered;
import com.server.PhoneData;
import com.server.SendEmailAsyncTask;
import com.server.StackmobQuery;
import com.stackmob.sdk.callback.StackMobModelCallback;
import com.stackmob.sdk.exception.StackMobException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;


public class UserSettingsFragment extends Fragment {
	
	List<UserRegistered> USERLIST;
	List<UserAccount> userAccountList;
	String name;
	String email;
	String phone;
	String bankR;
	String solde;
	String creditC;
	String accountID;
	
	AutoCompleteTextView autoComp_email;
	AutoCompleteTextView autoComp_phone;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_user_settings, container, false);
		
		
		// Display message
		TextView settings = (TextView) rootView.findViewById(R.id.textView_Settings_User);
		String text = "\n\nYou can choose another MoneyXX user register on Stackmob, in order " +
				"to check Send&Beg requests, transactions and email notifications on both side.";
		appendTextDifSize(settings, text, 0.5f);
		
		
		final AutoCompleteTextView autoComp_username = (AutoCompleteTextView) rootView.findViewById(R.id.AutoComp_Settings_Username);
		autoComp_email = (AutoCompleteTextView) rootView.findViewById(R.id.AutoComp_Settings_Email);
		autoComp_phone = (AutoCompleteTextView) rootView.findViewById(R.id.AutoComp_Settings_Phone);
		
		
		StackmobQuery stQuery = new StackmobQuery();
		stQuery.fetchAllUserRegistered();
		USERLIST = stQuery.getUserList();
		ArrayList<String> usernameList = new ArrayList();
		for(UserRegistered ur : USERLIST){
			usernameList.add(ur.getUsername().trim());
		}
		

		//doesn't work with R.layout.custcontview.xml  -->  NullPointerException
		ArrayAdapter<String> adapterName = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, usernameList);
		autoComp_username.setAdapter(adapterName);
		autoComp_username.setThreshold(1);

		
		autoComp_username.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v ,int index, long arg3) {
				name = (String) parent.getItemAtPosition(index);
				autoComp_username.setText(""+name);

				for(UserRegistered user : USERLIST){
					if (user.getUsername().trim().equals(name)){
						email = user.getEmail().trim();
						phone = user.getPhone().trim();
						accountID = user.getUser_account().getID().trim();
						autoComp_email.setText(email);
						autoComp_phone.setText(phone);
					}
				}
			}
		});
		
		
		Button b = (Button) rootView.findViewById(R.id.button_Settings_UserSave);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

					// Send a confirmation mail
					String[] mailAddress = { email.trim() };
					String sub = "Successfull registration";
					String message = "Hi "+name+","
							+ "\n\nWe are pleased to announce that you change your account on MoneyXX "
							+ "this is allowed in the test version ...blabla";
					new SendEmailAsyncTask(mailAddress, sub, message).execute();
					
					//the query need to return userAccountList otherwise the query is empty
					StackmobQuery stQuery1 = new StackmobQuery();
					userAccountList = stQuery1.fetchUserAccountByID(accountID.trim());
					
					for(UserAccount userAcc : userAccountList){
						solde = userAcc.getSolde().trim();
						bankR =  userAcc.getbankRIB().trim();
						creditC =  userAcc.getCreditCard().trim();
					}
				
					
					PhoneData phoneData = new PhoneData();
					phoneData.savePrefs(getActivity(), "USERNAME", name);
					phoneData.savePrefs(getActivity(), "EMAIL", email);
					phoneData.savePrefs(getActivity(), "ACCOUNTID", accountID.trim());
					phoneData.savePrefs(getActivity(), "BANKRIB", bankR);
					phoneData.savePrefs(getActivity(), "CREDITCARD", creditC);
					phoneData.savePrefs(getActivity(), "SOLDE", ""+solde);
					
					// Go to MainActivity
					Intent intent = new Intent(getActivity(), MainActivity.class);
					getActivity().startActivity(intent);
					getActivity().finish();
				}
		});
		
		return rootView;
	}
	
	
	
	public void appendTextDifSize(TextView tv, String text, float size){
		int start = tv.getText().length();
		
		if(start>13){
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
	
	
}
