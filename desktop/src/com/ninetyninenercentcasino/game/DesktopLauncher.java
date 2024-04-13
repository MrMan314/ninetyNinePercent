package com.ninetyninenercentcasino.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.ninetyninenercentcasino.game.MainCasino;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] args) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("99% Casino");
		config.useVsync(true);
		config.setWindowedMode(1920, 1080);
		new Lwjgl3Application(new MainCasino(), config);
	}
}
