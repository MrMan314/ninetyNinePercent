package com.ninetyninepercentcasino;

import com.ninetyninepercentcasino.net.BJServer;

import java.io.IOException;

/**
 * Class to launch the server
 */
public class ServerLauncher {
	public static void main(String[] args) throws IOException {
		BJServer server = new BJServer();
//		Broker broker = new Broker();
		server.start();
//		broker.start();
	}
}
