package com.neopragma.legacy.screen;

public class ApplicantName {

	public static final int VALID_NAME = 0;
	public static final int INVALID_NAME = 6;
	
	String firstName;
	String middleName;
	String lastName;

	public void setName(String first, String middle, String last) {
		this.firstName = protectAgainstNull(first);
		this.middleName = protectAgainstNull(middle);
		this.lastName = protectAgainstNull(last);
	}

	private String protectAgainstNull(String string) {
		return string == null ? "" : string;
	}

	public void setSpanishName(String primerNombre, String segundoNombre,
							   String primerApellido, String segundoApellido) {
		String lastName = null;
		if ( primerApellido != null ) {
  		    StringBuilder sb = new StringBuilder(primerApellido);
  		    if (segundoApellido != null && !segundoApellido.isEmpty()) {
  		    	sb.append(" " + segundoApellido);
  		    }
		    lastName = sb.toString();
		}
		setName(primerNombre, segundoNombre, lastName);
	}

	public int validateName() {
		if ( firstName.isEmpty() || lastName.isEmpty() ) {
			return INVALID_NAME;
		}
		
		return VALID_NAME;
	}

	public String formatLastNameFirst() {
		StringBuilder sb = new StringBuilder(lastName);
		sb.append(", ");
		sb.append(firstName);
		if ( !middleName.isEmpty() ) {
			sb.append(" ");
			sb.append(middleName);
		}
		return sb.toString();
	}

}
