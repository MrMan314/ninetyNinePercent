package com.ninetyninepercentcasino.net;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.EOFException;

import com.ninetyninepercentcasino.net.Connection;
import com.ninetyninepercentcasino.net.NetMessage;

public class Client extends Connection {
	private BufferedReader consoleIn;

	public Client(String ip, int port) throws IOException {
		super.clientSocket = new Socket(ip, port);
		super.alive = true;
		super.out = new ObjectOutputStream(clientSocket.getOutputStream());
		super.in = new ObjectInputStream(clientSocket.getInputStream());
		consoleIn = new BufferedReader(new InputStreamReader(System.in));
		super.timer.scheduleAtFixedRate(super.keepAliveTimeout, 5000, 5000);
		System.out.println("Connection Opened.");
	}

	public void finish() throws IOException {
		System.out.println("Connection Closed.");
		super.alive = false;
		super.clientSocket.close();
		Thread.currentThread().interrupt();
		super.in.close();
		super.out.close();
		consoleIn.close();
	}

	public static void main(String[] args) throws IOException {
		Client client = new Client("127.0.0.1", 9925);
		client.start();
//		client.message(new NetMessage(NetMessage.MessageType.NORMAL, "balls"));
	}
}
