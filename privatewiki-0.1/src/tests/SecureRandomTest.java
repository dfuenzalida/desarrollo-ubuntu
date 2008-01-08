package tests;

import java.security.SecureRandom;

import privatewiki.datalib.DesEncrypter;
import privatewiki.datalib.MD5;

public class SecureRandomTest {

	public static void main(String[] args) {
				
		/*
		SecureRandom secureRandom = new SecureRandom();
		for (int i=0; i<100; i++){
			Integer sri = new Integer(secureRandom.nextInt());
			String digest = MD5.getMD5AsBase64( sri.toString() );
			
			System.out.println( digest );
			
		}
		*/
		
		String passwordValue = "123";
		
		SecureRandom secureRandom = new SecureRandom();
		Integer secureInteger = new Integer( secureRandom.nextInt());
		String digest = MD5.getMD5AsBase64( secureInteger.toString() );
		
		// crypt the hash with the password from user input
		DesEncrypter encrypter = new DesEncrypter(digest);
		String cryptedDocumentPass = encrypter.encrypt( digest );
		
		System.out.println("userpassword=" + MD5.getMD5AsBase64(passwordValue));
		System.out.println("docpassword=" + cryptedDocumentPass);
		
	}
}
