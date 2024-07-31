/**
 * 99% Casino Project
 * Multiplayer casino game with a blockchain to handle transactions
 * Grant, Luke, and Felix
 * ICS4Ud
 * Mr. Gonzalez
 * June 12 2024
 */
package com.ninetyninepercentcasino.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import com.ninetyninepercentcasino.MainCasino;

import java.util.Properties;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument

/**
 * Launches the desktop application
 */
public class DesktopLauncher {
	public static void main (String[] args) throws IOException {
		Properties props = new Properties();
		String serverAddress = "";
		int serverPort = -1;
		boolean ready = false;
		while (!ready) {
			try {
				props.load(new FileInputStream("client.properties"));
				serverAddress = props.getProperty("serverAddress");
				if (serverAddress == null) {
					throw new RuntimeException("Configuration missing required fields.");
				}
				try {
					serverPort = Integer.parseInt(props.getProperty("serverPort"));
					if (serverPort < 0 || serverPort > 65535) {
						throw new RuntimeException(String.format("Invalid port number: %d.", serverPort));
					}
				} catch (NumberFormatException e) {
					throw new RuntimeException(String.format("Invalid port number: %s.", props.getProperty("port")));
				}
				ready = true;
			} catch (FileNotFoundException e) {
				File configFile = new File("client.properties");
				configFile.createNewFile();
				FileWriter config = new FileWriter("client.properties");
				config.write("serverAddress=127.0.0.1\nserverPort=9925");
				config.close();
			}
		}
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("99% Casino"); //set the title of the window
		config.useVsync(true);
		config.setWindowedMode(1920, 1080);
		config.setWindowIcon("DesktopWindowIcon.png");
		new Lwjgl3Application(new MainCasino(serverAddress, serverPort), config);
	}
}
