package com.ninetyninepercentcasino.net;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.EOFException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.ServerSocket;

public class HolePunchConnection extends Connection {
	private Socket punchSocket;
	private ServerSocket punchListener;
	private SocketAddress externalAddress;

	private ObjectInputStream punchIn;
	private ObjectOutputStream punchOut;

	public HolePunchConnection(Socket clientSocket, Socket punchSocket) throws IOException {
		super(clientSocket);
		try {
			// Initialize the input and output streams, should be switched around from the order of the server
			punchIn = new ObjectInputStream(clientSocket.getInputStream());
			punchOut = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (StreamCorruptedException e) {
			// In case of a corrupt connection, exit
			finish();
		}
		this.punchSocket = punchSocket;
	}

	public void run() {
		new Thread() {
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
									case INFO:
										if (message.getContent() instanceof SocketAddress) {
											synchronized (externalAddress) {
												externalAddress = (SocketAddress) message.getContent();
												externalAddress.notify();
											}
										}
									case NORMAL:
										if (message.getContent() instanceof SocketAddress) {
											synchronized (externalAddress) {
												externalAddress = (SocketAddress) message.getContent();
												externalAddress.notify();
											}
										}
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
		}.start();
		new Thread() {
			public void run() {
				try {
					synchronized (externalAddress) {
						externalAddress.wait();
					}
					synchronized (punchOut) {
						NetMessage message = new NetMessage(NetMessage.MessageType.INFO, externalAddress);
						punchOut.writeObject(message);
					}
					synchronized (externalAddress) {
						externalAddress.wait();
					}
					System.out.println(externalAddress);
	//				punchListener = new ServerSocket(externalAddress.);
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();	
	}

	public static void main(String[] args) throws IOException {
		Connection connection = new HolePunchConnection(new Socket("127.0.0.1", 9937), new Socket("127.0.0.1", 9938));
		connection.start();
	}
}
