/**
 * Server.java
 * Server object to accept connections from clients
 */

package com.ninetyninepercentcasino.net;

import java.net.ServerSocket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Server extends Thread {
	// Server socket and port
	protected ServerSocket serverSocket;
	private int port;

	// List of connections
	protected List<Connection> clients = new ArrayList<Connection>();

	/**
	 * Constructor for Server with port
	 * pre: none
	 * post: Server started on the specified ports
	 */
	public Server(int port) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket(this.port);
	}

	/**
	 * Main method of the server to accept connections
	 * pre: Server object is initialized
	 * post: Server has been run
	 */
	public abstract void run();

	/**
	 * Method to stop the server
	 * pre: Server is started
	 * post: Server is stopped
	 */
	public void finish() throws IOException {
		serverSocket.close();
	}

	/**
	 * Method to send a message to all client connection threads
	 * pre: Server is started
	 * post: message is sent to all connected and alive clients
	 */
	public void sendAll(NetMessage message) throws IOException {
		for (Connection client : clients) {
			client.message(message);
		}
	}

	/**
	 * Method to send a message to all client connection threads, with an origin
	 * pre: Server is started
	 * post: message is sent to all connected and alive clients
	 */
	public void sendAll(NetMessage message, Connection origin) throws IOException {
		for (Connection client : clients) {
			if (client != origin) {
				client.message(message);
			}
		}
	}
}
