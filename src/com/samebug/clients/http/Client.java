package com.samebug.clients.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import com.google.gson.Gson;
import com.samebug.clients.http.entities.jsonapi.CreatedSearchResource;
import com.samebug.clients.http.json.Json;

public class Client {
	
	final HttpClient httpClient;
    static final int MaxConnections = 20;
    private static String APIKey;
    
    final static URI server=URI.create("https://nightly.samebug.com/rest/");
    final static URI search=server.resolve("searches");
    
    final static Gson gson= Json.gson;
    private Integer searchID;
    public int firstLine;
	
	public Client() {
		 HttpClientBuilder httpBuilder = HttpClientBuilder.create();
		 
		 List<BasicHeader> defaultHeaders = new ArrayList<BasicHeader>();
	     defaultHeaders.add(new BasicHeader("User-Agent", "Samebug Eclipse Plugin"));

	     httpClient = httpBuilder.setDefaultRequestConfig(RequestConfig.DEFAULT).setMaxConnTotal(MaxConnections).setMaxConnPerRoute(MaxConnections)
	    		 .setDefaultHeaders(defaultHeaders).build();
	}
	
	public void sendStacktrace(String stacktrace) {
		NewSearch newSearch= new NewSearch(stacktrace);
		HttpPost request = new HttpPost(search);
		request.setHeader("Content-Type", "application/json");
		request.setHeader("X-Samebug-ApiKey", APIKey);
		request.setEntity(new StringEntity(gson.toJson(newSearch), Consts.UTF_8));
		
		try {
			final HttpResponse httpResponse = httpClient.execute(request);
			InputStream content = httpResponse.getEntity().getContent();
            Reader reader = new InputStreamReader(content, "UTF-8");
            CreatedSearchResource createdSearchResource=gson.fromJson(reader, CreatedSearchResource.class);
            firstLine=createdSearchResource.getMeta().getFirstLine();
            searchID=createdSearchResource.getData().getId();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setKey(String apiKey) {
		APIKey=apiKey;
	}
	
	public Integer getSearchID() {
		return this.searchID;
	}
	
}
