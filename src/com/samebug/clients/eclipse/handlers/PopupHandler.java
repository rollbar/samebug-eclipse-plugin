package com.samebug.clients.eclipse.handlers;

import java.util.ArrayList;
import java.util.List;

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
		
		ConsoleLineTracker consoleTracker = Activator.getDefault().consoleTracker;
		
		List<Integer> lineIDs=new ArrayList<Integer>();
		
		int i=0;
			
		for(String line : consoleTracker.stacktraces) {
			
			String[] split = line.split("\\r\\n|\\r|\\n", -1);
						
			int startindex = consoleTracker.firstLines.get(i);
			
			int lastindex = split.length-1;
			
			for(int j=0; j<startindex; j++) {
				lineIDs.add(0);
			}
			
			for(int j=startindex; j<=lastindex; j++) {
					lineIDs.add(consoleTracker.IDs.get(i));
			}
			i++;
		}
				
		if(page.getSelection() instanceof TextSelection) {
			
			TextSelection textSelection=(TextSelection) page.getSelection();
			int lineNumber = textSelection.getStartLine();
			int ID= lineIDs.get(lineNumber);
			if(ID!=0)
				Activator.getDefault().browser.setUrl("https://nightly.samebug.com/searches/" + ID);
		}
		
		return null;
	}
	
}