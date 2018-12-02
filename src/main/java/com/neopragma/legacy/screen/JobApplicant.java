package com.neopragma.legacy.screen;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;


/**
 * Job applicant class.
 */
public class JobApplicant {
	private SocialSecurityNumber ssn;
	
	private ZipCode zipCode;
	
	private ApplicantName name;

	public void setName(String firstName, String middleName, String lastName) {
		name = new ApplicantName();
		name.setName(firstName, middleName, lastName);
	}
	
	public void setSpanishName(String primerNombre, String segundoNombre,
							   String primerApellido, String segundoApellido) {
		name = new ApplicantName();
		name.setSpanishName(primerNombre, segundoNombre, primerApellido, segundoApellido);
	}
	
	public String formatLastNameFirst() {
		return name.formatLastNameFirst();
	}
	
	public int validateName() {
		return name.validateName();
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
