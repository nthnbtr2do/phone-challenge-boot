package challenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class PhoneNumberTotalTest {

	@Test
	public void testZero() {
		PhoneTranslator phoneT = new PhoneTranslator("0000000000");
		assertEquals(phoneT.getTotal(), 0);
		//fail("Not yet implemented");
	}
	
	@Test
	public void testTwos() {
		PhoneTranslator phoneT = new PhoneTranslator("2222222222");
		assertEquals(phoneT.getTotal(), 3);
		//fail("Not yet implemented");
	}
	
	@Test
	public void testZeroNines() {
		PhoneTranslator phoneT = new PhoneTranslator("2222222299");
		assertEquals(phoneT.getTotal(), 3);
		//fail("Not yet implemented");
	}
	
	@Test
	public void testNines() {
		PhoneTranslator phoneT = new PhoneTranslator("9999999999");
		assertEquals(phoneT.getTotal(), 3);
		//fail("Not yet implemented");
	}

	@Test
	public void testAllNums() {
		PhoneTranslator phoneT = new PhoneTranslator("0123456789");
		assertEquals(phoneT.getTotal(), 3);
		//fail("Not yet implemented");
	}
}
