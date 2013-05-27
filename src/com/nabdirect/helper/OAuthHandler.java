package com.nabdirect.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

public class OAuthHandler {

	private final  String BASE_URL="https://www.appdirect.com";
    private final  String PATH="/api/integration/v1/events/";
	private final  String CUNSUMER_KEY= "nabdirect-5733";
	private final  String CUNSUMER_SECRET= "iOyUVAX06VFs3PLq";
	//private final  String BASE_URL= "https://www.acme-marketplace.com";
	
	
	public String getEvent(String token) {
		String eventURL = BASE_URL+PATH+token;
		OAuthConsumer consumer = new DefaultOAuthConsumer(CUNSUMER_KEY, CUNSUMER_SECRET);
		URL url= null;
		BufferedReader reader= null;
		StringBuilder sb= new StringBuilder();

		try {
				url = new URL(eventURL);
				HttpURLConnection request= (HttpURLConnection) url.openConnection();
				consumer.sign(request);
				request.connect();
	
				reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
				String line= "";
				while((line= reader.readLine()) != null) {
					sb.append(line);
				}
			} catch (MalformedURLException e) {
				return e.toString();// handle this
			} catch (IOException e) {
				return e.toString();
			} catch (OAuthMessageSignerException e) {
				return e.toString();// this
			} catch (OAuthExpectationFailedException e) {
				return e.toString();
			} catch (OAuthCommunicationException e) {
				return e.toString();
			} finally {
				try {
					if (reader != null) reader.close();
				} catch(IOException ignore) {
				}
		}

		return sb.toString();
	}

}