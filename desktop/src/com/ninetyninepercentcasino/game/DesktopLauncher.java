package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.ninetyninepercentcasino.MainCasino;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] args) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("99% Casino"); //set the title of the window
		config.useVsync(true);
		config.setWindowedMode(1920, 1080);
		config.setWindowIcon("DesktopWindowIcon.png");
		new Lwjgl3Application(new MainCasino(), config); //launch a new MainCasino for the window
	}
}
