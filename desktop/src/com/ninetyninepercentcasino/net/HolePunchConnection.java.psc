package com.ninetyninepercentcasino.net
public class HolePunchConnection extends Connection {
	private Socket punchSocket
	private ServerSocket punchListener
	private SocketAddress externalAddress
	private ObjectInputStream punchIn
	private ObjectOutputStream punchOut
	private String sync = "", sync2 = ""
	public HolePunchConnection(Socket clientSocket, Socket punchSocket) {
		super(clientSocket)
		try {
			punchIn = new ObjectInputStream(punchSocket.getInputStream())
			punchOut = new ObjectOutputStream(punchSocket.getOutputStream())
		} catch StreamCorruptedException e {
			e.printStackTrace()
			finish()
		}
		this.punchSocket = punchSocket
		this.externalAddress = clientSocket.getRemoteSocketAddress()
	}
	public void run() {
		new Thread() {
			public void run() {
				try {
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
									case INFO:
										if message.getContent() instanceof SocketAddress {
											synchronized sync {
												externalAddress = (SocketAddress) message.getContent()
												sync.notify()
											}
										}
									case NORMAL:
										if message.getContent() instanceof SocketAddress {
											synchronized sync2 {
												externalAddress = (SocketAddress) message.getContent()
												sync2.notify()
											}
										}
									default:
								}
							}
						} catch OptionalDataException e {
							e.printStackTrace()
						} catch EOFException e {
							e.printStackTrace()
							finish()
						} catch IOException | ClassNotFoundException e {
							e.printStackTrace()
						}
					}
				} catch IOException e {
					e.printStackTrace()
				}
			}
		}.start()
		new Thread() {
			public void run() {
				try {
					synchronized sync {
						sync.wait()
					}
					synchronized punchOut {
						NetMessage message = new NetMessage(NetMessage.MessageType.INFO, externalAddress)
						punchOut.writeObject(message)
					}
					synchronized sync2 {
						sync2.wait()
					}
				} catch IOException | InterruptedException e {
					e.printStackTrace()
				}
			}
		}.start()
	}
	public static void main(String[] args) {
		Connection connection = new HolePunchConnection(new Socket("127.0.0.1", 9937), new Socket("127.0.0.1", 9938))
		connection.start()
	}
	public void dispose() {
	}
}
