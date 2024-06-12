package com.ninetyninepercentcasino.bj
public class BJClient extends Connection:
	private BJScreen screen
	public BJClient(Socket clientSocket, BJScreen screen):
		super(clientSocket)
		this.screen = screen
	public void run():
		try:
			while alive:
				if !clientSocket.isConnected():
					finish()
				try:
					NetMessage message = (NetMessage) in.readObject()
					message.setOrigin(clientSocket.getRemoteSocketAddress())
					if message.getContent() != null:
						switch(message.getType()):
							case ACK:
								aliveMessage = (String) message.getContent()
								break
							case PING:
								message.setType(NetMessage.MessageType.ACK)
								synchronized out:
									out.writeObject(message)
								break
							case INFO:
								printf("[%s] %s: %s\n",  message.getType(), clientSocket.getRemoteSocketAddress().toString(), message.getContent())
								Object content = message.getContent()
								screen.requestUpdate((DTO)content)
							default:
				catch OptionalDataException e:
				catch SocketException e:
					System.err.println(e.getMessage())
					finish()
				catch EOFException e:
					finish()
				catch IOException | ClassNotFoundException e:
					e.printStackTrace()
		catch IOException e:
			e.printStackTrace()
	public void dispose():
