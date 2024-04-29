import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Server extends Thread {
	private ServerSocket serverSocket;
	private int port = 9925;

	private ArrayList<ServerThread> clients = new ArrayList<ServerThread>();

	public Server() throws IOException {
		serverSocket = new ServerSocket(this.port);
	}
	
	public Server(int port) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket(this.port);
	}

	public void run() {
		boolean running = true;
		while (running) {
			try {
				clients.add(new ServerThread(serverSocket.accept()));
				clients.get(clients.size() - 1).start();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

	public void finish() throws IOException {
		serverSocket.close();
	}

	private class ServerThread extends Thread {
		private Socket clientSocket;
		private PrintWriter out;
		private BufferedReader in;

		public ServerThread(Socket socket) throws IOException {
			this.clientSocket = socket;
		}

		public void finish() throws IOException {
			in.close();
			out.close();
			clientSocket.close();
			System.out.printf("Connection closed from %s\n", clientSocket.getRemoteSocketAddress().toString());
		}

		public Socket getClientSocket() {
			return clientSocket;
		}

		public void run() {
			try {
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				System.out.printf("New connection from %s\n", clientSocket.getRemoteSocketAddress().toString());
				while (true) {
					try {
        		        wait(5);
						out.println("hello vro1!!!");
		            } catch (InterruptedException e) {
						System.out.println("interrupted!!!11");
						break;
					}
				}
				finish();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.start();
	}
}
