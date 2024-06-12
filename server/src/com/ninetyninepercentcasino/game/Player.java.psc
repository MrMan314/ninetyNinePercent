package com.ninetyninepercentcasino.game
public class Player:
	protected Account account
	protected ServerConnection connection
	public Player(Account account, ServerConnection connection):
		this.account = account
		this.connection = connection
	public ServerConnection getConnection():
		return connection
	public Account getAccount():
		return account
