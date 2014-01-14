package com.moneyxx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.entity.UserAccount;
import com.entity.UserRegistered;
import com.moneyxx.R;
import com.server.BaseActivity;
import com.server.SendEmailAsyncTask;
import com.server.StackmobQuery;
import com.stackmob.android.sdk.common.StackMobAndroid;
import com.stackmob.sdk.api.StackMobQuery;
import com.stackmob.sdk.api.StackMobQueryField;
import com.stackmob.sdk.callback.StackMobCallback;
import com.stackmob.sdk.callback.StackMobModelCallback;
import com.stackmob.sdk.callback.StackMobQueryCallback;
import com.stackmob.sdk.exception.StackMobException;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.support.v4.app.NavUtils;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SendBegActivity extends BaseActivity {

	// Read local contact
	private ArrayList<Map<String, String>> contactList;

	// Selected contact
	private String[] contactData = new String[] { "Name", "Phone", "Email" };

	// text return by checkIfUserIsRegistered()
	String alertDialog_text;

	Boolean userIsRegisterd;
	String thisUserName;
	String thisUserEmail;
	String bankRIB;
	String creditCard;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_beg);

		// read local contact and provide autocompleteTextView
		// feature to display and select them
		contactList = new ArrayList<Map<String, String>>();
		contactList.clear();
		PopulateContactList();

		// Enable the callBack button
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// SharedPreferences pref =
		// PreferenceManager.getDefaultSharedPreferences(this);
		// thisUserName = pref.getString("USERNAME", null);
		// final String amount = ((EditText)
		// findViewById(R.id.editText_AmountOfMoney)).getText().toString();

		// thisUserName = query username in preferences;

		// UserAccount account = new UserAccount("123456789", "123456789", "0");
		// UserAccount account = new UserAccount("1", "123456789", "123456789",
		// "0");
		// account.setID("1111222");
		// account.save();
		//
		// UserRegistered u = new UserRegistered("sergelefou", "qsdf",
		// "serge@hotmail.fr", "0648765342");
		// u.setUser_account(account);
		// u.save();

		// String pS= "3344447532";
		// UserRegistered uS = new UserRegistered("sergelefou", "qsdf",
		// "serge@hotmail.fr", pS);
		// uS.save();
		//
		// String pSS= "41083993";
		// UserRegistered uSS = new UserRegistered("sergY", "QSDf",
		// "migwel@gmail.com", pSS);
		// uSS.save();

		// if user click on one of the 2 button --> onClick(view)

	}

	// if user click on the buttons
	public void onClick(View view) {
		if (loadPrefs()) {
			final String amount = ((EditText) findViewById(R.id.editText_AmountOfMoney))
					.getText().toString();
			userIsRegisterd = false;

			// query on stackmob datastore to check if user are registered or
			// not
			// call onItemClickListener because it is asynchronous, which
			// enhance speed.
			// put userIsRegisterd to true or false.
			final StackmobQuery stQuery = new StackmobQuery();
<<<<<<< HEAD
			userIsRegisterd = stQuery.checkByMailOrPhone(contactData[1], contactData[2]);
			
				if (userIsRegisterd) {
					alertDialog_text = "\n\nGreat! This person is kown and have a MoneyXX account.";
					
				} else {
					alertDialog_text = "\n\nWARNING : this person doesn't have any MoneyXX Account"
							+ "\nthis action is not allowed yet";
//							+ "\n- 'OK' and we will credit a temporary account based on the information "
//							+ "you gave and send him a message to register to MoneyXX"
//							+ "\n- 'NO' if you are not sure";
				}
		
		
		switch (view.getId()) {
		
		//if user click on "Send Money"
		case R.id.buton_SendRequest:
			
			Builder send_message = new AlertDialog.Builder(this);
			send_message.setTitle("Warning");
			send_message.setMessage("You are going to send "+amount+" $ to :\n" +
					"  "+contactData[0]+"\n  "+contactData[1]+"\n  "+contactData[2] + alertDialog_text);
			
			send_message.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if ( userIsRegisterd ){
						//credit receiver account
						//debit sender account
						creditDebit(stQuery.getUsername(), thisUserName, amount);
					
						//send message (mail or sms) to receiver
						String[] mailAddressR = {contactData[2].trim()};
						String subR = "Earn Money";
						// add message from sender fill in the layout
						String m = ((EditText)findViewById(R.id.editText_message)).getText().toString();
						String messageR = "you just receive "+amount+"$ from "+thisUserName+
								" and your account was credited/n/n"+m;
						new SendEmailAsyncTask(mailAddressR, subR, messageR).execute();
					
						//send message (mail or sms) to sender
						String[] mailAddressS = {thisUserName.trim()};
						String subS = "money sent";
						String messageS = "you sent "+amount+"$ to "+contactData[0].trim()+
							" and your account was debited";
						new SendEmailAsyncTask(mailAddressS, subS, messageS).execute();
						
						//confirmation display on MoneyXX
						Builder confirmation = new AlertDialog.Builder(SendBegActivity.this);
						confirmation.setTitle("Confirmaation");
						confirmation.setMessage("Successfull transaction !" +
								"/nYou and your contact will be notified by email");
						
					} 
//						else if ( !userIsRegisterd ) {
//						
//						//create an account!!!
//						//credit receiver account
////						//debit sender account
//						
//						//send message (mail or sms) to receiver
//						String[] mailAddressR = {contactData[2].trim()};
//						String subR = "money earned";
//						String messageR = "you just receive "+amount+"$ from "+thisUserName+
//							" and your account was credited";
//						new SendEmailAsyncTask(mailAddressR, subR, messageR).execute();
//					
//						//send message (mail or sms) to sender
//						String[] mailAddressS = {thisUserName.trim()};
//						String subS = "money sent";
//						String messageS = "you sent "+amount+"$ to "+contactData[0].trim()+
//							" and your account was debited";
//						new SendEmailAsyncTask(mailAddressS, subS, messageS).execute();
//
//					}
				}
			});
			send_message.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					contactList.clear();
					PopulateContactList();
				}});
				
			send_message.show();
			
			
		break;
		
		//if user click on "Beg Money"
		//1st version will just send an email and ask the receiver to send the amount via MoneyXX
		//2nd version will fill a table which be displayed at launch before MainActivity if it is
		//not empty.(table on stackmob and table layout)
		case  R.id.button_BegRequest:
			
			Builder beg_message = new AlertDialog.Builder(this);
			beg_message.setTitle("Warning");
			beg_message.setMessage("You want to beg "+amount+" $ to :\n" +
					"  "+contactData[0]+"\n  "+contactData[1]+"\n  "+contactData[2] + alertDialog_text);
			
			beg_message.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if ( userIsRegisterd ){
						
						//send message (mail or sms) to receiver
						String[] mailAddressR = {contactData[2].trim()};
						String subR = "Ask Money";
						// add message from sender fill in the layout
						String m = ((EditText)findViewById(R.id.editText_message)).getText().toString();
						String messageR = thisUserName+" is beging you "+amount+"$"
								+" We need your authorization to do the transaction."+
								"/n/nStart MoneyXX, go to Send, enter the due amount and email: "
								+thisUserEmail.trim()+", or refuse the transaction./n/n"+m;
						new SendEmailAsyncTask(mailAddressR, subR, messageR).execute();
					
						//send message (mail or sms) to sender
						String[] mailAddressS = {thisUserName.trim()};
						String subS = "you beg Money";
						String messageS = "you beg "+amount+"$ to "+contactData[0].trim()+
							"/n/n We send him a message to make the transaction on MoneyXX";
						new SendEmailAsyncTask(mailAddressS, subS, messageS).execute();
						
						//confirmation display on MoneyXX
						Builder confirmation = new AlertDialog.Builder(SendBegActivity.this);
						confirmation.setTitle("Confirmaation");
						confirmation.setMessage("Successfull transaction !" +
								"/nYou and your contact will be notified by email");
						
					} 
//						else if ( !userIsRegisterd ) {
//						
//						//create an account!!!
//						//credit receiver account
////						//debit sender account
//						
//						//send message (mail or sms) to receiver
//						String[] mailAddressR = {contactData[2].trim()};
//						String subR = "money earned";
//						String messageR = "you just receive "+amount+"$ from "+thisUserName+
//							" and your account was credited";
//						new SendEmailAsyncTask(mailAddressR, subR, messageR).execute();
//					
//						//send message (mail or sms) to sender
//						String[] mailAddressS = {thisUserName.trim()};
//						String subS = "money sent";
//						String messageS = "you sent "+amount+"$ to "+contactData[0].trim()+
//							" and your account was debited";
//						new SendEmailAsyncTask(mailAddressS, subS, messageS).execute();
//
//					}
				}
			});
			beg_message.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			});
			beg_message.show();
			
		break;
		}
=======
			userIsRegisterd = stQuery.checkByMailOrPhone(contactData[1],
					contactData[2]);

			if (userIsRegisterd) {
				alertDialog_text = "\n\nGreat! This person is kown and have a MoneyXX account.";

			} else {
				alertDialog_text = "\n\nWARNING : this person doesn't have any MoneyXX Account"
						+ "\nthis action is not allowed yet";
				// +
				// "\n- 'OK' and we will credit a temporary account based on the information "
				// + "you gave and send him a message to register to MoneyXX"
				// + "\n- 'NO' if you are not sure";
			}

			switch (view.getId()) {

			// if user click on "Send Money"
			case R.id.buton_SendRequest:

				Builder send_message = new AlertDialog.Builder(this);
				send_message.setTitle("Warning");
				send_message.setMessage("You are going to send " + amount
						+ " $ to :\n" + "  " + contactData[0] + "\n  "
						+ contactData[1] + "\n  " + contactData[2]
						+ alertDialog_text);

				send_message.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (userIsRegisterd) {
									// credit receiver account
									// debit sender account
									// and check that the transaction succeed
									if (creditDebit(thisUserName,
											stQuery.getUsername(), amount)) {

										// send message (mail or sms) to
										// receiver
										String[] mailAddressR = { contactData[2]
												.trim() };
										String subR = "Earn Money";
										// add message from sender fill in the
										// layout
										String m = ((EditText) findViewById(R.id.editText_message))
												.getText().toString();
										String messageR = "you just receive "
												+ amount
												+ "$ from "
												+ thisUserName
												+ " and your account was credited/n/n"
												+ m;
										new SendEmailAsyncTask(mailAddressR,
												subR, messageR).execute();

										// send message (mail or sms) to sender
										String[] mailAddressS = { thisUserName
												.trim() };
										String subS = "money sent";
										String messageS = "you sent "
												+ amount
												+ "$ to "
												+ contactData[0].trim()
												+ " and your account was debited";
										new SendEmailAsyncTask(mailAddressS,
												subS, messageS).execute();

										// confirmation display on MoneyXX
										Builder confirmation = new AlertDialog.Builder(
												SendBegActivity.this);
										confirmation.setTitle("Confirmaation");
										confirmation
												.setMessage("Successfull transaction !"
														+ "/nYou and your contact will be notified by email");
									}

								}
								// else if ( !userIsRegisterd ) {
								//
								// //create an account!!!
								// //credit receiver account
								// // //debit sender account
								//
								// //send message (mail or sms) to receiver
								// String[] mailAddressR =
								// {contactData[2].trim()};
								// String subR = "money earned";
								// String messageR =
								// "you just receive "+amount+"$ from "+thisUserName+
								// " and your account was credited";
								// new SendEmailAsyncTask(mailAddressR, subR,
								// messageR).execute();
								//
								// //send message (mail or sms) to sender
								// String[] mailAddressS =
								// {thisUserName.trim()};
								// String subS = "money sent";
								// String messageS =
								// "you sent "+amount+"$ to "+contactData[0].trim()+
								// " and your account was debited";
								// new SendEmailAsyncTask(mailAddressS, subS,
								// messageS).execute();
								//
								// }
							}
						});
				send_message.setNegativeButton("NO",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								contactList.clear();
								PopulateContactList();
							}
						});

				send_message.show();

				break;

			// if user click on "Beg Money"
			// 1st version will just send an email and ask the receiver to send
			// the amount via MoneyXX
			// 2nd version will fill a table which be displayed at launch before
			// MainActivity if it is
			// not empty.(table on stackmob and table layout)
			case R.id.button_BegRequest:

				Builder beg_message = new AlertDialog.Builder(this);
				beg_message.setTitle("Warning");
				beg_message.setMessage("You want to beg " + amount
						+ " $ to :\n" + "  " + contactData[0] + "\n  "
						+ contactData[1] + "\n  " + contactData[2]
						+ alertDialog_text);

				beg_message.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (userIsRegisterd) {

									// send message (mail or sms) to receiver
									String[] mailAddressR = { contactData[2]
											.trim() };
									String subR = "Ask Money";
									// add message from sender fill in the
									// layout
									String m = ((EditText) findViewById(R.id.editText_message))
											.getText().toString();
									String messageR = thisUserName
											+ " is beging you "
											+ amount
											+ "$"
											+ " We need your authorization to do the transaction."
											+ "/n/nStart MoneyXX, go to Send, enter the due amount and email: "
											+ thisUserEmail.trim()
											+ ", or refuse the transaction./n/n"
											+ m;
									new SendEmailAsyncTask(mailAddressR, subR,
											messageR).execute();

									// send message (mail or sms) to sender
									String[] mailAddressS = { thisUserName
											.trim() };
									String subS = "you beg Money";
									String messageS = "you beg "
											+ amount
											+ "$ to "
											+ contactData[0].trim()
											+ "/n/n We send him a message to make the transaction on MoneyXX";
									new SendEmailAsyncTask(mailAddressS, subS,
											messageS).execute();

									// confirmation display on MoneyXX
									Builder confirmation = new AlertDialog.Builder(
											SendBegActivity.this);
									confirmation.setTitle("Confirmaation");
									confirmation
											.setMessage("Successfull transaction !"
													+ "/nYou and your contact will be notified by email");

								}
								// else if ( !userIsRegisterd ) {
								//
								// //create an account!!!
								// //credit receiver account
								// // //debit sender account
								//
								// //send message (mail or sms) to receiver
								// String[] mailAddressR =
								// {contactData[2].trim()};
								// String subR = "money earned";
								// String messageR =
								// "you just receive "+amount+"$ from "+thisUserName+
								// " and your account was credited";
								// new SendEmailAsyncTask(mailAddressR, subR,
								// messageR).execute();
								//
								// //send message (mail or sms) to sender
								// String[] mailAddressS =
								// {thisUserName.trim()};
								// String subS = "money sent";
								// String messageS =
								// "you sent "+amount+"$ to "+contactData[0].trim()+
								// " and your account was debited";
								// new SendEmailAsyncTask(mailAddressS, subS,
								// messageS).execute();
								//
								// }
							}
						});
				beg_message.setNegativeButton("NO",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
							}
						});
				beg_message.show();

				break;
			}
>>>>>>> 82a705e5cc7ded006539433bcf03489da27c2784
		}

	}
<<<<<<< HEAD
	
	
	
	
//	public void checkIfUserIsRegisteredByMailOrPhone(){
//		 userIsRegisterd = false;
//		 
//		 //to make query on phoneNumber we had to change phone attribute in UserRegistered
//		 //into String field. because you have things like "+33" or "("... in android field numbers
//		 String phnum = contactData[1].replaceAll("[+()-]", "").replace(" ", "").trim();
//		 String mail = contactData[2].trim();
//		 
//		 StackMobQuery checkMail = new StackMobQuery().fieldIsEqualTo("email", mail);
//		 StackMobQuery checkPhone = new StackMobQuery().fieldIsEqualTo("phone", phnum);
//		
//		// while because the query is asynchronous and we need to wait the result
//		UserRegistered.query(
//				UserRegistered.class, 
//				new StackMobQuery().fieldIsEqualTo("phone", phnum).or(checkMail),
//				new StackMobQueryCallback<UserRegistered>() {
//
//					@Override
//					public void failure(StackMobException e) {
//						alertDialog_text = "\n\nWARNING : this person doesn't have any MoneyXX Account\n" +
//								"Clik on OK and we will send him a message to register and credit a temporary account based" +
//								" on the information you gave.";
//					}
//
//					@Override
//					public void success(List<UserRegistered> user) {
//						if(!user.isEmpty()){
//							alertDialog_text = "\n\nGreat! This person is kown and have a MoneyXX account.";
//							userIsRegisterd = true;
//						} else {
//							alertDialog_text = "\n\nWARNING : this person doesn't have any MoneyXX Account" +
//									"\n- 'OK' and we will credit a temporary account based on the information " +
//									"you gave and send him a message to register to MoneyXX"+
//									"\n- 'NO' if you are not sure";
//							userIsRegisterd = false;
//						}
//					}
//				});
//		
//	}
	
	
	public void creditDebit(String sender, String receiver, String amount) {
		// debit the sender account
		StackmobQuery stQuery = new StackmobQuery();
		String accountIDs = stQuery.fetchUserAccountID(sender.trim());

		StackmobQuery stQuery1 = new StackmobQuery();
		int solde0 = stQuery1.fetchUserAccountSolde(accountIDs);
		int solde1 = Integer.parseInt(amount.trim());

		UserAccount us = new UserAccount();
		us.setID(accountIDs);
		
		// debit "-"
		int soldef = solde0 - solde1;
		us.setSolde("" + soldef);
		us.save();
		
		
		// credit the receiver account
		StackmobQuery stQuery2 = new StackmobQuery();
		String accountIDr = stQuery2.fetchUserAccountID(receiver.trim());

		StackmobQuery stQuery3 = new StackmobQuery();
		int solde00 = stQuery3.fetchUserAccountSolde(accountIDr);
		int solde11 = Integer.parseInt(amount.trim());

		UserAccount ur = new UserAccount();
		ur.setID(accountIDr);
		
		// credit "+"
		int soldeff = solde00 + solde11;
		ur.setSolde("" + soldeff);
		ur.save();
=======

	// public void checkIfUserIsRegisteredByMailOrPhone(){
	// userIsRegisterd = false;
	//
	// //to make query on phoneNumber we had to change phone attribute in
	// UserRegistered
	// //into String field. because you have things like "+33" or "("... in
	// android field numbers
	// String phnum = contactData[1].replaceAll("[+()-]", "").replace(" ",
	// "").trim();
	// String mail = contactData[2].trim();
	//
	// StackMobQuery checkMail = new StackMobQuery().fieldIsEqualTo("email",
	// mail);
	// StackMobQuery checkPhone = new StackMobQuery().fieldIsEqualTo("phone",
	// phnum);
	//
	// // while because the query is asynchronous and we need to wait the result
	// UserRegistered.query(
	// UserRegistered.class,
	// new StackMobQuery().fieldIsEqualTo("phone", phnum).or(checkMail),
	// new StackMobQueryCallback<UserRegistered>() {
	//
	// @Override
	// public void failure(StackMobException e) {
	// alertDialog_text =
	// "\n\nWARNING : this person doesn't have any MoneyXX Account\n" +
	// "Clik on OK and we will send him a message to register and credit a temporary account based"
	// +
	// " on the information you gave.";
	// }
	//
	// @Override
	// public void success(List<UserRegistered> user) {
	// if(!user.isEmpty()){
	// alertDialog_text =
	// "\n\nGreat! This person is kown and have a MoneyXX account.";
	// userIsRegisterd = true;
	// } else {
	// alertDialog_text =
	// "\n\nWARNING : this person doesn't have any MoneyXX Account" +
	// "\n- 'OK' and we will credit a temporary account based on the information "
	// +
	// "you gave and send him a message to register to MoneyXX"+
	// "\n- 'NO' if you are not sure";
	// userIsRegisterd = false;
	// }
	// }
	// });
	//
	// }

	public Boolean creditDebit(String sender, String receiver, String amount) {
		boolean done = false;

		while (!done) {
			// credit the receiver account
			StackmobQuery stQuery2 = new StackmobQuery();
			String accountIDur = stQuery2.fetchUserAccountID(receiver.trim());

			StackmobQuery stQuery3 = new StackmobQuery();
			int solde00 = stQuery3.fetchUserAccountSolde(accountIDur);
			// int solde00 = 0;
			// credit "+"
			int solde11 = solde00 + Integer.parseInt(amount.trim());

			UserAccount ur = new UserAccount();
			ur.setID(accountIDur.trim());
			ur.setSolde("" + solde11);
			ur.save();

			done = true;
		}
		done = false;

		while (!done) {
			// debit the sender account
			StackmobQuery stQuery1 = new StackmobQuery();
			int solde0 = Integer.parseInt(thisUserAccountSolde.trim());
			// debit "-"
			int solde1 = solde0 - Integer.parseInt(amount.trim());

			UserAccount us = new UserAccount();
			us.setID(thisUserAccountID);
			us.setSolde("" + solde1);
			us.save();

			PhoneData p = new PhoneData();
			p.savePrefs(this, "SOLDE", "" + solde1);

			done = true;
		}

		return done;

>>>>>>> 82a705e5cc7ded006539433bcf03489da27c2784
	}

	// allow to find by Name, phone number or email in all your local contacts
	// Display in the autocompleteTextView even if there is no phone but an
	// email or vice versa
	public void PopulateContactList() {
		contactList.clear();

		// Retrieve ContactName, PhoneNumber, Email for each contact
		// there is several method to retrieve contacts data this one is the
		// fastest
		// in case you have lots of contacts. it uses only 1 cursor to retrieve
		// 3 != data types
		// HAS_PHONE_NUMBER is really important if we want to display contact
		// without phone numbers
		Cursor c = getContentResolver()
				.query(Data.CONTENT_URI,
						null,
						Data.HAS_PHONE_NUMBER + " >=0 AND (" + Data.MIMETYPE
								+ "=? OR " + Data.MIMETYPE + "=?)",
						new String[] { Email.CONTENT_ITEM_TYPE,
								Phone.CONTENT_ITEM_TYPE }, Data.CONTACT_ID);

		// the mandatory var to retrieve data
		// Declared here in order to compare previous and next contact
		Boolean email_ok = false;
		Boolean phone_ok = false;
		Boolean add_ok = false;
		String contactName = "";
		String phoneNumber = "";
		String email = "";
		String contactId = "";
		int hasphone = 0;
		String email_or_phone = "";

		while (c.moveToNext()) {

			Map<String, String> NamePhoneEmail = new HashMap<String, String>();
			NamePhoneEmail.clear();

			// we need to compare the previous contactId with the nextOne
			// because the cursor will send
			// email data or phone data first and then make another loop on the
			// same contactID
			// this manipulation will allow us to retrieve phone and email even
			// if one is missing --> else
			if (contactId
					.equals(c.getString(c.getColumnIndex(Data.CONTACT_ID)))
					|| contactId.equals("")) {
				add_ok = false;

				contactId = c.getString(c.getColumnIndex(Data.CONTACT_ID));
				contactName = c.getString(c.getColumnIndex(Data.DISPLAY_NAME));
				email_or_phone = c.getString(c.getColumnIndex(Data.MIMETYPE));
				hasphone = c.getInt(c.getColumnIndex(Data.HAS_PHONE_NUMBER));

				// we need to put phone_ok to true if there is no phone number
				// but email
				if (hasphone == 0) {
					phoneNumber = "";
					phone_ok = true;
				}

				if (email_or_phone.equals(Phone.CONTENT_ITEM_TYPE)) {
					phoneNumber = c.getString(c.getColumnIndex(Data.DATA1));
					phone_ok = true;
				}

				if (email_or_phone.equals(Email.CONTENT_ITEM_TYPE)) {
					email = c.getString(c.getColumnIndex(Data.DATA1));
					email_ok = true;
				}

				if (email_ok && phone_ok) {
					NamePhoneEmail.clear();
					NamePhoneEmail.put("Name", contactName);
					NamePhoneEmail.put("Phone", phoneNumber);
					NamePhoneEmail.put("Email", email);
					// Then add this map to the list.
					contactList.add(NamePhoneEmail);
					email_ok = false;
					phone_ok = false;
					add_ok = true;
				}
			} else {
				if (!add_ok
						|| ((!phone_ok && email_ok) || (!email_ok && phone_ok))) {
					if (!phone_ok && email_ok)
						phoneNumber = "";
					if (!email_ok && phone_ok)
						email = "";
					NamePhoneEmail.clear();
					NamePhoneEmail.put("Name", contactName);
					NamePhoneEmail.put("Phone", phoneNumber);
					NamePhoneEmail.put("Email", email);
					// Then add this map to the list.
					contactList.add(NamePhoneEmail);
					email_ok = false;
					phone_ok = false;
				}

				contactId = c.getString(c.getColumnIndex(Data.CONTACT_ID));
				contactName = c.getString(c.getColumnIndex(Data.DISPLAY_NAME));
				email_or_phone = c.getString(c.getColumnIndex(Data.MIMETYPE));
				hasphone = c.getInt(c.getColumnIndex(Data.HAS_PHONE_NUMBER));

				// we need to put phone_ok to true if there is no phone number
				// but email
				if (hasphone == 0) {
					phoneNumber = "";
					phone_ok = true;
				}

				if (email_or_phone.equals(Phone.CONTENT_ITEM_TYPE)) {
					phoneNumber = c.getString(c.getColumnIndex(Data.DATA1));
					phone_ok = true;
				}

				if (email_or_phone.equals(Email.CONTENT_ITEM_TYPE)) {
					email = c.getString(c.getColumnIndex(Data.DATA1));
					email_ok = true;
				}

				if (email_ok && phone_ok) {
					NamePhoneEmail.clear();
					NamePhoneEmail.put("Name", contactName);
					NamePhoneEmail.put("Phone", phoneNumber);
					NamePhoneEmail.put("Email", email);
					// Then add this map to the list.
					contactList.add(NamePhoneEmail);
					email_ok = false;
					phone_ok = false;
					add_ok = true;
				}
			}

			// here contactID_prev != contactID_next
			// so before storing contactID_next we need to check data
			// integrity of contactID_prev
			// } else {
			// if ((!phone_ok && email_ok) || (phone_ok && !email_ok)) {
			//
			// if (!phone_ok)
			// phoneNumber = "";
			// if (!email_ok)
			// email = "";
			//
			// // we need to have at least a phone number or an email
			// // address
			// NamePhoneEmail.put("Name", contactName);
			// NamePhoneEmail.put("Phone", phoneNumber);
			// NamePhoneEmail.put("Email", email);
			//
			// email_ok = false;
			// phone_ok = false;
			//
			// // Then add this map to the list.
			// contactList.add(NamePhoneEmail);
			//
			// } else {
			//
			// // Then, when data are consistent for contactID_prev
			// // we start our operations on contactID_next
			// contactId = c.getString(c.getColumnIndex(Data.CONTACT_ID));
			// contactName = c.getString(c.getColumnIndex(Data.DISPLAY_NAME));
			// email_or_phone = c.getString(c.getColumnIndex(Data.MIMETYPE));
			// hasphone = c.getInt(c.getColumnIndex(Data.HAS_PHONE_NUMBER));
			//
			// if (hasphone == 0) {
			// phoneNumber = ""; phone_ok = true;
			// }
			//
			// if (email_or_phone.equals(Phone.CONTENT_ITEM_TYPE)) {
			// phoneNumber = c.getString(c.getColumnIndex(Data.DATA1));
			// phone_ok = true;
			// }
			//
			// if (email_or_phone.equals(Email.CONTENT_ITEM_TYPE)) {
			// email = c.getString(c.getColumnIndex(Data.DATA1));
			// email_ok = true;
			// }
			//
			// // we need to have at least a phone number or an email
			// // address
			// if (email_ok && phone_ok) {
			//
			// NamePhoneEmail.put("Name", contactName);
			// NamePhoneEmail.put("Phone", phoneNumber);
			// NamePhoneEmail.put("Email", email);
			//
			// // Then add this map to the list.
			// contactList.add(NamePhoneEmail);
			// email_ok = false;
			// phone_ok = false;
			// }
			// }
			//
			// }
		}

		c.close();

		// Display the result of the query in an autoCompleteTextView allowing
		// user
		// to retrieve data by : Name, Phone or Email
		final AutoCompleteTextView TxtView_AutoComp_TO = (AutoCompleteTextView) findViewById(R.id.AutoComp_TO_);
		SimpleAdapter contactAdapter = new SimpleAdapter(this, contactList,
				R.layout.custcontview,
				new String[] { "Name", "Phone", "Email" }, new int[] {
						R.id.ccontName, R.id.ccontPhone, R.id.ccontEmail });

		TxtView_AutoComp_TO.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> av, View arg1, int index,
					long arg3) {

				Map<String, String> map = (Map<String, String>) av
						.getItemAtPosition(index);

				// Store selected contact info for next step
				String name = map.get("Name");
				String ph = map.get("Phone");
				String em = map.get("Email");
				contactData[0] = name;
				contactData[1] = ph;
				contactData[2] = em;

				TxtView_AutoComp_TO.setText("" + name);
			}
		});
		TxtView_AutoComp_TO.setThreshold(1);
		TxtView_AutoComp_TO.setAdapter(contactAdapter);

	}

	private Boolean loadPrefs() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		boolean AccountIsOK = pref.getBoolean("ACCOUNT", false);

		// Sign_up activity should be display only at the first time. For this
		// we need to store preference
		if (AccountIsOK) {
			bankRIB = pref.getString("BANKRIB", null);
			creditCard = pref.getString("CREDITCARD", null);
			thisUserName = pref.getString("USERNAME", null);
			thisUserEmail = pref.getString("EMAIL", null);
<<<<<<< HEAD
			
		}else{
=======
			thisUserAccountID = pref.getString("ACCOUNTID", null);
			thisUserAccountSolde = pref.getString("SOLDE", null);

		} else {
>>>>>>> 82a705e5cc7ded006539433bcf03489da27c2784
			Builder build = new AlertDialog.Builder(this);
			build.setTitle("WARNING");
			build.setMessage("You have to fill your RIB and CreditCard in Wallet "
					+ "if you want to send and beg money !");
			build.setPositiveButton("Go to Wallet", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent myIntent = new Intent(SendBegActivity.this,
							MyWalletActivity.class);
					SendBegActivity.this.startActivity(myIntent);
				}
			});
			build.setNegativeButton("Cancel", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			build.show();
		}
		return AccountIsOK;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_beg, menu);
		return true;
	}

	// Set the call back to return to previous activity
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
