package com.ninetyninepercentcasino.net;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import com.ninetyninepercentcasino.net.NetMessage;

public class Client extends Thread {
	private Socket clientSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public Client(String ip, int port) throws IOException {
		clientSocket = new Socket(ip, port);
		in = new ObjectInputStream(clientSocket.getInputStream());
		out = new ObjectOutputStream(clientSocket.getOutputStream());
	}

	public void finish() throws IOException {
		clientSocket.close();
		Thread.currentThread().interrupt();
		in.close();
		out.close();
	}

	public void run() {
		try {
			NetMessage testMessage = (NetMessage) in.readObject();
			System.out.printf("%s: %s\n", testMessage.getType(), testMessage.getMessage());
			out.writeObject(new NetMessage(NetMessage.MessageType.NORMAL, "hello vro!"));
			finish();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		Client client = new Client("127.0.0.1", 9925);
		client.start();
	}
}
