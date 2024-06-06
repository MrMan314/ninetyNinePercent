package com.ninetyninepercentcasino.game;

import java.security.PublicKey;

import com.ninetyninepercentcasino.database.Account;
import com.ninetyninepercentcasino.net.ServerConnection;

/**
 * Models a player of a game with a Connection that links to an Account
 * @author Grant Liang
 */
public class Player {
	protected Account account;
	protected ServerConnection connection;
	protected PublicKey key;
	public Player(Account account, ServerConnection connection, PublicKey key){
		this.account = account;
		this.connection = connection;
		this.key=key;
	}
	public ServerConnection getConnection(){
		return connection;
	}
	public Account getAccount(){
		return account;
	}
	public PublicKey getPublicKey() {
		return key;
	}
}
