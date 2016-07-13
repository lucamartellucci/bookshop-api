package io.lucci.bookshop.util;

import org.apache.commons.lang3.RandomStringUtils;

public class ISBNGenerator {

	private static String calculateChecksumDigit(String s) {
		int iSum = 0;
		int iDigit = 0;
		for (int i = s.length(); i >= 1; i--) {
			iDigit = Integer.parseInt(""+s.charAt((i - 1)));
			if (i % 2 == 0) { // odd
				iSum += iDigit * 3;
			} else { // even
				iSum += iDigit * 1;
			}
		}
		return "" + (10 - (iSum % 10)) % 10;
	}

	public static String generate() {
		String fakeisbn = "978001" + RandomStringUtils.randomNumeric(6);
		String check = calculateChecksumDigit(fakeisbn);
		return fakeisbn + check;
	}
}
