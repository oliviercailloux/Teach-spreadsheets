package io.github.oliviercailloux.y2018.teach_spreadsheets.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class GUIPref {
	public static void main(String[] args) {
		// setup the SWT window
		Display display = new Display();
		Shell shell = new Shell(display, SWT.RESIZE | SWT.CLOSE | SWT.MIN);
		shell.setText("Preferences");

		// initialize a grid layout manager
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shell.setLayout(gridLayout);
		
		private void createPrefs() {
			
		}
	}
	
	
}
