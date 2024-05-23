import java.util.ArrayList;

public class NetworkSendManager {
	private static ArrayList<NetworkSend> networkSends;
	public static void update() {
		for(int i=0; i<NodeIP.getSize(); i++) {
			for(int ii=0; ii<NodeIP.getSize(); ii++) {
				if(networkSends.get(ii).getIP().equalsIgnoreCase(NodeIP.getIP(i))) {
					break;
				}
			}
			networkSends.add(new NetworkSend(NodeIP.getIP(i)));
		}
	}
	public static void addToQueue(Object toSend) {
		for(int i=0; i<networkSends.size(); i++) {
			networkSends.get(i).addToQueue(toSend);
		}
	}
}