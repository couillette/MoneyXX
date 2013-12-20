package com.entity;

import com.stackmob.sdk.model.StackMobUser;

public class SignedUser extends StackMobUser{

	private String username;
	private int phone;
	private String email;
	private int creditCardTEST;
//	private int ip;
//	private int id_Address;
//	private int id_Account;
	
	
	public SignedUser(String username, String password, String email , int creditCardTEST){
		super(SignedUser.class, username, password);
		this.email = email;
		this.creditCardTEST = creditCardTEST;
	}
	
	
	
	

//	public int getId_Address() {
//		return id_Address;
//	}
//
//	public void setId_Address(int id_Address) {
//		this.id_Address = id_Address;
//	}
//
//	public int getId_Account() {
//		return id_Account;
//	}
//
//	public void setId_Account(int id_Account) {
//		this.id_Account = id_Account;
//	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String pseudo) {
		this.username = pseudo;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getMail() {
		return email;
	}

	public void setMail(String email) {
		this.email = email;
	}

//	public int getIp() {
//		return ip;
//	}
//
//	public void setIp(int ip) {
//		this.ip = ip;
//	}
	

}
