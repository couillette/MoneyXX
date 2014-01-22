package com.entity;

import com.stackmob.sdk.model.StackMobModel;

public class UserUnRegistered extends StackMobModel {
	private String phone;
	private String email;
	private String password;
	private UserAccount user_account;
	
	public UserUnRegistered(String email, String phone, String pwd) {
		super(UserUnRegistered.class);
		this.phone = phone;
		this.email = email;
		this.password = pwd;
	}

	public UserUnRegistered(String email) {
		super(UserUnRegistered.class);
		this.email = email;
	}
	
	public UserUnRegistered() {
		super(UserUnRegistered.class);
	}
	
	
	public UserAccount getUser_account() {
		return user_account;
	}

	public void setUser_account(UserAccount user_account) {
		this.user_account = user_account;
	}
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
	

	
	


