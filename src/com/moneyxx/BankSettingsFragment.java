package com.moneyxx;

import com.entity.UserAccount;
import com.entity.UserRegistered;
import com.moneyxx.R;
import com.server.PhoneData;
import com.server.StackmobQuery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BankSettingsFragment extends Fragment {
	String bankRIB;
	String creditCard;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_bank_settings,container, false);
		
		
		
		// Display message
		TextView settings = (TextView) rootView.findViewById(R.id.textView_Settings_bankInfo);
		String text = "\n\nChange your banking info and save these changes for your MoneyXX account.";
		appendTextDifSize(settings, text, 0.5f);
		
		
		
		Button b = (Button) rootView.findViewById(R.id.button_Settings_BankSave);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				bankRIB = ((EditText) rootView.findViewById(R.id.editText_Settings_bankRIB)).getText().toString();
				creditCard = ((EditText) rootView.findViewById(R.id.editText_Settings_creditCard)).getText().toString();
				
				//set relationship between UserRegisterd and Account on stackmob
				SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
				String username = pref.getString("USERNAME", null);
				
				StackmobQuery stQuery = new StackmobQuery();
				String accountID = stQuery.fetchUserAccountID(username.trim());
				
				UserAccount account = new UserAccount();
				account.setID(accountID);
				account.setbankRIB(bankRIB.trim());
				account.setCreditCard(creditCard.trim());
				account.save();
				
				PhoneData phoneData = new PhoneData();
				phoneData.savePrefs(getActivity(), "BANKRIB", bankRIB);
				phoneData.savePrefs(getActivity(), "CREDITCARD", creditCard);
				
			}
		});

		return rootView;
	}
	
	public void appendTextDifSize(TextView tv, String text, float size){
		int start = tv.getText().length();
		
		if(start>17){
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
