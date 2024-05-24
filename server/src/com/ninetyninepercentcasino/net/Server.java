/**
 * Server.java
 * Server object to accept connections from clients
 */

package com.ninetyninepercentcasino.net;

import java.net.ServerSocket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ninetyninepercentcasino.net.ServerConnection;
import com.ninetyninepercentcasino.net.Connection;
import com.ninetyninepercentcasino.net.NetMessage;

public class Server extends Thread {
	// Server socket and port
	private ServerSocket serverSocket;
	private int port = 9925;

	// List of connections
	private List<Connection> clients = new ArrayList<Connection>();

	/**
	 * Constructor for Server without port
	 * pre: none
	 * post: Server started on port 9925 (default)
	 */
	public Server() throws IOException {
		serverSocket = new ServerSocket(this.port);
	}

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
	public void run() {
		// Running flag
		boolean running = true;
		while (running) {
			try {
				// Accept a new client connection from the next client
				clients.add(new ServerConnection(serverSocket.accept(), clients));
				// Start the client connection thread
				clients.get(clients.size() - 1).start();
			} catch (IOException e) {
				// Show error
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method to stop the server
	 * pre: Server is started
	 * post: Server is stopped
	 */
	public void finish() throws IOException {
		for (Connection client: clients) {
			client.finish();
		}
		serverSocket.close();
	}

	/**
	 * Method to send a message to all client connection threads
	 * pre: Server is started
	 * post: message is sent to all connected and alive clients
	 */
	public void sendAll(NetMessage message) throws IOException {
		for (Connection client: clients) {
			client.message(message);
		}
	}
	
	/**
	 * Method to send a message to all client connection threads, with an origin
	 * pre: Server is started
	 * post: message is sent to all connected and alive clients
	 */
	public void sendAll(NetMessage message, Connection origin) throws IOException {
		for (Connection client: clients) {
			// Send message to everyone but the original client
			if (client != origin) {
				client.message(message);
			}
		}
	}
}
