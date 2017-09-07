package com.samebug.clients.eclipse.handlers;

import org.eclipse.swt.browser.Browser;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.samebug.clients.http.Client;

public class Activator extends AbstractUIPlugin {
	
	private static Activator plugin;
	public Browser browser;
	public Client client;
	public ConsoleLineTracker consoleTracker;
	public IWorkbenchWindow window;

	public Activator() {
		//
	}
	
	public void registerConsoleTracker(ConsoleLineTracker clt) {
		consoleTracker=clt;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		client=new Client();
		super.start(context);	
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
	
	public static Activator getDefault() {
		return plugin;
	}
}