package com.samebug.clients.eclipse.handlers;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicHttpResponse;

public class Main {

	public static void main(String[] args) {

		URI uri = null;
		try {
			uri = new URIBuilder()
			        .setScheme("http")
			        .setHost("www.google.com")
			        .setPath("/search")
			        .setParameter("q", "httpclient")
			        .setParameter("btnG", "Google Search")
			        .setParameter("aq", "f")
			        .setParameter("oq", "")
			        .build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpGet httpget = new HttpGet(uri);
		System.out.println(httpget.getURI());
		
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");

		System.out.println(response.getProtocolVersion());
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(response.getStatusLine().getReasonPhrase());
		System.out.println(response.getStatusLine().toString());
		
		response.addHeader("Set-Cookie", "c1=a; path=/; domain=localhost");
		response.addHeader("Set-Cookie", "c2=b; path=\"/\", c3=c; domain=\"localhost\"");
		
		HeaderIterator it=response.headerIterator("Set-Cookie");
		
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		

	}

}
