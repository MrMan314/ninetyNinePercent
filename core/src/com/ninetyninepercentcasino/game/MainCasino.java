package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.Game;
import com.ninetyninepercentcasino.game.screens.MainMenu;

public class MainCasino extends Game {
	MusicManager music;
	@Override
	public void create () {
		music = new MusicManager();
		music.playMusic();
		setScreen(new MainMenu(this));
	}
}
