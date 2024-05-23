import java.security.PublicKey;
import java.security.Signature;
import java.security.PrivateKey;

public class Sign {
	public static byte[] privateKeySign(byte[] toSign, PrivateKey privateKey) {
		try {
			Signature signature = Signature.getInstance("SHA256WithDSA");
			signature.initSign(privateKey);
			signature.update(toSign);
			return signature.sign();
		} catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}
	public static byte[] privateKeySign(byte[] toSign, String keyName) {
		try {
			Signature signature = Signature.getInstance("SHA256WithDSA");
			signature.initSign(KeyPairManager.readKey(keyName).getPrivate());
			signature.update(toSign);
			return signature.sign();
		} catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}
	public static byte[] publicKeySign(byte[] toSign, PublicKey publicKey) {
		try {
			Signature signature = Signature.getInstance("SHA256WithDSA");
			signature.initVerify(publicKey);
			signature.update(toSign);
			return signature.sign();
		} catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}
}