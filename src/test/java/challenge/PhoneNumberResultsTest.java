package challenge;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class PhoneNumberResultsTest {

	@Test
	public void testZero() {
		PhoneTranslator phoneT = new PhoneTranslator("0000000000");
		PhoneNumbers nums = new PhoneNumbers(0,phoneT.getTotal(), 1, 10);
		phoneT.getResultOptions(nums);
		Iterator<String> iter = nums.getNumberIter();
		if(iter.hasNext()){
			fail("Shouldn't have any options");
		}
		
		//fail("Not yet implemented");
	}
	
	@Test
	public void testZeroTwo() {
		PhoneTranslator phoneT = new PhoneTranslator("0000000002");

		PhoneNumbers nums = new PhoneNumbers(0,phoneT.getTotal(), 1, 10);
		phoneT.getResultOptions(nums);
		System.out.println(nums);
		Iterator<String> iter = nums.getNumberIter();
		assertTrue(iter.hasNext());
		if(iter.hasNext()){
			assertEquals(iter.next(), "000000000A");
			assertEquals(iter.next(), "000000000B");
			assertEquals(iter.next(), "000000000C");
		}
		
	}

	@Test
	public void testZeroFourTwo() {
		PhoneTranslator phoneT = new PhoneTranslator("0000000092");

		PhoneNumbers nums = new PhoneNumbers(0,phoneT.getTotal(), 1, 10);
		phoneT.getResultOptions(nums);
		System.out.println(nums);
		Iterator<String> iter = nums.getNumberIter();
		assertTrue(iter.hasNext());
		if(iter.hasNext()){
			assertEquals(iter.next(), "000000009A");
			assertEquals(iter.next(), "000000009B");
			assertEquals(iter.next(), "000000009C");
			assertEquals(iter.next(), "00000000V2");
			assertEquals(iter.next(), "00000000VA");
			assertEquals(iter.next(), "00000000VB");
			assertEquals(iter.next(), "00000000VC");
			assertEquals(iter.next(), "00000000W2");
			assertEquals(iter.next(), "00000000WA");
			assertEquals(iter.next(), "00000000WB");
//			assertEquals(iter.next(), "00000000WC");
//			assertEquals(iter.next(), "00000000X2");
//			assertEquals(iter.next(), "00000000XA");
//			assertEquals(iter.next(), "00000000XB");
//			assertEquals(iter.next(), "00000000XC");
//			assertEquals(iter.next(), "00000000Y2");
//			assertEquals(iter.next(), "00000000YA");
//			assertEquals(iter.next(), "00000000YB");
//			assertEquals(iter.next(), "00000000YC");
//			assertEquals(iter.next(), "00000000Z2");
//			assertEquals(iter.next(), "00000000ZA");
//			assertEquals(iter.next(), "00000000ZB");
//			assertEquals(iter.next(), "00000000ZC");
		}
		
	}
}
