package com.neopragma.legacy.screen;

import static org.junit.Assert.*;

import org.junit.Test;

public class ApplicantNameTest {

	@Test
	public void completeNameProvided() {
		ApplicantName name = new ApplicantName();
		name.setName("First", "Middle", "Last");
		assertEquals(0, name.validateName());
	}


}
