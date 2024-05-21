public class TransactionOut implements MerkleTreeable {
	byte[] nextTransactionPublicKey; //Public key that corresponds to the private key that the next transaction must be signed with
	int value; //Value of transaction
	public byte[] hash() {
		return SHA256Hash.hash(nextTransactionPublicKey);
	}
}