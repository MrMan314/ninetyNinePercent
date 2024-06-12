package com.ninetyninepercentcasino.net
public abstract class Server extends Thread:
	protected ServerSocket serverSocket
	private int port
	protected List()
	public Server(int port):
		this.port = port
		serverSocket = new ServerSocket(this.port)
	public abstract void run()
	public void finish():
		serverSocket.close()
	public void sendAll(NetMessage message):
		for Connection client in clients:
			client.message(message)
	public void sendAll(NetMessage message, Connection origin):
		for Connection client in clients:
			if client != origin:
				client.message(message)
