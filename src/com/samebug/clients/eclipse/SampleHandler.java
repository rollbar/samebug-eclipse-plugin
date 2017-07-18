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

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
				
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		IWorkbenchPage page = window.getActivePage();
		try {
			page.showView("com.samebug.clients.eclipse.views.MyView");
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		
		Browser browser = new Browser(MyView.getParent(), SWT.NONE);
		browser.getParent();
		browser.addTitleListener(new TitleListener() {
			public void changed(TitleEvent event) {
				//shell.setText(event.title);
			}
		});
		browser.setBounds(0, 0, 600, 400);
		browser.setUrl("http://samebug.io");

		return null;
	}
}
