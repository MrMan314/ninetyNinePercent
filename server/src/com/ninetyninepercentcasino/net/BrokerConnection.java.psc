package com.ninetyninepercentcasino.net
public class BrokerConnection extends Connection {
	public BrokerConnection(Socket clientSocket, List clients) {
		super(clientSocket, clients)
	}
	public void run() {
		try {
			NetMessage address = new NetMessage(NetMessage.MessageType.INFO, clientSocket.getRemoteSocketAddress())
			synchronized out {
				out.writeObject(address)
			}
			while alive {
				if !clientSocket.isConnected() {
					finish()
				}
				try {
					NetMessage message = (NetMessage) in.readObject()
					message.setOrigin(clientSocket.getRemoteSocketAddress())
					if message.getContent() != null {
						printf("[%s] %s: %s\n",  message.getType(), clientSocket.getRemoteSocketAddress().toString(), message.getContent())
						switch(message.getType()) {
							case ACK:
								aliveMessage = (String) message.getContent()
								break
							case PING:
								message.setType(NetMessage.MessageType.ACK)
								synchronized out {
									out.writeObject(message)
								}
								break
							default:
						}
					}
				} catch OptionalDataException e {
				} catch EOFException | SocketException e {
					finish()
				} catch IOException | ClassNotFoundException e {
					e.printStackTrace()
				}
			}
		} catch IOException e {
			e.printStackTrace()
		}
	}
	public void dispose() {
	}
}
