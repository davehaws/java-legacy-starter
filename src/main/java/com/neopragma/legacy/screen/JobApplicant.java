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
	
	private static final int INVALID_SSN_SPECIAL_CASE = 4;
	private static final int INVALID_SSN_SERIAL = 3;
	private static final int INVALID_SSN_GROUP = 2;
	private static final int INVALID_SSN_LENGTH = 1;
	private String firstName = null;
	private String middleName = null;
	private String lastName = null;
	
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
	
	private String ssn;
	
	private String[] specialCases = new String[] {
	    "219099999", "078051120"
	};
	
	private String zipCode;    
	private String city;
	private String state;

	public void setSsn(String ssn) {
		if ( ssn.matches("(\\d{3}-\\d{2}-\\d{4}|\\d{9})") ) {
  		    this.ssn = ssn.replaceAll("-", "");
		} else {
  		    this.ssn = "";
		}    
	}
	
	public String formatSsn() {
		StringBuilder sb = new StringBuilder(ssn.substring(0,3));
		sb.append("-");
		sb.append(ssn.substring(3,5));
		sb.append("-");
		sb.append(ssn.substring(5));
		return sb.toString();
	}

	public int validateSsn() {
		if ( ssnIsTooLong() ) {
			return INVALID_SSN_LENGTH;
		}
		if ( ssnHasInvalidArea() ) {
			return INVALID_SSN_GROUP;
		}
		if ( ssnHasInvalidSerial() ) {
			return INVALID_SSN_SERIAL;
		}
		if ( ssnIsSpecialCase() ) {
			return INVALID_SSN_SPECIAL_CASE;
		}
		return 0;
	}

	private boolean ssnIsSpecialCase() {
		for (int i = 0 ; i < specialCases.length ; i++ ) {
			if ( ssn.equals(specialCases[i]) ) {
				return true;
			}
		}
		return false;
	}

	private boolean ssnHasInvalidSerial() {
		return "0000".equals(ssn.substring(5));
	}

	private boolean ssnHasInvalidArea() {
		return "000".equals(ssn.substring(0,3)) || 
			 "666".equals(ssn.substring(0,3)) ||
			 "9".equals(ssn.substring(0,1));
	}

	private boolean ssnIsTooLong() {
		return !ssn.matches("\\d{9}");
	}

	public void setZipCode(String zipCode) throws URISyntaxException, IOException {
		this.zipCode = zipCode;
		CloseableHttpResponse response = getCityStateResponseFromZipCodeService();
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                long len = entity.getContentLength();
              	BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));
           		StringBuffer result = new StringBuffer();
           		String line = "";
           		while ((line = rd.readLine()) != null) {
           			result.append(line);
       		    }
                int metaOffset = result.indexOf("<meta ");
                int contentOffset = result.indexOf(" content=\"Zip Code ", metaOffset);
                contentOffset += 19;
                contentOffset = result.indexOf(" - ", contentOffset);
                contentOffset += 3;
                int stateOffset = result.indexOf(" ", contentOffset);
                city = result.substring(contentOffset, stateOffset);
                stateOffset += 1;
                state = result.substring(stateOffset, stateOffset+2);
            } else {
            	city = "";
            	state = "";
            }
        } finally {
            response.close();
        }
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
