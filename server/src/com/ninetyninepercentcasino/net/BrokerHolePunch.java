package com.ninetyninepercentcasino.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.List;

public class BrokerHolePunch extends Thread {
	private List<Connection> clients;
	private List<BrokerHolePunch> punchClients;
	private Socket clientSocket;

	private ObjectInputStream in;
	private ObjectOutputStream out;

	private boolean alive;

	public BrokerHolePunch(Socket clientSocket, List clients, List punchClients) throws IOException {
		this.clients = clients;
		this.clientSocket = clientSocket;
		this.punchClients = punchClients;

		try {
			// Initialize the input and output streams, should be switched around from the order of the client
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
			// Schedule a timer for keepAlive
		} catch (StreamCorruptedException e) {
			// In case of a corrupt connection, exit
			finish();
		}

		// Set alive flag
		alive = true;

		// Log the connection to the console
		System.out.printf("New holepunch connection from %s\n", clientSocket.getRemoteSocketAddress().toString());
	}

	public void run() {
		try {
			NetMessage message = (NetMessage) in.readObject();
			if (message.getContent() instanceof SocketAddress) {
				SocketAddress address = (SocketAddress) message.getContent();
				message.setType(NetMessage.MessageType.NORMAL);
				for (Connection client: clients) {
					if(client.getClientSocket().getRemoteSocketAddress() == address) {
						client.message(message);
					}
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void finish() throws IOException {
		// Set alive flag
		alive = false;

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

		clients.remove(this);
		punchClients.remove(this);

		// Log the connection to the console
		System.out.printf("Connection closed from %s\n", clientSocket.getRemoteSocketAddress().toString());
	}
}
