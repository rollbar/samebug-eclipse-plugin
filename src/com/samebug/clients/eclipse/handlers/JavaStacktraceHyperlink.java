package com.samebug.clients.eclipse.handlers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.ui.console.IHyperlink;

public class JavaStacktraceHyperlink implements IHyperlink {

	private String exceptionName;

	public JavaStacktraceHyperlink(String ename) {
		this.exceptionName = ename;
	}

	@Override
	public void linkActivated() {
		Desktop d = Desktop.getDesktop();
		try {
			d.browse(new URI("http://google.com"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void linkEntered() {

	}

	@Override
	public void linkExited() {

	}

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

}