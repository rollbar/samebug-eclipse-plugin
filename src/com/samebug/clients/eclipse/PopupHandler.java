package com.samebug.clients.eclipse;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;

public class PopupHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPage x = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
		return null;
	}

}
