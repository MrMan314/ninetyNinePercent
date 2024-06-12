package com.ninetyninepercentcasino.net
public class BrokerHolePunch extends Thread {
	private List clients
	private List punchClients
	private Socket clientSocket
	private ObjectInputStream in
	private ObjectOutputStream out
	private boolean alive
	public BrokerHolePunch(Socket clientSocket, List clients, List punchClients) {
		this.clients = clients
		this.clientSocket = clientSocket
		this.punchClients = punchClients
		try {
			out = new ObjectOutputStream(clientSocket.getOutputStream())
			in = new ObjectInputStream(clientSocket.getInputStream())
		} catch StreamCorruptedException e {
			finish()
		}
		alive = true
		printf("New holepunch connection from %s\n", clientSocket.getRemoteSocketAddress().toString())
	}
	public void run() {
		try {
			NetMessage message = (NetMessage) in.readObject()
			if message.getContent() instanceof SocketAddress {
				SocketAddress address = (SocketAddress) message.getContent()
				message.setType(NetMessage.MessageType.NORMAL)
				for Connection client in clients {
					if client.getClientSocket().getRemoteSocketAddress().toString().equals(address.toString()) {
						message.setContent(clientSocket.getRemoteSocketAddress())
						client.message(message)
						break
					}
				}
			}
		} catch IOException | ClassNotFoundException e {
			e.printStackTrace()
		}
	}
	public void finish() {
		alive = false
		clientSocket.close()
		Thread.currentThread().interrupt()
		try {
			in.close()
		} catch NullPointerException e {
		}
		try {
			out.close()
		} catch SocketException e {
		}
		clients.remove(this)
		punchClients.remove(this)
		printf("Connection closed from %s\n", clientSocket.getRemoteSocketAddress().toString())
	}
}
