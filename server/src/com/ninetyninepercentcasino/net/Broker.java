package com.ninetyninepercentcasino.net;

import java.net.Socket;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Broker extends Server {
	private int port = 9937;
	private boolean running;
	
	public Broker() throws IOException {
		super(9937);
		running = true;
	}

	public Broker(int port) throws IOException {
		super(port);
		this.port = port;
		running = true;
	}

	public void run() {
		while (running) {
			try {
				clients.add(new BrokerConnection(serverSocket.accept(), clients));
				clients.get(clients.size() - 1).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
