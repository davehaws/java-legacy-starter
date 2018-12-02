package com.neopragma.legacy.screen;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ApplicantNameTest {

	ApplicantName name;
	
	@Before
	public void beforeEach() {
		name = new ApplicantName();
	}
	
	@Test
	public void completeNameProvided() {
		name.setName("First", "Middle", "Last");
		assertEquals(ApplicantName.VALID_NAME, name.validateName());
	}

	@Test
	public void firstAndLastNamesProvided() {
		name.setName("First", null, "Last");
		assertEquals(ApplicantName.VALID_NAME, name.validateName());
	}
	
	@Test
	public void missingFirstName() {
		name.setName(null, null, "Last");
		assertEquals(ApplicantName.INVALID_NAME, name.validateName());
	}
	
	@Test
	public void missingLastName() {
		name.setName("First", null, null);
		assertEquals(ApplicantName.INVALID_NAME, name.validateName());
	}
	
	@Test
	public void completeSpanishNameProvided() {
		name.setSpanishName("PrimerNombre", "SegundoNombre", "PrimerApellido", "SegundoApellido");
		assertEquals(ApplicantName.VALID_NAME, name.validateName());
	}
	
	@Test
	public void spanishNameWithOneFirstNameProvided() {
		name.setSpanishName("PrimerNombre", null, "PrimerApellido", "SegundoApellido");
		assertEquals(ApplicantName.VALID_NAME, name.validateName());
	}
	
	@Test
	public void spanishNameWithOneLastNameProvided() {
		name.setSpanishName("PrimerNombre", null, "PrimerApellido", null);
		assertEquals(ApplicantName.VALID_NAME, name.validateName());
	}
	
	@Test
	public void spanishNameWithNoFirstNameProvided() {
		name.setSpanishName(null, null, "PrimerApellido", null);
		assertEquals(ApplicantName.INVALID_NAME, name.validateName());
	}
	
	@Test
	public void spanishNameWithNoLastNameProvided() {
		name.setSpanishName("PrimerNombre", "SegundoNombre", null, null);
		assertEquals(ApplicantName.INVALID_NAME, name.validateName());
	}
	
	@Test
	public void formatEnglishNameLastNameFirst() {
		name.setName("First", "Middle", "Last");
		assertEquals("Last, First Middle", name.formatLastNameFirst());
	}
	

}
