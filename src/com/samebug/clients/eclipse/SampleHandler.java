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

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		//IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		Browser browser = new Browser(MyEditor.getParent(), SWT.NONE);
		browser.getParent();
		browser.addTitleListener(new TitleListener() {
			public void changed(TitleEvent event) {
				//shell.setText(event.title);
			}
		});
		browser.setBounds(0, 0, 600, 400);
		//shell.pack();
		//shell.open();
		browser.setUrl("http://samebug.io");

		/*while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}*/

		return null;
	}
}
