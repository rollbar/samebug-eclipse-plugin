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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;

import com.google.gson.Gson;
import com.samebug.clients.eclipse.handlers.Activator;
import com.samebug.clients.eclipse.handlers.SampleHandler;
import com.samebug.clients.http.entities.jsonapi.ApiKeyResponseData;
import com.samebug.clients.http.entities.jsonapi.CreatedSearchResource;
import com.samebug.clients.http.entities.jsonapi.LoginRequest;
import com.samebug.clients.http.json.Json;

public class Client {
	
	final HttpClient httpClient;
    static final int MaxConnections = 20;
    private static String APIKey;
    
    final static URI server=URI.create("https://samebug.io/rest/");
    final static URI search=server.resolve("searches");
    
    final static URI signin=server.resolve("auth/signin");
    final static URI API=server.resolve("auth/api-key");
    
    final static Gson gson= Json.gson;
    private Integer searchID;
    public int firstLine;
	
	public Client() {
		 HttpClientBuilder httpBuilder = HttpClientBuilder.create();
		 
		 List<BasicHeader> defaultHeaders = new ArrayList<BasicHeader>();
	     defaultHeaders.add(new BasicHeader("User-Agent", "Samebug-Plugin/0.5.0+eclipse Eclipse"));

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
			showMessageDialog();
			e.printStackTrace();
		} catch (IOException e) {
			showMessageDialog();
			e.printStackTrace();
		}
	}
	
	public void serverLogin(String username, String password) {
		 IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		 LoginRequest loginRequest=new LoginRequest(username,password);
		 HttpPost request = new HttpPost(signin);
		 request.setHeader("Content-Type", "application/json");
		 request.setEntity(new StringEntity(gson.toJson(loginRequest), Consts.UTF_8));
		 HttpResponse response=null;
		 
	     HttpClientContext httpClientContext = new HttpClientContext();
	     try {
	    	 	response = httpClient.execute(request, httpClientContext);
		} catch (ClientProtocolException e) {
			SampleHandler.window.frame.setVisible(false);
			showMessageDialog();
			e.printStackTrace();
		} catch (IOException e) {
			SampleHandler.window.frame.setVisible(false);
			showMessageDialog();
			e.printStackTrace();
		}
	     
	     HttpGet getrequest=new HttpGet(API);
	     try {
		    response=httpClient.execute(getrequest, httpClientContext);
			checkResponse(response);
		    InputStream content = response.getEntity().getContent();
            Reader reader = new InputStreamReader(content, "UTF-8");
            ApiKeyResponseData responseData=gson.fromJson(reader, ApiKeyResponseData.class);
            APIKey=responseData.getData().getKey();
            store.setValue("API", APIKey);
            store.setDefault("API", APIKey);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showMessageDialog() {
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    		MessageDialog.openInformation(
						Activator.getDefault().window.getShell(),
						" ",
						"Oops! Something went wrong.");
		    }
		});
	}
	
	private void checkResponse(HttpResponse response) {
		String r=response.toString();
		if(r.contains("401 Unauthorized")) {
			SampleHandler.window.frame.setVisible(false);
			Display.getDefault().syncExec(new Runnable() {
			    public void run() {
			    		MessageDialog.openInformation(
							Activator.getDefault().window.getShell(),
							" ",
							"Invalid username or password!");
			    }
			});
		}
	}
	
	public void setKey(String apiKey) {
		APIKey=apiKey;
	}
	
	public static String getKey() {
		return APIKey;
	}
	
	public Integer getSearchID() {
		return this.searchID;
	}
	
}
