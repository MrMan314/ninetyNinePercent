package com.ninetyninepercentcasino.game.bj;

import java.security.PublicKey;

import com.ninetyninepercentcasino.database.Account;
import com.ninetyninepercentcasino.game.Player;
import com.ninetyninepercentcasino.net.Connection;
import com.ninetyninepercentcasino.net.ServerConnection;

/**
 * Models a blackjack player that manages a CardGroup, Connection, and links to an Account
 */
public class BJPlayer extends Player {
<<<<<<< Updated upstream
	public BJPlayer(Account account, ServerConnection connection){
		super(account, connection);
=======
	public BJPlayer(Account account, ServerConnection connection, PublicKey publicKey){
		super(account, connection, publicKey);
>>>>>>> Stashed changes
	}
}
