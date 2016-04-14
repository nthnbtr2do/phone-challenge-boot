package challenge;

import java.util.HashMap;
import java.util.Map;

/*
 * Step 1: Run calculation of total possibilities based on passed in number (use array below?)
 * Step 2: Translate any alpha to number OR use array below
 * Step 3: Run recursion until reached the proper number for the particular page
 * Step 4: 
 */
/**
 * @author dlfeu788
 *
 */
public class PhoneTranslator {
	// use this if reverse lookup (from alpha) needed
	// private static final String[] LOOKUPTABLE = new String[] {"1","2ABC",
	// "3DEF", "4GHI", "5JKL", "6MNO", "7PQR", "8STU", "9VWXYZ", "0"};

	private static final Map<Character, String> LOOKUPMAP = new HashMap<Character, String>();

	private String phoneNum;
	private int pageNum = 1;
	private int pageIndex = 0;

	public PhoneTranslator(String phoneNum) {
		this.phoneNum = phoneNum;
		LOOKUPMAP.put('0', "0");
		LOOKUPMAP.put('1', "1");
		LOOKUPMAP.put('2', "2ABC");
		LOOKUPMAP.put('3', "3DEF");
		LOOKUPMAP.put('4', "4GHI");
		LOOKUPMAP.put('5', "5JKL");
		LOOKUPMAP.put('6', "6MNO");
		LOOKUPMAP.put('7', "7PQR");
		LOOKUPMAP.put('8', "8STU");
		LOOKUPMAP.put('9', "9VWXYZ");
	}

	/**
	 * Get the count of TOTAL options - without actually calculating each option (too expensive!)
	 * @return
	 */
	public int getTotal() {
		int total = 1;

		// TODO: Paralellize!
		for (int i = 0; i < phoneNum.length(); i++) {
			total *= getMatch(phoneNum.charAt(i)).length();
			System.out.println("Running Total: " + total);
		}
		
		// Attempt at paralellism - try again later! //int[] totals = newb
		/* 
		 * int[11]; List<Integer> totals = Collections.synchronizedList(new ArrayList<Integer>());
		 * 
		 * phoneNum.chars().forEach(c -> System.out.println("Char Int Val: " + Integer.toString(c)));
		 * 
		 * //split phone number and iterate 
		 * phoneNum.chars().forEach(c -> totals.add(getMatch((char)c).length()));
		 */
		
		System.out.println("TOTAL: " + (total - 1));
		return total - 1;
	}

	// Get the valid alpha equivalents as characters in a list for the given
	// number
	private String getMatch(char c) {
		String validOptions = "";

		/*
		 * If alpha was accepted as input, this would be the best way to
		 * determine options 
		 * for(int i = 0; i < LOOKUPTABLE.length; i++){
		 * 
		 * 	if(LOOKUPTABLE[i].indexOf(c, 0) >= 0){ 
		 * 		validOptions = LOOKUPTABLE[i];
		 * 	} 
		 * }
		 */
		validOptions = LOOKUPMAP.get(c);
		System.out.println("character " + c + " and return " + validOptions);

		return validOptions;
	}

	// 
	/**
	 * Call method that will recursively go through options until reaching the
	 * page of results needed
	 * 
	 * @param pnums
	 */
	public void getResultOptions(PhoneNumbers pnums) {
		// for (int i=0; i < phoneNum.length(); i++) {
		// 	total *= getMatch(phoneNum.charAt(i)).length();
		// 	System.out.println("Running Total: " + total);
		// 	addOptions(0, pnums, phone)
		// }
		addOptions(0, pnums, phoneNum.toCharArray());

	}

	/**
	 * Recursive method to go through each option for each digit in the phone number
	 * until the correct number of results has been found for display (and no more)
	 * 
	 * @param indx - which digit are we looking at in the input phone number
	 * @param pnums - the input phone number, results, and display details
	 * @param num - currently being evaluated phone + alpha option (up to index)
	 */
	private void addOptions(int indx, PhoneNumbers pnums, char[] num) {
		char c = num[indx];

		String options = LOOKUPMAP.get(c);

		for (int i = 0; i < options.length() && pageNum <= pnums.getPage(); i++) {
			num[indx] = options.charAt(i);

			System.out.println("num.length: " + num.length + " index: " + indx);
			
			if (num.length - 1 <= indx) {
				// increment page number or index on page
				if (pageIndex >= pnums.getPerPage()) {
					pageIndex = 1;
					pageNum++;
				} else {
					pageIndex++;
				}
				//System.out.println("pageIndex: " + pageIndex);

				// Paging Support
				if (pageNum == pnums.getPage()) {
					// Duplicate Filter - don't want to show what was input!
					if (!phoneNum.equals(String.valueOf(num))) {
						pnums.addNumber(String.valueOf(num));
					} else {
						pageIndex--;
					}
				}

			} else {
				addOptions(indx + 1, pnums, num.clone());
			}
		}
	}

}
