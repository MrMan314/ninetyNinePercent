package com.ninetyninepercentcasino.net;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.IllegalStateException;
import java.lang.IllegalMonitorStateException;
import java.lang.NullPointerException;

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

	private class ServerThread extends Thread {
		private Socket clientSocket;
		private ObjectInputStream in;
		private ObjectOutputStream out;

		private String aliveMessage = "";

		private boolean alive;

		private TimerTask keepAliveTimeout = new TimerTask() {
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
						} catch (IllegalMonitorStateException e) {
							return;
						} catch (IOException | InterruptedException e) {
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
		public ServerThread(Socket socket) throws IOException {
			try {
				this.clientSocket = socket;
				alive = true;
				out = new ObjectOutputStream(clientSocket.getOutputStream());
				in = new ObjectInputStream(clientSocket.getInputStream());
				System.out.printf("New connection from %s\n", clientSocket.getRemoteSocketAddress().toString());
				timer.scheduleAtFixedRate(keepAliveTimeout, 0, 5000);
			} catch (StreamCorruptedException e) {
				this.finish();
			}	
		}

		public void finish() throws IOException {
			alive = false;
			try {
				timer.cancel();
				timer.purge();
			} catch (IllegalStateException e) {

			}
			clientSocket.close();
			Thread.currentThread().interrupt();
			try {
				in.close();
			} catch (NullPointerException e) {

			}
			out.close();
			clients.remove(this);
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
				while (alive) {
					if(!clientSocket.isConnected()) {
						finish();
					}
					try {
						NetMessage message = (NetMessage) in.readObject();
						message.setOrigin(clientSocket.getRemoteSocketAddress());
						if (message.getType() == NetMessage.MessageType.ACK) {
							aliveMessage = (String) message.getContent();
						} else if (message.getContent() != null) {
							System.out.printf("[%s] %s: %s\n",  message.getType(), clientSocket.getRemoteSocketAddress().toString(), message.getContent());
							sendAll(message, this);
						}
					} catch (EOFException | SocketException e) {
						finish();
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.start();
	}
}
