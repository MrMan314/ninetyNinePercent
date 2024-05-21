import java.io.ByteArrayOutputStream;

public class TransactionIn implements MerkleTreeable {
	int previousOutBlock; //Block number of previous transaction
	int previousOutTransaction; //Transaction number of previous transaction
	int previousOutOutputNumber; //The output number of the previous trasaction
	byte[] privateKeySignature; //Signature with coresponding private key of transaction output
	public byte[] hash() {
		return SHA256Hash.hash(toByteArray());
	}
	public byte[] toByteArray() {
		ByteArrayOutputStream headerAsByteArray=new ByteArrayOutputStream();
		headerAsByteArray.write(previousOutBlock);
		headerAsByteArray.write(previousOutTransaction);
		headerAsByteArray.write(previousOutOutputNumber);
		return headerAsByteArray.toByteArray();
	}
	public void sign(String keyName) {
		privateKeySignature=Sign.privateKeySign(toByteArray(), keyName);
	}
}