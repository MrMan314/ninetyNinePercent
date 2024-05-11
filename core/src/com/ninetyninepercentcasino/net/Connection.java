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
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.ninetyninepercentcasino.net.NetMessage;

public abstract class Connection extends Thread {
	protected Socket clientSocket;
	protected ObjectInputStream in;
	protected ObjectOutputStream out;

	abstract void finish() throws IOException;

	protected String aliveMessage = "";
	protected boolean alive = false;
	protected Timer timer = new Timer();
	protected TimerTask keepAliveTimeout = new TimerTask() {
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

	public Socket getClientSocket() {
		return clientSocket;
	}

	public void message(NetMessage message) throws IOException {
		out.writeObject(message);
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
