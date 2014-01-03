package com.entity;

import com.stackmob.sdk.model.StackMobModel;

public class UserAccount extends StackMobModel{
	
	private String banque_RIB;
	private String creditCard;
	private String moneyXX_solde;
	
	private UserRegistered user_Account;
	
	
	public UserAccount(){
		super(UserAccount.class);
	}
	
	public UserAccount(String banque_RIB, String creditCard, String solde){
		super(UserAccount.class);
		this.banque_RIB = banque_RIB;
		this.creditCard = creditCard;
		this.moneyXX_solde = solde;
	}
	
	

	public UserRegistered getUser_Account() {
		return user_Account;
	}

	public void setUser_Account(UserRegistered user_Account) {
		this.user_Account = user_Account;
	}

	public String getBanque_RIB() {
		return banque_RIB;
	}

	public void setBanque_RIB(String banque_RIB) {
		this.banque_RIB = banque_RIB;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public String getMoneyXX_solde() {
		return moneyXX_solde;
	}

	public void setMoneyXX_solde(String moneyXX_solde) {
		this.moneyXX_solde = moneyXX_solde;
	}

}
