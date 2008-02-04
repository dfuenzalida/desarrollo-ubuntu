package filesplitter;

import junit.framework.TestCase;


public class MD5Test extends TestCase {
	
	MD5 md = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		md = MD5.getInstance();
	}
	
	public void testAppendBytes() throws Exception {
		
		md = MD5.getInstance();
		md.addBytes("hello".getBytes());
		md.addBytes("world".getBytes());
		String hash1 = md.hashData();

		md = MD5.getInstance();
		md.addBytes("helloworld".getBytes());
		String hash2 = md.hashData();
		
		assertEquals(hash1, hash2);

	}

	public void testMd5Hello() throws Exception {
		
		md = MD5.getInstance();
		assertEquals("5D41402ABC4B2A76B9719D911017C592", md.hashData("hello".getBytes()));

	}
	
}
