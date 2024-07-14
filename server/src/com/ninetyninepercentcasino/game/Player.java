package com.ninetyninepercentcasino.game;

import com.ninetyninepercentcasino.database.Account;
import com.ninetyninepercentcasino.net.ServerConnection;

/**
 * Models a player of a game with a Connection that links to an Account
 * @author Grant Liang
 */
public abstract class Player {
	protected Account account;
	protected ServerConnection connection;

	/**
	 * initializes a new player
	 * @param account the account the player is connected to
	 * @param connection
	 */
	public Player(Account account, ServerConnection connection){
		this.account = account;
		this.connection = connection;
	}
	public ServerConnection getConnection(){
		return connection;
	}
	public Account getAccount(){
		return account;
	}
}
