package com.samebug.clients.eclipse;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWTException;

public class ShowApiKey extends AbstractHandler {
	final private String extractApiKeyJs = "return (function () {\n" + 
			"var request = new XMLHttpRequest();\n" + 
			"request.open('GET', '/rest/auth/api-key', false);\n" + 
			"request.setRequestHeader('Accept-Language', 'en-US,en;q=0.8');\n" +
			"request.send(null);\n" +
			"var response = JSON.parse(request.responseText);" +
			"return response.data.key;"
			+ "})()";


	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			Object r = Activator.browser.evaluate(extractApiKeyJs);
			String apiKey = (String) r;
			System.out.println("Successfully extracted API key: " + apiKey);
		} catch (SWTException x) {
			System.err.println("Extracting API key failed");;
		}
		return null;
	}

}
