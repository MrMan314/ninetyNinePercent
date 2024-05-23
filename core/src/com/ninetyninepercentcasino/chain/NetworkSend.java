import java.io.ObjectOutputStream;
import java.lang.Thread;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkSend extends Thread {
	private String ip;
	private ArrayList<Object> sendQueue=new ArrayList<Object>();
	public NetworkSend(String ip) {
		this.ip=ip;
	}
	public void run() {
		try(Socket endpoint=new Socket(ip, 9938)) {
			ObjectOutputStream endpointOutputStream=new ObjectOutputStream(endpoint.getOutputStream());
			while(true) {
				endpointOutputStream.writeObject(sendQueue.remove(0));
				wait();
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	public String getIP() {
		return ip;
	}
	public void addToQueue(Object toSend) {
		sendQueue.add(toSend);
	}
}