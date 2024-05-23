import java.net.Socket;
import java.lang.Thread;
import java.io.ObjectInputStream;

class QueryDNS extends Thread {
	public void run() {
		int port=9940;
		String DNSIP="10.10.166.222";
		while(true) {
			try {
				Socket socket=new Socket(DNSIP, port);
				ObjectInputStream read=new ObjectInputStream(socket.getInputStream());
				for(int i=0; i<10; i++) {
					NodeIP.addIP((String) read.readObject());
				}
				read.close();
				socket.close();
				if(NodeIP.getSize()==0) {
					Thread.sleep(1000);
				} else {
					Thread.sleep(600000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}