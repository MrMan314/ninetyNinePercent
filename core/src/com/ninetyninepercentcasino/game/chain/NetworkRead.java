public class NetworkRead {
	public static void add(Object toAdd) {
		if(toAdd.getClass()==Transaction.class) {
			if(CheckValidity.checkTransaction((Transaction) toAdd)) {
				FindBlockHashManager.addTransaction((Transaction) toAdd);
			}
		} else if(toAdd.getClass()==Block.class) {
			if(CheckValidity.checkBlock((Block) toAdd)) {
				BlockFile.writeBlock((Block) toAdd);
			}
		}
	}
}