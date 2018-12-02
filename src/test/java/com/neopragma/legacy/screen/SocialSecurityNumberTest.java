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
	public void badSsn_DashesInWrongLocation() {
		SocialSecurityNumber ssn = new SocialSecurityNumber("12-345-6789");
		assertEquals(SocialSecurityNumber.INVALID_SSN_FORMAT, ssn.getSsnState());
	}

	@Test
	public void badSsn_AreaIs000() {
		SocialSecurityNumber ssn = new SocialSecurityNumber("000-45-6789");
		assertEquals(SocialSecurityNumber.INVALID_SSN_AREA, ssn.getSsnState());
	}

	@Test
	public void badSsn_AreaIs666() {
		SocialSecurityNumber ssn = new SocialSecurityNumber("666-45-6789");
		assertEquals(SocialSecurityNumber.INVALID_SSN_AREA, ssn.getSsnState());
	}

	@Test
	public void badSsn_AreaIs9xx() {
		SocialSecurityNumber ssn = new SocialSecurityNumber("901-45-6789");
		assertEquals(SocialSecurityNumber.INVALID_SSN_AREA, ssn.getSsnState());
	}

	@Test
	public void validSsn_AreaContains9ButDoesntStartWithIt() {
		SocialSecurityNumber ssn = new SocialSecurityNumber("191-45-6789");
		assertEquals(SocialSecurityNumber.VALID_SSN, ssn.getSsnState());
	}

	@Test
	public void badSsn_SerialIs0000() {
		SocialSecurityNumber ssn = new SocialSecurityNumber("123-45-0000");
		assertEquals(SocialSecurityNumber.INVALID_SSN_SERIAL, ssn.getSsnState());
	}

	@Test
	public void badSsn_SpecialCase078051120() {
		SocialSecurityNumber ssn = new SocialSecurityNumber("078051120");
		assertEquals(SocialSecurityNumber.INVALID_SSN_SPECIAL_CASE, ssn.getSsnState());
	}

	@Test
	public void badSsn_SpecialCase219099999() {
		SocialSecurityNumber ssn = new SocialSecurityNumber("219099999");
		assertEquals(SocialSecurityNumber.INVALID_SSN_SPECIAL_CASE, ssn.getSsnState());
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
