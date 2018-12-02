package com.neopragma.legacy.screen;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

public class ZipCodeTest {

	@Test
	public void canFindCityStateWith5Digits() throws URISyntaxException, IOException {
		ZipCode zip = new ZipCode("75001");
		assertEquals("Addison", zip.getCity());
		assertEquals("TX", zip.getState());
	}

	@Test
	public void canFindCityStateWith9Digits() throws URISyntaxException, IOException {
		ZipCode zip = new ZipCode("856585578");
		assertEquals("Marana", zip.getCity());
		assertEquals("AZ", zip.getState());
	}

}
