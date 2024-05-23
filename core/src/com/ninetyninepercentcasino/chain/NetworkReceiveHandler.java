import java.lang.Thread;
import java.net.ServerSocket;

public class NetworkReceiveHandler extends Thread {
	public void run() {
		try(ServerSocket endpoint=new ServerSocket(9938)) {
			while(true) {
				new NetworkReceive(endpoint.accept()).start();
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}