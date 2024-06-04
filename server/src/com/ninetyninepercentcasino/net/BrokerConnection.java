package com.ninetyninepercentcasino.net;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class BrokerConnection extends Connection {
	public BrokerConnection(Socket clientSocket, List<Connection> clients) throws IOException {
		super(clientSocket, clients);
	}
	public void run() {
		try {
			while (alive) {
				if(!clientSocket.isConnected()) {
					finish();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
