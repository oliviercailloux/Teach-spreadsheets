package io.github.oliviercailloux.y2018.teach_spreadsheets.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Teacher;
import io.github.oliviercailloux.y2018.teach_spreadsheets.csv.CsvFileReader;

public class GUIPref {
	private final static Logger LOGGER = LoggerFactory.getLogger(GUIPref.class);

	private void initializeMainMenu() throws IOException {

		String logoFileName = "logoGUI.png";

		try (InputStream inputStream = GUIPref.class.getResourceAsStream(logoFileName)) {
			if (inputStream == null) {
				LOGGER.error("File " + logoFileName + " not found.");
				throw new FileNotFoundException("File not found");
			}

			LOGGER.info("Image-Logo bien récupérée");

			Display display = new Display();
			Shell shell = new Shell(display, SWT.CLOSE);
			shell.setText("Menu principal - Teach-spreadsheets");
			shell.setLayout(new GridLayout(1, false));
			shell.setSize(500, 700);

			// Display an image
			Image image = new Image(display, inputStream);
			Label labelImg = new Label(shell, SWT.CENTER);
			Rectangle clientArea = shell.getClientArea();
			// labelImg.setLocation(clientArea.x, clientArea.y);
			labelImg.setImage(image);
			labelImg.pack();

			// Create a horizontal separator
			Label separator;
			separator = new Label(shell, SWT.HORIZONTAL | SWT.SEPARATOR);
			separator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			// Label with teacher name
			Label lblCentered = new Label(shell, SWT.NONE);
			lblCentered.setText("Bienvenue" + getTheTeacher());
			lblCentered.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false));

			// Create a horizontal separator
			separator = new Label(shell, SWT.HORIZONTAL | SWT.SEPARATOR);
			separator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			Button button = new Button(shell, SWT.NONE);
			button.setText("Créez mes préférences");
			button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			button.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					// =====================================================
					// on button press, create a child Shell object passing
					// the main Display. The child could also access the
					// display itself by calling Display.getDefault()
					// =====================================================
					LOGGER.info("Shell for the courses preferences well opened");
					prefShell(display);
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});

			Text text = new Text(shell, SWT.BORDER);
			text.setText("aaaa");

			shell.pack();
			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
			image.dispose();
			display.dispose();
			LOGGER.info("Display well closed");

		}
	}

	/**
	 * This method allows to get the teacher's name from reading a CSV file
	 * containing all his infos like address, phone number...
	 * 
	 * @return the teacher's names
	 */
	private String getTheTeacher()
			throws NumberFormatException, FileNotFoundException, IllegalArgumentException, IOException {

		String teacherDetails = "teacher_info.csv";

		try (InputStream inputStream = GUIPref.class.getResourceAsStream(teacherDetails)) {
			if (inputStream == null) {
				LOGGER.error("File " + teacherDetails + " not found.");
				throw new FileNotFoundException("File not found");
			}

			Reader reader = new InputStreamReader(inputStream);
			Teacher t = new Teacher();
			t = CsvFileReader.readTeacherFromCSVfile(reader);

			LOGGER.info("Les informations du Teacher ont bien été récupérées !");
			String teacherName = t.getFirstName() + t.getName();
			return teacherName;
		}
	}

	private void prefShell(Display display) {
		
		// Doesn't allow the user to close the main shell when the child shell is open
		final Shell prefShell = new Shell(display, SWT.CLOSE | SWT.SYSTEM_MODAL);
		prefShell.setLayout(new GridLayout(1, false));
		prefShell.setText("Mes préférences - Teach-spreadsheets");

		// ============================
		// Create a Label in the Shell
		// ============================
		Label topLabel = new Label(prefShell, SWT.NONE);
		topLabel.setText("Insérez vos préférences");
		topLabel.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false));

		// Create a horizontal separator
		Label separator = new Label(prefShell, SWT.HORIZONTAL | SWT.SEPARATOR);
		separator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		 Button buttonSemester;
		 buttonSemester = new Button(prefShell, SWT.CHECK | SWT.WRAP);
		 buttonSemester.setText("Semestre 1");
		 buttonSemester = new Button(prefShell, SWT.CHECK | SWT.WRAP);
		 buttonSemester.setText("Semestre 2");
		
		final List list = new List (prefShell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);

		// shell.pack();
		prefShell.open();

		// =============================================================
		// Register a listener for the Close event on the child Shell.
		// This disposes the child Shell
		// =============================================================
		prefShell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event event) {
				LOGGER.info("Shell for the courses preferences well closed");
				prefShell.dispose();
			}
		});
	}
	
	private ArrayList<Course> getCoursesFromSemester() {
		ArrayList<Course> courses = new ArrayList();
		
		return courses;
	}

	public static void main(String[] args) throws IOException {
		GUIPref gui = new GUIPref();
		gui.initializeMainMenu();
	}

}
