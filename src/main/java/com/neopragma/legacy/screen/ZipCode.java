package com.neopragma.legacy.screen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ZipCode {

	String zip;
	String city;
	String state;

	public ZipCode(String zip) throws URISyntaxException, IOException {
		this.zip = zip;
		setCityState();
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public void setCityState() throws URISyntaxException, IOException {
		String cityStateContent = getCityStateContentFromZipCode(zip);
        
        if (cityStateContent != null) {
        	int cityIndex = getIndexOfCityInContent(cityStateContent);
        	int cityEnd = cityStateContent.indexOf(" ", cityIndex);
        	city = cityStateContent.substring(cityIndex, cityEnd);
        	
        	int stateIndex = cityEnd+1;
        	int stateEnd = stateIndex+2;
        	state = cityStateContent.substring(stateIndex, stateEnd);
        }
	}

	private static int getIndexOfCityInContent(String cityStateContent) {
		int currentOffset = 0;
		String[] contentSections = new String[]{"<meta ", " content=\"Zip Code ", " - "};
		
		for (String section : contentSections) {
			currentOffset = cityStateContent.indexOf(section, currentOffset);
			currentOffset += section.length();
		}
		return currentOffset;
	}

	private static String getCityStateContentFromZipCode(String zip) throws URISyntaxException, IOException, ClientProtocolException {
		String cityStateContent = null;
		
		CloseableHttpResponse response = getZipCodeServiceResponse(zip);
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

	private static String getContentFromResponse(CloseableHttpResponse response, HttpEntity entity) throws IOException {
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

	private static CloseableHttpResponse getZipCodeServiceResponse(String zip)
			throws URISyntaxException, IOException, ClientProtocolException {
		URI uri = getURIForZipCodeService(zip);
        HttpGet request = new HttpGet(uri);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(request);
		return response;
	}

	private static URI getURIForZipCodeService(String zip) throws URISyntaxException {
		URI uri = new URIBuilder()
            .setScheme("http")
            .setHost("www.zip-codes.com")
            .setPath("/search.asp")
            .setParameter("fld-zip", zip)
            .setParameter("selectTab", "0")
            .setParameter("srch-type", "city")
            .build();
		return uri;
	}

	
}
