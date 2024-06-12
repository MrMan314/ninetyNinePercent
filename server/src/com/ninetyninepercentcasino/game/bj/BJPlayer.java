package com.ninetyninepercentcasino.game.bj;

import com.ninetyninepercentcasino.database.Account;
import com.ninetyninepercentcasino.game.Player;
import com.ninetyninepercentcasino.net.ServerConnection;
import java.security.PublicKey;
import ninetyNinePercentChain.Keys.KeyPairManager;

/**
 * Models a blackjack player that manages a CardGroup, Connection, and links to an Account
 */
public class BJPlayer extends Player {
	private PublicKey key;

	/**
	 * initializes a new blackjack player
	 * @param account the account connected to the player
	 * @param connection the player's connection to the server
	 */
	public BJPlayer(Account account, ServerConnection connection){
		super(account, connection);
		key=KeyPairManager.readKey("Client");
	}
	public PublicKey getPublicKey() {
		return key;
	}
}
