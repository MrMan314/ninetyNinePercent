//package com.ninetyninepercentcasino.net;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.InetAddress;
import java.io.IOException;

public class Server extends Thread {
	private DatagramSocket socket;
	private boolean running;
	private byte[] buf = new byte[256];

	public static void main(String[] args) throws SocketException, IOException {
		Server server = new Server();
		server.start();
	}

	public Server() throws SocketException, IOException {
		socket = new DatagramSocket(9925);
	}

	public Server(int port) throws SocketException, IOException {
		socket = new DatagramSocket(port);
	}

	public void run() {
		running = true;
		
		while (running) {
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try {
				socket.receive(packet);
			} catch (Exception e) {
				System.out.println(e);
			}

			InetAddress address = packet.getAddress();
			int port = packet.getPort();
			packet = new DatagramPacket(buf, buf.length, address, port);

			String recv = new String(packet.getData(), 0, packet.getLength()).strip();

			System.out.printf("Message from %s:%d:\n", address.getHostAddress(), port);
			System.out.printf("\t%d: %s\n", recv.length(), recv);

			try {
				socket.send(packet);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		socket.close();
	}
}
