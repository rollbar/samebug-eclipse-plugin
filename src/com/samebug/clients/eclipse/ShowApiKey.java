package com.samebug.clients.eclipse;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class ShowApiKey extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Object r = Activator.browser.evaluate("return extractApiKey();");
		System.out.println(r);
		return null;
	}

}
