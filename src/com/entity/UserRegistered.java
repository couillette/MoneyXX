package com.entity;


import com.stackmob.sdk.model.StackMobModel;

public class UserRegistered extends StackMobModel{

	private String username;
	private String password;
	//we can't store "long" in Stackmob and "int" is too short
	private String phone;
	private String email;
	private UserAccount user_account;

	
	public UserRegistered(String username, String pwd, String email ,String phone){
		super(UserRegistered.class);
		this.username = username;
		this.password = pwd;
		this.phone = phone;
		this.email = email;
	}
	
	public UserRegistered(String username, String pwd, String email){
		super(UserRegistered.class);
		this.username = username;
		this.password = pwd;
		this.email = email;
	}
	
	public UserRegistered(){
		super(UserRegistered.class);
	}
	
	
	
	public UserAccount getUser_account() {
		return user_account;
	}

	public void setUser_account(UserAccount user_account) {
		this.user_account = user_account;
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
