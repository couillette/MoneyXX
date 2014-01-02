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
import com.stackmob.android.sdk.common.StackMobAndroid;
import com.stackmob.sdk.api.StackMobQuery;
import com.stackmob.sdk.api.StackMobQueryField;
import com.stackmob.sdk.callback.StackMobCallback;
import com.stackmob.sdk.callback.StackMobModelCallback;
import com.stackmob.sdk.callback.StackMobQueryCallback;
import com.stackmob.sdk.exception.StackMobException;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
//	private SimpleAdapter contactAdapter;
//	private AutoCompleteTextView TxtView_AutoComp_TO;
	
	//Selected contact
	private String[] contactData = new String[] { "Name", "Phone", "Email" };
	
	//text return by checkIfUserIsRegistered()
	String alertDialog_text;
	
	Boolean userIsRegisterd;
	String thisUserName;
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_beg);
 
		// read local contact and provide autocompleteTextView 
		// feature to display and select them
		contactList = new ArrayList<Map<String, String>>();
		contactList.clear();
		PopulateContactList();
		
//		thisUserName = query username in preferences;
		
//		UserAccount account = new UserAccount("123456789", "123456789", "123456789", "0");
		UserAccount account = new UserAccount("1", "123456789", "123456789", "0");
		account.setID("1111222");
		account.save();

		UserRegistered u = new UserRegistered("sergelefou", "qsdf", "serge@hotmail.fr", "0648765342");
		u.setUser_account(account);
		u.save();
		
		

		
		
		
//		String pS= "3344447532";
//		UserRegistered uS = new UserRegistered("sergelefou", "qsdf", "serge@hotmail.fr", pS);
//		uS.save();
//		
//		String pSS= "41083993";
//		UserRegistered uSS = new UserRegistered("sergY", "QSDf", "migwel@gmail.com", pSS);
//		uSS.save();
		
		
		//if user click on one of the 2 button --> onClick(view)

		
	}
	

	
	
	// if user click on the buttons
	public void onClick(View view){
		switch (view.getId()) {
		
		//if user click on "Send Money"
		case R.id.buton_SendRequest:
			
			final String amount = ((EditText) findViewById(R.id.editText_AmountOfMoney)).getText().toString();

			// checkIfUserIsRegistered();
			// this query was called onItemClickListener because it is asynchronous, which is quicker.

			new AlertDialog.Builder(this)
			.setTitle("Warning")
			.setMessage("You are going to send "+amount+" $ to :\n" +
					"  "+contactData[0]+"\n  "+contactData[1]+"\n  "+contactData[2] + alertDialog_text)
			
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if ( userIsRegisterd ){
//						
//						//credit receiver account
//						//debit sender account
					
//						//send message (mail or sms) to receiver
						String[] mailAddressR = {contactData[2].trim()};
						String subR = "money earned";
						String messageR = "you just receive "+amount+"$ from "+thisUserName+
							" and your account was credited";
						new SendEmailAsyncTask(mailAddressR, subR, messageR).execute();
					
						//send message (mail or sms) e to sender
						String[] mailAddressS = {contactData[2].trim()};
						String subS = "money sent";
						String messageS = "you sent "+amount+"$ to "+contactData[0].trim()+
							" and your account was debited";
						new SendEmailAsyncTask(mailAddressS, subS, messageS).execute();
//						
					} else if ( !userIsRegisterd ) {
//						
//						
//						
					}
				}
			})
			.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			})
			.show();
		break;
		
		//if user click on "Beg Money"
		case  R.id.button_BegRequest:
			
			new AlertDialog.Builder(this)
			.setTitle("Warning contact")
			.setMessage("va bien niker BEG")
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			})
			.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			})
			.show();
			
		break;
		}
		
	}
	
	
	
	
	public void checkIfUserIsRegistered(){
		 userIsRegisterd = false;
		 
		 //to make query on phoneNumber we had to change phone attribute in UserRegistered
		 //into String field. because you have things like "+33" or "("... in android field numbers
		 String phnum = contactData[1].replaceAll("[+()-]", "").replace(" ", "").trim();
		 String mail = contactData[2].trim();
		 
		 StackMobQuery checkMail = new StackMobQuery().fieldIsEqualTo("email", mail);
		 StackMobQuery checkPhone = new StackMobQuery().fieldIsEqualTo("phone", phnum);
		
		// while because the query is asynchronous and we need to wait the result
		UserRegistered.query(
				UserRegistered.class, 
				new StackMobQuery().fieldIsEqualTo("phone", phnum).or(checkMail),
				new StackMobQueryCallback<UserRegistered>() {

					@Override
					public void failure(StackMobException e) {
						alertDialog_text = "\n\nWARNING : this person doesn't have any MoneyXX Account\n" +
								"Clik on OK and we will send him a message to register and credit a temporary account based" +
								" on the information you gave.";
					}

					@Override
					public void success(List<UserRegistered> user) {
						if(!user.isEmpty()){
							alertDialog_text = "\n\nGreat! This person is kown and have a MoneyXX account.";
							userIsRegisterd = true;
						} else {
							alertDialog_text = "\n\nWARNING : this person doesn't have any MoneyXX Account" +
									"\n- 'OK' and we will credit a temporary account based on the information " +
									"you gave and send him a message to register to MoneyXX"+
									"\n- 'NO' if you are not sure";
							userIsRegisterd = false;
						}
					}
				});
		
	}
	
	
	public void creditAccount(){
		String amount = ((EditText) findViewById(R.id.editText_AmountOfMoney)).getText().toString();
		String [] contactData_buf = contactData;
//		userIsRegisterd
	}
	
	
	
	// allow to find by Name, phone number or email in all your local contacts
	// Display in the autocompleteTextView even if there is no phone but an email or vice versa
	public void PopulateContactList() {
		contactList.clear();

		// Retrieve ContactName, PhoneNumber, Email for each contact
		// there is several method to retrieve contacts data this one is the
		// fastest
		// in case you have lots of contacts. it uses only 1 cursor to retrieve
		// 3 != data types
		// HAS_PHONE_NUMBER is really important if we want to display contact
		// without phone numbers
		Cursor c = getContentResolver().query(
				Data.CONTENT_URI,
				null,
				Data.HAS_PHONE_NUMBER + " >=0 AND (" + Data.MIMETYPE + "=? OR " + Data.MIMETYPE + "=?)",
				new String[] { Email.CONTENT_ITEM_TYPE, Phone.CONTENT_ITEM_TYPE }, Data.CONTACT_ID);

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
			if (contactId.equals(c.getString(c.getColumnIndex(Data.CONTACT_ID))) || contactId.equals("")) {
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
					add_ok=true;
				}
			} else {
				if (!add_ok || ((!phone_ok && email_ok) || (!email_ok && phone_ok)) ) {
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
					add_ok=true;
				}
				}
			


				// here contactID_prev != contactID_next
				// so before storing contactID_next we need to check data
				// integrity of contactID_prev
//			} else {
//				if ((!phone_ok && email_ok) || (phone_ok && !email_ok)) {
//
//					if (!phone_ok)
//						phoneNumber = "";
//					if (!email_ok)
//						email = "";
//
//					// we need to have at least a phone number or an email
//					// address
//					NamePhoneEmail.put("Name", contactName);
//					NamePhoneEmail.put("Phone", phoneNumber);
//					NamePhoneEmail.put("Email", email);
//
//					email_ok = false;
//					phone_ok = false;
//
//					// Then add this map to the list.
//					contactList.add(NamePhoneEmail);
//
//				} else {
//
//					// Then, when data are consistent for contactID_prev
//					// we start our operations on contactID_next
//					contactId = c.getString(c.getColumnIndex(Data.CONTACT_ID));
//					contactName = c.getString(c.getColumnIndex(Data.DISPLAY_NAME));
//					email_or_phone = c.getString(c.getColumnIndex(Data.MIMETYPE));
//					hasphone = c.getInt(c.getColumnIndex(Data.HAS_PHONE_NUMBER));
//
//					if (hasphone == 0) {
//						phoneNumber = ""; phone_ok = true;
//					}
//
//					if (email_or_phone.equals(Phone.CONTENT_ITEM_TYPE)) {
//						phoneNumber = c.getString(c.getColumnIndex(Data.DATA1));
//						phone_ok = true;
//					}
//
//					if (email_or_phone.equals(Email.CONTENT_ITEM_TYPE)) {
//						email = c.getString(c.getColumnIndex(Data.DATA1));
//						email_ok = true;
//					}
//
//					// we need to have at least a phone number or an email
//					// address
//					if (email_ok && phone_ok) {
//						
//						NamePhoneEmail.put("Name", contactName);
//						NamePhoneEmail.put("Phone", phoneNumber);
//						NamePhoneEmail.put("Email", email);
//						
//						// Then add this map to the list.
//						contactList.add(NamePhoneEmail);
//						email_ok = false;
//						phone_ok = false;
//					}
//				}
//
//			}
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

				Map<String, String> map = (Map<String, String>) av.getItemAtPosition(index);

				// Store selected contact info for next step
				String name = map.get("Name");
				String ph = map.get("Phone");
				String em = map.get("Email");
				contactData[0] = name;
				contactData[1] = ph;
				contactData[2] = em;

				TxtView_AutoComp_TO.setText("" + name);
				
				// query on stackmob datastore to check if user are registered or not
				// call onItemClickListener because it is asynchronous, which enhance speed.
				// put userIsRegisterd to true or false.
				checkIfUserIsRegistered();
			}
		});
		TxtView_AutoComp_TO.setAdapter(contactAdapter);

	}
}
