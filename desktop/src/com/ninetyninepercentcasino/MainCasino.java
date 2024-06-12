package com.ninetyninepercentcasino;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.ninetyninepercentcasino.audio.MusicManager;
import com.ninetyninepercentcasino.audio.SFXManager;
import com.ninetyninepercentcasino.screens.CasinoScreen;
import com.ninetyninepercentcasino.screens.SplashScreen;
import ninetyNinePercentChain.NetworkTransaction.TransactionComposer;
import ninetyNinePercentChain.NetworkTransaction.NetworkInterface;
import ninetyNinePercentChain.Keys.KeyPairManager;
import ninetyNinePercentChain.Keys.NewKeyPair;
import java.io.File;

/**
 * The main Game class that will delegate to screens
 * @author Grant Liang
 */
public class MainCasino extends Game {
	public MusicManager music; //the music manager for the game
	public CasinoScreen menu;
	public int balance; //the amount of money the client has
	/**
	 * creates a new game, delegating immediately to a new MainMenu screen
	 */
	@Override
	public void create () {
		NetworkInterface.setup(); //Lets us send transactions
		new File("keys").mkdir(); //Makes a new keys directory, if one does not already exist
		if(!new File("./keys/Client.ser").exists()) { //If a client key does not exist,
			KeyPairManager.writekey(NewKeyPair.newKeyPair(), "Client"); //Create client key
		}
		try {
			TransactionComposer.createTransaction(1000, "Server", KeyPairManager.readKey("Client").getPublic().getEncoded()); //Gives some money to start
		} catch(Exception e) {
			System.out.println(e);
		}
		balance = TransactionComposer.findAccountValue(KeyPairManager.readKey("Client").getPublic().getEncoded()); //Gets player's balance
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode()); //set the game to fullscreen
		music = new MusicManager(); //music manager that will be accessible to all screens
		SFXManager.loadSFX();
		setScreen(new SplashScreen(this)); //set the screen to a new splash screen
	}

	/**
	 * called when the game is disposed
	 */
	@Override
	public void dispose() {
		music.dispose();
		menu.dispose();
	}
}
