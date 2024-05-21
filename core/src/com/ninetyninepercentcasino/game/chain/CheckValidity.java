import java.io.File;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;

public class CheckValidity {
	public static boolean checkBlock(Block toCheck) {
		if(!Block.checkHashZeros(toCheck.hash(), 16)) {
			return false;
		} else if(!(new File("./blockchain/"+toCheck.index+".ser").exists()&&BlockFile.readBlock(toCheck.index).equals(toCheck))) {
			return false;
		} else if(!Arrays.equals(BlockFile.readBlock(toCheck.index-1).hash(), toCheck.previousHash)) {
			return false;
		} else if(!Arrays.equals(toCheck.merkleRoot, new MerkleTree<Transaction>(toCheck.transactions).genTree())) {
			return false;
		} if(toCheck.index<=0) {
			return false;
		}
		for(int i=0; i<toCheck.transactions.size(); i++) {
			if(!checkTransaction(toCheck.transactions.get(i))) {
				return false;
			}
		}
		return true;
	}
	public static boolean checkTransaction(Transaction toCheck) {
		try {
			for(int i=0; i<toCheck.TIN.length; i++) {
				if(!Arrays.equals(Sign.publicKeySign(BlockFile.readBlock(toCheck.TIN[i].previousOutBlock).transactions.get(toCheck.TIN[i].previousOutTransaction).TOUT[toCheck.TIN[i].previousOutOutputNumber].nextTransactionPublicKey, KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(toCheck.TIN[i].privateKeySignature))), toCheck.TIN[i].hash())) {
					return false;
				}
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		if(!Arrays.equals(toCheck.merkleRoot, ByteArray.merge(new MerkleTree<TransactionIn>(new ArrayList<TransactionIn>(Arrays.asList(toCheck.TIN))).genTree(), new MerkleTree<TransactionOut>(new ArrayList<TransactionOut>(Arrays.asList(toCheck.TOUT))).genTree()))) {
			return false;
		}
		try {
			for(int i=0; i<toCheck.TIN.length; i++) {
				if(!Arrays.equals(toCheck.merkleRoot, Sign.publicKeySign(toCheck.signature[i], KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(BlockFile.readBlock(toCheck.TIN[i].previousOutBlock).transactions.get(toCheck.TIN[i].previousOutTransaction).TOUT[toCheck.TIN[i].previousOutOutputNumber].nextTransactionPublicKey))))) {
					return false;
				}
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		int totalValueInput=0;
		int totalValueOutput=0;
		for(int i=0; i<toCheck.TIN.length; i++) {
			totalValueInput+=BlockFile.readBlock(toCheck.TIN[i].previousOutBlock).transactions.get(toCheck.TIN[i].previousOutTransaction).TOUT[toCheck.TIN[i].previousOutOutputNumber].value;
		}
		for(int i=0; i<toCheck.TOUT.length; i++) {
			totalValueInput+=toCheck.TOUT[i].value;
		}
		if(totalValueInput!=totalValueOutput) {
			return false;
		}
		return true;
	}
}