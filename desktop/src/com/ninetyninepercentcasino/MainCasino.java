package com.ninetyninepercentcasino;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.ninetyninepercentcasino.game.MusicManager;
import com.ninetyninepercentcasino.game.SFXManager;
import com.ninetyninepercentcasino.screens.MainMenu;
import com.ninetyninepercentcasino.screens.CasinoScreen;

public class MainCasino extends Game {
	public MusicManager music;
	public CasinoScreen menu;
	@Override
	public void create () {
		Gdx.graphics.setContinuousRendering(false); //this project is a turn-based game that doesn't require continuous rendering
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode()); //set the game to fullscreen
		music = new MusicManager(); //music manager that will be accessible to all screens
		music.playMusic();
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
