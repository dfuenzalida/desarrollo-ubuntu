package filesplitter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Split {

	public void splitFile(File origen, File carpetaDestino, long tamano) throws Exception {
		splitFile(origen, carpetaDestino, tamano, false);
	}

	
	/**
	 * Divide un archivo 'origen' en trozos de tamaño 'tamano' y los coloca
	 * en la carpeta 'carpetaDestino'
	 *  
	 * @param sourceFile Archivo a dividir
	 * @param destFolder Carpeta de destino de los trozos
	 * @param size Tamaño máximo de cada trozo en bytes
	 */
	public void splitFile(File origen, File carpetaDestino, long tamano, boolean validateChecksum) throws Exception {
		
		FileReader fileReader = new FileReader(origen);
		FileWriter fileWriter;
		
		int totalBytes = 0;
		int piece = 0;
		int writtenToPiece = 0;

		String pieceName = carpetaDestino.getAbsolutePath() + File.separator + origen.getName() + "." + piece;
		fileWriter = new FileWriter(new File(pieceName));

		char[] buffer = new char[1];
		while (fileReader.read(buffer) > -1){
			fileWriter.write(buffer);
			
			totalBytes++;
			writtenToPiece++;
			
			// next piece, please
			if (writtenToPiece == tamano){
				fileWriter.flush();
				fileWriter.close();
				writtenToPiece = 0;
				piece++;
				pieceName = carpetaDestino.getAbsolutePath() + File.separator + origen.getName() + "." + piece;
				fileWriter = new FileWriter(new File(pieceName));
			}

		}

		fileWriter.flush();
		fileWriter.close();

		// delete last empty file
		if (writtenToPiece == 0){
			(new File(pieceName)).delete();
		}

		
	}

	/**
	 * Asumo que el primer archivo tiene extensión ".0"
	 * 
	 * @param firstPieceFile
	 * @param destinationFolder
	 * @param verifyChecksum
	 * @return
	 */
	public String join(File firstPieceFile, File destinationFolder, boolean verifyChecksum) throws Exception {

		MD5 md5Pieces = MD5.getInstance();
		boolean moreFiles = true;
		int piece = 0;
		
		String prefixName = firstPieceFile.getName().substring(0, firstPieceFile.getName().length()-2);
		
		char[] buffer;
		
		while (moreFiles){

			try {
				while (true){
					String pieceName = destinationFolder.getAbsolutePath() + File.separator + prefixName + "." + piece;
					FileReader pieceFileReader = new FileReader(pieceName);

					buffer = new char[1];
					while (pieceFileReader.read(buffer) > -1){
						md5Pieces.addBytes(new byte[]{(byte) buffer[0]});
					}
					
					piece++;
				}
				
			} catch (Exception e) {
				moreFiles = false;
			}

		}
		
		String piecesHash = md5Pieces.hashData();
		
		return piecesHash;
	}
	
}
