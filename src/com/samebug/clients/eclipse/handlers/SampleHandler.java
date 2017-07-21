package com.samebug.clients.eclipse.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.samebug.clients.eclipse.views.MyView;

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
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
				
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		IWorkbenchPage page = window.getActivePage();
		try {
			page.showView("com.samebug.clients.eclipse.views.MyView");
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		
		Activator.browser = new Browser(MyView.getParent(), SWT.NONE);
		final Browser browser = Activator.browser;
		browser.getParent();
		browser.setBounds(0, 0, 600, 400);
		browser.setUrl("https://nightly.samebug.com/login");
		
		Activator.browser.addTitleListener(new TitleListener() {
			@Override
			public void changed(TitleEvent arg0) {
				try {
					Object r = Activator.browser.evaluate(extractApiKeyJs);
					String apiKey = (String) r;
					SampleHandler.APIkey=apiKey;
					System.out.println("Successfully extracted API key: " + apiKey);
					IPreferenceStore store = Activator.getDefault().getPreferenceStore();
					store.setValue("API", SampleHandler.getKey());
				} catch (SWTException x) {
					System.err.println("Extracting API key failed");;
				}
			}
			
		});
		
		return null;
	}
}
