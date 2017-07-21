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
import org.eclipse.ui.console.IHyperlink;

public class ConsoleLineTracker implements IConsoleLineTrackerExtension{

	private static IConsole console;
	private static List<IRegion> lines= new ArrayList<IRegion>(); 
	
	private StackTraceMatcher stackTraceMatcher;
	
	private static boolean consoleClosed= true;

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
	        stackTraceMatcher=new StackTraceMatcher(new StackTraceListener(){

				@Override
				public void stacktraceFound(String stacktrace) {
					if(SampleHandler.getKey()!=null) {
						Client client = new Client(SampleHandler.getKey());
						client.sendStacktrace(stacktrace);
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
		stackTraceMatcher.append(console.getDocument().get());
	}
		
	public void addHyperlink(IRegion line) {
		int offset=line.getOffset();
		int length=line.getLength();
		String exceptionName = null;
		try {
			exceptionName = console.getDocument().get(offset,length);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		IHyperlink link = new JavaStacktraceHyperlink(exceptionName);
		console.addLink(link, offset, length);
	}
	
	public void recognizeWord(String word) {
		String searchfor=console.getDocument().get();
		if(searchfor.contains(word)) {
			int startindex=searchfor.indexOf(word);
			IHyperlink link = new JavaStacktraceHyperlink(word);
			console.addLink(link, startindex, word.length());
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