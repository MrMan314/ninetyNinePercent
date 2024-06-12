package com.ninetyninepercentcasino.net
public class Broker extends Server {
	private int port = 9937
	private int punchPort = 9938
	private ServerSocket punchSocket
	private boolean running
	private ArrayList punchClients
	public Broker() {
		super(9937)
		punchSocket = new ServerSocket(punchPort)
		punchClients = new ArrayList()
		running = true
	}
	public Broker(int port, int punchPort) {
		super(port)
		this.port = port
		this.punchPort = punchPort
		punchSocket = new ServerSocket(punchPort)
		punchClients = new ArrayList()
		running = true
	}
	public void run() {
		new Thread() {
			public void run() {
				while running {
					try {
						clients.add(new BrokerConnection(serverSocket.accept(), clients))
						clients.get(clients.size() - 1).start()
					} catch IOException e {
						e.printStackTrace()
					}
				}
			}
		}.start()
		new Thread() {
			public void run() {
				while running {
					try {
						punchClients.add(new BrokerHolePunch(punchSocket.accept(), clients, punchClients))
						punchClients.get(punchClients.size() - 1).start()
					} catch IOException e {
						e.printStackTrace()
					}
				}
			}
		}.start()
	}
}
