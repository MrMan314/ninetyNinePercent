package com.ninetyninepercentcasino;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.ninetyninepercentcasino.game.MusicManager;
import com.ninetyninepercentcasino.screens.MainMenu;

public class MainCasino extends Game {
	public MusicManager music;
	@Override
	public void create () {
		Gdx.graphics.setContinuousRendering(false); //this project is a turn-based game that doesn't require continuous rendering
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode()); //set the game to fullscreen
		music = new MusicManager(); //music manager that will be accessible to all screens
		music.playMusic();
		setScreen(new MainMenu(this)); //set the screen to be the main menu screen, passing it an instance of the game
	}
}
