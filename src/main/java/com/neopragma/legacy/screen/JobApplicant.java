package com.neopragma.legacy.screen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Job applicant class.
 */
public class JobApplicant {
	
	private String firstName = null;
	private String middleName = null;
	private String lastName = null;
	
	private SocialSecurityNumber ssn;

	private String zipCode;    
	private String city;
	private String state;

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

	public void setZipCode(String zipCode) throws URISyntaxException, IOException {
		this.zipCode = zipCode;
		
		String cityStateContent = getCityStateContentFromZipCode();
        
        if (cityStateContent != null) {
        	int cityIndex = getIndexOfCityInContent(cityStateContent);
        	int cityEnd = cityStateContent.indexOf(" ", cityIndex);
        	city = cityStateContent.substring(cityIndex, cityEnd);
        	
        	int stateIndex = cityEnd+1;
        	int stateEnd = stateIndex+2;
        	state = cityStateContent.substring(stateIndex, stateEnd);
        }
	}

	private int getIndexOfCityInContent(String cityStateContent) {
		int currentOffset = 0;
		String[] contentSections = new String[]{"<meta ", " content=\"Zip Code ", " - "};
		
		for (String section : contentSections) {
			currentOffset = cityStateContent.indexOf(section, currentOffset);
			currentOffset += section.length();
		}
		return currentOffset;
	}

	private String getCityStateContentFromZipCode() throws URISyntaxException, IOException, ClientProtocolException {
		String cityStateContent = null;
		
		CloseableHttpResponse response = getCityStateResponseFromZipCodeService();
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                cityStateContent = getContentFromResponse(response, entity);
            }
        } finally {
            response.close();
        }
		return cityStateContent;
	}

	private String getContentFromResponse(CloseableHttpResponse response, HttpEntity entity) throws IOException {
		long len = entity.getContentLength();
		BufferedReader rd = new BufferedReader(
		        new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}

	private CloseableHttpResponse getCityStateResponseFromZipCodeService()
			throws URISyntaxException, IOException, ClientProtocolException {
		URI uri = new URIBuilder()
            .setScheme("http")
            .setHost("www.zip-codes.com")
            .setPath("/search.asp")
            .setParameter("fld-zip", this.zipCode)
            .setParameter("selectTab", "0")
            .setParameter("srch-type", "city")
            .build();
        HttpGet request = new HttpGet(uri);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(request);
		return response;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
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
