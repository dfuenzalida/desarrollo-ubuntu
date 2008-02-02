import java.util.ResourceBundle;

public class FileSplitter {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		ResourceBundle resourceBundle = ResourceBundle.getBundle("FileSplitter");
		
		String greeting = resourceBundle.getString("hello.world");
		
		System.out.println(greeting);
	}
}
