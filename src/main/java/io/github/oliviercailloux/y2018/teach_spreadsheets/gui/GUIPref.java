package io.github.oliviercailloux.y2018.teach_spreadsheets.gui;

import java.awt.Label;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CoursePref;

public class GUIPref {
	public static void main(String[] args) {

		// setup the SWT window
		Display display = new Display();
		Shell shell = new Shell(display, SWT.RESIZE | SWT.CLOSE | SWT.MIN);
		shell.setText("Conference");

		// initialize a grid layout manager
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shell.setLayout(gridLayout);

		// create the label and the field text
		Label labelTitle = new Label(shell, SWT.NONE);
		labelTitle.setText("Title : ");
		Text textTitle = new Text(shell, SWT.BORDER);

		Button buttonSubmit = new Button(shell, SWT.PUSH);
		buttonSubmit.setText("Submit");
		buttonSubmit.addSelectionListener(new SelectionAdapter() {
			// the function proceed() executed once we press the button Submit
			public void widgetSelected(SelectionEvent event) {
				try {
					createPref();
				} catch (ValidationException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParserException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}

			}

			// this function save the value in the fields of GUI in a conference and
			// write-read a ICalendar
			private void createPref() {
				List<CoursePref> prefs = new ArrayList<CoursePref>();

			}
		});

	}

}
