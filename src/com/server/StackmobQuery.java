package com.server;

import java.util.List;

import android.text.GetChars;

import com.entity.UserRegistered;
import com.stackmob.sdk.api.StackMobQuery;
import com.stackmob.sdk.callback.StackMobQueryCallback;
import com.stackmob.sdk.exception.StackMobException;

public class StackmobQuery {
	
	Boolean query;
	Boolean userExist;


	public Boolean checkIfUsernameExist(String usernameval){
		userExist = false;
//		query = false;

		 // "while" because stackmob query are asynchronous
//		while(!query){
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
							userExist = true;
						}
						query=true;
					}
				});
//		 }
		 return userExist;
	}
	
	
	public Boolean checkIfUserIsRegisteredByMailOrPhone(String phoneNumber, String email){
		userExist = false;
		query = false;
		 
		 //to make query on phoneNumber we had to change phone attribute in UserRegistered
		 //into String field. because you have things like "+33" or "("... in android field numbers
		 String phnum = phoneNumber.replaceAll("[+()-]", "").replace(" ", "").trim();
		 String mail = email.trim();
		 
		 StackMobQuery checkMail = new StackMobQuery().fieldIsEqualTo("email", mail);
		 StackMobQuery checkPhone = new StackMobQuery().fieldIsEqualTo("phone", phnum);
		
		// while because the query is asynchronous and we need to wait the result
		 while(!query){
		UserRegistered.query(
				UserRegistered.class, 
				new StackMobQuery().fieldIsEqualTo("phone", phnum).or(checkMail),
				new StackMobQueryCallback<UserRegistered>() {

					@Override
					public void failure(StackMobException e) {
//						userExist = false;
						query=true;
					}

					@Override
					public void success(List<UserRegistered> user) {
						if(!user.isEmpty()){
							userExist = true;
						} else {
							userExist = false;
						}
						query=true;
					}
				});
		 }
		return userExist;
	}
	
	
	 private void fetchAllUserRegistered() {
		 
		    // fetch all todo objects from StackMob
		    // Pass in an empty query in order to fetch all objects
		 UserRegistered.query(UserRegistered.class, new StackMobQuery(), new StackMobQueryCallback<UserRegistered>() {
		      @Override
		      public void success(List<UserRegistered> userReg) {
//		        muserReg = userReg; // set our todos container

		        // set our list view adapter
//		        mTodoAdapter = new TodoAdapter(thisContext, android.R.layout.simple_list_item_1, mTodos);

		        // remember that in order to update UI in main view, it needs to be run from UI thread
		      }

		      @Override
		      public void failure(StackMobException e) {
		      }
		    });
		  }
}
