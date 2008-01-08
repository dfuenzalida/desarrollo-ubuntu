package tests;

import java.util.Vector;

public class LinkRenderer {

	public static void main(String[] args) {

		String texto = "Este es un texto de prueba para ver como funciona el texto y si esta bien";

		Vector titulos = new Vector();
		titulos.add("texto de prueb");
		titulos.add("funciona el");
		titulos.add("a");

		linkRenderer(texto, titulos);

	}

	public static void linkRenderer(String text, Vector documentTitles) {

		int maxColumns = 30;
		int column = 0;
		int textPos = 0;
		boolean isLink = false;

		while (textPos < text.length()) {

			// for each documentTitle
			for (int i = 0; i < documentTitles.size(); i++) {
				String documentTitle = (String) documentTitles.get(i);
				int matchEndPos = textPos + documentTitle.length();
				
				// comparision for the text can't be outside of the text
				if (matchEndPos > text.length()){ matchEndPos = text.length(); }
				
				String documentMatch = text.substring(textPos, matchEndPos);
				isLink = false;

				//System.out.println("***" + documentTitle + "=" + documentMatch);

				// if it's a match with a documentTitle, render it as a link
				if (documentTitle.equals(documentMatch)) {

					// move textPos
					textPos += documentTitle.length();

					// TODO: move column count/print to a special function
					// render link
					System.out.print(" [" + documentMatch + "]");
					
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

				// if no other space found
				if (nextSpacePos == -1) {
					labelText = text.substring(textPos);
					textPos = text.length();
				} else {
					labelText = text.substring(textPos, nextSpacePos);
					// move after the next space in the text
					textPos = nextSpacePos + 1;
				}

				// render label
				System.out.print(labelText + " ");
				
				// insert a line-break if max lenght is reached with the next text 
				if ( column + labelText.length() > maxColumns ){
					System.out.print("\n");
					column = 0;
				}
				
				column += labelText.length();

			}

		}

	}

}

