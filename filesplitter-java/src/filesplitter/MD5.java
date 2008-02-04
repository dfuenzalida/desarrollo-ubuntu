/**
 * Copied from http://www.osix.net/modules/article/?id=42
 */

package filesplitter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	private MessageDigest md = null;
	static private MD5 md5 = null;
	private static final char[] hexChars = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * Constructor is private so you must use the getInstance method
	 */
	private MD5() throws NoSuchAlgorithmException {
		md = MessageDigest.getInstance("MD5");
	}

	/**
	 * This returns the singleton instance
	 */
	public static MD5 getInstance() throws NoSuchAlgorithmException {

		if (md5 == null) {
			md5 = new MD5();
		}

		return (md5);
	}

	public String hashData(byte[] dataToHash) {
		return hexStringFromBytes((calculateHash(dataToHash)));
	}
	
	public String hashData() {
		return hexStringFromBytes(md.digest());
	}

	public void addBytes(byte[] data){
		md.update(data, 0, data.length);
	}
	
	
	private byte[] calculateHash(byte[] dataToHash) {
		md.update(dataToHash, 0, dataToHash.length);
		return (md.digest());
	}

	public String hexStringFromBytes(byte[] b) {

		// String hex = "";
		StringBuffer sb = new StringBuffer();

		int msb;

		int lsb = 0;
		int i;

		// MSB maps to idx 0

		for (i = 0; i < b.length; i++) {
			msb = ((int) b[i] & 0x000000FF) / 16;
			lsb = ((int) b[i] & 0x000000FF) % 16;
			sb.append(hexChars[msb]);
			sb.append(hexChars[lsb]);
			// hex = hex + hexChars[msb] + hexChars[lsb];
		}

		// return (hex);
		return (sb.toString());
	}

	public static void main(String[] args) {
		try {
			MD5 md = MD5.getInstance();
			System.out.println(md.hashData("hello".getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(System.out);
		}
	}
}