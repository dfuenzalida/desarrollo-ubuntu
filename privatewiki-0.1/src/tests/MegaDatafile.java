
package tests;

import java.util.Date;

import privatewiki.datalib.Datalib;

/*
 * 
 * @author denis
 *
 * Crea un data.zip gigante para medir performance
 */

public class MegaDatafile {
	
	

	public static void main(String[] args) {
		
		Datalib datalib = new Datalib();
		String password = "01020304050607080910111213141516";
		
		for (int i=0; i<400; i++){
			String titulo = "" + new Date().getTime();
			
			StringBuffer contenido = new StringBuffer();
			for (int j=0; j<200; j++){
				contenido.append( new Date().getTime() );
				contenido.append( "\n" );
			}
			
			System.out.println(i);

			datalib.savePublicDocument(titulo, contenido.toString());
			
		}
		
		
		
	}
}
