package com.ninetyninepercentcasino;

import com.ninetyninepercentcasino.net.BJServer;
import com.ninetyninepercentcasino.net.Broker;

import java.io.IOException;

public class ServerLauncher {
	public static void main(String[] args) throws IOException {
		BJServer server = new BJServer();
		Broker broker = new Broker();
		server.start();
		broker.start();
	}
}
