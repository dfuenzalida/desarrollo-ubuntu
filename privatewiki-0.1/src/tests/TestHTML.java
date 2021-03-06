// Generated by ThinG on 11-ene-2005 16:32:25.

package tests;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Vector;

import thinlet.Thinlet;
import thinlet.FrameLauncher;

/**
 * A thinlet with the contents of "<code>test.xml</code>".
 */
public class TestHTML extends Thinlet
{
	
	private Object panelContenido = null;
	private Object contenedor = null;
	private Vector panelContenidos = new Vector(); 

    /**
     * Create a new Main instance.
     */
    public TestHTML() throws java.io.IOException
    {
        add(parse("Main.xml"));
        
        // Change to a XP-like display style
        setFont(new Font("Tahoma", Font.PLAIN, 11));
			setColors(0xece9d8, 0x000000, 0xffffff,
				0x909090, 0xb0b0b0, 0xededed, 0xc7c5b2, 0xe68b2c, 0xf2c977);
    }


    /**
     * Callback handler for the 'init' method of panel 'panelContenido'.
     *
     * @param panelContenido the panel named 'panelContenido'.
     */
    public void initPanelContenido(Object panelContenido)
    {
    	// creo un nuevo panel para colocar los botones
    	
    	this.panelContenido = panelContenido;

    	Object panel2 = create("panel");
    	contenedor = panel2;
    	add(panelContenido, panel2, 0);

    	System.out.println("iniciando panelContenido...");
    	
    	Object boton = create("button");
    	setString(boton, "text", "Primer link");
    	setChoice(boton, "type", "link");
    	setString(boton, "tooltip", "enlace a otra parte");
    	setMethod(boton, "action", "clickBoton(this)", boton, this); 
    	agregar( boton );

    	Object boton2 = create("button");
    	setString(boton2, "text", "Segundo link");
    	setChoice(boton2, "type", "link");
    	setString(boton2, "tooltip", "enlace a otra parte");
    	setMethod(boton2, "action", "clickBoton(this)", boton, this); 
    	agregar( boton2 );

    }
    
    public void agregar(Object objeto){
    	
    	panelContenidos.add( objeto );
    	add(contenedor, objeto, 1);
    }
    
    public void clickBoton(Object boton){
    	
    	// imprimo el texto del boton
    	System.out.println( "Pulsaron el boton que dice '" + getString(boton, "text")  + "' ....");
    	
    	borrarContenidos();

    	Object panel2 = create("panel");
    	contenedor = panel2;
    	setInteger(contenedor, "weightx", 1); // Para que el panel llene el area de contenidos
    	add(panelContenido, panel2, 0);
    	
    	// Un titulo y una textarea de pruebas
    	String titulo = getString(boton, "text");
    	
    	/*
    	// revisar el error - no se despliega
    	Object labelTitulo = create("label");
    	setInteger(labelTitulo, "columns", 1);
    	setInteger(labelTitulo, "weightx", 1);
    	setString(labelTitulo, "text", titulo);
    	add(contenedor, labelTitulo, 1);
    	*/
    	
    	Object textArea = create("textarea");
    	setBoolean(textArea, "wrap", true);
    	setInteger(textArea, "weightx", 1);
    	setBoolean(textArea, "editable", false);
    	setString(textArea, "text","Esta es la nueva p�gina a la que se llega desde un link");
    	add(contenedor, textArea, 1);

    }
    
    public void borrarContenidos(){

    	for (int i=0; i<panelContenidos.size(); i++){
    		remove(panelContenidos.get(i));
    	}
    	
    	remove(this.contenedor);

    }
    
    /*
     * button_back callback
     */
    
    public void onBack(){
    	
    	borrarContenidos();
    	initPanelContenido(this.panelContenido);
    }
    
    /*
     * menu_file_exit callback
     */
    public void onExit(){
    	System.exit(0);
    }


    /**
     * Show a test frame with the Thinlet contents.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) throws java.io.IOException
    {
        FrameLauncher frame = new FrameLauncher(
        		"Window Title", new TestHTML(), 300, 400);
        frame.setIconImage(Toolkit.getDefaultToolkit().createImage(TestHTML.class.getResource("/icons/main_icon.gif")));
        
    }

}
