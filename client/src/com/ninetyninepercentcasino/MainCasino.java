package com.ninetyninepercentcasino;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.ninetyninepercentcasino.game.MusicManager;
import com.ninetyninepercentcasino.screens.MainMenu;

public class MainCasino extends Game {
	public MusicManager music;
	@Override
	public void create () {
		Gdx.graphics.setContinuousRendering(false);
		Gdx.graphics.requestRendering();
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		music = new MusicManager();
		music.playMusic();
		setScreen(new MainMenu(this));
	}
}
