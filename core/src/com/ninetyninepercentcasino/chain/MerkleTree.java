import java.util.ArrayList;

class MerkleTree<T extends MerkleTreeable> {
	ArrayList<byte[]> hash;
	public byte[] genTree() {
		while(hash.size()>1) {
			balanceTree();
			for(int i=0; i<hash.size()/2; i++) {
				hash.set(i, SHA256Hash.hash(ByteArray.merge(hash.get(i*2), hash.get((i*2)+1))));
			}
			trimList();
		}
		return hash.get(0);
	}
	private void balanceTree() { //Makes number of inputs even
		if(hash.size()%2==1) {
			hash.add(hash.get(hash.size()-1));
		}
	}
	private void trimList() { //Chops off the second half of the list
		int amountToChop=hash.size()/2;
		for(int i=hash.size()-1; i>=amountToChop; i--) {
			hash.remove(i);
		}
		System.out.println();
	}
	public MerkleTree(ArrayList<T> toHash) {
		hash=new ArrayList<byte[]>();
		for(int i=0; i<toHash.size(); i++) {
			hash.add(i, toHash.get(i).hash());
		}
	}
}