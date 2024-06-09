package com.ninetyninepercentcasino;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.ninetyninepercentcasino.audio.MusicManager;
import com.ninetyninepercentcasino.audio.SFXManager;
import com.ninetyninepercentcasino.screens.MainMenu;
import com.ninetyninepercentcasino.screens.CasinoScreen;

public class MainCasino extends Game {
	public MusicManager music;
	public CasinoScreen menu;
	private String serverAddress;
	private int serverPort;

	public MainCasino(String serverAddress, int serverPort) {
		super();
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
	}

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

	public String getServerAddress() {
		return serverAddress;
	}

	public int getServerPort() {
		return serverPort;
	}

	@Override
	public void dispose() {
		music.dispose();
		menu.dispose();
	}
}
