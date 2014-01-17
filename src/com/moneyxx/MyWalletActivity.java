package com.moneyxx;

import java.util.List;

import com.entity.UserAccount;
import com.entity.UserRegistered;
import com.moneyxx.R;
import com.server.Mail;
import com.server.PhoneData;
import com.server.SendEmailAsyncTask;
import com.server.StackmobQuery;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.text.Spannable;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyWalletActivity extends Activity {

	private String bankRIB;
	private String creditCard;
	private String solde;
	private String username;
	private String accountID;
	private PhoneData phoneData;
	private TextView error_message;


	TextView bankRIB_TV;
	TextView creditCard_TV;
	TextView username_TV;
	TextView balance_TV;

	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loadPrefs();

		// Enable the callBack button
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.button_WallettOK:
			bankRIB = ((EditText) findViewById(R.id.editText_bankRIB))
					.getText().toString();
			creditCard = ((EditText) findViewById(R.id.editText_creditCard))
					.getText().toString();

			if (!(bankRIB.trim().equals("") || creditCard.trim().equals(""))) {
				phoneData = new PhoneData();
				phoneData.savePrefs(this, "ACCOUNT", true);
				phoneData.savePrefs(this, "BANKRIB", bankRIB);
				phoneData.savePrefs(this, "CREDITCARD", creditCard);
				phoneData.savePrefs(this, "SOLDE", "0");


				// StackmobQuery stqq = new StackmobQuery();
				// accountID = stqq.fetchUserAccountID(username);
				// phoneData.savePrefs(this, "ACCOUNTID", accountID.trim());

				// set relationship between UserRegisterd and Account on stackmob
				UserAccount account = new UserAccount(bankRIB, creditCard, "0");
				account.save();
				UserRegistered usr = new UserRegistered();
				usr.setID(username.trim());
				usr.setUser_account(account);
				usr.save();

				accountID = account.getID();
				phoneData.savePrefs(this, "ACCOUNTID", accountID.trim());

				setContentView(R.layout.activity_my_wallet);
				loadPrefs();

			} else {
				// Display error message when required field are not complete
				error_message = (TextView) findViewById(R.id.textView_Wallet1);
				String text = "\n\nYou need to fill correctly the required field "
						+ "to send and beg money";
				appendTextDifSize(error_message, text, 0.5f);
			}
			break;

		case R.id.imageButton_wallet_refresh:

			StackmobQuery stQ = new StackmobQuery();
			String accountIDD = stQ.fetchUserAccountID(username.trim());
			StackmobQuery stQQ = new StackmobQuery();
			List<UserAccount> usrList = stQQ.fetchUserAccountByID(accountIDD);

			bankRIB_TV.setText("Bank RIB: "
					+ usrList.get(0).getbankRIB().trim());
			creditCard_TV.setText("CreditCard: "
					+ usrList.get(0).getCreditCard().trim());
			balance_TV.setText("Balance: " + usrList.get(0).getSolde().trim()
					+ "$");

			PhoneData ph = new PhoneData();
			ph.savePrefs(this, "SOLDE", usrList.get(0).getSolde().trim());

			break;

		case R.id.button_Wallet_Historic:
			intent = new Intent(MyWalletActivity.this, HistoricActivity.class);
			MyWalletActivity.this.startActivity(intent);
			break;

		case R.id.button_Wallet_Recharge:
		case R.id.button_Wallet_Transfer:
			intent = new Intent(MyWalletActivity.this,
					TransferRechargeActivity.class);
			MyWalletActivity.this.startActivity(intent);
			break;

		case R.id.button_Wallet_Settings:
			intent = new Intent(MyWalletActivity.this, SettingsActivity.class);
			MyWalletActivity.this.startActivity(intent);
			break;
		}

	}


	private void loadPrefs() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		// boolean AccountIsOK = pref.getBoolean("ACCOUNT", false);
		//
		// // Sign_up activity should be display only at the first time. For
		// this we need to store preference
		// if (AccountIsOK) {
		username = pref.getString("USERNAME", null);
		bankRIB = pref.getString("BANKRIB", null);
		creditCard = pref.getString("CREDITCARD", null);
		solde = pref.getString("SOLDE", null);

		if (!(bankRIB == null) && !(creditCard == null)) {
			this.setContentView(R.layout.activity_my_wallet);

			bankRIB_TV = (TextView) findViewById(R.id.textView_Wallet_BankRIB);
			creditCard_TV = (TextView) findViewById(R.id.textView_Wallet_CreditCard);
			username_TV = (TextView) findViewById(R.id.textView_Wallet_Username);
			balance_TV = (TextView) findViewById(R.id.textView_Wallet_Balance);
			bankRIB_TV.append(bankRIB);
			creditCard_TV.append(creditCard);
			username_TV.setText(username);
			balance_TV.append("" + solde + "$");
			//
		} else {
			this.setContentView(R.layout.activity_my_wallet1);
		}
		// } else {
		// this.setContentView(R.layout.activity_my_wallet1);
		// }
	}

	public void appendTextDifSize(TextView tv, String text, float size) {
		int start = tv.getText().length();

		if (start > 18) {
			char[] charList = tv.getText().toString().toCharArray();
			int i = 0;
			while (charList[i] != '\n') {
				i++;
			}
			start = i;
			CharSequence prevText = tv.getText().subSequence(0, i);
			tv.setText(prevText);
		}

		tv.append(text);
		int end = tv.getText().length();

		Spannable span = (Spannable) tv.getText();
		span.setSpan(new RelativeSizeSpan(size), start, end,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wallet, menu);
		return true;
	}

	// Set the call back to return to previous activity
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		Intent myIntent ;
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_money_beg:
            // action_money_beg
        	myIntent = new Intent(MyWalletActivity.this, SendBegActivity.class);
        	MyWalletActivity.this.startActivity(myIntent);
            return true;
        case R.id.action_money_send:
            // action_money_send
        	myIntent = new Intent(MyWalletActivity.this, SendBegActivity.class);
        	MyWalletActivity.this.startActivity(myIntent);
            return true;
        case R.id.action_jackpot:
            // action_jackpot
        	myIntent = new Intent(MyWalletActivity.this, JackpotActivity.class);
        	MyWalletActivity.this.startActivity(myIntent);
            return true;
        case R.id.action_wallet:
            // action_wallet
        	myIntent = new Intent(MyWalletActivity.this, MyWalletActivity.class);
        	MyWalletActivity.this.startActivity(myIntent);
            return true;
        case R.id.action_settings:
            // action_settings
        	myIntent = new Intent(MyWalletActivity.this, SettingsActivity.class);
			MyWalletActivity.this.startActivity(myIntent);
            return true;      
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
