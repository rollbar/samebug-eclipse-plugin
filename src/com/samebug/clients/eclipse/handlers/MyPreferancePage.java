package com.samebug.clients.eclipse.handlers;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
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
		//IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		addField(new StringFieldEditor("URI", "&Server URL:", getFieldEditorParent()));
		addField(new StringFieldEditor("API", "&API Key:", getFieldEditorParent()));
		addField(new StringFieldEditor("TIMEOUT", "&Connection Timeout", getFieldEditorParent()));
		
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault("URI", "https://nightly.samebug.com/login");
		
	}

}
