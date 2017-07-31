package com.samebug.clients.eclipse.handlers;

import java.util.ArrayList;
import java.util.List;

import com.samebug.clients.eclipse.search.*;
import com.samebug.clients.http.Client;

import org.eclipse.debug.ui.console.IConsole;
import org.eclipse.debug.ui.console.IConsoleLineTrackerExtension;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

public class ConsoleLineTracker implements IConsoleLineTrackerExtension{

	private static IConsole console;
	private static List<IRegion> lines= new ArrayList<IRegion>(); 
	
	public List<Integer> IDs=new ArrayList<Integer>();
	public List<String> stacktraces=new ArrayList<String>();
	public List<Integer> firstLines = new ArrayList<Integer>();
	
	private StackTraceMatcher stackTraceMatcher;
	
	private static boolean consoleClosed = true;

	/**
	 * @see org.eclipse.debug.ui.console.IConsoleLineTracker#dispose()
	 */
	public void dispose() {
	}

	/**
	 * @see org.eclipse.debug.ui.console.IConsoleLineTracker#init(org.eclipse.debug.ui.console.IConsole)
	 */
	public void init(IConsole c) {
	    synchronized(lines) {
	        ConsoleLineTracker.console= c;
	        lines= new ArrayList<IRegion>();
	        consoleClosed= false;
	        
	        Activator.getDefault().registerConsoleTracker(this);
	        stackTraceMatcher=new StackTraceMatcher(new StackTraceListener(){

				@Override
				public void stacktraceFound(String stacktrace) {
					if(SampleHandler.getKey()!=null) {
						Client client = Activator.getDefault().client;
						client.setKey(SampleHandler.getKey());
						client.sendStacktrace(stacktrace);
						IDs.add(client.getSearchID());
						firstLines.add(client.firstLine);
						stacktraces.add(stacktrace);
					}
				}
			});
	    }
	}

	/**
	 * @see org.eclipse.debug.ui.console.IConsoleLineTracker#lineAppended(org.eclipse.jface.text.IRegion)
	 */
	public void lineAppended(IRegion line) {
		lines.add(line);
		try {
			stackTraceMatcher.append(console.getDocument().get(line.getOffset(), line.getLength())+"\n");
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
				
	public static int getNumberOfMessages() {
		return lines.size();
	}
	
	public static String getMessage(int index) {
		if (index < lines.size()){
			IRegion lineRegion= (IRegion)lines.get(index);
			try {
				return console.getDocument().get(lineRegion.getOffset(), lineRegion.getLength());
			} catch (BadLocationException e) {
				return null;
			}
		}
		return null;
	}
	
	public static List<String> getAllMessages() {
		List<String> all= new ArrayList<String>(lines.size());
		for (int i = 0; i < lines.size(); i++) {
			IRegion lineRegion= (IRegion)lines.get(i);
			try {
				all.add(console.getDocument().get(lineRegion.getOffset(), lineRegion.getLength()));
			} catch (BadLocationException e) {
				continue;
			}
		}
		return all;
	}
	
	public static IDocument getDocument() {
		return console.getDocument();
	}
	
	public static void waitForConsole() {
		synchronized (lines) {
			if (consoleClosed) {
				return;
			}
			try {
				lines.wait(20000);
			} catch (InterruptedException ie) {
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.console.IConsoleLineTrackerExtension#consoleClosed()
	 */
	public void consoleClosed() {
		synchronized (lines) {
			consoleClosed= true;
			lines.notifyAll();
		}
	}
    
    public static boolean isClosed() {
        synchronized (lines) {
            return consoleClosed;
        }
    }    
}