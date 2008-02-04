package filesplitter;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {

		TestSuite s = new TestSuite();
		s.addTestSuite(MD5Test.class);
		s.addTestSuite(SplitTest.class);
		
		return s;
	}
	
}
