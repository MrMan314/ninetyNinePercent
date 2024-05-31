package com.ninetyninepercentcasino.net;

import com.ninetyninepercentcasino.database.Database;

import java.net.ServerSocket;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
	private ServerSocket serverSocket;
	private int port = 9925;
	private Database database;

	private List<Connection> clients = new ArrayList<Connection>();

	public Server() throws IOException {
		serverSocket = new ServerSocket(this.port);
//        try { database = new Database();
//        } catch (SQLException e) { throw new RuntimeException(e); }
    }

	public Server(int port) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket(this.port);
//		try { database = new Database();
//		} catch (SQLException e) { throw new RuntimeException(e); }
	}

	public void run() {
		boolean running = true;
		while (running) {
			try {
				clients.add(new ServerConnection(serverSocket.accept(), clients));
				clients.get(clients.size() - 1).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void finish() throws IOException {
		serverSocket.close();
	}

	public void sendAll(NetMessage message) throws IOException {
		for (Connection client: clients) {
			client.message(message);
		}
	}
	
	public void sendAll(NetMessage message, Connection origin) throws IOException {
		for (Connection client: clients) {
			if (client != origin) {
				client.message(message);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.start();
	}
}
