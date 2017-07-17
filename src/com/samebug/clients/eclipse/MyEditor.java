package com.samebug.clients.eclipse;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.part.EditorPart;

public class MyEditor extends EditorPart{

	 private Label contents;
	 private static Composite parent;
	 
	 public MyEditor() {
	 }
	 
	 public  static Composite getParent() {
		 return parent;
	 }
	 
     public void createPartControl(Composite parent) {
    	 	MyEditor.parent=parent;
     }
     public void init(IEditorSite site, IEditorInput input) {
        setSite(site);
        setInput(input);
     }
     public void setFocus() {
        if (contents != null)
           contents.setFocus();
     }
	@Override
	public void doSave(IProgressMonitor arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

}
