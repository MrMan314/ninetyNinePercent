/**
 * BJServer.java
 * BJServer object to accept Blackjack game connections
 */

package com.ninetyninepercentcasino.net;

import com.ninetyninepercentcasino.database.Database;

import java.net.Socket;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;

public class BJServer extends Server {
	// Default port
	private int port = 9925;
	private boolean running, accountsSupported;
	private Database database;

	/**
	 * Constructor for BJServer without port
	 * pre: none
	 * post: Server started on the default port
	 */
	public BJServer(String DBAddr, String DBUser, String DBPassword) throws IOException {
		super(9925);
		accountsSupported = true;
		try {
			database = new Database(DBAddr, DBUser, DBPassword);
		} catch (SQLException e) {
			accountsSupported = false;
		}
		running = true;
	}

	/**
	 * Constructor for BJServer with port
	 * pre: none
	 * post: Server started on the specified ports
	 */
	public BJServer(String DBAddr, String DBUser, String DBPassword, int port) throws IOException {
		super(port);
		this.port = port;
		accountsSupported = true;
		try {
			database = new Database(DBAddr, DBUser, DBPassword);
		} catch (SQLException e) {
			accountsSupported = false;
		}
		running = true;
	}

	/**
	 * Main method in thread to accept connections and start a new thread
	 * pre: Server is started
	 * post: Client connections are accepted
	 */
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
