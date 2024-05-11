package com.ninetyninepercentcasino.net;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.StreamCorruptedException;
import java.io.OptionalDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.IllegalStateException;
import java.lang.IllegalMonitorStateException;
import java.lang.NullPointerException;

import com.ninetyninepercentcasino.net.Connection;
import com.ninetyninepercentcasino.net.NetMessage;

public class Server extends Thread {
	private ServerSocket serverSocket;
	private int port = 9925;

	private List<ServerThread> clients = new ArrayList<ServerThread>();

	public Server() throws IOException {
		serverSocket = new ServerSocket(this.port);
	}

	public Server(int port) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket(this.port);
	}

	public void run() {
		boolean running = true;
		while (running) {
			try {
				clients.add(new ServerThread(serverSocket.accept()));
				clients.get(clients.size() - 1).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void finish() throws IOException {
		serverSocket.close();
	}

	public void sendAll(NetMessage message, ServerThread origin) throws IOException {
		for (ServerThread client: clients) {
			if (client != origin) {
				client.message(message);
			}
		}
	}

	private class ServerThread extends Connection {
		public ServerThread(Socket socket) throws IOException {
			try {
				super.clientSocket = socket;
				super.alive = true;
				super.in = new ObjectInputStream(clientSocket.getInputStream());
				super.out = new ObjectOutputStream(clientSocket.getOutputStream());
				System.out.printf("New connection from %s\n", clientSocket.getRemoteSocketAddress().toString());
				super.timer.scheduleAtFixedRate(super.keepAliveTimeout, 5000, 5000);
			} catch (StreamCorruptedException e) {
				this.finish();
			}	
		}

		public void finish() throws IOException {
			super.alive = false;
			try {
				super.timer.cancel();
				super.timer.purge();
			} catch (IllegalStateException e) {

			}
			super.clientSocket.close();
			Thread.currentThread().interrupt();
			try {
				super.in.close();
			} catch (NullPointerException e) {

			}
			super.out.close();
			clients.remove(this);
			System.out.printf("Connection closed from %s\n", clientSocket.getRemoteSocketAddress().toString());
		}
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.start();
	}
}
