package privatewiki;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.Vector;

import privatewiki.datalib.Datalib;
import privatewiki.datalib.DesEncrypter;
import privatewiki.datalib.MD5;

import thinlet.FrameLauncher;
import thinlet.Thinlet;

/**
 * A thinlet with the contents of "<code>test.xml</code>".
 */
public class Main extends Thinlet implements ComponentListener
{
	
	private Object contentPanel = null;
	private Object contenedor = null;
	private Vector panelContents = new Vector(); 
	
	private String userPassword = null; // "01020304050607080910111213141516"; // MD5-like password
	private String docPassword = null;
	// f0V564Aqa9YPbT5X7JTLDQ==
	
	private boolean documentIsDirty = false;
	private String document = "";

	private Datalib datalib = new Datalib();
	
	private int containterCount = 0;
	
	private FrameLauncher frame = null;
	private int maxColumns = 30;
	
	private String currentLocation;
	
	private String baseTitle = "PrivateWiki";
	private boolean isEditView = false;
	
	//private String fontName = "SansSerif";// "MS Mincho";
	private int fontSize = 12;
	
	private String defaultLocation = "Welcome";
	private Properties configProperties;
	
	private History history = null;
	private ImageCache imageCache = null;

    /**
     * Create a new Main instance.
     */
    public Main() throws java.io.IOException
    {

    	add(parse("/xml/main.xml"));
        
    	this.configProperties = datalib.getConfigProperties();
    	System.out.println("password: " + configProperties.getProperty("userpassword"));
		//configProperties.put("password", MD5.getMD5AsBase64("01020304050607080910111213141516") );
		//datalib.setConfigProperties( configProperties );

    	if (configProperties.containsKey("userpassword")){
        	try {
            	System.out.println("onlogin");
            	add(parse("/xml/login.xml"));    		
        	} catch (Exception ex){
            	System.out.println("onlogin ex");        		
        	}
        	
    	}
    	
    	// inits the navigation history
		history = new History(this.defaultLocation);
		imageCache = new ImageCache();
		
		// TODO: Set default properties
		setProperty("global-font", "tahoma");
		setProperty("render-font", "sansserif");
		setProperty("edit-font", "sansserif");
		setProperty("defaultpage", "Welcome");
		setProperty("antialias", "true");
		datalib.setConfigProperties( this.configProperties);
		
        setFont(new Font(configProperties.getProperty("global-font"), Font.PLAIN, fontSize));
        // Change to a XP-like display style
		setColors(	0xece9d8, 0x000000, 0xffffff, 0x909090, 
					0xb0b0b0, 0xededed, 0xc7c5b2, 0xe68b2c, 0xf2c977);

		
    }
    
    public void setProperty(String property, String defaultValue){
    	if (!this.configProperties.containsKey(property)){
    		configProperties.setProperty(property, defaultValue);
    	}
    }


    /**
     * Callback handler for the 'init' method of panel 'contentPanel'.
     *
     * @param contentPanel the panel named 'contentPanel'.
     */
    public void initContentPanel(Object contentPanel) {
    	
    	//System.out.println("initContentPanel...");
    	this.currentLocation = this.defaultLocation;
    	this.contentPanel = contentPanel;
    }
    
    public void addContent(Object objeto){
    	
    	panelContents.add( objeto ); // required for deletion?
    	add(contenedor, objeto);
    }
    
    public void onClickPageLink(Object boton){

    	String location = getString(boton, "tooltip");
    	this.currentLocation = location;
    	history.add(location);    	
    	renderDocument();

    }
    
    public void cleanContents(){

    	for (int i=0; i<panelContents.size(); i++){
    		remove(panelContents.get(i));
    	}
    	
    	remove(this.contenedor);
    	containterCount = 0;

    }
    
    public void onGoHome(){
    	this.isEditView = false;
    	this.currentLocation = this.defaultLocation;
    	this.history.add(this.defaultLocation);
    	renderDocument();    	
    }
    
    public void onBack(){
    	// TODO: if editing, on-back prompts for saving document 
    	this.isEditView = false; // just in case
    	this.currentLocation = history.back();
    	renderDocument();
    }
    
    public void onForward(){
    	// TODO: if editing, on-forward prompts for saving document 
    	this.isEditView = false; // just in case
    	this.currentLocation = history.forward();
    	renderDocument();
    }
    
    /*
     * menu_file_exit callback
     */
    public void onExit(){ System.exit(0); }
    
    public void onClickEditDocument(Object object){
    	cleanContents();

    	isEditView = true;
    	
    	Object panel2 = create("panel");
    	contenedor = panel2;
    	setInteger(panel2, "weightx", 1);
    	setInteger(panel2, "weighty", 1);
    	setInteger(panel2, "columns", 1);
    	add(contentPanel, panel2, 0);

    	// render the title and the edit option
    	Object titlePanel = create("panel");
    	setInteger(titlePanel, "weightx", 1);
    	setInteger(titlePanel, "gap", 4);
    	setBoolean(titlePanel, "border", true);
    	
    	Object titleLabel = create("label");
    	setFont(titleLabel, new Font(configProperties.getProperty("render-font"), Font.BOLD, fontSize+2));
    	setString(titleLabel, "text", this.currentLocation);
    	setInteger(titleLabel, "weightx", 1);

    	Object linkSave = create("button");
    	setIcon(linkSave, "icon", imageCache.getImage("/icons/save.png") );
    	setString(linkSave, "text", "Save");
    	setChoice(linkSave, "type", "link");
    	setString(linkSave, "tooltip", "Save this document");
    	setMethod(linkSave, "action", "onSave(this)", linkSave, this);
    	
    	add(titlePanel, titleLabel);
    	add(titlePanel, linkSave);
    	addContent(titlePanel);
    	// end rendering title + edit option

    	String texto = datalib.openPrivateDocument( this.currentLocation , this.docPassword);
    	
    	Object textArea = create("textarea");
    	setFont(textArea, new Font(configProperties.getProperty("edit-font"), Font.PLAIN, fontSize));
    	setBoolean(textArea, "wrap", true);
    	setInteger(textArea, "weightx", 1);
    	setInteger(textArea, "weighty", 1);
    	setBoolean(textArea, "editable", true);
    	setString(textArea, "text", texto);
    	setMethod(textArea, "action", "onChangeDocument(this)", textArea, this);
    	
    	addContent(textArea);
    	
    	documentIsDirty = false;
    	document = texto;
    	    	
    }
    
    public void onChangeLocation(Object combo){
    	
    	String newLocation = getString(combo, "text");
    	this.history.add( newLocation );
    	this.currentLocation = newLocation;
    	renderDocument();
    }
    
    // TODO: implement REAL search here 
    public void onSearch(){
    	Object combo = find("combo_location");
    	onChangeLocation(combo);
    }
    
    /**
     * Callback called on each change in the textarea (edit mode)
     * @param textarea
     */
    public void onChangeDocument(Object textarea){
    	document = getString(textarea, "text");
    	documentIsDirty = true;
    }
    
    public void onSave(Object botonSave){
    	
    	if (documentIsDirty){
        	System.out.println("saving '" + this.currentLocation + "' ...");        	
        	datalib.savePrivateDocument( this.currentLocation, document, this.docPassword);        	
        	System.out.println("'" + this.currentLocation + "' ...saved");
    	}
    	
    	documentIsDirty = false;
    	
    	// after saving, return to "rendered" view
    	renderDocument();
    }
    
    public void onClickDeleteDocument(){
    	String text = "Delete document ''" + this.currentLocation + "'' ?"; 
    	confirm("Confirm Deletion",  text, "deleteDocument()" );
    }
    
    public void deleteDocument(){
    	// remove deletion prompt
    	Object window = find("dialog_confirm");
    	remove(window);
    	
    	datalib.deletePrivateDocument( this.currentLocation);
    	this.isEditView = false;
    	renderDocument();
    	alert("Document ''" + this.currentLocation + "'' deleted.");
    }
	
    public void renderText(String text, Vector documentTitles){
    	
    	setTitle( this.currentLocation );
    	
    	// render the title and the edit option
    	Object titlePanel = create("panel");
    	setInteger(titlePanel, "weightx", 1);
    	setInteger(titlePanel, "left", 4);
    	setInteger(titlePanel, "right", 4);
    	setInteger(titlePanel, "gap", 8);
    	setBoolean(titlePanel, "border", true);
    	
    	Object titleLabel = create("label");
    	setFont(titleLabel, new Font(configProperties.getProperty("render-font"), Font.BOLD, fontSize+2));
    	setString(titleLabel, "text", this.currentLocation);
    	setInteger(titleLabel, "weightx", 1);
    	add(titlePanel, titleLabel);

    	Object linkEdit = create("button");
    	setIcon(linkEdit, "icon", imageCache.getImage("/icons/edit.png") );
    	setString(linkEdit, "text", "Edit");
    	setChoice(linkEdit, "type", "link");
    	setString(linkEdit, "tooltip", "Edit this document");
    	setMethod(linkEdit, "action", "onClickEditDocument(this)", linkEdit, this);
				
    	add(titlePanel, linkEdit);
    	
    	Object linkDelete = create("button");
    	setIcon(linkDelete, "icon", imageCache.getImage("/icons/delete.png") );
    	setString(linkDelete, "text", "Delete");
    	setChoice(linkDelete, "type", "link");
    	setString(linkDelete, "tooltip", "Delete this document");
    	setMethod(linkDelete, "action", "onClickDeleteDocument()", linkDelete, this);
    	//setMethod(linkDelete, "action", "notImplemented()", linkEdit, this);
    	add(titlePanel, linkDelete);
    	
    	addContent(titlePanel);
    	// end rendering title + edit option

		int column = 0;
		int textPos = 0;
		boolean isLink = false;
		
		// initial panel to store links and labels in the "render" view 
		Object renderPanel = create("panel");
		setInteger(renderPanel, "weightx", 1);
		setInteger(renderPanel, "weighty", 1);
		setInteger(renderPanel, "columns", 1);
		setInteger(renderPanel, "left", 4);
		setInteger(renderPanel, "right", 2);
		setBoolean(renderPanel, "scrollable", true);
		
		// new panel to render a line of text and links
		Object newPanel = create("panel");
		setInteger(newPanel , "weightx", 1);

		while (textPos < text.length()) {

			// for each documentTitle
			for (int i = 0; i < documentTitles.size(); i++) {
				String documentTitle = (String) documentTitles.get(i);
				int matchEndPos = textPos + documentTitle.length();
				
				// comparision for the text can't be outside of the text
				if (matchEndPos > text.length()){ matchEndPos = text.length(); }
				
				String documentMatch = text.substring(textPos, matchEndPos);
				isLink = false;

				// if it's a match with a documentTitle, render it as a link
				// NOTE: CASE INSENSITIVE
				if (documentTitle.toUpperCase().equals(documentMatch.toUpperCase())) {

					// move textPos
					textPos += documentTitle.length();

					// render link
					//System.out.print(" [" + documentMatch + "]");

					Object linkButton = create("button");
    		    	setString(linkButton, "text", documentMatch);
    		    	setChoice(linkButton, "type", "link");
    		    	setString(linkButton, "tooltip", documentTitle);
    		    	setFont(linkButton, new Font(configProperties.getProperty("render-font"), Font.PLAIN, fontSize));
    		    	setMethod(linkButton, "action", "onClickPageLink(this)", linkButton, this);
    		    	
    		    	add(newPanel, linkButton);
					
					// update column count
					column += documentTitle.length();
					
					break; // out of documentTitles loop

				}
			}

			/* 
			 * If not link was found, render a string as text from current position
			 * to the next space (or the end of the text)
			 */
			if (!isLink) {
				String labelText = "";
				
				// from textPos to the next space in the text
				int nextSpacePos = text.indexOf(" ", textPos);
				int nextNewlinePos = text.indexOf("\n", textPos);
				
				// if no other space found, count a virtual space at the end of text
				if (nextSpacePos == -1) { nextSpacePos = text.length(); }
				// idem for newline
				if (nextNewlinePos == -1) { nextNewlinePos = text.length(); }
				
				// pick the shorter string (textPos->nextSpacePos) vs (textPos->nextNewlinePos)
				if (nextSpacePos < nextNewlinePos){
					labelText = text.substring(textPos, nextSpacePos);
					textPos = nextSpacePos + 1;
				} else {
					labelText = text.substring(textPos, nextNewlinePos);
					textPos = nextNewlinePos + 1;
					// force a newline - somewhat magical calculus :-)
					column = maxColumns - labelText.length() +1;
				}
				
				// render label
				//System.out.print("?" + labelText + "?");
		    	Object textLabel = create("label");
		    	setString(textLabel, "text", labelText + " ");
		    	setFont(textLabel, new Font(configProperties.getProperty("render-font"), Font.PLAIN, fontSize));
		    	
		    	add(newPanel, textLabel);
				
				// insert a line-break if max lenght is reached with the next text 
				if ( column + labelText.length() > maxColumns ){
					//System.out.print("\n");
					column = 0;
					add(renderPanel, newPanel);

					newPanel = create("panel");
					setInteger(newPanel , "weightx", 1);
				}
				
				column += labelText.length();

			}

		} // end while pos
		
		// finally, add the renderPanel to the contentsPanel
		addContent(renderPanel);

    }
    
    public void renderHistory(){

    	Object locationCombo = find("combo_location");
    	Object choice = null;
    	
    	// while exist some elements in the locationCombo
    	while( (choice=find(locationCombo, "historychoice")) != null ){
    		remove(choice);
    	}

    	Vector historyLocations = history.getHistory();
    	//Vector publicDocs = datalib.getPublicDocumentNames();
    	Vector privateDocs = datalib.getPrivateDocumentNames();
    	int selectedIndex = history.getSelectedIndex();
    	
    	/*
    	 * loop over document list, if it's private, set a "lock" icon
    	 * or a "public" icon if it's public instead
    	 */
    	
    	for (int i=0; i<historyLocations.size(); i++){
    		String loc = (String)historyLocations.get(i);
    		
    		String iconRes = "/icons/public.png";
    		if (privateDocs.contains(loc)){ iconRes = "/icons/private.png"; }
    		
    		Object locChoice = create("choice");
    		setString(locChoice, "text", loc);
    		setString(locChoice, "name", "historychoice");
    		setIcon(locChoice, "icon", imageCache.getImage(iconRes));
    		add(locationCombo, locChoice);
    		
    		// if current option is the selected option in the index, update the combo properties
    		if (i == selectedIndex){
    			setString(locationCombo, "text", loc);
    			setIcon(locationCombo, "icon", imageCache.getImage(iconRes));
    		}
    		
    	}
    	
    	// set the selected index of the location combo
    	setInteger(locationCombo, "selected", history.getSelectedIndex());
    	
    }
    
    public void renderDocument(){
    	
    	renderHistory();

    	setTitle("");
    	cleanContents();
    	isEditView = false;
    	
    	//System.out.println("renderDocument = " + this.currentLocation );
    	
    	Object panel2 = create("panel");
    	contenedor = panel2;
    	setInteger(panel2, "weightx", 1);
    	setInteger(panel2, "weighty", 1);
    	setInteger(panel2, "columns", 1);
    	add(contentPanel, panel2);

    	String texto =	datalib.openPrivateDocument( this.currentLocation , this.docPassword );
    	Vector titulos = datalib.getPrivateDocumentNames();

		renderText( texto, titulos);
    }
    
    /**
     * Shortcut for setting the title of the window
     * @param titulo
     */
    public void setTitle(String title){
    	if (frame != null){

    		if (title == null  || "".equals(title)) {
        		frame.setTitle(baseTitle);
        	} else {
        		frame.setTitle(baseTitle + " - " + title);
        	}
    		
    	}    	
    }
    
    /**
     * Called on startup. Determinates if the datafile contains a
     * password. If a password exists, prompt for it.
     *
     */
    public void onSetChangePassword(){
    	
    	// if no password is found, prompt for it
    	if (this.docPassword == null){
    		try {
        		add(parse("/xml/setpassword.xml"));    			
    		} catch (Exception ex){ }
    	} else {
    		try {
        		add(parse("/xml/newpassword.xml"));    			
    		} catch (Exception ex){ }
    	}
    	
    }
    
    public void onCancelSetPassword(){
    	Object dialog = find("dialog_setpassword");
    	remove(dialog);    	
    }
    
    public void onCancelSetNewPassword(){
    	Object dialogNewPassword = find("dialog_newpassword");
    	remove(dialogNewPassword);
    }
    
    public void onEnterPassword(){
    	try {
    		Object passwordfield = find("passwordfield");
    		String passwordValue = getString(passwordfield, "text");
    		Object dialogLogin = find("dialog_login");
    		remove(dialogLogin);
    		
    		// Confirm password with metadata values
    		String configPassword = configProperties.getProperty("userpassword");
    		
    		// right password
    		if ( configPassword.equals( MD5.getMD5AsBase64(passwordValue) ) ){
    			System.out.println("good password");
        		this.userPassword = passwordValue;
        		// use this password to decrypt the docpassword
        		DesEncrypter encrypter = new DesEncrypter(configPassword);
        		this.docPassword = encrypter.decrypt( configProperties.getProperty("docpassword"));
        		
            	// after successfull login, render the start page
            	renderDocument();
    		} else {
    			// wrong password, re-display prompt
            	add(parse("/xml/login.xml"));
            	// set "wrong password" alert on password prompt
            	Object loginerror = find("loginerror");
            	setString(loginerror, "text", "Wrong password!");
    		}
    		
    		
    	} catch (Exception ex){
    		
    	}
    }
    
    public void onSetPassword(){
    	
    	Object passwordField  = find("setpasswordfield");
    	Object passwordField2 = find("setpasswordfield2");
    	
    	String passwordValue  = getString(passwordField, "text");
    	String passwordValue2 = getString(passwordField2, "text");
    	
    	if (passwordValue.equals(passwordValue2)){
    		// Generate a new SecureRandom and a hash from it
    		SecureRandom secureRandom = new SecureRandom();
    		Integer secureInteger = new Integer( secureRandom.nextInt());
    		String digest = MD5.getMD5AsBase64( secureInteger.toString() );
    		
    		// crypt the hash with the password from user input
    		DesEncrypter encrypter = new DesEncrypter(digest);
    		String cryptedDocumentPass = encrypter.encrypt( digest );
    		
    		configProperties.setProperty("userpassword", MD5.getMD5AsBase64(passwordValue));
    		configProperties.setProperty("docpassword", cryptedDocumentPass);
    		datalib.setConfigProperties( configProperties );
    		
    	}
    	
    }
    
    public void onChangePassword(){
    	Object oldPasswordField  = find("oldpasswordfield");
    	Object newPasswordField  = find("newpasswordfield");
    	Object newPasswordField2 = find("newpasswordfield2");
    	
    	String oldPassword  = getString(oldPasswordField, "text");
    	String newPassword  = getString(newPasswordField, "text");
    	String newPassword2 = getString(newPasswordField2, "text");
    	
    	// after getting values, remove from screen
    	Object dialogNewPassword = find("dialog_newpassword");
    	remove(dialogNewPassword);
    	
		// right password
		if ( this.userPassword.equals( oldPassword ) ){
			
			if (newPassword.equals(newPassword2)){
				String userPasswordHash = MD5.getMD5AsBase64(newPassword);
				
				configProperties.setProperty("userpassword", userPasswordHash);
				// encrypt the docs password with the userpassword hash
				DesEncrypter encrypter = new DesEncrypter( userPasswordHash );
				String docPasswordCrypted = encrypter.encrypt( this.docPassword );
				configProperties.setProperty("docpassword", docPasswordCrypted);
				datalib.setConfigProperties( configProperties );
				this.userPassword = newPassword;
				return;
			}
			
		}

		try {
	    	add(parse("/xml/newpassword.xml"));
	    	// set "wrong password" alert on password prompt
	    	Object error = find("newpassworderror");
	    	setString(error, "text", "Wrong password!");			
		} catch (Exception ex){ }

    	
    }

    public void onNewDocument(){
    	try{
    		add(parse("/xml/newdocument.xml"));
    	} catch (Exception ex){
    		ex.printStackTrace();
    	}
    }

    public void onCancelNewDocument(){
    	Object aboutWindow = find("dialog_newdocument");
    	remove(aboutWindow);
    }
    
    public void onEnterNewDocument(){
    	
    	// Get the new document title and type (public/private)
    	Object newDocumentTitleField = find("newdocumenttitle");
    	String newDocumentTitle = getString(newDocumentTitleField, "text");
    	
    	Object newDocumentType = find("newdocumenttype");
    	boolean isPrivate = false;
    	if (getInteger(newDocumentType, "selected") == 1){ isPrivate = true; }
    	
    	Object aboutWindow = find("dialog_newdocument");
    	remove(aboutWindow);
    	
    	// if documentTitle already exists, handle error
    	if (datalib.getPublicDocumentNames().contains(newDocumentTitle)
    		|| datalib.getPrivateDocumentNames().contains(newDocumentTitle) ){

    		try{
        		add(parse("/xml/newdocument.xml"));
        		Object error = find("newdocumenterror");
        		setString(error, "text", "Document ''" + newDocumentTitle + "'' already exists.");
            	newDocumentTitleField = find("newdocumenttitle");
            	setString(newDocumentTitleField, "text", newDocumentTitle);
        	} catch (Exception ex){
        		ex.printStackTrace();
        	}
    		
    	} else {
    		
    		String newDocumentMessage = "New Document created.\nEdit and replace this text with your own.";
    		
    		// if document title doesn't exist in public/private documents, go on
    		if (isPrivate){
    			datalib.savePrivateDocument(newDocumentTitle, newDocumentMessage, this.docPassword );
    		} else {
    			datalib.savePublicDocument(newDocumentTitle, newDocumentMessage);
    		}
    		    		
    		this.isEditView = true;
    		this.currentLocation = newDocumentTitle;
    		this.history.add( newDocumentTitle );
    		renderHistory(); // needed unless we renderDocument()
    		onClickEditDocument(null); // begin editing the document
    		
    	}
    	
    }
    
    public void onAbout(){
    	try{
    		add(parse("/xml/about.xml"));
    	} catch (Exception ex){
    		ex.printStackTrace();
    	}
    }
    
    public void notImplemented(){
    	alert("Function not implemented yet.");
    }
    
    public void raiseError(){
    	error("An error ocurred.");
    }
    
    public void alert(String text){
    	try{
    		add(parse("/xml/alert.xml"));
    		Object dialogAlert = find("dialog_alert");
    		setIcon(dialogAlert, "icon", imageCache.getImage("/icons/alert.png") );
    		setString(dialogAlert, "text", "Warning");
    		Object alertLabel = find("alerttext");
    		setString(alertLabel, "text", text);
    	} catch (Exception ex){ }
    }

    public void error(String text){
    	try{
    		add(parse("/xml/alert.xml"));
    		Object dialogAlert = find("dialog_alert");
    		setIcon(dialogAlert, "icon", imageCache.getImage("/icons/error.png") );
    		setString(dialogAlert, "text", "Error");
    		Object alertLabel = find("errortext");
    		setString(alertLabel, "text", text);
    	} catch (Exception ex){ }
    }

    
    public void onOKAlert(){
    	Object alertWindow = find("dialog_alert");
    	remove(alertWindow);
    }
    
    public void onOKAbout(){
    	Object aboutWindow = find("dialog_about");
    	remove(aboutWindow);
    }
    
    public void confirm(String title, String text, String method){
		try {
    		add(parse("/xml/confirm.xml"));    			
		} catch (Exception ex){ }
		
		
		Object dialog = find("dialog_confirm");
		Object buttonConfirmOK = find("buttonconfirmok");
		Object confirmText = find("confirmtext");
		
		setString(dialog, "text", title);
		setString(confirmText, "text", text);
		setMethod(buttonConfirmOK, "action", method, buttonConfirmOK, this);

    }
    
    public void confirmCancel(){
    	Object confirmWindow = find("dialog_confirm");
    	remove(confirmWindow);    	
    }
    
    /**
     * Detects window resize and computes number of columns for text display,
     * then redraws screen in "edit" or "render" mode
     */
    public void componentResized( ComponentEvent ce){
    	//if (ce.getID() == ComponentEvent.COMPONENT_RESIZED){
        	//System.out.println("resized to " + getWidth() + "x" + getHeight() );
        	
        	// compute new width in columns and repaint
        	maxColumns = (int) (getWidth() / 8);
        	
        	// if resizing occurs in render-view -> re-render 
        	if (!isEditView){ 
        		renderDocument();
        	}
        	
    	//}
    }
    
    public void componentShown(ComponentEvent ce){ }
    public void componentMoved(ComponentEvent ce){ }
    public void componentHidden(ComponentEvent ce){ }

    /** 
     * Show a frame with the Thinlet contents.
     * @param args ignored.
     */
    public static void main(String[] args) throws java.io.IOException
    {
        
    	Main main = new Main();
    	
    	FrameLauncher frame = new FrameLauncher(main.baseTitle, main, 300, 400);
        
        frame.setIconImage(
        		Toolkit.getDefaultToolkit().createImage(
        				Main.class.getResource("/icons/private.png")));
        
        main.frame = frame;
        frame.addComponentListener( main );
                                
    }
    
    /** 
     * Paint with Antialiasing hints (from ThinG - AntialiasedThinlet.java)
     */
    public void paint(Graphics g)
    {
    	if ("true".equals(configProperties.getProperty("antialias"))){    		
            ((Graphics2D) g).setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            ((Graphics2D) g).setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    	}
    	
        super.paint(g);
    }


}
