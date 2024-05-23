import java.net.ServerSocket;
import java.lang.Thread;

class KeepAliveResponse extends Thread {
	int port=9939;
	public void run() {
		try(ServerSocket serverSocket=new ServerSocket(port);) {
			while(true) {
				serverSocket.accept().close();
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}