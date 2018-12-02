package com.neopragma.legacy.screen;

import static org.junit.Assert.*;

import org.junit.Test;

public class SocialSecurityNumberTest {

	@Test
	public void badSsn_TooShort() {
		SocialSecurityNumber ssn = new SocialSecurityNumber("12345678");
		assertEquals(SocialSecurityNumber.INVALID_SSN_FORMAT, ssn.getSsnState());
	}

	@Test
	public void badSsn_TooLong() {
		SocialSecurityNumber ssn = new SocialSecurityNumber("12345678900");
		assertEquals(SocialSecurityNumber.INVALID_SSN_FORMAT, ssn.getSsnState());
	}

	@Test
	public void badSsn_BadDashLayout() {
		SocialSecurityNumber ssn = new SocialSecurityNumber("12-345-6789");
		assertEquals(SocialSecurityNumber.INVALID_SSN_FORMAT, ssn.getSsnState());
	}

	@Test
	public void validSsn_NoDashes() {
		SocialSecurityNumber ssn = new SocialSecurityNumber("123456789");
		assertEquals(SocialSecurityNumber.VALID_SSN, ssn.getSsnState());
	}

	@Test
	public void validSsn_WithDashes() {
		SocialSecurityNumber ssn = new SocialSecurityNumber("123-45-6789");
		assertEquals(SocialSecurityNumber.VALID_SSN, ssn.getSsnState());
	}

}
