package com.ninetyninepercentcasino.net;

import java.io.IOException;

public class ServerLauncher {
	public static void main(String[] args) throws IOException {
		BJServer server = new BJServer();
		Broker broker = new Broker();
		server.start();
		broker.start();
	}
}
