package io.github.oliviercailloux.y2018.teach_spreadsheets.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Choice;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CoursePref;

/*
 * INFO: If layout() is not called and changes are made after the shell is opened, then the children may not be correctly laid out until the shell is somehow resized
 * When you modify a shell child, you need to use shell.layout(), so the modification can be effective.
 * 
 */

/**
 * This class is a Graphic User Interface allowing the user to import a file (in
 * which there are courses) and to set preferences on these courses. He can
 * choose a specified year of study, then a semester, then a specified course.
 * 
 * @author Victor CHEN (Kantoki)
 * @version 2.0
 * 
 */
public class GUIPref {
	private final static Logger LOGGER = LoggerFactory.getLogger(GUIPref.class);

	private Display display;
	// This shell is the main one
	private Shell shell;
	// This shell is used for the preferences inputs
	private Shell prefShell;

	private TeachSpreadSheetController teach;

	private Composite preferenceContent = null;
	private Composite summaryContent = null;

	// Composite in preferenceContent
	private Composite compositeYearsStudy;
	private Composite compositeSemesters;
	private Composite compositeCourses;
	private Composite compositeChoices;
	private Composite compositeSubmit;

	// Composite in summaryContent
	private Composite compositeSummary;

	private Group groupCMButtons;
	private Group groupTDButtons;
	private Group groupTPButtons;

	private String selectedYearStudy = "";
	private Integer selectedSemester;
	private String selectedCourse = "";
	private Choice selectedCMCHoice = Choice.NA;
	private Choice selectedTDCHoice = Choice.NA;
	private Choice selectedTPCHoice = Choice.NA;

	private static int currentStep = 1;

	public GUIPref(TeachSpreadSheetController teach) {
		this.teach = teach;
	}

	/**
	 * Set to null these composites
	 */
	private void resetComposite() {
		compositeSemesters.dispose();
		compositeChoices.dispose();
		compositeCourses.dispose();
		compositeYearsStudy.dispose();
		compositeSummary.dispose();
		this.preferenceContent.pack();
	}

	/**
	 * Reset the selected items in the pref shell
	 */
	private void resetSelectedItems() {
		selectedYearStudy = "";
		selectedSemester = 0;
		selectedCourse = "";
		selectedCMCHoice = Choice.NA;
		selectedTDCHoice = Choice.NA;
		selectedTPCHoice = Choice.NA;
		currentStep = 1;
	}

	/**
	 * This method is the main interface (display). This is the first shell where
	 * the user starts
	 */
	public void initializeMainMenu() throws IOException {

		String logoFileName = "logoGUI.png";
		String iconFileName = "iconGUI.png";

		LOGGER.info("Image-Logo bien récupérée");

		display = new Display();
		shell = new Shell(display, SWT.CLOSE);

		shell.setText("Teach-spreadsheets");
		shell.setLayout(new GridLayout(1, false));

		setShellIcon(iconFileName);

		// Center the shell

		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();

		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;

		shell.setLocation(x, y);

		// Display an image
		Label labelImg = new Label(shell, SWT.NONE);
		Rectangle clientArea = shell.getClientArea();
		labelImg.setLocation(clientArea.x, clientArea.y);

		try (InputStream inputStream = GUIPref.class.getResourceAsStream(logoFileName)) {
			if (inputStream == null) {
				LOGGER.error("File " + logoFileName + " not found.");
				throw new FileNotFoundException("File not found");
			}
			Image image = new Image(display, inputStream);

			labelImg.setImage(image);
			labelImg.pack();
		}
		// Create a horizontal separator
		Label lblSeparator = new Label(shell, SWT.HORIZONTAL | SWT.SEPARATOR);
		lblSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Label with teacher name
		Label lblCentered = new Label(shell, SWT.NONE);
		lblCentered.setText("Welcome " + this.teach.getTeacherName());
		lblCentered.setLayoutData(new GridData(SWT.NONE, SWT.FILL, false, false));

		// Create a horizontal separator
		lblSeparator = new Label(shell, SWT.HORIZONTAL | SWT.SEPARATOR);
		lblSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Button to open the file explorer so that the user chooses the
		// file in which
		// there are courses
		Button buttonFileExplorer = new Button(shell, SWT.NONE);
		buttonFileExplorer.setText("Open spreadsheet");
		buttonFileExplorer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		buttonFileExplorer.addListener(SWT.Selection, event -> {
			String fileName = openFileExplorer();

			FileInputStream fis;
			if (!(fileName == null)) {
				// we must use a Try/Catch because we can't throw on the method
				try {
					fis = new FileInputStream(fileName);
					teach.setSource(fis);
					prefShell();
				} catch (Exception e1) {
					LOGGER.error("File not opened");
					throw new IllegalStateException(e1);
				}
			}
		});

		// Button allowing the user to quit the application (it closes the
		// display)
		Button buttonExit;
		buttonExit = new Button(shell, SWT.NONE);
		buttonExit.setText("Exit");
		buttonExit.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		buttonExit.addListener(SWT.Selection, event -> exitApplication());

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();
		LOGGER.info("Display well closed");

	}

	/**
	 * This method allows to set an icon to a shell from a classpath resource
	 */
	private void setShellIcon(String resource) {
		Image img = new Image(display, GUIPref.class.getResourceAsStream(resource));
		shell.setImage(img);
	}

	/**
	 * This methods opens a new shell in order to let the user sets his preferences
	 * for a specified course
	 */
	private void prefShell() {

		// Doesn't allow the user to close the main shell when the preferences shell is
		// open (SYSTEM_MODAL)
		prefShell = new Shell(display, SWT.SYSTEM_MODAL | SWT.SHELL_TRIM);
		prefShell.setMaximized(true);
		prefShell.setText("Preferences");
		prefShell.setLayout(new GridLayout(2, false));

		this.preferenceContent = new Composite(prefShell, SWT.BORDER);
		preferenceContent.setLayout(new GridLayout(1, true));
		this.summaryContent = new Composite(prefShell, SWT.BORDER);
		summaryContent.setLayout(new GridLayout(1, true));

		// proportion of pref and summary

		GridData preferenceData = new GridData(SWT.FILL, SWT.FILL, true, true);
		GridData summaryData = new GridData(SWT.FILL, SWT.FILL, true, true);
		Point size = prefShell.getSize();
		preferenceData.widthHint = (int) (size.x * 0.55);
		summaryData.widthHint = size.x - preferenceData.widthHint;
		this.preferenceContent.setLayoutData(preferenceData);
		this.summaryContent.setLayoutData(summaryData);

		Image logoPref = new Image(display, GUIPref.class.getResourceAsStream("logo-pref.png"));
		prefShell.setImage(logoPref);

		// HEADER for preferenceContent
		Composite header = new Composite(this.preferenceContent, SWT.NONE);
		header.setLayout(new GridLayout(2, false));
		Label labelImg = new Label(header, SWT.LEFT);
		labelImg.setImage(logoPref);
		Label txt = new Label(header, SWT.RIGHT);

		txt.setText("My preferences - Teach-spreadsheets");
		header.pack();

		// HEADER for summaryContent
		Composite header2 = new Composite(this.summaryContent, SWT.NONE);
		header2.setLayout(new GridLayout(2, false));

		Label labelImg2 = new Label(header2, SWT.LEFT);
		Image logoSummary = new Image(display, GUIPref.class.getResourceAsStream("logo-summary.png"));
		labelImg2.setImage(logoSummary);
		Label txt2 = new Label(header2, SWT.RIGHT);
		txt2.setText("Dashboard");

		header2.pack();

		// Create a menu
		Menu menu = new Menu(prefShell, SWT.BAR);
		// create a file menu and add an exit item
		final MenuItem file = new MenuItem(menu, SWT.CASCADE);
		file.setText("&Menu");
		final Menu fileMenu = new Menu(prefShell, SWT.DROP_DOWN);
		file.setMenu(fileMenu);
		final MenuItem exportItem = new MenuItem(fileMenu, SWT.PUSH);
		exportItem.setText("&Export your prefs");
		exportItem.addListener(SWT.Selection, event -> exportAllPreferences());

		final MenuItem openNewFileItem = new MenuItem(fileMenu, SWT.PUSH);
		openNewFileItem.setText("&Open new file courses");
		openNewFileItem.addListener(SWT.Selection, event -> {
			String pathFile = openFileExplorer();
			if (pathFile != null) {
				prefShell.dispose();
				currentStep = 1;
			}
		});

		final MenuItem closeShellItem = new MenuItem(fileMenu, SWT.PUSH);
		closeShellItem.setText("Close this display");
		closeShellItem.addListener(SWT.Selection, event -> {
			boolean closing = exitShell();
			if (closing) {
				prefShell.dispose();
				currentStep = 1;
			}
		});

		final MenuItem menuSeparator = new MenuItem(fileMenu, SWT.SEPARATOR);
		final MenuItem exitItem = new MenuItem(fileMenu, SWT.NONE);
		exitItem.setText("E&xit the application");
		exitItem.addListener(SWT.Selection, event -> exitApplication());

		prefShell.setMenuBar(menu);

		// Display a group containing a list of the years of study
		compositeYearsStudy = createGroupYearsOfStudy();
		compositeSummary = createCompositeSummary();

		summaryContent.layout();
		preferenceContent.layout();
		prefShell.pack();
		prefShell.open();
		LOGGER.info("Shell for the preferences well opened");

		prefShell.addListener(SWT.Close, event -> {
			LOGGER.info("Shell for the courses preferences well closed");
			prefShell.dispose();
			currentStep = 1;
		});

	}

	/**
	 * This methods creates a Group in which there is a list of Years of Study from
	 * the file opened
	 */
	private Composite createGroupYearsOfStudy() {
		Composite c = new Composite(this.preferenceContent, SWT.NONE);
		c.setLayout(new GridLayout(2, false));

		Group groupYearOfStudy = new Group(c, SWT.NONE);

		java.util.List<String> yearNames = teach.getYearNames();

		groupYearOfStudy.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		groupYearOfStudy.setText("Step " + currentStep++ + " : Choose the year of study");

		groupYearOfStudy.setLayout(new GridLayout(1, false));

		final List listYearStudy = new List(groupYearOfStudy, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);

		// List size
		GridData gridDataList = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridDataList = new GridData();
		gridDataList.widthHint = 400;
		gridDataList.heightHint = 70;
		listYearStudy.setLayoutData(gridDataList);

		for (String string : yearNames) {
			listYearStudy.add(string);
		}

		listYearStudy.addListener(SWT.Selection, event -> {
			String s[] = listYearStudy.getSelection();
			String outString = s[0];
			String actualYearOfStudy = selectedYearStudy;
			selectedYearStudy = outString;
			LOGGER.info("Year of study " + selectedYearStudy + " well chosen.");

			if (compositeSemesters != null) {
				compositeSemesters.dispose();
				if (compositeCourses != null) {
					compositeCourses.dispose();
					if (compositeChoices != null) {
						compositeChoices.dispose();
						if (compositeSubmit != null) {
							compositeSubmit.dispose();
						}
					}
				}
			}
			currentStep = 2;
			compositeSemesters = createCompositeSemesters();

			preferenceContent.layout();
			prefShell.pack();
			prefShell.open();
		});

		return c;
	}

	/**
	 * This methods creates a Composite in which there are 2 buttons for each
	 * semester available
	 */
	private Composite createCompositeSemesters() {
		Composite c = new Composite(preferenceContent, SWT.NONE);
		GridLayout gl = new GridLayout(2, false);
		c.setLayout(gl);

		Group group2 = new Group(c, SWT.SHADOW_OUT);
		group2.setText("Step " + currentStep++ + " : Choose the semester");
		group2.setLayout(new GridLayout(1, true));

		// store in a list of integer all the semester available for the selected year
		// of study
		java.util.List<Integer> listSemester = teach.getSemesters(selectedYearStudy);
		int firstSemester = 0;
		int secondSemester = 0;

		firstSemester = listSemester.get(0);
		secondSemester = listSemester.get(1);

		// Button for the first semester
		final Button button1 = new Button(group2, SWT.RADIO);
		button1.setText("Semestre " + String.valueOf(firstSemester));
		// Button for the second semester
		final Button button2 = new Button(group2, SWT.RADIO);
		button2.setText("Semestre " + String.valueOf(secondSemester));

		// listener on both buttons
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				int user_choice = 0;

				if (event.widget == button1) {
					String fistSemester = button1.getText().split(" ")[1];
					user_choice = Integer.valueOf(fistSemester);
					selectedSemester = user_choice;
					LOGGER.info(button1.getText() + " well chosen");
				} else if (event.widget == button2) {
					String secondSemester = button2.getText().split(" ")[1];
					user_choice = Integer.valueOf(secondSemester);
					selectedSemester = user_choice;
					LOGGER.info(button2.getText() + " well chosen");
				}
				selectedSemester = user_choice;

				if (compositeCourses != null) {
					compositeCourses.dispose();
					if (compositeChoices != null) {
						compositeChoices.dispose();
						if (compositeSubmit != null) {
							compositeSubmit.dispose();
						}
					}
				}
				currentStep = 3;
				compositeCourses = createCompositeCourses();
				preferenceContent.layout();
				prefShell.pack();
				prefShell.open();
			}
		};

		button1.addListener(SWT.Selection, listener);
		button2.addListener(SWT.Selection, listener);

		return c;
	}

	/**
	 * This methods creates a Composite in which there is a list of Courses
	 */
	private Composite createCompositeCourses() {
		Composite c = new Composite(this.preferenceContent, SWT.NONE);
		GridLayout gl = new GridLayout(2, false);
		c.setLayout(gl);

		Group groupCourses = new Group(c, SWT.NONE);

		// retrieve every courses from the selected year of study and a given semester
		// (selected semester)
		java.util.List<String> courseNames = teach.getCoursesName(selectedYearStudy, selectedSemester);

		groupCourses.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		groupCourses.setText("Step " + currentStep++ + " : Choose the course");
		groupCourses.setLayout(new GridLayout(1, true));

		final List listCourses = new List(groupCourses, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);

		// display every course available
		for (String string : courseNames) {
			listCourses.add(string);
		}

		GridData gridDataList = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridDataList = new GridData();
		gridDataList.heightHint = 70;
		listCourses.setLayoutData(gridDataList);

		listCourses.addListener(SWT.Selection, event -> {
			String actualCourse = selectedCourse;
			String s[] = listCourses.getSelection();
			String outString = s[0];
			selectedCourse = outString;

			LOGGER.info("Course " + selectedCourse + " well chosen.");
			if (!actualCourse.equals(selectedCourse)) {

				java.util.List<String> listPossibleChoice = teach.getPossibleChoice(selectedYearStudy, selectedSemester,
						selectedCourse);

				if (compositeChoices != null) {
					compositeChoices.dispose();
					if (compositeSubmit != null) {
						compositeSubmit.dispose();
					}
				}
				compositeChoices = createCompositeForChoices();

				currentStep = 4;
				for (String possibleChoice : listPossibleChoice) {
					if (possibleChoice.equals("CM")) {
						groupCMButtons = createGroupButtonsCM();
					}
					if (possibleChoice.equals("TD")) {
						groupTDButtons = createGroupButtonsTD();
					}
					if (possibleChoice.equals("TP")) {
						groupTPButtons = createGroupButtonsTP();
					}
				}
				compositeSubmit = createButtonSubmitPreference();
			}
			preferenceContent.layout();
			prefShell.pack();
			prefShell.open();
		});

		return c;
	}

	/**
	 * This method creates a Composite in which we store the 3 groups for the
	 * Choices (CM, TD, TP)
	 */
	private Composite createCompositeForChoices() {
		compositeChoices = new Composite(this.preferenceContent, SWT.NONE);
		GridLayout gl = new GridLayout(2, true);
		compositeChoices.setLayout(gl);
		return compositeChoices;
	}

	/**
	 * This method creates a group of buttons for the CM choices
	 */
	private Group createGroupButtonsCM() {
		Group groupCM = new Group(compositeChoices, SWT.NONE);
		groupCM.setText("Step " + currentStep++ + " : Choose your preferences for CM");
		groupCM.setLayout(new GridLayout(1, true));

		Choice choiceA = Choice.A;
		Choice choiceB = Choice.B;
		Choice choiceC = Choice.C;
		Choice choiceAbs = Choice.ABSENT;

		final Button buttonA = new Button(groupCM, SWT.RADIO);
		buttonA.setText(choiceA.toString());
		final Button buttonB = new Button(groupCM, SWT.RADIO);
		buttonB.setText(choiceB.toString());
		final Button buttonC = new Button(groupCM, SWT.RADIO);
		buttonC.setText(choiceC.toString());
		final Button buttonAbs = new Button(groupCM, SWT.RADIO);
		buttonAbs.setText(choiceAbs.toString());

		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				Choice CMChoice = Choice.NA;

				if (event.widget == buttonA) {
					CMChoice = choiceA;
					selectedCMCHoice = CMChoice;
				} else if (event.widget == buttonB) {
					CMChoice = choiceB;
					selectedCMCHoice = CMChoice;
				}
				if (event.widget == buttonC) {
					CMChoice = choiceC;
					selectedCMCHoice = CMChoice;
				} else if (event.widget == buttonAbs) {
					CMChoice = choiceAbs;
					selectedCMCHoice = CMChoice;
				}

				selectedCMCHoice = CMChoice;
				LOGGER.info("You selected the choice " + selectedCMCHoice + " for the CM.");

			}

		};
		buttonA.addListener(SWT.Selection, listener);
		buttonB.addListener(SWT.Selection, listener);
		buttonC.addListener(SWT.Selection, listener);
		buttonAbs.addListener(SWT.Selection, listener);

		return groupCM;
	}

	/**
	 * This method creates a group of buttons for the TD choices
	 */
	private Group createGroupButtonsTD() {
		Group groupTD = new Group(compositeChoices, SWT.NONE);
		groupTD.setText("Step " + currentStep++ + " : Choose your preferences for TD");
		groupTD.setLayout(new GridLayout(1, true));

		Choice choiceA = Choice.A;
		Choice choiceB = Choice.B;
		Choice choiceC = Choice.C;
		Choice choiceAbs = Choice.ABSENT;

		final Button buttonA = new Button(groupTD, SWT.RADIO);
		buttonA.setText(choiceA.toString());
		final Button buttonB = new Button(groupTD, SWT.RADIO);
		buttonB.setText(choiceB.toString());
		final Button buttonC = new Button(groupTD, SWT.RADIO);
		buttonC.setText(choiceC.toString());
		final Button buttonAbs = new Button(groupTD, SWT.RADIO);
		buttonAbs.setText(choiceAbs.toString());

		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				Choice TDChoice = Choice.NA;

				if (event.widget == buttonA) {
					TDChoice = choiceA;
					selectedTDCHoice = TDChoice;
				} else if (event.widget == buttonB) {
					TDChoice = choiceB;
					selectedTDCHoice = TDChoice;
				}
				if (event.widget == buttonC) {
					TDChoice = choiceC;
					selectedTDCHoice = TDChoice;
				} else if (event.widget == buttonAbs) {
					TDChoice = choiceAbs;
					selectedTDCHoice = TDChoice;
				}

				selectedTDCHoice = TDChoice;
				LOGGER.info("You selected the choice " + selectedTDCHoice + " for the TD.");

			}
		};
		buttonA.addListener(SWT.Selection, listener);
		buttonB.addListener(SWT.Selection, listener);
		buttonC.addListener(SWT.Selection, listener);
		buttonAbs.addListener(SWT.Selection, listener);

		return groupTD;
	}

	/**
	 * This method creates a group of buttons for the TP choices
	 */
	private Group createGroupButtonsTP() {
		Group groupTP = new Group(compositeChoices, SWT.NONE);
		groupTP.setText("Step " + currentStep++ + " : Choose your preferences for TP");
		groupTP.setLayout(new GridLayout(1, true));

		Choice choiceA = Choice.A;
		Choice choiceB = Choice.B;
		Choice choiceC = Choice.C;
		Choice choiceAbs = Choice.ABSENT;

		final Button buttonA = new Button(groupTP, SWT.RADIO);
		buttonA.setText(choiceA.toString());
		final Button buttonB = new Button(groupTP, SWT.RADIO);
		buttonB.setText(choiceB.toString());
		final Button buttonC = new Button(groupTP, SWT.RADIO);
		buttonC.setText(choiceC.toString());
		final Button buttonAbs = new Button(groupTP, SWT.RADIO);
		buttonAbs.setText(choiceAbs.toString());

		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				Choice TPChoice = Choice.NA;

				if (event.widget == buttonA) {
					TPChoice = choiceA;
					selectedTPCHoice = TPChoice;
				} else if (event.widget == buttonB) {
					TPChoice = choiceB;
					selectedTPCHoice = TPChoice;
				}
				if (event.widget == buttonC) {
					TPChoice = choiceC;
					selectedTPCHoice = TPChoice;
				} else if (event.widget == buttonAbs) {
					TPChoice = choiceAbs;
					selectedTPCHoice = TPChoice;
				}

				selectedTPCHoice = TPChoice;
				LOGGER.info("You selected the choice " + selectedTPCHoice + " for the TP.");

			}
		};
		buttonA.addListener(SWT.Selection, listener);
		buttonB.addListener(SWT.Selection, listener);
		buttonC.addListener(SWT.Selection, listener);
		buttonAbs.addListener(SWT.Selection, listener);

		return groupTP;
	}

	/**
	 * This methods open the file explorer
	 */
	private String openFileExplorer() {
		Shell shellFE = new Shell(display);

		FileDialog fd = new FileDialog(shellFE, SWT.OPEN);
		fd.setText("Open");
		// only file finishing by .ods are allowed
		String[] filterExt = { "*.ods" };
		fd.setFilterExtensions(filterExt);
		String selectedFile = fd.open();
		if (selectedFile == null) {
			LOGGER.error("None file has been opened !");
			return null;
		}
		LOGGER.info("The file " + selectedFile + " has been opened.");

		shellFE.dispose();
		return selectedFile;
	}

	/**
	 * This method opens the directory explorer
	 */
	private String openDirectoryExplorer() {
		DirectoryDialog dlg = new DirectoryDialog(prefShell);
		dlg.setText("Open");
		String selectedDir = dlg.open();
		if (selectedDir == null) {
			LOGGER.error("None folder has been opened !");
			return null;
		}
		LOGGER.info("The folder " + selectedDir + " has been opened.");
		return selectedDir;
	}

	/**
	 * This method closes a Shell if the user confirms it (by pressing YES button)
	 */
	private boolean exitShell() {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		messageBox.setMessage("Do you really want to leave this window? All unsaved data will be lost!");
		messageBox.setText("Closing the window");
		int response = messageBox.open();
		if (response == SWT.YES) {
			LOGGER.info("The window has been closed.");
			return true;
		}
		return false;
	}

	/**
	 * This method closes the display.
	 */
	private void exitApplication() {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		messageBox.setMessage("Do you really want to quit the application?");
		messageBox.setText("Closing the application");
		int response = messageBox.open();
		if (response == SWT.YES) {
			LOGGER.info("The application has been closed.");
			System.exit(0);
		}
	}

	/**
	 * Create a button that allows the user to submit his preferences for a Course
	 */
	private Composite createButtonSubmitPreference() {
		Composite c = new Composite(this.preferenceContent, SWT.NONE);
		GridLayout gl = new GridLayout(1, true);
		c.setLayout(gl);

		// Create a horizontal separator
		Label lblSeparator = new Label(c, SWT.HORIZONTAL | SWT.SEPARATOR);
		lblSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Button to submit the preferences for a specified course
		Button buttonSubmit;
		buttonSubmit = new Button(c, SWT.NONE);
		buttonSubmit.setText("Submit");
		buttonSubmit.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		buttonSubmit.addListener(SWT.Selection, event -> {
			CoursePref cp = submitPreference(selectedYearStudy, selectedSemester, selectedCourse, selectedCMCHoice,
					selectedTDCHoice, selectedTPCHoice);
			teach.updatePref(cp);

			// Reset the shell and shows only the list of year of study
			resetComposite();
			buttonSubmit.dispose();
			resetSelectedItems();

			compositeYearsStudy = createGroupYearsOfStudy();
			compositeSummary = createCompositeSummary();

			preferenceContent.layout();
			summaryContent.layout();
			prefShell.pack();
			prefShell.open();
		});

		return c;
	}

	/**
	 * This methods creates a Composite in which there is a list of Courses
	 */
	private Composite createCompositeSummary() {
		Composite c = new Composite(this.summaryContent, SWT.NONE);
		c.setLayout(new GridLayout(1, false));

		Table t = new Table(c, SWT.BORDER);

		TableColumn tYear = new TableColumn(t, SWT.NONE);
		TableColumn tSemester = new TableColumn(t, SWT.NONE);
		TableColumn tCourse = new TableColumn(t, SWT.NONE);
		TableColumn tCM = new TableColumn(t, SWT.NONE);
		TableColumn tTD = new TableColumn(t, SWT.NONE);
		TableColumn tTP = new TableColumn(t, SWT.NONE);
		tYear.setText("Year");
		tSemester.setText("Semester");
		tCourse.setText("Course");
		tCM.setText("CM");
		tTD.setText("TD");
		tTP.setText("TP");

		tYear.setWidth(70);
		tSemester.setWidth(70);
		tCourse.setWidth(70);
		tCM.setWidth(70);
		tTD.setWidth(70);
		tTP.setWidth(70);

		t.setHeaderVisible(true);

		java.util.List<CoursePref> prefSummary = teach.getPrefSummary();

		for (CoursePref coursePref : prefSummary) {
			TableItem item = new TableItem(t, SWT.NONE);
			item.setText(new String[] { coursePref.getCourse().getYearOfStud(),
					String.valueOf(coursePref.getCourse().getSemester()), coursePref.getCourse().getName(),
					coursePref.getCmChoice().toString(), coursePref.getTdChoice().toString(),
					coursePref.getTpChoice().toString() });
		}
		return c;
	}

	/**
	 * This methods creates a new Course Preference object using all the selected
	 * elements from the user
	 */
	@SuppressWarnings("hiding")
	private CoursePref submitPreference(String selectedYearOfStudy, Integer selectedSemester, String selectedCourse,
			Choice selectedCMChoice, Choice selectedTDChoice, Choice selectedTPChoice) {
		Course c = new Course(selectedCourse, "", selectedYearOfStudy, "", "", selectedSemester);
		CoursePref cp = new CoursePref(c);
		cp.setCmChoice(selectedCMChoice);
		cp.setTdChoice(selectedTDChoice);
		cp.setTpChoice(selectedTPChoice);

		return cp;
	}

	/**
	 * Export the preferences and write in the ods file containing the courses with
	 * their preferences
	 */
	@SuppressWarnings("resource")
	private void exportAllPreferences() {
		boolean failed = true;

		while (failed) {
			failed = false;
			String destination = openDirectoryExplorer();
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			Date date = new Date();
			if (!(destination == null)) {
				destination = destination + "\\Saisie_de_Voeux_" + teach.getTeacherName() + "_"
						+ dateFormat.format(date).toString() + ".ods";
				try {
					teach.setDestination(new FileOutputStream(destination));
					prefShell.dispose();
					currentStep = 1;
				} catch (@SuppressWarnings("unused") FileNotFoundException e1) {
					failed = true;
				} catch (@SuppressWarnings("unused") Exception e1) {
					failed = false;
				}
			}

		}
	}
}