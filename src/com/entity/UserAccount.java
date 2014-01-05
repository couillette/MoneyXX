package com.entity;

import com.stackmob.sdk.model.StackMobModel;

public class UserAccount extends StackMobModel{
	
	private String bankRIB;
	private String creditCard;
	private String solde;
	
	
	
	public UserAccount(){
		super(UserAccount.class);
	}
	
	public UserAccount(String id){
		super(UserAccount.class);
		this.setID(id);
	}
	
	public UserAccount(String bank_RIB, String creditCard){
		super(UserAccount.class);
		this.bankRIB = bank_RIB.trim();
		this.creditCard = creditCard.trim();
	}
	
	public UserAccount(String bank_RIB, String creditCard, String solde){
		super(UserAccount.class);
		this.bankRIB = bank_RIB.trim();
		this.creditCard = creditCard.trim();
		this.solde = solde.trim();
	}
	


	public String getbankRIB() {
		return bankRIB;
	}

	public void setbankRIB(String bankRIB) {
		this.bankRIB = bankRIB;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public String getSolde() {
		return solde;
	}

	public void setSolde(String solde) {
		this.solde = solde;
	}

}
