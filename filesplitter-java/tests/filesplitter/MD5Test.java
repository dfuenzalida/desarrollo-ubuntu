package filesplitter;

import junit.framework.TestCase;


public class MD5Test extends TestCase {
	
	MD5 md = null;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		md = MD5.getInstance();
	}
	

	public void testMd5Hello() throws Exception {
		
		md = MD5.getInstance();
		assertEquals("5D41402ABC4B2A76B9719D911017C592", md.hashData("hello".getBytes()));

	}
	
}
