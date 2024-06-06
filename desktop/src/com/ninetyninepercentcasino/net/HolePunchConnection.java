package com.ninetyninepercentcasino.net;

import java.io.OptionalDataException;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class HolePunchConnection extends Connection {
	private Socket punchSocket;
	private ServerSocket punchListener;

	public HolePunchConnection(Socket clientSocket, Socket punchSocket) throws IOException {
		super(clientSocket);
		this.punchSocket = punchSocket;
	}

	public void run() {
		try {
			while (alive) {
				if(!clientSocket.isConnected()) {
					finish();
				}
				try {
					NetMessage message = (NetMessage) in.readObject();
					message.setOrigin(clientSocket.getRemoteSocketAddress());
					if (message.getContent() != null) {
						System.out.printf("[%s] %s: %s\n",  message.getType(), clientSocket.getRemoteSocketAddress().toString(), message.getContent());
						switch(message.getType()) {
							case ACK:
								aliveMessage = (String) message.getContent();
								break;
							case PING:
								message.setType(NetMessage.MessageType.ACK);
								out.writeObject(message);
								break;
							default:
						}
					}
				} catch (OptionalDataException e) {

				} catch (EOFException e) {
					finish();
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		Connection connection = new HolePunchConnection(new Socket("127.0.0.1", 9937), new Socket("127.0.0.1", 9938));
		connection.start();
	}
}
