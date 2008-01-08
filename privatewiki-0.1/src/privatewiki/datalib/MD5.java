package privatewiki.datalib;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

/**
 * Test MD5 digest computation
 *
 * @author Roedy Green
 * @version 1.0
 * @since 2004-06-07
 */
public class MD5 {

   /**
    * test MD5 digest, requires Java 1.4+
    *
    * @param args not used
    */
   public static void main ( String[] args ) 
   	throws UnsupportedEncodingException, NoSuchAlgorithmException {

   		for (int i=0; i<10; i++){
   			String mensaje = "Hello " + i;
   			//System.out.println( MD5HexString( mensaje ) );
   			
   			BASE64Encoder encoder = new BASE64Encoder();
   			String base64 = encoder.encode( MD5bytes( mensaje ));
   			System.out.println( base64);
   		}

   }
   
   // TODO: Find/Write a Encoder for the digest which doesn't rely on provider-specific packages (ie sun.*)
   
   public static String getMD5AsBase64(String text){
		BASE64Encoder encoder = new BASE64Encoder();
		String base64 = "";
		try { base64 = encoder.encode( MD5bytes( text )); } catch (Exception ex){ }
		return base64;
   	
   }
   
   public static byte[] MD5bytes(String text) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance( "MD5" );
		md.update( text.getBytes() );
		byte[] digest = md.digest();
		return digest;
   }
   
}
