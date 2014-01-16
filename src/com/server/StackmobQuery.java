package com.server;

import java.util.ArrayList;
import java.util.List;

import com.entity.UserAccount;
import com.entity.UserRegistered;
import com.stackmob.sdk.api.StackMobQuery;
import com.stackmob.sdk.callback.StackMobModelCallback;
import com.stackmob.sdk.callback.StackMobQueryCallback;
import com.stackmob.sdk.exception.StackMobException;

public class StackmobQuery {

	Boolean query;
	Boolean userExist;
	String username;

	String userAccountID;
	int userAccountSolde;
	String userAccountBankRIB;
	String userAccountCreditCard;

	List<UserRegistered> USERLIST;
	List<UserAccount> userAccountList;

	public Boolean checkByUsername(String usernameval) {
		userExist = false;
		query = false;

		final String name = usernameval.trim();

		// "while" because stackmob query are asynchronous
		while (!query) {
			UserRegistered.query(UserRegistered.class,
					new StackMobQuery().fieldIsEqualTo("username", name),
					new StackMobQueryCallback<UserRegistered>() {

						@Override
						public void failure(StackMobException e) {
							query = true;
						}

						@Override
						public void success(List<UserRegistered> userN) {
							if (!userN.isEmpty()) {
								userExist = true;
							}
							query = true;
						}
					});
		}
		return userExist;
	}

	public Boolean checkByMailOrPhone(String phoneNumber, String email) {
		userExist = false;
		query = false;
		username = "";

		// to make query on phoneNumber we had to change phone attribute in
		// UserRegistered
		// into String field. because you have things like "+33" or "("... in
		// android field numbers
		String phnum = phoneNumber.replaceAll("[+()-]", "").replace(" ", "")
				.trim();
		String mail = email.trim();

		StackMobQuery checkMail = new StackMobQuery().fieldIsEqualTo("email",
				mail);
		StackMobQuery checkPhone = new StackMobQuery().fieldIsEqualTo("phone",
				phnum);

		// while because the query is asynchronous and we need to wait the
		// result
		while (!query) {
			UserRegistered.query(UserRegistered.class, new StackMobQuery()
					.fieldIsEqualTo("phone", phnum).or(checkMail),
					new StackMobQueryCallback<UserRegistered>() {

						@Override
						public void failure(StackMobException e) {
							// userExist = false;
							query = true;
						}

						@Override
						public void success(List<UserRegistered> user) {
							if (!user.isEmpty()) {
								userExist = true;
								username = user.get(0).getUsername().toString()
										.trim();
							} else {
								userExist = false;
							}
							query = true;
						}
					});
		}
		return userExist;
	}

	public String fetchUserAccountID(String usernamev) {
		userAccountID = "";
		query = false;

		while (!query) {
			UserRegistered.query(UserRegistered.class, new StackMobQuery()
					.fieldIsEqualTo("userregistered_id", usernamev.trim()),
					new StackMobQueryCallback<UserRegistered>() {

						public void failure(StackMobException e) {
						}

						public void success(List<UserRegistered> userAsolde) {
							if (!userAsolde.isEmpty()) {
								userAccountID = userAsolde.get(0)
										.getUser_account().getID().trim();
							}
							query = true;
						}
					});
		}
		return userAccountID;

	}

	public int fetchUserAccountSolde(String accountID) {
		userAccountSolde = 0;
		query = false;

		while (!query) {
			UserAccount.query(UserAccount.class, new StackMobQuery()
					.fieldIsEqualTo("useraccount_id", accountID.trim()),
					new StackMobQueryCallback<UserAccount>() {

						public void failure(StackMobException e) {
						}

						public void success(List<UserAccount> userAcc) {
							userAccountSolde = Integer.parseInt(userAcc.get(0)
									.getSolde().trim());
						}
					});
		}
		return userAccountSolde;
	}

	public List<UserAccount> fetchUserAccountByID(String accountID) {
		query = false;

		while (!query) {
			UserAccount.query(UserAccount.class, new StackMobQuery()
					.fieldIsEqualTo("useraccount_id", accountID.trim()),
					new StackMobQueryCallback<UserAccount>() {
						@Override
						public void success(List<UserAccount> useraccounts) {
							userAccountList = useraccounts;
							query = true;
						}

						@Override
						public void failure(StackMobException e) {
						}
					});
		}
		return userAccountList;
	}

	public void fetchAllUserRegistered() {
		query = false;

		// fetch all todo objects from StackMob
		// Pass in an empty query in order to fetch all objects
		while (!query) {
			UserRegistered.query(UserRegistered.class, new StackMobQuery(),
					new StackMobQueryCallback<UserRegistered>() {
						@Override
						public void success(List<UserRegistered> userReg) {
							USERLIST = userReg;
							query = true;
						}

						@Override
						public void failure(StackMobException e) {
						}
					});
		}
	}


	public List<UserAccount> fetchSoldeByBankRIBandCreditCard(
			String bankrib_solde, String creditcard_solde) {
		query = false;
		// userAccountList.clear();

		// StackMobQuery checkBankRIB = new
		// StackMobQuery().fieldIsEqualTo("bankrib",bankrib_solde.trim());
		StackMobQuery checkCreditCard = new StackMobQuery().fieldIsEqualTo(
				"creditcard", creditcard_solde.trim());

		while (!query) {
			UserAccount.query(
					UserAccount.class,
					new StackMobQuery().fieldIsEqualTo("bankrib",
							bankrib_solde.trim()).and(checkCreditCard),
					new StackMobQueryCallback<UserAccount>() {

						public void failure(StackMobException e) {
						}

						public void success(List<UserAccount> userAS) {
							if (!userAS.isEmpty()) {
								userAccountList = userAS;
							}
							query = true;
						}
					});
		}
		return userAccountList;
	}
	
	public List<UserAccount> getUserAccountList() {
		return userAccountList;
	}

	public String getUserAccountBankRIB() {
		return userAccountBankRIB;
	}

	public String getUserAccountCreditCard() {
		return userAccountCreditCard;
	}

	public Boolean getQuery() {
		return query;
	}

	public void setQuery(Boolean query) {
		this.query = query;
	}

	public int getUserAccountSolde() {
		return userAccountSolde;
	}

	public String getUsername() {
		return username;
	}

	public List<UserRegistered> getUserList() {
		return USERLIST;
	}

	public Boolean getUserExist() {
		return userExist;
	}

	public String getUserAccountID() {
		return userAccountID;
	}

	public void setUserAccountID(String userAccountID) {
		this.userAccountID = userAccountID;
	}
}
