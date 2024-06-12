package com.ninetyninepercentcasino.net
public class ServerConnection extends Connection:
	private BJGame bjGame
	private Database database
	public ServerConnection(Socket clientSocket, List clients, Database database):
		super(clientSocket, clients)
		this.database = database
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
								if content instanceof BJBeginGame:
									bjGame = new BJGame(new BJPlayer(new Account("REPLACE"), this))
									bjGame.start()
								else if content instanceof BJBetMessage:
									bjGame.setFirstBet(((BJBetMessage)content).getAmountBet())
									synchronized bjGame.getBjSynchronizer():
										bjGame.getBjSynchronizer().notify()
								else if content instanceof BJInsuranceMessage:
									bjGame.setInsuranceBet(((BJInsuranceMessage) content).getInsureAmount())
									synchronized bjGame.getBjSynchronizer():
										bjGame.getBjSynchronizer().notify()
								else if content instanceof BJActionUpdate:
									bjGame.setAction(((BJActionUpdate)content).getChosenAction())
									synchronized bjGame.getBjSynchronizer():
										bjGame.getBjSynchronizer().notify()
								else if content instanceof LoginRequest:
									NetMessage loginMessage
									try:
										LoginRequest request = (LoginRequest) content
										Account account = database.loadUser(request.getUsername(), request.getPassword())
										loginMessage = new NetMessage(NetMessage.MessageType.NORMAL, account)
									catch SQLException e:
										loginMessage = new NetMessage(NetMessage.MessageType.ERROR, new ServerError())
									catch AccountNonExistent | PasswordIncorrect e:
										loginMessage = new NetMessage(NetMessage.MessageType.ERROR, new LoginError())
									synchronized out:
										out.writeObject(loginMessage)
								else if content instanceof CreateUserRequest:
									NetMessage creationMessage
									try:
										LoginRequest request = (LoginRequest) content
										Account account = database.createUser(request.getUsername(), request.getPassword())
										creationMessage = new NetMessage(NetMessage.MessageType.NORMAL, account)
									catch SQLException | AccountNonExistent | PasswordIncorrect e:
										creationMessage = new NetMessage(NetMessage.MessageType.ERROR, new ServerError())
									catch UserAlreadyExists e:
										creationMessage = new NetMessage(NetMessage.MessageType.ERROR, new CreateUserError())
							default:
				catch OptionalDataException e:
				catch SocketException | EOFException e:
					finish()
				catch IOException | ClassNotFoundException e:
					e.printStackTrace()
		catch IOException e:
			e.printStackTrace()
	public void dispose():
		try:
			bjGame.interrupt()
		catch NullPointerException e:
