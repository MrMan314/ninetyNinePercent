package com.ninetyninepercentcasino.net;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.EOFException;

import com.ninetyninepercentcasino.net.NetMessage;

public class Client extends Thread {
	private Socket clientSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private BufferedReader consoleIn;

	private boolean alive = true;

	public Client(String ip, int port) throws IOException {
		clientSocket = new Socket(ip, port);
		in = new ObjectInputStream(clientSocket.getInputStream());
		out = new ObjectOutputStream(clientSocket.getOutputStream());
		consoleIn = new BufferedReader(new InputStreamReader(System.in));
	}

	public void finish() throws IOException {
		System.out.println("Connection Closed.");
		alive = false;
		clientSocket.close();
		Thread.currentThread().interrupt();
		in.close();
		out.close();
	}

	public void run() {
		new Thread() {
			public void run() {
				while (alive) {
					try {
						NetMessage message = (NetMessage) in.readObject();
						if (message.getType() == NetMessage.MessageType.PING) {
							out.writeObject(new NetMessage(NetMessage.MessageType.ACK, message.getContent()));
						} else if (message.getContent() != null) {
							System.out.printf("[%s] %s: %s\n", message.getType(), message.getOrigin().toString(), message.getContent());
						}
					} catch (EOFException e) {
						try {
							finish();
						} catch (IOException f) {
							f.printStackTrace();
						}
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public static void main(String[] args) throws IOException {
		Client client = new Client("127.0.0.1", 9925);
		client.start();
	}
}
