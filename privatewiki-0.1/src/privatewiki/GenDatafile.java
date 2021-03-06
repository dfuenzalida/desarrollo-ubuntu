package privatewiki;

import java.security.SecureRandom;
import java.util.Properties;

import privatewiki.datalib.Datalib;
import privatewiki.datalib.DesEncrypter;
import privatewiki.datalib.MD5;

public class GenDatafile {

	public static void main(String[] args) {
		
		String userPassword = "123";
		
		SecureRandom secureRandom = new SecureRandom();
		Integer secureInteger = new Integer( secureRandom.nextInt());
		String docPassword = MD5.getMD5AsBase64( secureInteger.toString() );
		
		// crypt the password with the hashed-password from user input
		DesEncrypter encrypter = new DesEncrypter( MD5.getMD5AsBase64(userPassword));
		String cryptedDocumentPass = encrypter.encrypt( docPassword );
		
		Datalib datalib = new Datalib();		
		Properties pro = new Properties();
		pro.setProperty("userpassword", MD5.getMD5AsBase64(userPassword) );
		pro.setProperty("docpassword", cryptedDocumentPass);
		System.out.println("userpassword=" + MD5.getMD5AsBase64(userPassword));
		System.out.println("docpassword=" + cryptedDocumentPass);
		
		// 1st save
		datalib.setConfigProperties(pro);

		// save 1st document
		String text = "Welcome to the welcome page generated by Gendatafile.";
		datalib.savePrivateDocument("Welcome", text, docPassword);


		
	}
}
