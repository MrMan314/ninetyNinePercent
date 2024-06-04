package com.ninetyninepercentcasino.net;

import java.net.Socket;
import java.io.IOException;

public class HolePunchConnection extends Connection {
	public HolePunchConnection(Socket clientSocket) throws IOException {
		super(clientSocket);
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
