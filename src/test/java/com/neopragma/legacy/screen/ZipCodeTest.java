package com.neopragma.legacy.screen;

import static org.junit.Assert.*;

import org.junit.Test;

public class ZipCodeTest {

	@Test
	public void canFindCityStateWith5Digits() {
		ZipCode zip = new ZipCode("75001");
		assertEquals("Addison", zip.getCity());
		assertEquals("TX", zip.getState());
	}

}
