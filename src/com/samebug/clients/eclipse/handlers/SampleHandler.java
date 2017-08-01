package com.samebug.clients.eclipse.handlers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.samebug.clients.eclipse.views.BrowserView;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */

public class SampleHandler extends AbstractHandler {
	
	private static String APIkey;

	final private String extractApiKeyJs = "return (function () {\n" + 
			"var request = new XMLHttpRequest();\n" + 
			"request.open('GET', '/rest/auth/api-key', false);\n" + 
			"request.setRequestHeader('Accept-Language', 'en-US,en;q=0.8');\n" +
			"request.send(null);\n" +
			"var response = JSON.parse(request.responseText);" +
			"return response.data.key;"
			+ "})()";
	
	public static String getKey() {
		return APIkey;
	}
	
/*
(function () {
            var postData = {
                "type": "signin-api-key-request",
                "apiKey": "a7b1ae5e-44d0-497b-8a00-d151854eeb14"
            }
            
            var request = new XMLHttpRequest();
            request.open('POST', '/rest/auth/signin-api-key', false);
            request.setRequestHeader('Content-Type', 'application/json');
                        request.setRequestHeader('Accept-Language', 'en-US,en;q=0.8');
    
            request.send(JSON.stringify(postData));
            })()
*/
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String loggedIn="";
	
		if(!store.getString("API").isEmpty()) {
			loggedIn = "return (function () {\n"+
					"var interval = setInterval(function() {\n" + 
					"if(document.getElementsByClassName('button-label').length != 0) {\n" + 
					"alert('klslskl');\n" +
					"clearInterval(interval);\n" + 
					"var postData = {\n"+
					"\"type\": \"signin-api-key-request\",\n" +
					"\"apiKey\":" + "\"" + store.getString("API") + "\"\n" +
					"}\n" +
					"var request = new XMLHttpRequest();\n" +
					"request.open('POST', '/rest/auth/signin-api-key', true);\n" + 
					"request.setRequestHeader('Content-Type', 'application/json');\n" +
					"request.setRequestHeader('Accept-Language', 'en-US,en;q=0.8');\n" +
					"request.send(JSON.stringify(postData));\n" + 
					"}\n" + 
					"}, 1000);" +
					"})()";
		}
		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		IWorkbenchPage page = window.getActivePage();
		
		try {
			page.showView("com.samebug.clients.eclipse.views.BrowserView");
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		
		Rectangle bounds = BrowserView.getParent().getBounds();
		
		Activator.getDefault().browser = new Browser(BrowserView.getParent(), SWT.NONE);
		Activator.getDefault().browser.setUrl("https://nightly.samebug.com/login");
		Activator.getDefault().browser.setBounds(bounds);
				
		System.out.println(store.getString("API"));
		if(!loggedIn.isEmpty()) {
			try {
				Activator.getDefault().browser.evaluate(loggedIn);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		Activator.getDefault().browser.addOpenWindowListener(new OpenWindowListener() {

			@Override
			public void open(WindowEvent event) {
				Desktop desktop = Desktop.getDesktop();
				URI uri = URI.create(Activator.getDefault().browser.getUrl());
				try {
					desktop.browse(uri);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		Activator.getDefault().browser.addTitleListener(new TitleListener() {
			
			@Override
			public void changed(TitleEvent arg0) {
				try {
					Object r = Activator.getDefault().browser.evaluate(extractApiKeyJs);
					String apiKey = (String) r;
					APIkey=apiKey;
					System.out.println("Successfully extracted API key: " + apiKey);						
				} catch (SWTException x) {
					System.err.println("Extracting API key failed");
				}
			}
		});
		
		return null;
	}
}
