package com.ninetyninepercentcasino;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.ninetyninepercentcasino.audio.MusicManager;
import com.ninetyninepercentcasino.audio.SFXManager;
import com.ninetyninepercentcasino.screens.CasinoScreen;
import com.ninetyninepercentcasino.screens.MainMenu;
import com.ninetyninepercentcasino.screens.SplashScreen;

/**
 * The main Game class that will delegate to screens
 * @author Grant Liang
 */
public class MainCasino extends Game {
	public MusicManager music; //the music manager for the game
	public CasinoScreen menu;
	public int balance;
	/**
	 * creates a new game, delegating immediately to a new MainMenu screen
	 */
	@Override
	public void create () {
		balance = 1000;
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode()); //set the game to fullscreen
		music = new MusicManager(); //music manager that will be accessible to all screens
		SFXManager.loadSFX();
		menu = new MainMenu(this);
		SplashScreen splashScreen = new SplashScreen(this);
		setScreen(splashScreen);
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
