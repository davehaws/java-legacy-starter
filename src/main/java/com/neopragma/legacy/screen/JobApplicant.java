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
	
	private static String getLine(String message, Scanner scanner) {
		System.out.println(message);
		return scanner.nextLine();
	}
	
	// TODO Will need to break this apart a bit so that we can test
	//  - Will need to change some of the code here so that we can mock the istream rather than System.in
	//  - Will also want to make the getLine calls allow for badly formatted input (request again rather than move on)
	public static void main(String[] args) throws URISyntaxException, IOException {
		JobApplicant jobApplicant = new JobApplicant();
		boolean done = false;
		Scanner scanner = new Scanner(System.in);
		while (!done) {
			System.out.println("Please enter info about a job candidate or 'quit' to quit");
            String firstLine = getLine("First name?", scanner);		
            if (firstLine.equals("quit")) {
            	scanner.close();
            	System.out.println("Bye-bye!");
            	done = true;
            	break;
            }

            String firstName = firstLine;
            String middleName = getLine("Middle name?", scanner);
            String lastName = getLine("Last name?", scanner);
            String ssn = getLine("SSN?", scanner);
            String zipCode = getLine("Zip Code?", scanner);

            jobApplicant.setName(firstName, middleName, lastName);          
            jobApplicant.setSsn(ssn);
            jobApplicant.setZipCode(zipCode);
            jobApplicant.save();
		}
	}
	
}
