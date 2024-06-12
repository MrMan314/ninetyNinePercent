package com.ninetyninepercentcasino.game.bj
public class BJPlayer extends Player:
	public BJPlayer(Account account, ServerConnection connection):
		super(account, connection)
	public void addBalance(int amountAdded):
		account.addBalance(amountAdded)
	public void withdraw(int withdrawAmount):
		account.withdraw(withdrawAmount)
