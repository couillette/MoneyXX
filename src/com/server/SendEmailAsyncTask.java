package com.server;

import com.moneyxx.WalletActivity;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean>{
	
	Mail m;
	
	public SendEmailAsyncTask(String[] emailList, String sub, String message) {
//		this.m = new Mail("moneyxx2013@gmail.com", "moneyxxisep");
//		
//	      m.set_to(emailList); 
//	      m.set_from("money2013@gmail.com"); 
//	      m.set_subject("[MoneyXX] - "+sub); 
//	      m.setBody(message); 
	}


	@Override
	protected Boolean doInBackground(Void... params) {
	 
	      try { 
	 
	        if(m.send()) { 
	          System.out.println("Email was sent successfully.");
	        } else { 
	          System.out.println("Email was not sent.");
	        } 
	      } catch(Exception e) { 
	        Log.e("WalletActivity", "Could not send email", e); 
	      } 
		return null;
	}
	

}
