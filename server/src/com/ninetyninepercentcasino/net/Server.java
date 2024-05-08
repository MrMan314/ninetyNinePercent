package com.ninetyninepercentcasino.net;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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

	private class ServerThread extends Thread {
		private Socket clientSocket;
		private PrintWriter out;
		private BufferedReader in;

		private String aliveMessage = "";

		private boolean alive;

		public ServerThread(Socket socket) throws IOException {
			this.clientSocket = socket;
			alive = true;
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			System.out.printf("New connection from %s\n", clientSocket.getRemoteSocketAddress().toString());
		}

		public void finish() throws IOException {
			alive = false;
			clientSocket.close();
			Thread.currentThread().interrupt();
			in.close();
			out.close();
			System.out.printf("Connection closed from %s\n", clientSocket.getRemoteSocketAddress().toString());
		}

		public Socket getClientSocket() {
			return clientSocket;
		}

		public void message() {
		}

		public void run() {
			try {
				while (alive) {
					out.println("hello vro1!!!");
					aliveMessage = "";

					TimerTask keepAliveTimeout = new TimerTask() {
						public void run() {
							try {
								if(aliveMessage == "") {
									finish();
									return;
								}
								synchronized (this) {
									notify();
								}
							} catch (IOException e) {
								e.printStackTrace();
								return;
							}
						}
					};

					Timer timer = new Timer();
					timer.schedule(keepAliveTimeout, 5000);

					synchronized (keepAliveTimeout) {
						try {
							aliveMessage = in.readLine();
							keepAliveTimeout.wait();
						} catch (InterruptedException e) {
							System.out.println("interrupted!!!11");
							break;
						}
					}
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
