package com.samebug.clients.eclipse;

import javax.inject.Inject;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;

public class MyView extends ViewPart{
	
	public static final String ID = "com.samebug.clients.eclipse.views.MyView";
	private static Composite parent;
	
	@Inject IWorkbench workbench;
	
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}
		@Override
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		@Override
		public Image getImage(Object obj) {
			return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}
	
	@Override
	public void createPartControl(Composite parent) {
		MyView.parent=parent;
	}
	
	public static Composite getParent() {
		return parent;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

}
