package com.ninetyninepercentcasino;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.ninetyninepercentcasino.audio.MusicManager;
import com.ninetyninepercentcasino.audio.SFXManager;
import com.ninetyninepercentcasino.screens.CasinoScreen;
import com.ninetyninepercentcasino.screens.MainMenu;

public class MainCasino extends Game {
	public MusicManager music;
	public CasinoScreen menu;
	public int balance;
	@Override
	public void create () {
		balance = 1000;
		Gdx.graphics.setContinuousRendering(false); //this project is a turn-based game that doesn't require continuous rendering
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode()); //set the game to fullscreen
		music = new MusicManager(); //music manager that will be accessible to all screens
		//music.playMusic(); //TODO
		SFXManager.loadSFX();
		menu = new MainMenu(this);
		setScreen(menu); //set the screen to be the main menu screen, passing it an instance of the game
	}

	@Override
	public void dispose() {
		music.dispose();
		menu.dispose();
	}
}
