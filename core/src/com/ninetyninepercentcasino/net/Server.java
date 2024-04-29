import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

public class Server extends Thread {
	private ServerSocket serverSocket;

	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	public void run() {
		boolean running = true;
		while (running) {
			try {
				new ServerThread(serverSocket.accept()).start();
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
		}

		public void run() {
			try {
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				out.println("when the " + in.readLine());
				finish();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}
