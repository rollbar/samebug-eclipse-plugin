package com.samebug.clients.eclipse.handlers;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PreferencePage() {
		super(GRID);
	}
	
	@Override
	public void init(IWorkbench workbench) {
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
    }

	@Override
	public void createFieldEditors() {

		addField(new StringFieldEditor("URI", "&Server URL:", getFieldEditorParent()));
		addField(new StringFieldEditor("API", "&API Key:", getFieldEditorParent()));
		addField(new StringFieldEditor("TIMEOUT", "&Connection Timeout", getFieldEditorParent()));
		
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault("URI", "https://nightly.samebug.com/login");
		if(SampleHandler.getKey()!=null) {
			store.setDefault("API", SampleHandler.getKey());
		}
		
	}

}
