package com.server;

import com.moneyxx.WalletActivity;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean>{
	
	Mail m;
	
	public SendEmailAsyncTask(String[] emailList, String sub, String message) {
		this.m = new Mail("moneyxx.isep@gmail.com", "moneyxxisep");
		
//		String[] toArr = {"s_couillette@hotmail.com"}; 
	      m.set_to(emailList); 
	      m.set_from("moneyxx.isep@gmail.com"); 
	      m.set_subject("[MoneyXX] - "+sub); 
	      m.setBody(message); 
	}


	@Override
	protected Boolean doInBackground(Void... params) {
//		m = new Mail("moneyxx.isep@gmail.com", "moneyxxisep");
//		
//	      String[] toArr = {"s_couillette@hotmail.com"}; 
//	      m.set_to(toArr); 
//	      m.set_from("moneyxx.isep@gmail.com"); 
//	      m.set_subject("[MoneyXX] - Android application from an Android device."); 
//	      m.setBody("Email body."); 
	 
	      try { 
//	        m.addAttachment("/sdcard/filelocation"); 
	 
	        if(m.send()) { 
//	          Toast.makeText(null, "Email was sent successfully.", Toast.LENGTH_LONG).show();
	          System.out.println("Email was sent successfully.");
	        } else { 
//	          Toast.makeText(null, "Email was not sent.", Toast.LENGTH_LONG).show(); 
	          System.out.println("Email was not sent.");
	        } 
	      } catch(Exception e) { 
	        //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show(); 
	        Log.e("WalletActivity", "Could not send email", e); 
	      } 
		return null;
	}
	

}
