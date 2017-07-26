package com.samebug.clients.eclipse.handlers;

import org.eclipse.ui.console.IHyperlink;

public class JavaStacktraceHyperlink implements IHyperlink {

	private String exceptionName;

	public JavaStacktraceHyperlink(String ename) {
		this.exceptionName = ename;
	}

	@Override
	public void linkActivated() {
		if(SampleHandler.getKey()!=null) {
			if(Activator.getDefault().client.getSearchID()==null)
				System.out.println("Search ID is null!");
			Activator.getDefault().browser.setUrl("https://nightly.samebug.com/searches/" + Activator.getDefault().client.getSearchID());
		}
	}

	@Override
	public void linkEntered() {
		//
	}

	@Override
	public void linkExited() {
		//
	}

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

}