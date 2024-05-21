import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class Transaction implements Serializable, MerkleTreeable {
	TransactionIn[] TIN; //Not in header
	TransactionOut[] TOUT; //Not in header
	byte[] merkleRoot; //Header
	long timestamp=System.currentTimeMillis(); //Header
	byte[][] signature; //Not in header. Contains the merkle root signed by each of the TIN private keys.

	public Transaction(TransactionIn[] TIN, TransactionOut[] TOUT, long timestamp) {
		this.TIN=TIN;
		this.TOUT=TOUT;
		this.timestamp=timestamp;
	}
	public byte[] hash() {
		return SHA256Hash.hash(headerAsByteArray());
	}
	public byte[] headerAsByteArray() {
		try {
			ByteArrayOutputStream headerAsByteArray=new ByteArrayOutputStream();
			DataOutputStream longWriter=new DataOutputStream(headerAsByteArray);
			longWriter.writeLong(timestamp);
			longWriter.flush();
			genMerkleRoot();
			headerAsByteArray.write(merkleRoot);
			return headerAsByteArray.toByteArray();
		} catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}
	public void signTransaction(String[] keyNames) {
		try {
			for(int i=0; i<TIN.length; i++) {
				signature[i]=Sign.privateKeySign(headerAsByteArray(), KeyPairManager.readKey(keyNames[i]).getPrivate());
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	public void genMerkleRoot() {
		merkleRoot=ByteArray.merge(new MerkleTree<TransactionIn>(new ArrayList<TransactionIn>(Arrays.asList(TIN))).genTree(), new MerkleTree<TransactionOut>(new ArrayList<TransactionOut>(Arrays.asList(TOUT))).genTree());
	}
}