import java.security.KeyPair;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class KeyPairManager {
	public static KeyPair readKey(String keyName) {
		try {
			FileInputStream keyFile=new FileInputStream("./keys/"+keyName+".ser");
			ObjectInputStream keyReader=new ObjectInputStream(keyFile);
			KeyPair key=(KeyPair) keyReader.readObject();
			keyReader.close();
			keyFile.close();
			return key;
		} catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}
	public static void writekey(KeyPair toWrite, String keyName) {
		try {
			FileOutputStream keyFile=new FileOutputStream("./keys/"+keyName+".ser");
			ObjectOutputStream keyWriter=new ObjectOutputStream(keyFile);
			keyWriter.writeObject(toWrite);
			keyWriter.close();
			keyFile.close();
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
