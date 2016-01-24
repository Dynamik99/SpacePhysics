package util;

import java.math.BigInteger;

public class ScientificNotations {
	
	public static BigInteger getBigInteger(float prefix, int tensPower) {
		while(prefix >= 10f) {
			prefix /= 10f;
			tensPower += 1;
		}
		if(tensPower > 0) {
			String number = "";
			for(int i = 0; i < tensPower; i++) {
				number = number + "0";
			}
			String prefixString = "" + prefix;
			String stringedPrefix = "";
			int max = 0;
			for(int i = 0; i < prefixString.length(); i++) {
				if(!prefixString.substring(i, i + 1).equals(".")) {
					stringedPrefix = stringedPrefix + prefixString.substring(i, i + 1);
					max += 1;
				}
			}
			number = stringedPrefix + number.substring(max, number.length());
			return new BigInteger(number);
		}
		return null;
	}
}