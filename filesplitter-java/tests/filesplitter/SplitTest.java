package filesplitter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import junit.framework.TestCase;

public class SplitTest extends TestCase {

	File fileToSplit;
	
	protected void setUp() throws Exception {
		
		fileToSplit = File.createTempFile("split", "tmp");
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSplit));
		
		for (int i=0; i<100000; i++){
			String random = new Integer((int) (1000+Math.random()*9000)).toString();
			writer.append(random);
		}
		
		writer.flush();

	}

	
	/**
	 * Pruebo dividir un archivo de aprox. 1 mega en 100 trozos
	 * 
	 * Resultado esperado:
	 * - TODO: Deben quedar 10000*4/100 archivos
	 * - El hash de la suma de las partes debe ser el hash del todo 
	 * 
	 * @throws Exception
	 */
	
	public void testSplit100() throws Exception {
		
		Split split = new Split();
		
		/*
		 * Creo el directorio "pieces" en la carpeta temporal
		 */
		String parentDir = fileToSplit.getParent();
		File carpetaDestino = new File(parentDir + File.separator + "pieces");
		
		if (!carpetaDestino.exists()){
			if (!carpetaDestino.mkdirs()){ fail("No pude crear directorio"); }			
		}
		
		/*
		 * Invoco el split sobre el archivo temporal
		 */
		split.splitFile(fileToSplit, carpetaDestino, 40000);
		
		/*
		 * Calculo el hash sobre el archivo temporal 
		 */
		
		FileReader wholeFileReader = new FileReader(fileToSplit);
		MD5 md5WholeFile = MD5.getInstance();
		
		char[] buffer = new char[1];
		while (wholeFileReader.read(buffer) > -1){
			md5WholeFile.addBytes(new byte[]{(byte) buffer[0]});
		}
		
		String wholeFileHash = md5WholeFile.hashData();
		
		/*
		 * Calculo el hash sobre todos los trozos 
		 */
		
		MD5 md5Pieces = MD5.getInstance();
		boolean moreFiles = true;
		int piece = 0;
		
		while (moreFiles){

			try {
				while (true){
					String pieceName = carpetaDestino.getAbsolutePath() + File.separator + fileToSplit.getName() + "." + piece;
					FileReader pieceFileReader = new FileReader(pieceName);

					buffer = new char[1];
					while (pieceFileReader.read(buffer) > -1){
						md5Pieces.addBytes(new byte[]{(byte) buffer[0]});
					}
					
					piece++;
				}
				
			} catch (Exception e) {
				
				// No hay mas archivos por abrir
				moreFiles = false;
			}

		}
		
		String piecesHash = md5Pieces.hashData();
	
		/*
		 * Assertion
		 */
		
		assertEquals("Hash no coincide entre archivo completo y trozos", wholeFileHash, piecesHash);
	}
	
	
	/**
	 * Split a file and join the pieces using the Split class
	 * @throws Exception
	 */
	
	public void testJoin() throws Exception {
		
		Split split = new Split();
		
		/*
		 * Creo el directorio "pieces" en la carpeta temporal
		 */
		String parentDir = fileToSplit.getParent();
		File carpetaDestino = new File(parentDir + File.separator + "pieces");
		
		if (!carpetaDestino.exists()){
			if (!carpetaDestino.mkdirs()){ fail("No pude crear directorio"); }			
		}
		
		/*
		 * Invoco el split sobre el archivo temporal
		 */
		split.splitFile(fileToSplit, carpetaDestino, 40000);
		
		/*
		 * Calculo el hash sobre el archivo temporal 
		 */
		
		FileReader wholeFileReader = new FileReader(fileToSplit);
		MD5 md5WholeFile = MD5.getInstance();
		
		char[] buffer = new char[1];
		while (wholeFileReader.read(buffer) > -1){
			md5WholeFile.addBytes(new byte[]{(byte) buffer[0]});
		}
		
		String wholeFileHash = md5WholeFile.hashData();
		
		/*
		 * junto todos los trozos y calculo el hash 
		 */
		
		String firstPieceName = carpetaDestino.getAbsolutePath() + File.separator + fileToSplit.getName() + ".0";
		File firstPieceFile = new File(firstPieceName);
		String joinHash = split.join(firstPieceFile, carpetaDestino, true);
	
		/*
		 * Assertion
		 */
		
		assertEquals("Hash no coincide entre archivo completo y trozos", wholeFileHash, joinHash);
	}
	
}
