import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

public class Client {
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

	public void start(String ip, int port) throws IOException {
		clientSocket = new Socket(ip, port);
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public void stop() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
	}

	public String test(String s) throws IOException {
		out.println(s);
		String resp = in.readLine();
		return resp;
	}

	public static void main(String[] args) throws IOException {
		Client client = new Client();
		client.start("127.0.0.1", 9925);
		String resp = client.test("test");
		System.out.println(resp);
	}
}
