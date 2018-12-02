package com.neopragma.legacy.screen;

public class SocialSecurityNumber {
	public static final int INVALID_SSN_SPECIAL_CASE = 4;
	public static final int INVALID_SSN_SERIAL = 3;
	public static final int INVALID_SSN_GROUP = 2;
	public static final int INVALID_SSN_FORMAT = 1;
	public static final int VALID_SSN = 0;
	
	String area = "";
	String group = "";
	String serial = "";
	int ssnState;
	
	public SocialSecurityNumber(String ssn) {
		if ( ssn.matches("(\\d{3}-\\d{2}-\\d{4}|\\d{9})") ) {
  		    ssn = ssn.replaceAll("-", "");
  		    
  		    area = ssn.substring(0, 3);
  		    group = ssn.substring(3, 5);
  		    serial = ssn.substring(5);
  		    ssnState = VALID_SSN;
		} else {
  		    ssn = "";
  		    ssnState = INVALID_SSN_FORMAT;
		}    
	}

	public int getSsnState() {
		// TODO Auto-generated method stub
		return ssnState;
	}
	
}
