package com.samebug.clients.eclipse.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;

public class ShowApiKey extends AbstractHandler {
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

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
			Activator.getDefault().browser.addTitleListener(new TitleListener() {
				@Override
				public void changed(TitleEvent arg0) {
					try {
						Object r = Activator.getDefault().browser.evaluate(extractApiKeyJs);
						String apiKey = (String) r;
						ShowApiKey.APIkey=apiKey;
						System.out.println("Successfully extracted API key: " + apiKey);
					} catch (SWTException x) {
						System.err.println("Extracting API key failed");;
					}
				}
				
			});
		
		
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setValue("API", ShowApiKey.getKey());
		store.setValue("URI", "https://nightly.samebug.com/login");
		
		return null;
	}

}

