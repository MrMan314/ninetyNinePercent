package com.ninetyninepercentcasino.database;

/**
 * handles a player account with a balance, username, and password
 */
public class Account {
	String username;
	byte[] address;
	Database database;
	/**
	 * initializes a new account
	 * @param username the user's username
	 */
	public Account(String username, String password) {
		this.username=username;
		try {
			database=new Database();
			database.loadUser(username, password);
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * adds money to the account
	 * @param amountAdded the amount added
	 */
	public void addBalance(int amountAdded){
		database.addBalance(this, amountAdded);
	}

	/**
	 * withdraws money from the account
	 * @param withdrawAmount the amount withdrawn
	 */
	public void withdraw(int withdrawAmount){
		database.withdraw(this, amountAdded);
	}
}