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
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
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
	private Shell shell;
	private Shell prefShell;

	private CTabFolder folder;
	private CTabItem item1;

	private TeachSpreadSheetController teach;

	private Composite compositeYearsStudy;
	private Composite compositeSemesters;
	private Composite compositeCourses;
	private Composite compositeChoices;
	private Composite compositeSubmit;

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

	private void resetComposite() {
		compositeSemesters.dispose();
		compositeChoices.dispose();
		compositeCourses.dispose();
		compositeYearsStudy.dispose();
		prefShell.pack();
	}

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
	 * This methods is the main interface (display). This is the first shell where
	 * the user starts
	 */
	public void initializeMainMenu() throws IOException {

		String logoFileName = "logoGUI.png";

		try (InputStream inputStream = GUIPref.class.getResourceAsStream(logoFileName)) {
			if (inputStream == null) {
				LOGGER.error("File " + logoFileName + " not found.");
				throw new FileNotFoundException("File not found");
			}

			LOGGER.info("Image-Logo bien récupérée");

			display = new Display();
			shell = new Shell(display, SWT.CLOSE);

			folder = new CTabFolder(shell, SWT.BORDER);
			item1 = new CTabItem(folder, SWT.CLOSE);

			shell.setText("Menu principal - Teach-spreadsheets");
			shell.setLayout(new GridLayout(1, false));
			shell.setSize(500, 700);
			shell.setImage(new Image(display, GUIPref.class.getResourceAsStream("iconGUI.png")));
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
			lblCentered.setText("Bienvenue " + this.teach.getTeacherName());
			lblCentered.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false));

			// Create a horizontal separator
			lblSeparator = new Label(shell, SWT.HORIZONTAL | SWT.SEPARATOR);
			lblSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			// Button to open the file explorer so that the user chooses the
			// file in which
			// there are courses
			Button buttonFileExplorer = new Button(shell, SWT.NONE);
			buttonFileExplorer.setText("Ouvrez votre fichier contenant tous les cours");
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

			// Button allowing the user to quit the application (it closes the display)
			Button buttonExit;
			buttonExit = new Button(shell, SWT.NONE);
			buttonExit.setText("Quitter l'application");
			buttonExit.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			buttonExit.addListener(SWT.Selection, event -> exitApplication());

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
	 * This methods opens a new shell in order to let the user sets his preferences
	 * for a specified course
	 */
	private void prefShell() {

		// Doesn't allow the user to close the main shell when the preferences
		// shell is
		// open
		prefShell = new Shell(display, SWT.SYSTEM_MODAL | SWT.SHELL_TRIM);
		prefShell.setMinimumSize(200, 200);
		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		prefShell.setLayout(gl);

		InputStream logoStream = GUIPref.class.getResourceAsStream("logo-pref.png");
		Image logo = new Image(display, logoStream);
		prefShell.setImage(logo);
		// prefShell.setLayout(new GridLayout(2, false));

		// HEADER
		Composite header = new Composite(prefShell, SWT.CENTER);
		header.setLayout(new GridLayout(2, false));
		Label labelImg = new Label(header, SWT.LEFT);
		labelImg.setImage(logo);
		Label txt = new Label(header, SWT.RIGHT);
		txt.setText("Mes préférences - Teach-spreadsheets");
		header.pack();
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
		exportItem.addListener(SWT.Selection, event -> exportAllPreferences());

		// method
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

		// ============================
		// Create a Label in the Shell
		// ============================
		// Label topLabel = new Label(prefShell, SWT.NONE);
		// topLabel.setText("Insérez vos préférences");
		// topLabel.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false,
		// false));
		//
		// // Create a horizontal separator
		// Label lblSeparator;
		// lblSeparator = new Label(prefShell, SWT.HORIZONTAL | SWT.SEPARATOR);
		// lblSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		compositeYearsStudy = createGroupYearsOfStudy();
		prefShell.layout();
		prefShell.pack();
		prefShell.open();
		LOGGER.info("Shell for the preferences well opened");

		// =============================================================
		// Register a listener for the Close event on the child Shell.
		// This disposes the child Shell
		// =============================================================

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
		Composite c = new Composite(prefShell, SWT.CENTER);
		c.setLayout(new GridLayout(2, false));

		Group groupYearOfStudy = new Group(c, SWT.CENTER);

		java.util.List<String> yearNames = teach.getYearNames();

		groupYearOfStudy.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		groupYearOfStudy.setText("Step " + currentStep++ + " : Choose the year of study");

		groupYearOfStudy.setLayout(new GridLayout(1, false));

		final List listYearStudy = new List(groupYearOfStudy, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		listYearStudy.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		for (String string : yearNames) {
			listYearStudy.add(string);
		}

		final Text text = new Text(c, SWT.BORDER | SWT.H_SCROLL);
		text.setBounds(60, 130, 160, 25);

		listYearStudy.addListener(SWT.Selection, event -> {
			String s[] = listYearStudy.getSelection();
			String outString = s[0];
			String actualYearOfStudy = selectedYearStudy;
			text.setText("Selected year of study : " + outString);
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

			prefShell.layout();
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
		Composite c = new Composite(prefShell, SWT.CENTER);
		GridLayout gl = new GridLayout(2, false);
		c.setLayout(gl);

		Group group2 = new Group(c, SWT.SHADOW_OUT);
		group2.setText("Step " + currentStep++ + " : Choose the semester");
		group2.setLayout(new GridLayout(1, true));

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
				prefShell.layout();
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
		Composite c = new Composite(prefShell, SWT.CENTER);
		GridLayout gl = new GridLayout(2, false);
		c.setLayout(gl);

		Group groupCourses = new Group(c, SWT.CENTER);

		java.util.List<String> courseNames = teach.getCoursesName(selectedYearStudy, selectedSemester);

		groupCourses.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		groupCourses.setText("Step " + currentStep++ + " : Choose the course");
		groupCourses.setLayout(new GridLayout(1, true));

		final List listCourses = new List(groupCourses, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);

		for (String string : courseNames) {
			listCourses.add(string);
		}

		GridData gridDataList = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridDataList = new GridData();
		gridDataList.widthHint = 400;
		// 8 items displayed then need to scroll
		gridDataList.heightHint = 140;
		listCourses.setLayoutData(gridDataList);

		final Text text = new Text(c, SWT.BORDER | SWT.H_SCROLL);
		text.setBounds(60, 130, 160, 25);

		GridData gridDataText = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridDataText = new GridData();
		gridDataText.widthHint = 400;
		gridDataText.heightHint = 35;
		text.setLayoutData(gridDataText);

		listCourses.addListener(SWT.Selection, event -> {
			String actualCourse = selectedCourse;
			String s[] = listCourses.getSelection();
			String outString = s[0];
			String actuelSelectedCourse = selectedCourse;
			text.setText("Selected course : " + outString);
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
			prefShell.layout();
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
		compositeChoices = new Composite(prefShell, SWT.CENTER);
		GridLayout gl = new GridLayout(3, true);
		compositeChoices.setLayout(gl);
		return compositeChoices;
	}

	/**
	 * This method creates a group of buttons for the CM choices
	 */
	private Group createGroupButtonsCM() {
		Group group2 = new Group(compositeChoices, SWT.NONE);
		group2.setText("Step " + currentStep++ + " : Choose your preferences for CM");
		group2.setLayout(new GridLayout(1, true));

		Choice choiceA = Choice.A;
		Choice choiceB = Choice.B;
		Choice choiceC = Choice.C;
		Choice choiceAbs = Choice.ABSENT;

		final Button buttonA = new Button(group2, SWT.RADIO);
		buttonA.setText(choiceA.toString());
		final Button buttonB = new Button(group2, SWT.RADIO);
		buttonB.setText(choiceB.toString());
		final Button buttonC = new Button(group2, SWT.RADIO);
		buttonC.setText(choiceC.toString());
		final Button buttonAbs = new Button(group2, SWT.RADIO);
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

		return group2;
	}

	/**
	 * This method creates a group of buttons for the TD choices
	 */
	private Group createGroupButtonsTD() {
		Group group2 = new Group(compositeChoices, SWT.NONE);
		group2.setText("Step " + currentStep++ + " : Choose your preferences for TD");
		group2.setLayout(new GridLayout(1, true));

		Choice choiceA = Choice.A;
		Choice choiceB = Choice.B;
		Choice choiceC = Choice.C;
		Choice choiceAbs = Choice.ABSENT;

		final Button buttonA = new Button(group2, SWT.RADIO);
		buttonA.setText(choiceA.toString());
		final Button buttonB = new Button(group2, SWT.RADIO);
		buttonB.setText(choiceB.toString());
		final Button buttonC = new Button(group2, SWT.RADIO);
		buttonC.setText(choiceC.toString());
		final Button buttonAbs = new Button(group2, SWT.RADIO);
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

		return group2;
		// return c;
	}

	/**
	 * This method creates a group of buttons for the TP choices
	 */
	private Group createGroupButtonsTP() {
		Group group2 = new Group(compositeChoices, SWT.NONE);
		group2.setText("Step " + currentStep++ + " : Choose your preferences for TP");
		group2.setLayout(new GridLayout(1, true));

		Choice choiceA = Choice.A;
		Choice choiceB = Choice.B;
		Choice choiceC = Choice.C;
		Choice choiceAbs = Choice.ABSENT;

		final Button buttonA = new Button(group2, SWT.RADIO);
		buttonA.setText(choiceA.toString());
		final Button buttonB = new Button(group2, SWT.RADIO);
		buttonB.setText(choiceB.toString());
		final Button buttonC = new Button(group2, SWT.RADIO);
		buttonC.setText(choiceC.toString());
		final Button buttonAbs = new Button(group2, SWT.RADIO);
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

		return group2;
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
		// dlg.setFilterPath("C:/");
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
	 * This methods closes the display.
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

	private Composite createButtonSubmitPreference() {
		Composite c = new Composite(prefShell, SWT.CENTER);
		GridLayout gl = new GridLayout(1, true);
		c.setLayout(gl);

		// Create a horizontal separator
		Label lblSeparator = new Label(c, SWT.HORIZONTAL | SWT.SEPARATOR);
		lblSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Button to submit the preferences for a specified course
		Button buttonSubmit;
		buttonSubmit = new Button(c, SWT.NONE);
		buttonSubmit.setText("Submit your preferences for the course : " + selectedCourse);
		buttonSubmit.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		buttonSubmit.addListener(SWT.Selection, event -> {
			CoursePref cp = submitPreference(selectedYearStudy, selectedSemester, selectedCourse, selectedCMCHoice,
					selectedTDCHoice, selectedTPCHoice);
			teach.updatePref(cp);

			resetComposite();
			buttonSubmit.dispose();
			resetSelectedItems();

			compositeYearsStudy = createGroupYearsOfStudy();
			prefShell.layout();
			prefShell.pack();
			prefShell.open();
		});

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