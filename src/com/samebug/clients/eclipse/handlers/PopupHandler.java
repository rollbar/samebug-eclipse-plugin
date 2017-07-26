package com.samebug.clients.eclipse.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class PopupHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		IWorkbenchPage page = window.getActivePage();
		
		if(page.getSelection() instanceof TextSelection) {
			TextSelection textSelection=(TextSelection) page.getSelection();
			textSelection.getStartLine();
		}
		
		System.out.println(Activator.getDefault().consoleTracker.IDs);
		System.out.println(Activator.getDefault().consoleTracker.stacktraces);
		return null;
	}
	
}