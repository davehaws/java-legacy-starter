package com.neopragma.legacy.screen;

public class SocialSecurityNumber {
	public static final int INVALID_SSN_SPECIAL_CASE = 4;
	public static final int INVALID_SSN_SERIAL = 3;
	public static final int INVALID_SSN_AREA = 2;
	public static final int INVALID_SSN_FORMAT = 1;
	public static final int VALID_SSN = 0;
	
	private static String[] invalidAreas = new String[] {
			"000", "666", "9"	
		};	
			
	private static String[] specialCases = new String[] {
			"078051120", "219099999"
		};	
			
	int state;
	
	public SocialSecurityNumber(String ssn) {
		if ( ssn.matches("(\\d{3}-\\d{2}-\\d{4}|\\d{9})") ) {
  		    ssn = ssn.replaceAll("-", "");
  		    
  		    String area = ssn.substring(0, 3);
  		    String group = ssn.substring(3, 5);
  		    String serial = ssn.substring(5);
  		    
  		    if (areaIsInvalid(area)) {
  		    	state = INVALID_SSN_AREA;
  		    }  else if (serialIsInvalid(serial)) {
  		    	state = INVALID_SSN_SERIAL;
  		    }  else if (numberIsSpecialCase(ssn)) {
  		    	state = INVALID_SSN_SPECIAL_CASE;
  		    } else {
  	  		    state = VALID_SSN;
  		    }
		} else {
  		    state = INVALID_SSN_FORMAT;
		}    
	}

	private static boolean numberIsSpecialCase(String number) {
		for (String invalid : specialCases) {
			if (number.equals(invalid)) {
				return true;
			}
		}
		return false;
	}

	private static boolean serialIsInvalid(String serial) {
		return serial.equals("0000");
	}
	
	private static boolean areaIsInvalid(String area) {
		for (String invalid : invalidAreas) {
			if (stringBeginsWith(area, invalid)) {
				return true;
			}
		}
		return false;
	}

	private static boolean stringBeginsWith(String area, String invalid) {
		return area.indexOf(invalid) == 0;
	}

	public int getSsnState() {
		// TODO Auto-generated method stub
		return state;
	}
	
}
