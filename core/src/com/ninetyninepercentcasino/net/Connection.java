/**
 * Connection.java
 * TCP connection object used by the client and server for communications over the network
 */

package com.ninetyninepercentcasino.net;

import java.net.Socket;
import java.net.SocketException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.StreamCorruptedException;
import java.io.OptionalDataException;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.IllegalStateException;
import java.lang.IllegalMonitorStateException;
import java.lang.NullPointerException;

import com.ninetyninepercentcasino.net.NetMessage;

public abstract class Connection extends Thread {
	// These values should be accessible by the child classes
	protected Socket clientSocket;
	protected ObjectInputStream in;
	protected ObjectOutputStream out;
	protected List<Connection> clients;

	// Not this one though
	private boolean isServer;

	/**
	 * Constructor for a client-side connection
	 * pre: the client socket is given
	 * post: new Connection object
	 */
	public Connection(Socket clientSocket) throws IOException {
		// Log the connection to the console
		System.out.printf("New connection to %s\n", clientSocket.getRemoteSocketAddress().toString());

		// Copy clientSocket reference
		this.clientSocket = clientSocket;
		timerThreads = new ArrayList<Thread>();
		// Set server flag
		isServer = false;
		try {
			// Initialize the input and output streams, should be switched around from the order of the server
			in = new ObjectInputStream(clientSocket.getInputStream());
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			// Schedule a timer for keepAlive
			timer.scheduleAtFixedRate(keepAliveTimeout, 5000, 5000);
		} catch (StreamCorruptedException e) {
			// In case of a corrupt connection, exit
			alive = false;
			finish();
			return;
		}

		// Set alive flag
		alive = true;
	}

	/**
	 * Constructor for a server-side client connection
	 * pre: the client socket and List of Connections is given
	 * post: new Connection object
	 */
	public Connection(Socket clientSocket, List<Connection> clients) throws IOException {
		// Log the connection to the console
		System.out.printf("New connection from %s\n", clientSocket.getRemoteSocketAddress().toString());

		// Copy clientSocket and clients List reference
		this.clients = clients;
		this.clientSocket = clientSocket;
		timerThreads = new ArrayList<Thread>();

		// Set server flag
		isServer = true;
		try {
			// Initialize the input and output streams, should be switched around from the order of the client
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
			// Schedule a timer for keepAlive
			timer.scheduleAtFixedRate(keepAliveTimeout, 5000, 5000);
		} catch (StreamCorruptedException e) {
			// In case of a corrupt connection, exit
			alive = false;
			finish();
			return;
		}

		// Set alive flag
		alive = true;
	}

	/**
	 * Method to close the connection
	 * pre: the Connection is initialized
	 * post: all streams closed, timers stopped, Connection removed from the clients List if server
	 */
	public void finish() throws IOException {
		// Set alive flag
		alive = false;

		// Clear the timers
		try {
			timer.cancel();
			timer.purge();
		} catch (IllegalStateException e) {

		}

		// Close the connections
		clientSocket.close();
		Thread.currentThread().interrupt();
		try {
			in.close();
		} catch (NullPointerException e) {

		}
		try {
			out.close();
		} catch (SocketException e) {

		}

		for (Thread timerThread : timerThreads) {
			timerThread.interrupt();
		}

		// Remove self from clients list if it is a server thread
		if (isServer) {
			clients.remove(this);
		}

		dispose();

		// Log the connection to the console
		System.out.printf("Connection closed from %s\n", clientSocket.getRemoteSocketAddress().toString());
	}

	// Variables for keepalive timer
	protected String aliveMessage = "";
	protected boolean alive = false;
	protected Timer timer = new Timer();
	protected List<Thread> timerThreads;

	// Timer task for keepAlive
	protected TimerTask keepAliveTimeout = new TimerTask() {
		/**
		 * Main method of the keepAlive timer task
		 * pre: none
		 * post: the keepAlive thread is started
		 */
		public void run() {
			// Create a new thread to allow for concurrent executions, set initial value to avoid some goofy ahh error about not being initialized possibly
			Thread timerThread = null;
			timerThread = new Thread() {
				/**
				 * Main method of the keepAlive thread
				 * pre: none
				 * post: keepAlive thread is run, close the connection if dead.
				 */
				public void run() {
					try {
						// If the connection is closed, finish
						if (!clientSocket.isConnected()) {
							finish();
							return;
						}

						// Put aliveMessage into a synchronized block to notify
						synchronized (aliveMessage) {
							// Notify the existing keepAlive threads, if any, to resume execution of the keepAlive
							aliveMessage.notify();
							// Clear alive message
							aliveMessage = "";
							// Create and send a new ping message
							NetMessage pingMessage = new NetMessage(NetMessage.MessageType.PING, "ping");
							synchronized (out) {
								out.writeObject(pingMessage);
							}
							// Wait for a notification later from the keepAlive thread
							aliveMessage.wait();
						}

						// If the aliveMessage is still empty, the client is dead.  finish them.
						if (aliveMessage.isEmpty()) {
							finish();
							return;
						}
						timerThreads.remove(this);
					} catch (IllegalMonitorStateException | InterruptedException e) {
						// These errors can be ignored.
						return;
					} catch (SocketException | NullPointerException e) {
						// If the connection is unexpectedly closed, these errors are thrown
						try {
							// Close the connection
							finish();
						} catch (IOException f) {
							f.printStackTrace();
						}
						return;
					} catch (IOException e) {
						// This error should not be ignored
						e.printStackTrace();
						return;
					}
				}
			};
			timerThread.start();
			timerThreads.add(timerThread);
		}
	};

	/**
	 * Accessor method for clientSocket
	 * pre: clientSocket exists
	 * post: clientSocket is returned
	 */
	public Socket getClientSocket() {
		return clientSocket;
	}

	/**
	 * Method to message the client
	 * pre: Connection has been started
	 * post: NetMessage is sent
	 */
	public void message(NetMessage message) throws IOException {
		out.writeObject(message);
	}

	/**
	 * Method for additional dispositions of child classes, called from finish
	 * pre: Connection is being closed
	 * post: More things are disposed of
	 */
	public abstract void dispose();

	/**
	 * Main method of the Connection thread, to be implemented by children
	 * pre: Connection object is initialized
	 * post: Connection is started and run
	 */
	public abstract void run();
}
