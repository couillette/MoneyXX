package com.entity;

import com.stackmob.sdk.model.StackMobModel;

public class ProjectAccount extends StackMobModel{
	
	private String bankRIB;
	private String solde;
	
	
	public ProjectAccount(){
		super(ProjectAccount.class);
	}
	
	
	public ProjectAccount(String bank_RIB,String solde){
		super(ProjectAccount.class);
		this.bankRIB = bank_RIB.trim();
		this.solde = solde.trim();
	}
	
	

	public String getbankRIB() {
		return bankRIB;
	}
	public void setbankRIB(String bankRIB) {
		this.bankRIB = bankRIB;
	}
	public String getSolde() {
		return solde;
	}
	public void setSolde(String solde) {
		this.solde = solde;
	}

}
