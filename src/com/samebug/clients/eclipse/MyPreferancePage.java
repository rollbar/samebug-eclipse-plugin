package com.samebug.clients.eclipse;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class MyPreferancePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public MyPreferancePage() {
		super(GRID);
	}
	
	@Override
	public void init(IWorkbench workbench) {
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
    }

	
	@Override
	public void createFieldEditors() {
		addField(new StringFieldEditor("URI", "&Server URL:", getFieldEditorParent()));
		addField(new StringFieldEditor("String", "&API Key:", getFieldEditorParent()));
		addField(new StringFieldEditor("Integer", "&Connection Timeout", getFieldEditorParent()));
	}

}
