package com.ninetyninepercentcasino.net;

import java.io.OptionalDataException;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class BrokerConnection extends Connection {
	public BrokerConnection(Socket clientSocket, List<Connection> clients) throws IOException {
		super(clientSocket, clients);
	}
	public void run() {
		try {
			NetMessage address = new NetMessage(NetMessage.MessageType.INFO, clientSocket.getRemoteSocketAddress());
			synchronized (out) {
				out.writeObject(address);
			}
			// Loop until dead
			while (alive) {
				// If connection is closed, quit
				if(!clientSocket.isConnected()) {
					finish();
				}
				try {
					// Read message from input stream
					NetMessage message = (NetMessage) in.readObject();
					// Set the origin of the message on the server side
					message.setOrigin(clientSocket.getRemoteSocketAddress());
					// Run if the message is not bogus
					if (message.getContent() != null) {
						// Log the message to the console
						System.out.printf("[%s] %s: %s\n",  message.getType(), clientSocket.getRemoteSocketAddress().toString(), message.getContent());
						switch(message.getType()) {
							case ACK:
								// Set the aliveMessage to the content of the message
								aliveMessage = (String) message.getContent();
								break;
							case PING:
								// Set the type of the message to ACK and send it back
								message.setType(NetMessage.MessageType.ACK);
								synchronized (out) {
									out.writeObject(message);
								}
								break;
							default:
						}
					}
				} catch (OptionalDataException e) {
					// This error can be safely ignored.
				} catch (EOFException | SocketException e) {
					// This occurs as a result of the
					finish();
				} catch (IOException | ClassNotFoundException e) {
					// These errors cannot be ignored
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void dispose() {
	}
}
