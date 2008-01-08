package privatewiki.datalib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Datalib {

	Logger logger = Logger.getLogger("datalib");
	
	String publicData = "public/";
	String privateData = "private/";
	String metadata = "meta/";
		
	/**
	 * This inits the zipfile if doesn't exist, with a simple
	 * timestamp document in the metadata area
	 */
	public void init(){
		
		try {
			FileInputStream fis = new FileInputStream("data.zip");
			try { fis.close();} catch (Exception ex){ }
			
		} catch (FileNotFoundException e){
			
			// if "data.zip" is not found, create it with a "meta/timestamp" document
			try {
				ZipOutputStream out = new ZipOutputStream(new FileOutputStream("data.zip"));
	            out.putNextEntry( new ZipEntry( "meta/timestamp"));
	            String text = new Long( (new Date()).getTime() ).toString();	            
	           	out.write( text.getBytes(), 0, text.getBytes().length);
	           	out.closeEntry();
	           	out.close();
			} catch (Exception ex){
				
			}
			
		}
		
		
	}
	
	public String openPrivateDocument(String document, String password){

		String cryptedDocument = openDocument(privateData+document);
        
        // now i decript
        DesEncrypter desencrypter = new DesEncrypter(password);
        
        //System.out.println(new Date().getTime()); // PROFILING
        String plainDocument = desencrypter.decrypt( cryptedDocument );
        //System.out.println(new Date().getTime()); // PROFILING
        
        return plainDocument;
	}
	
	public String openPublicDocument(String document){
		return openDocument(publicData+document);
	}	

	private String openDocument(String document){
	
		init();
		
		String zipDocument = document;
		
		try {
	        // Open the ZIP file
	        String inFilename = "data.zip";
	        ZipInputStream in = new ZipInputStream(new FileInputStream(inFilename));
	        
	        ZipEntry zipEntry = null;
	        StringBuffer buffer = new StringBuffer();
	        
	        while ( (zipEntry = in.getNextEntry()) != null){
	        	
	        	logger.log(Level.FINE, "buscando en zip ... " + zipEntry.getName());

	        	// if i find the document
	        	if (zipEntry.getName().equals(zipDocument)){
	        		
	        		byte[] bufferBytes = new byte[1024];
	                int len;
	                
	                while ((len = in.read(bufferBytes)) > 0) {
	                    buffer.append( new String(bufferBytes, 0, len));
	                }
	                
	                String documentText = buffer.toString();
	                	                
	    	        in.close();
	    	        in = null;
	                
	                return documentText;
	                
	        	}
	        	
	        }

	        
		} catch (Exception ex){
			
		}
		
		return "";
	
	}
	
	public void savePrivateDocument(String title, String document, String password){

		// Crypt the text
		DesEncrypter desEncrypter = new DesEncrypter(password);
		String cryptedText = desEncrypter.encrypt(document);

		// save crypted text instead
		saveDatafile(privateData+title, cryptedText, false);
	}

	public void savePublicDocument(String title, String document){
		saveDatafile(publicData+title, document, false);
	}

	public void saveMetadata(String title, String document){
		saveDatafile(metadata+title, document, false);
	}
	
	public void deletePrivateDocument(String title){
		saveDatafile(privateData+title, "", true);
	}
	
	public void deletePublicDocument(String title){
		saveDatafile(publicData+title, "", true);
	}	
	
	/**
	 * Saves a document in the datafile. 
	 * @param title full-document name
	 * @param document data contents (which must be previously crypted if needed)
	 */
	private void saveDatafile(String title, String document, boolean deleteTitle){
		
		init();
		
		// Store the text in the zipfile
		try {

	        String inFilename = "data.zip";
			String outFilename = "data_tmp.zip";

			ZipInputStream in = new ZipInputStream(new FileInputStream(inFilename));
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFilename));
			ZipEntry zipEntry;
			
	        while ( (zipEntry = in.getNextEntry()) != null){
	        	
	        	// for all documents except the new
	        	String zipEntryName = zipEntry.getName(); 
	        	if (!zipEntryName.equals(title)){

	        		byte[] bufferBytes = new byte[1024];
	                int len;
	                
	                //System.out.print("saving '" + zipEntryName + "' ...");
	                	                
	                out.putNextEntry( new ZipEntry( zipEntryName ));
	                while ((len = in.read(bufferBytes)) > 0) {
	                	out.write( bufferBytes, 0, len);
	                }
	                out.closeEntry();
	                //System.out.println(" saved ok");
	        		
	        	}
	        }
	        
	        // if not asked for deletion 
	        if (!deleteTitle){
		        // add the new document at the end of the tempfile
	            out.putNextEntry( new ZipEntry( title ));
	           	out.write( document.getBytes(), 0, document.getBytes().length);
	           	out.closeEntry();
	        }

           	out.close();
           	in.close();
           	
           	boolean done = false;

           	// rename data.zip -> data.del
           	try {
               	System.out.print("data.zip -> data.del ... ");
               	File dataZip = new File("data.zip");
               	done = dataZip.renameTo( new File("data.del"));
               	dataZip = null;
               	System.out.println((done)?"done":"error");           		
           	} catch (Exception ex){ }

           	// rename data_tmp.zip -> data.zip
           	try {
               	System.out.print("data_tmp.zip -> data.zip ... ");
	           	File dataTmpZip = new File ("data_tmp.zip");
	           	done = dataTmpZip.renameTo(new File("data.zip"));
	           	dataTmpZip = null;
	           	System.out.println((done)?"done":"error");
           	} catch (Exception ex){ }

           	// delete data.del
           	try {
               	System.out.print("deleting data.del ... ");
	           	File dataDel = new File("data.del");
	           	done = dataDel.delete();
	           	dataDel = null;
	           	System.out.println((done)?"done":"error");
           	} catch (Exception ex){ }           	
	        
		} catch (Exception ex){
			System.out.println("*** Exception in Datalib.SaveDocument() ***");
			ex.printStackTrace();
		}

		
	}
	
	public Vector getPublicDocumentNames(){ return getDocumentNames(publicData); }
	public Vector getPrivateDocumentNames(){ return getDocumentNames(privateData); }
	public Vector getMetadataFiles(){ return getDocumentNames(metadata); }
	
	private Vector getDocumentNames(String basename){

		init();
		
		Vector documentNames = new Vector();

		try {
	        // Open the ZIP file
	        String inFilename = "data.zip";
	        ZipInputStream in = new ZipInputStream(new FileInputStream(inFilename));
	        
	        ZipEntry zipEntry = null;
	        
	        while ( (zipEntry = in.getNextEntry()) != null){	        	
	        	String fullName = zipEntry.getName();
	        	
	        	try {
	        		// if the fullname begins with fullname
	        		if (fullName.indexOf(basename) == 0){	        		
		        		String documentName = fullName.substring(basename.length());
			        	//System.out.println("getDocumentNames = " + documentName);
			        	
			        	/** IMPORTANT: a blank documentName will cause ALL OTHER 
			        	 *  documentNames to be skipped, resulting in bad rendering
			        	 */
			        	if (!"".equals(documentName)){ documentNames.add( documentName); }
	        		}
	        		
	        	} catch (Exception ex){ }
	        	
	        }
		} catch (Exception ex){ }
		
		return documentNames;

	}
	
	public Properties getConfigProperties(){
		
		HashMap config = new HashMap();
		
		// open the file as a plain string
		String configString = openDocument( metadata + "config");
		
		StringBufferInputStream is = new StringBufferInputStream(configString);
		Properties properties = new Properties();
		try { properties.load((InputStream)is); } catch (Exception ex){ }
		
		return properties;
	}
	
	public void setConfigProperties( Properties properties ){
		
		Iterator i = properties.keySet().iterator();
		StringBuffer propertiesBuffer = new StringBuffer();
		String equals = "=";
		String newline = "\n";
		
		while (i.hasNext()){
			String key = (String) i.next();
			String value = (String) properties.get(key);
			
			propertiesBuffer.append(key);
			propertiesBuffer.append(equals);
			propertiesBuffer.append(value);
			propertiesBuffer.append(newline);
		}
		
		// save 
		saveMetadata("config", propertiesBuffer.toString());
		
	}
}
