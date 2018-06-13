package io.github.oliviercailloux.y2018.teach_spreadsheets.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swt.widgets.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Choice;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CoursePref;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CourseSheet;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CourseSheetMetadata;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Teacher;
import io.github.oliviercailloux.y2018.teach_spreadsheets.csv.CsvFileReader;
import io.github.oliviercailloux.y2018.teach_spreadsheets.odf.ReadCourses;

public class GUIPref {
	private final static Logger LOGGER = LoggerFactory.getLogger(GUIPref.class);

	private Display display;
	private Shell shell;
	private Shell prefShell;

	private String fileName;

	private TeachSpreadSheetController teach;

	private Composite groupButtons;
	private Group groupYearsStudy;

	private String selectedYearStudy = "";

	public GUIPref(TeachSpreadSheetController teach) {
		this.teach = teach;
	}

	private void initializeMainMenu() throws IOException {

		String logoFileName = "logoGUI.png";

		try (InputStream inputStream = GUIPref.class.getResourceAsStream(logoFileName)) {
			if (inputStream == null) {
				LOGGER.error("File " + logoFileName + " not found.");
				throw new FileNotFoundException("File not found");
			}

			LOGGER.info("Image-Logo bien récupérée");

			display = new Display();
			shell = new Shell(display, SWT.CLOSE);
			shell.setText("Menu principal - Teach-spreadsheets");
			shell.setLayout(new GridLayout(1, false));
			shell.setSize(500, 700);

			// Display an image
			Image image = new Image(display, inputStream);
			Label labelImg = new Label(shell, SWT.CENTER);
			Rectangle clientArea = shell.getClientArea();
			labelImg.setLocation(clientArea.x, clientArea.y);
			labelImg.setImage(image);
			labelImg.pack();

			// Create a horizontal separator
			Label lblSeparator = new Label(shell, SWT.HORIZONTAL | SWT.SEPARATOR);
			lblSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			// Label with teacher name
			Label lblCentered = new Label(shell, SWT.NONE);
			lblCentered.setText("Bienvenue" + getTheTeacher());
			lblCentered.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false));

			// Create a horizontal separator
			lblSeparator = new Label(shell, SWT.HORIZONTAL | SWT.SEPARATOR);
			lblSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			Button buttonFileExplorer = new Button(shell, SWT.NONE);
			buttonFileExplorer.setText("Ouvrez votre fichier contenant tous les cours");
			buttonFileExplorer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			buttonFileExplorer.addSelectionListener(new SelectionListener() {
				@SuppressWarnings("resource")
				@Override
				public void widgetSelected(SelectionEvent e) {
					// =====================================================
					// on button press, create a child Shell object passing
					// the main Display. The child could also access the
					// display itself by calling Display.getDefault()
					// =====================================================
					LOGGER.info("File Explorer well opened");
					// String chosenFile = openFileExplorer();
					// getCoursesFromFileExplorer();
					String fileName = openFileExplorerTwo();

					FileInputStream fis;
					try {
						fis = new FileInputStream(fileName);
					} catch (FileNotFoundException e1) {
						throw new IllegalStateException(e1);
					}
					teach.setSource(fis);
					// java.util.List<String> yearNames = teach.getYearNames();

					prefShell();
					// System.out.println(chosenFile);
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});

			Button buttonExit;
			buttonExit = new Button(shell, SWT.NONE);
			buttonExit.setText("Quitter l'application");
			buttonExit.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			buttonExit.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					// =====================================================
					// on button press, create a child Shell object passing
					// the main Display. The child could also access the
					// display itself by calling Display.getDefault()
					// =====================================================

					exitApplication();

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

	private void prefShell() {

		// Doesn't allow the user to close the main shell when the child shell is open
		prefShell = new Shell(display, SWT.CLOSE | SWT.SYSTEM_MODAL);

		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		prefShell.setLayout(gl);

		// prefShell.setLayout(new GridLayout(2, false));
		prefShell.setText("Mes préférences - Teach-spreadsheets");

		// Create a menu
		Menu menu = new Menu(prefShell, SWT.BAR);
		// create a file menu and add an exit item
		final MenuItem file = new MenuItem(menu, SWT.CASCADE);
		file.setText("&Menu");
		final Menu fileMenu = new Menu(prefShell, SWT.DROP_DOWN);
		// final Menu fileMenu = new Menu(file);
		file.setMenu(fileMenu);
		final MenuItem exportItem = new MenuItem(fileMenu, SWT.PUSH);
		exportItem.setText("&Export your prefs");
		// method
		final MenuItem openNewFileItem = new MenuItem(fileMenu, SWT.PUSH);
		openNewFileItem.setText("&Open new file courses");
		openNewFileItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// =====================================================
				// on button press, create a child Shell object passing
				// the main Display. The child could also access the
				// display itself by calling Display.getDefault()
				// =====================================================
				String pathFile = openFileExplorer();
				if (pathFile != null) {
					prefShell.dispose();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		final MenuItem closeShellItem = new MenuItem(fileMenu, SWT.PUSH);
		closeShellItem.setText("Close this display");
		closeShellItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// =====================================================
				// on button press, create a child Shell object passing
				// the main Display. The child could also access the
				// display itself by calling Display.getDefault()
				// =====================================================
				boolean closing = exitShell();
				if (closing) {
					prefShell.dispose();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		final MenuItem menuSeparator = new MenuItem(fileMenu, SWT.SEPARATOR);
		final MenuItem exitItem = new MenuItem(fileMenu, SWT.NONE);
		exitItem.setText("E&xit the application");
		exitItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// =====================================================
				// on button press, create a child Shell object passing
				// the main Display. The child could also access the
				// display itself by calling Display.getDefault()
				// =====================================================
				exitApplication();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		prefShell.setMenuBar(menu);

		// ============================
		// Create a Label in the Shell
		// ============================
		Label topLabel = new Label(prefShell, SWT.NONE);
		topLabel.setText("Insérez vos préférences");
		topLabel.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false));

		// Create a horizontal separator
		Label lblSeparator = new Label(prefShell, SWT.HORIZONTAL | SWT.SEPARATOR);
		lblSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// lblSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		// c = createComposite();
		groupYearsStudy = createGroupYearsStudy();

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

	private Group createGroupYearsStudy() {
		java.util.List<String> yearNames = teach.getYearNames();

		Composite c = new Composite(prefShell, SWT.CENTER);
		c.setLayout(new GridLayout(2, false));
		c.setSize(prefShell.getSize().x, prefShell.getSize().y);

		Group group1 = new Group(c, SWT.NONE);
		group1.setSize(c.getSize().x / 3, c.getSize().y / 3);

		group1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		group1.setText("Step 1: Choose the year of study");
		group1.setLayout(new GridLayout(1, false));

		List listYearStudy = new List(group1, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		listYearStudy.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		for (String string : yearNames) {
			listYearStudy.add(string);
		}

		/**
		 * listYearStudy.addListener(SWT.Selection, new Listener() {
		 * 
		 * @Override public void handleEvent(Event e) { int[] selection =
		 *           listYearStudy.getSelectionIndices(); java.util.List<String>
		 *           selectedYears = new ArrayList(); for (int i = 0; i <
		 *           selection.length; i++) {
		 *           selectedYears.add(listYearStudy.getItem(i)); } selectedYearStudy =
		 *           selectedYears.get(selectedYears.size() - 1); } });
		 **/

		final Text text = new Text(prefShell, SWT.BORDER);
		text.setBounds(60, 130, 160, 25);

		listYearStudy.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				int[] selectedItems = listYearStudy.getSelectionIndices();

				String s[] = listYearStudy.getSelection();

				String outString = s[0];
				text.setText("Selected Items: " + outString);
				selectedYearStudy = outString;

				System.out.println(selectedYearStudy);
				if (groupButtons != null)
					groupButtons.dispose();
				groupButtons = createGroupOfButtons();
			}

			public void widgetDefaultSelected(SelectionEvent event) {
				int[] selectedItems = listYearStudy.getSelectionIndices();
				String outString = "";
				for (int loopIndex = 0; loopIndex < selectedItems.length; loopIndex++)
					outString += selectedItems[loopIndex] + " ";
				System.out.println("Test Selected Items: " + outString);
			}
		});

		prefShell.pack();
		return group1;
	}

	private Composite createGroupOfButtons() {
		Composite c = new Composite(prefShell, SWT.CENTER);
		GridLayout f = new GridLayout(2, false);
		c.setLayout(f);
		GridData a = new GridData(SWT.FILL, SWT.CENTER, true, false);
		a.minimumWidth = SWT.FILL;
		a.horizontalAlignment = SWT.CENTER;
		a.widthHint = 200;

		Group group2 = new Group(c, SWT.SHADOW_OUT);
		group2.setText("Step 1: Choose:");
		group2.setSize(c.getSize().x / 3, c.getSize().y / 3);
		group2.setLayout(new GridLayout(1, true));

		java.util.List<Integer> listSemester = teach.getSemesters(selectedYearStudy);
		int firstSemester = 0;
		int secondSemester = 0;

		firstSemester = listSemester.get(0);
		secondSemester = listSemester.get(1);
		// buttonCsv
		final Button button1 = new Button(group2, SWT.RADIO);
		button1.setText(String.valueOf(firstSemester));
		// buttonTxt
		final Button button2 = new Button(group2, SWT.RADIO);
		button2.setText(String.valueOf(secondSemester));

		Listener listener1 = new Listener() {
			public void handleEvent(Event event) {
				int user_choice;

				if (event.widget == button1) {
					user_choice = 1;
					System.out.println(user_choice);
				} else if (event.widget == button2) {
					user_choice = 2;
					System.out.println(user_choice);
				}

			}
		};
		button1.addListener(SWT.Selection, listener1);
		button2.addListener(SWT.Selection, listener1);

		prefShell.pack();
		// return group2;
		return c;
	}

	private Composite createComposite() {
		java.util.List<String> yearNames = teach.getYearNames();

		Composite c = new Composite(prefShell, SWT.CENTER);
		c.setLayout(new GridLayout(3, true));
		c.setSize(prefShell.getSize().x, prefShell.getSize().y);

		Group group1 = new Group(c, SWT.NONE);
		group1.setSize(c.getSize().x / 3, c.getSize().y / 3);

		group1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		group1.setText("Step 1: Choose the year of study");
		group1.setLayout(new GridLayout(1, false));

		/**
		 * Label nameLabel = new Label(group1, SWT.NONE); nameLabel.setText("Choose");
		 **/

		List listYearStudy = new List(group1, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		listYearStudy.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		/**
		 * for( int i = 0; i < 10; i++ ) {
		 * 
		 * list.add( "Item " + i ); }
		 **/

		for (String string : yearNames) {
			listYearStudy.add(string);
		}

		listYearStudy.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {

				int[] selection = listYearStudy.getSelectionIndices();
				for (int i = 0; i < selection.length; i++) {
					selectedYearStudy = listYearStudy.getItem(i);

					// selectedYearStudy = listYearStudy.getItem(i);
					// System.out.println(listYearStudy.getItem(i));
					// string += selection[i] + " ";
				}
				// System.out.println(selectedYearStudy);
				// System.out.println("Selection={" + string + "}");
			}

			// selectedYearStudy = selectedYearStudyListener;
		});
		// List listSheetName = new List(group1, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		/**
		 * // Group group1 = new Group(prefShell, SWT.SHADOW_OUT); Group group2 = new
		 * Group(c, SWT.SHADOW_OUT); group2.setText("Step 1: Choose:");
		 * group2.setSize(c.getSize().x / 3, c.getSize().y / 3); group2.setLayout(new
		 * GridLayout(1, true));
		 * 
		 * 
		 * // buttonCsv final Button button1 = new Button(group2, SWT.RADIO);
		 * button1.setText("choice 1 "); // buttonTxt final Button button2 = new
		 * Button(group2, SWT.RADIO); button2.setText("choice 2 ");
		 * 
		 * Listener listener1 = new Listener() { public void handleEvent(Event event) {
		 * int user_choice;
		 * 
		 * if (event.widget == button1) { user_choice = 1;
		 * System.out.println(user_choice); } else if (event.widget == button2) {
		 * user_choice = 2; System.out.println(user_choice); }
		 * 
		 * } }; button1.addListener(SWT.Selection, listener1);
		 * button2.addListener(SWT.Selection, listener1);
		 **/

		// createButtons();

		// Group group2 = new Group(prefShell, SWT.SHADOW_OUT);
		/**
		 * Group group3 = new Group(c, SWT.SHADOW_OUT); group3.setSize(c.getSize().x /
		 * 3, c.getSize().y / 3); group3.setText("Step 2: Insert your age ");
		 * group3.setLayout(new GridLayout(1, true)); Text textRow=new Text(group3,
		 * SWT.BORDER); textRow.setText(" ");
		 **/

		return c;

	}

	private String openFileExplorer() {
		Shell shellFE = new Shell(display);

		FileDialog fd = new FileDialog(shellFE, SWT.OPEN);
		fd.setText("Open");
		fd.setFilterPath("C:/");
		String[] filterExt = { "*.ods" };
		fd.setFilterExtensions(filterExt);
		String selected = fd.open();
		if (selected == null) {
			LOGGER.error("None file has been opened !");
			return null;
		}
		LOGGER.info("The file " + selected + " has been opened.");
		// if a file has been opened then we open the preferences shell
		prefShell();
		LOGGER.info("Shell for the courses preferences well opened");
		shellFE.dispose();
		return selected;
	}

	private String openFileExplorerTwo() {
		Shell shellFE = new Shell(display);

		FileDialog fd = new FileDialog(shellFE, SWT.OPEN);
		fd.setText("Open");
		fd.setFilterPath("C:/");
		String[] filterExt = { "*.ods" };
		fd.setFilterExtensions(filterExt);
		String selected = fd.open();
		if (selected == null) {
			LOGGER.error("None file has been opened !");
			return null;
		}
		LOGGER.info("The file " + selected + " has been opened.");
		// if a file has been opened then we open the preferences shell
		// prefShell();
		// this.fileName = selected;

		// LOGGER.info("Shell for the courses preferences well opened");
		shellFE.dispose();
		return selected;
	}

	@SuppressWarnings("resource")
	private java.util.List<Course> getCoursesFromFileExplorer() throws Exception {
		java.util.List<Course> courses = new ArrayList();
		Shell shellFE = new Shell(display);

		FileDialog fd = new FileDialog(shellFE, SWT.OPEN);
		fd.setText("Open");
		fd.setFilterPath("C:/");
		String[] filterExt = { "*.ods" };
		fd.setFilterExtensions(filterExt);
		String selected = fd.open();
		if (selected == null) {
			LOGGER.error("None file has been opened !");
			return null;
		}
		LOGGER.info("The file " + selected + " has been opened.");
		// if a file has been opened then we open the preferences shell
		prefShell();
		LOGGER.info("Shell for the courses preferences well opened");
		shellFE.dispose();

		FileInputStream fis = new FileInputStream(selected);
		ReadCourses rc = new ReadCourses(fis);
		return courses = rc.readCourses();
	}

	private String getTheChosenFile() {
		String fileName = openFileExplorer();
		return fileName;
	}

	private boolean exitShell() {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		messageBox.setMessage(
				"Voulez-vous vraiment quitter cette fenêtre ? Toutes les données non sauvegardées seront perdues !");
		messageBox.setText("Fermeture de la fenêtre");
		int response = messageBox.open();
		if (response == SWT.YES) {
			LOGGER.info("La fenêtre a bien été fermée.");
			return true;
		}
		return false;
	}

	private void exitApplication() {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		messageBox.setMessage("Voulez-vous vraiment quitter l'application ?");
		messageBox.setText("Fermeture de l'application");
		int response = messageBox.open();
		if (response == SWT.YES) {
			LOGGER.info("L'application a bien été fermée.");
			System.exit(0);
		}
	}

	public static void main(String[] args) throws IOException {
		CourseSheetMetadata csm1 = new CourseSheetMetadata();
		csm1.setYearOfStud("L3 MIAGE");
		csm1.setFirstSemesterNumber(1);

		CourseSheetMetadata csm2 = new CourseSheetMetadata();
		csm2.setYearOfStud("L1");
		csm2.setFirstSemesterNumber(3);

		java.util.List<CourseSheet> courses = new ArrayList();

		Course c1 = new Course("Test", "Test", "L3", "Ok", "Ok", 1);
		CoursePref cp1 = new CoursePref(c1);

		Course c2 = new Course("Test", "Test", "L3", "Ok", "Ok", 3);
		CoursePref cp = new CoursePref(c2);
		java.util.List<CoursePref> coursePrefS1 = new ArrayList();
		java.util.List<CoursePref> coursePrefS2 = new ArrayList();
		coursePrefS1.add(cp);

		java.util.List<String> yearStudy = new ArrayList();
		yearStudy.add(c1.getYearOfStud());
		yearStudy.add(c2.getYearOfStud());

		CourseSheet cs1 = new CourseSheet(csm1, coursePrefS1, coursePrefS2);
		CourseSheet cs2 = new CourseSheet(csm2, coursePrefS1, coursePrefS2);
		courses.add(cs1);
		courses.add(cs2);

		TeachSpreadSheetController teach = new TeachSpreadSheetController(null, null, courses);
		GUIPref gui = new GUIPref(teach);
		gui.initializeMainMenu();
		/*
		 * Display display = new Display(); Shell shell = new Shell(display, SWT.RESIZE
		 * | SWT.CLOSE | SWT.MIN); shell.open(); shell.setText("File Choice");
		 * 
		 * // initialize a grid layout manager GridLayout gridLayout = new GridLayout();
		 * gridLayout.numColumns = 2; shell.setLayout(gridLayout);
		 * 
		 * // create the label and the field text Label labelTitle = new Label(shell,
		 * SWT.NONE); labelTitle.setText("File to load : "); labelTitle.setSize(100,
		 * 25); Text textTitle = new Text(shell, SWT.BORDER);
		 * textTitle.setText("Saisie_Voeux_Dauphine.ods"); Button buttonSubmit = new
		 * Button(shell, SWT.PUSH); buttonSubmit.setText("Submit");
		 * buttonSubmit.setSize(100, 25); buttonSubmit.addListener(SWT.Selection, new
		 * Listener() {
		 * 
		 * @Override public void handleEvent(Event e) { switch (e.type) { case
		 * SWT.Selection: String selection = textTitle.getText(); Shell shell2 = new
		 * Shell(display, SWT.RESIZE | SWT.CLOSE | SWT.MIN); shell2.open();
		 * 
		 * while (!shell2.isDisposed()) { // if (!display.readAndDispatch())
		 * display.sleep(); } break; default: break; } } });
		 */
	}

}