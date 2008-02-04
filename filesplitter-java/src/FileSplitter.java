import java.text.MessageFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class FileSplitter {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		ResourceBundle resourceBundle = ResourceBundle.getBundle("FileSplitter");
		
		String message  = resourceBundle.getString("hello.world");
		
		Object[] params = new Object[]{ new Date(), ""};
		String greeting = MessageFormat.format(message, params);
		
		System.out.println(greeting);
		
	}
}
