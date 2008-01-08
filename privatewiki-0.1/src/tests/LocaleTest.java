package tests;

import java.awt.GraphicsEnvironment;
import java.util.Locale;

public class LocaleTest {

	public static void main(String[] args) {
		
		Locale locale = Locale.getDefault();
		
		System.out.println("LANG: " + locale.getLanguage() + "-"+ locale.getDisplayLanguage());
		
		/**
		 * Muestro los idiomas conocidos
		 */
		
		Locale[] locales = Locale.getAvailableLocales();
		
		for (int i=0; locales!=null && i<locales.length; i++){
			System.out.println(i + ")" + locales[i].getDisplayCountry(locales[i]) + "-->" + locale.getDisplayCountry(locales[i]));
		}
		
		/**
		 * Determina los fonts disponibles en el sistema
		 * (para las propiedades del renderer de Privatewiki)
		 */
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fonts = ge.getAvailableFontFamilyNames();
		for (int i=0; fonts!= null && i<fonts.length; i++){
			//System.out.println("font='" + fonts[i] + "'"); // funciona
		}
		
		
		
	}
}
