
package com.samebug.clients.eclipse.handlers;


import java.awt.EventQueue;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */

public class SampleHandler extends AbstractHandler {
	
	private static String APIkey;
	public static LoginWindow window;

	public static String getKey() {
		return APIkey;
	}
	
	public static void setKey(String key) {
		APIkey=key;
	}
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		
		if(store.getString("API").isEmpty()) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						window = new LoginWindow();
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}else {
			Activator.getDefault().client.setKey(store.getString("API"));
			IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			MessageDialog.openInformation(
					window.getShell(),
					" ",
					"You are already logged in!");
		}		
		return null;
	}
}
