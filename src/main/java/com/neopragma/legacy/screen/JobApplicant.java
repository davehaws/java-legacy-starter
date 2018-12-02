package com.neopragma.legacy.screen;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;


/**
 * Job applicant class.
 */
public class JobApplicant {
	
	private String firstName = null;
	private String middleName = null;
	private String lastName = null;
	
	private SocialSecurityNumber ssn;
	
	private ZipCode zipCode;    

	public void setName(String firstName, String middleName, String lastName) {
		this.firstName = firstName == null ? "" : firstName;
		this.middleName = middleName == null ? "" : middleName;
		this.lastName = lastName == null ? "" : lastName;
	}
	
	public void setSpanishName(String primerNombre, String segundoNombre,
							   String primerApellido, String segundoApellido) {
		this.firstName = primerNombre == null ? "" : primerNombre;
		this.middleName = segundoNombre == null ? "" : segundoNombre;
		if ( primerApellido != null ) {
  		    StringBuilder sb = new StringBuilder(primerApellido);
		    sb.append(segundoApellido == null ? null : " " + segundoApellido);
		    this.lastName = sb.toString();
		} else {
			this.lastName = "";
		}
	}
	
	public String formatLastNameFirst() {
		StringBuilder sb = new StringBuilder(lastName);
		sb.append(", ");
		sb.append(firstName);
		if ( middleName.length() > 0 ) {
			sb.append(" ");
			sb.append(middleName);
		}
		return sb.toString();
	}
	
	public int validateName() {
		if ( firstName.length() > 0 && lastName.length() > 0 ) {
			return 0;
		} else {
			return 6;
		}
	}
	
	public void setSsn(String ssn) {
		this.ssn = new SocialSecurityNumber(ssn);
	}
	
	public String formatSsn() {
		return ssn.toString();
	}

	public int validateSsn() {
		return ssn.getSsnState();
	}

	public void setZipCode(String zip) throws URISyntaxException, IOException {
		zipCode = new ZipCode(zip);
	}

	public String getCity() {
		return zipCode.getCity();
	}

	public String getState() {
		return zipCode.getState();
	}
	
	public void add(String firstName,
			       String middleName,
			       String lastName,
			       String ssn,
			       String zipCode) throws URISyntaxException, IOException {
		setName(firstName, middleName, lastName);
		setSsn(ssn);
		setZipCode(zipCode);
		save();
	}
	
	private void save() {
		//TODO save information to a database
		System.out.println("Saving to database: " + formatLastNameFirst());
	}
	
	public static void main(String[] args) throws URISyntaxException, IOException {
		JobApplicant jobApplicant = new JobApplicant();
		boolean done = false;
		Scanner scanner = new Scanner(System.in);
		String firstName = "";
		String middleName = "";
		String lastName = "";
		String ssn = "";
		String zipCode = "";
		while (!done) {
			System.out.println("Please enter info about a job candidate or 'quit' to quit");
			System.out.println("First name?");
            firstName = scanner.nextLine();		
            if (firstName.equals("quit")) {
            	scanner.close();
            	System.out.println("Bye-bye!");
            	done = true;
            	break;
            }
			System.out.println("Middle name?");
            middleName = scanner.nextLine();
			System.out.println("Last name?");
            lastName = scanner.nextLine();			
			System.out.println("SSN?");
            ssn = scanner.nextLine();			
			System.out.println("Zip Code?");
            zipCode = scanner.nextLine();			
            jobApplicant.setName(firstName, middleName, lastName);          
            jobApplicant.setSsn(ssn);
            jobApplicant.setZipCode(zipCode);
            jobApplicant.save();
		}
	}
	
}
