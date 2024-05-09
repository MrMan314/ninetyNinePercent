package com.ninetyninepercentcasino.net;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.ninetyninepercentcasino.net.NetMessage;

public class Server extends Thread {
	private ServerSocket serverSocket;
	private int port = 9925;

	private ArrayList<ServerThread> clients = new ArrayList<ServerThread>();

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
				System.out.println(e);
			}
		}
	}

	public void finish() throws IOException {
		serverSocket.close();
	}

	public void sendAll(NetMessage message) throws IOException {
		for (ServerThread client: clients) {
			client.message(message);
		}
	}

	private class ServerThread extends Thread {
		private Socket clientSocket;
		private ObjectInputStream in;
		private ObjectOutputStream out;

		private String aliveMessage = "";

		private boolean alive;

		public ServerThread(Socket socket) throws IOException {
			this.clientSocket = socket;
			alive = true;
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
			System.out.printf("New connection from %s\n", clientSocket.getRemoteSocketAddress().toString());
		}

		public void finish() throws IOException {
			alive = false;
			timer.cancel();
			timer.purge();
			clientSocket.close();
			Thread.currentThread().interrupt();
			in.close();
			out.close();
			System.out.printf("Connection closed from %s\n", clientSocket.getRemoteSocketAddress().toString());
		}

		public Socket getClientSocket() {
			return clientSocket;
		}

		public void message(NetMessage message) throws IOException {
			out.writeObject(message);
		}

		private Timer timer = new Timer();
		public void run() {
			try {
				TimerTask keepAliveTimeout = new TimerTask() {
					public void run() {
						new Thread() {
							public void run() {
								try {
									if(!clientSocket.isConnected()) {
										finish();
										return;
									}
									synchronized (aliveMessage) {
										aliveMessage.notify();
										NetMessage pingMessage = new NetMessage(NetMessage.MessageType.PING, "my balls itch");
										out.writeObject(pingMessage);
										aliveMessage = "";
										aliveMessage.wait();
									}

									if(aliveMessage == "") {
										finish();
										return;
									}
								} catch (IOException e) {
									e.printStackTrace();
									return;
								} catch (InterruptedException e) {
									e.printStackTrace();
									return;
								} catch (NullPointerException e) {
									try {
										finish();
									} catch (IOException f) {
										f.printStackTrace();
									}
									return;
								}
							}
						}.start();
					}
				};
				timer.scheduleAtFixedRate(keepAliveTimeout, 0, 5000);
				while (alive) {
					if(!clientSocket.isConnected()) {
						alive = false;
					}
					try {
						NetMessage message = (NetMessage) in.readObject();
						System.out.printf("%s: %s\n",  message.getType(), message.getMessage());
					} catch (EOFException e) {
						alive = false;
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}//					message(aliveMessage);
				}
				finish();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.start();
	}
}
