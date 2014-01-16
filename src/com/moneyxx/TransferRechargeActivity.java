package com.moneyxx;

import android.os.Bundle;
<<<<<<< HEAD
=======
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
>>>>>>> d52219506c2e7465a86c5bb1b4ec3680fc9b879a
import android.app.Activity;
import android.view.Menu;
<<<<<<< HEAD
=======
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
>>>>>>> d52219506c2e7465a86c5bb1b4ec3680fc9b879a

public class TransferRechargeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transfer_recharge);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transfer_recharge, menu);
		return true;
	}
<<<<<<< HEAD
=======
	
	public void onClick(View view) {
		
		final String amount = ((EditText) findViewById(R.id.editText_TransRec_AmountOfMoney)).getText().toString();
		
		switch (view.getId()) {
		
		//Transfer will debit account for this version & Save the transaction in historics
		case R.id.button_TransRec_TransferRequest:
			
			Builder transfer_message = new AlertDialog.Builder(this);
			transfer_message.setTitle("Warning");
			transfer_message.setMessage("You are going to transfer "+amount+" $ from your MoneyXX account " +
					"to you bank account :\nBank RIB : "+bankRIB+"\nCreditCard : "+creditCard);
			
			transfer_message.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
						//credit bankRIB account
						//debit MoneyXX account
						debit(username, amount);
					
						//send message (mail or sms) to sender
						String[] mailAddressS = {username.trim()};
						String subS = "Transfer";
						String messageS = "your transfer from your MoneyXX account to your Bank account succeed !";
						new SendEmailAsyncTask(mailAddressS, subS, messageS).execute();
						
						// Go to MainActivity
						Intent i = new Intent(TransferRechargeActivity.this, MyWalletActivity.class);
						TransferRechargeActivity.this.startActivity(i);
						TransferRechargeActivity.this.finish();
					}
				});
			transfer_message.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {}});
			transfer_message.show();
			
			break;
			
		case R.id.buton_TransRec_RechargeRequest:
			
			Builder Recharge_message = new AlertDialog.Builder(this);
			Recharge_message.setTitle("Warning");
			Recharge_message.setMessage("You are going to Recharge your MoneyXX account with "+amount+" $" +
					" from your bank account :\nBank RIB : "+bankRIB+"\nCreditCard : "+creditCard);
			
			Recharge_message.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
						//credit bankRIB account
						//debit MoneyXX account
						credit(username, amount);
					
						//send message (mail or sms) to sender
						String[] mailAddressS = {username.trim()};
						String subS = "Transfer";
						String messageS = "your Recharge from your Bank account to your MoneyXX account succeed !";
						new SendEmailAsyncTask(mailAddressS, subS, messageS).execute();
						
						// Go to MainActivity
						Intent it = new Intent(TransferRechargeActivity.this, MyWalletActivity.class);
						TransferRechargeActivity.this.startActivity(it);
						TransferRechargeActivity.this.finish();
					}
				});
			Recharge_message.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {}});
			Recharge_message.show();
			
			break;
		}
	}
	
	public void debit(String user, String amount) {
		// debit the sender account
		int solde0 = Integer.parseInt(solde.trim());
		int solde1 = solde0 - (Integer.parseInt(amount.trim()));
		
		UserAccount uus = new UserAccount();
		uus.setID(accountID.trim());
		uus.setSolde("" + solde1);
		uus.save();

		PhoneData ph = new PhoneData();
		ph.savePrefs(this, "SOLDE", ""+solde1);
	}
	
	public void credit(String user, String amount) {
		// debit the sender account
		int solde0 = Integer.parseInt(solde.trim());
		int solde1 = solde0 + (Integer.parseInt(amount.trim()));
		
		UserAccount uus = new UserAccount();
		uus.setID(accountID.trim());
		uus.setSolde("" + solde1);
		uus.save();
		
		PhoneData ph = new PhoneData();
		ph.savePrefs(this, "SOLDE", ""+solde1);
	}
	
	//Set the call back to return to previous activity
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		Intent myIntent ;
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_money_beg:
            // action_money_beg
        	myIntent = new Intent(TransferRechargeActivity.this, SendBegActivity.class);
        	TransferRechargeActivity.this.startActivity(myIntent);
            return true;
        case R.id.action_money_send:
            // action_money_send
        	myIntent = new Intent(TransferRechargeActivity.this, SendBegActivity.class);
        	TransferRechargeActivity.this.startActivity(myIntent);
            return true;
        case R.id.action_jackpot:
            // action_jackpot
        	myIntent = new Intent(TransferRechargeActivity.this, JackpotActivity.class);
        	TransferRechargeActivity.this.startActivity(myIntent);
            return true;
        case R.id.action_wallet:
            // action_wallet
        	myIntent = new Intent(TransferRechargeActivity.this, MyWalletActivity.class);
        	TransferRechargeActivity.this.startActivity(myIntent);
            return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
>>>>>>> d52219506c2e7465a86c5bb1b4ec3680fc9b879a

}
