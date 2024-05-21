import java.util.ArrayList;

public class NodeIP {
	private static ArrayList<String> nodeIPs=new ArrayList<String>();
	private static int currentIndex=0;
	public static void addIP(String ip) {
		if(!nodeIPs.contains(ip)) {
			nodeIPs.add(ip);
			NetworkSendManager.update();
			new SyncChain(ip);
		}
	}
	public static void removeIP(String ip) {
		nodeIPs.remove(ip);
		NetworkSendManager.update();
	}
	public static String getIP(int index) {
		return nodeIPs.get(index);
	}
	public static int getSize() {
		return nodeIPs.size();
	}
	public static String getNextIP() {
		currentIndex=currentIndex++%nodeIPs.size();
		return getIP(currentIndex);
	}
}