package com.samebug.clients.eclipse;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
//import org.eclipse.jface.dialogs.MessageDialog;
//import org.eclipse.jface.wizard.IWizard;
//import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {
	final private String extractApiKeyFunctionJs = "function extractApiKey() {\n" + 
			"var request = new XMLHttpRequest();\n" + 
			"request.open('GET', '/rest/auth/api-key', false);\n" + 
			"request.setRequestHeader('Accept-Language', 'en-US,en;q=0.8');\n" +
			"request.send(null);\n" + 
			"return request.responseText;"
			+ "}";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
				
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		IWorkbenchPage page = window.getActivePage();
		try {
			page.showView("com.samebug.clients.eclipse.MyView");
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		
		
		Activator.browser = new Browser(MyView.getParent(), SWT.NONE);
		final Browser browser = Activator.browser;
		browser.getParent();
		browser.setBounds(0, 0, 600, 400);
		browser.setUrl("https://nightly.samebug.com/login");
		browser.addTitleListener(new TitleListener() {
			public void changed(TitleEvent event) {
				browser.execute(extractApiKeyFunctionJs);
			}
		});

		return null;
	}
}
