package com.ninetyninepercentcasino.game.bj;

import com.ninetyninepercentcasino.database.Account;
import com.ninetyninepercentcasino.game.Player;
import com.ninetyninepercentcasino.net.Connection;
import com.ninetyninepercentcasino.net.ServerConnection;

/**
 * Models a blackjack player that manages a CardGroup, Connection, and links to an Account
 */
public class BJPlayer extends Player {
	public BJPlayer(Account account, ServerConnection connection){
		super(account, connection);
	}

	/**
	 * adds to the balance of the player's account
	 * @param amountAdded the amount to add to the balance
	 */
	public void addBalance(int amountAdded){
		account.addBalance(amountAdded);
	}

	/**
	 * withdraws from the balance of the player's account
	 * @param withdrawAmount amount to withdraw from the balance
	 */
	public void withdraw(int withdrawAmount){
		account.withdraw(withdrawAmount);
	}
}
