package com.entity;

import com.stackmob.sdk.model.StackMobUser;

public class SignedUser extends StackMobUser{
	
	private int id;
	private int id_Address;
	private int id_Account;
	private String username;
	private int phone;
	private String mail;
	private int ip;
	
	
	public SignedUser(String username, String password){
		super(SignedUser.class, username, password);
	}
	
	
	
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_Address() {
		return id_Address;
	}

	public void setId_Address(int id_Address) {
		this.id_Address = id_Address;
	}

	public int getId_Account() {
		return id_Account;
	}

	public void setId_Account(int id_Account) {
		this.id_Account = id_Account;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getIp() {
		return ip;
	}

	public void setIp(int ip) {
		this.ip = ip;
	}
	

}
