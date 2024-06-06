package com.ninetyninepercentcasino.net;

import com.ninetyninepercentcasino.database.Database;

import java.net.Socket;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;

public class BJServer extends Server {
	private int port = 9925;
	private boolean running;
	private Database database;

	public BJServer() throws IOException {
		super(9925);
//		try {
//			database = new Database();
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
		running = true;
	}

	public BJServer(int port) throws IOException {
		super(port);
		this.port = port;
//		try {
//			database = new Database();
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
		running = true;
	}

	public void run() {
		while (running) {
			try {
				clients.add(new ServerConnection(serverSocket.accept(), clients, database));
				clients.get(clients.size() - 1).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
