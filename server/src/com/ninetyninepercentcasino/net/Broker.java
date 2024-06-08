package com.ninetyninepercentcasino.net;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

public class Broker extends Server {
	private int port = 9937;
	private int punchPort = 9938;
	private ServerSocket punchSocket;
	private boolean running;

	private ArrayList<BrokerHolePunch> punchClients;

	public Broker() throws IOException {
		super(9937);
		punchSocket = new ServerSocket(punchPort);
		punchClients = new ArrayList<BrokerHolePunch>();
		running = true;
	}

	public Broker(int port, int punchPort) throws IOException {
		super(port);
		this.port = port;
		this.punchPort = punchPort;
		punchSocket = new ServerSocket(punchPort);
		punchClients = new ArrayList<BrokerHolePunch>();
		running = true;
	}

	public void run() {
		new Thread() {
			public void run() {
				while (running) {
					try {
						clients.add(new BrokerConnection(serverSocket.accept(), clients));
						clients.get(clients.size() - 1).start();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();

		new Thread() {
			public void run() {
				while (running) {
					try {
						punchClients.add(new BrokerHolePunch(punchSocket.accept(), clients, punchClients));
						punchClients.get(punchClients.size() - 1).start();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
}
