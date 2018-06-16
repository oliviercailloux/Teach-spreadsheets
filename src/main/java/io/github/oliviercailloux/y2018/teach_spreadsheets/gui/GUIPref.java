package io.github.oliviercailloux.y2018.teach_spreadsheets.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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

/**
 * This class is a Graphic User Interface allowing the user to import a file (in
 * which there are courses) and to set preferences on these courses. He can
 * choose a specified year of study, then a semester, then a specified course.
 * 
 * @author Victor CHEN
 * @version 1.0
 * 
 */
public class GUIPref {
	private final static Logger LOGGER = LoggerFactory.getLogger(GUIPref.class);

	private Display display;
	private Shell shell;
	private Shell prefShell;

	private TeachSpreadSheetController teach;

	private Group groupYearsStudy;
	private Composite compositeSemesters;
	private Composite compositeCourses;
	private Composite compositeChoices;

	private Group groupCMButtons;
	private Group groupTDButtons;
	private Group groupTPButtons;

	private String selectedYearStudy = "";
	private Integer selectedSemester;
	private String selectedCourse = "";
	private Choice selectedCMCHoice = Choice.NA;
	private Choice selectedTDCHoice = Choice.NA;
	private Choice selectedTPCHoice = Choice.NA;

	private java.util.List<CoursePref> listCoursePref = new ArrayList<>();

	public GUIPref(TeachSpreadSheetController teach) {
		this.teach = teach;
	}

	private void resetComposite() {
		compositeSemesters.dispose();
		compositeChoices.dispose();
		compositeCourses.dispose();
	}

	private void resetSelectedItems() {
		selectedYearStudy = "";
		selectedSemester = 0;
		selectedCourse = "";
		selectedCMCHoice = Choice.NA;
		selectedTDCHoice = Choice.NA;
		selectedTPCHoice = Choice.NA;
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
			buttonFileExplorer.addSelectionListener(new SelectionListener() {
				@SuppressWarnings("resource")
				@Override
				public void widgetSelected(SelectionEvent e) {

					String fileName = openFileExplorer();

					FileInputStream fis;
					if (!(fileName == null)) {
						// we must use a Try/Catch because we can't throw on the
						// method
						// widgetSelected(SelectionEvent e)
						try {
							fis = new FileInputStream(fileName);
							teach.setSource(fis);
							prefShell();
						} catch (Exception e1) {
							LOGGER.error("File not opened");
							throw new IllegalStateException(e1);
						}
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});

			// Button allowing the user to quit the application (it closes the
			// display)
			Button buttonExit;
			buttonExit = new Button(shell, SWT.NONE);
			buttonExit.setText("Quitter l'application");
			buttonExit.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			buttonExit.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					exitApplication();

				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});

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
		Label lblSeparator;

		lblSeparator = new Label(prefShell, SWT.HORIZONTAL | SWT.SEPARATOR);
		lblSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		groupYearsStudy = createGroupYearsOfStudy();

		prefShell.open();
		LOGGER.info("Shell for the preferences well opened");

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

	/**
	 * This methods creates a Group in which there is a list of Years of Study from
	 * the file opened
	 */
	private Group createGroupYearsOfStudy() {
		Composite c = new Composite(prefShell, SWT.CENTER);
		c.setLayout(new GridLayout(2, false));

		Group groupYearOfStudy = new Group(c, SWT.CENTER);

		java.util.List<String> yearNames = teach.getYearNames();

		groupYearOfStudy.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		groupYearOfStudy.setText("Step 1: Choose the year of study");
		groupYearOfStudy.setLayout(new GridLayout(1, false));

		final List listYearStudy = new List(groupYearOfStudy, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		listYearStudy.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		for (String string : yearNames) {
			listYearStudy.add(string);
		}

		final Text text = new Text(c, SWT.BORDER | SWT.H_SCROLL);
		text.setBounds(60, 130, 160, 25);

		listYearStudy.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				String s[] = listYearStudy.getSelection();
				String outString = s[0];

				text.setText("Selected year of study : " + outString);
				selectedYearStudy = outString;

				LOGGER.info("Year of study " + selectedYearStudy + " well chosen.");

				if (compositeSemesters != null) {
					compositeSemesters.dispose();
					if (compositeCourses != null) {
						compositeCourses.dispose();
						if (compositeChoices != null) {
							compositeChoices.dispose();
						}
					}
				}
				compositeSemesters = createCompositeSemesters();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				String s[] = listYearStudy.getSelection();
				String outString = s[0];
				text.setText("Selected year of study : " + outString);
				selectedYearStudy = outString;
			}
		});

		prefShell.pack();
		return groupYearOfStudy;
	}

	/**
	 * This methods creates a Composite in which there are 2 buttons for each
	 * semester available
	 */
	private Composite createCompositeSemesters() {
		Composite c = new Composite(prefShell, SWT.CENTER);
		GridLayout f = new GridLayout(2, false);
		c.setLayout(f);

		Group group2 = new Group(c, SWT.SHADOW_OUT);
		group2.setText("Step 2: Choose the semester");
		group2.setLayout(new GridLayout(1, true));

		java.util.List<Integer> listSemester = teach.getSemesters(selectedYearStudy);
		int firstSemester = 0;
		int secondSemester = 0;

		firstSemester = listSemester.get(0);
		secondSemester = listSemester.get(1);

		// Button for the first semester
		final Button button1 = new Button(group2, SWT.RADIO);
		button1.setText(String.valueOf(firstSemester));
		// Button for the second semester
		final Button button2 = new Button(group2, SWT.RADIO);
		button2.setText(String.valueOf(secondSemester));

		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				int user_choice = 0;

				if (event.widget == button1) {
					user_choice = Integer.valueOf(button1.getText());
					selectedSemester = user_choice;
				} else if (event.widget == button2) {
					user_choice = Integer.valueOf(button2.getText());
					selectedSemester = user_choice;
				}
				selectedSemester = user_choice;
				System.out.println(selectedSemester);

				if (compositeCourses != null)
					compositeCourses.dispose();
				compositeCourses = createCompositeCourses();
			}
		};

		button1.addListener(SWT.Selection, listener);
		button2.addListener(SWT.Selection, listener);

		prefShell.pack();
		// return group2;
		return c;
	}

	/**
	 * This methods creates a Composite in which there is a list of Courses
	 */
	private Composite createCompositeCourses() {
		Composite c = new Composite(prefShell, SWT.CENTER);
		GridLayout f = new GridLayout(2, false);
		c.setLayout(f);

		Group groupCourses = new Group(c, SWT.CENTER);

		java.util.List<String> courseNames = teach.getCoursesName(selectedYearStudy, selectedSemester);

		groupCourses.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		groupCourses.setText("Step 3 : Choose the course");
		groupCourses.setLayout(new GridLayout(1, true));

		final List listCourses = new List(groupCourses, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);

		listCourses.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		for (String string : courseNames) {
			listCourses.add(string);
		}

		final Text text = new Text(c, SWT.BORDER | SWT.H_SCROLL);
		text.setBounds(60, 130, 160, 25);

		GridData gridDataText = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridDataText = new GridData();
		gridDataText.widthHint = 400;
		gridDataText.heightHint = 35;
		text.setLayoutData(gridDataText);

		listCourses.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				String s[] = listCourses.getSelection();
				String outString = s[0];

				text.setText("Selected course : " + outString);
				selectedCourse = outString;

				LOGGER.info("Course " + selectedCourse + " well chosen.");

				java.util.List<String> listPossibleChoice = teach.getPossibleChoice(selectedYearStudy, selectedSemester,
						selectedCourse);

				if (compositeChoices != null) {
					compositeChoices.dispose();
				}
				compositeChoices = createCompositeForChoices();
				for (String possibleChoice : listPossibleChoice) {
					if (possibleChoice.equals("CM")) {
						groupCMButtons = createGroupButtonsCM();
						// compositeCMButtons =
						// createGroupButtonsCM(compositeSemesters);
					}
					if (possibleChoice.equals("TD")) {
						groupTDButtons = createGroupButtonsTD();
					}
					if (possibleChoice.equals("TP")) {
						groupTPButtons = createGroupButtonsTP();
					}
				}
				Button buttonSubmit = createButtonSubmitPreference();
				prefShell.pack();
				prefShell.open();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				String s[] = listCourses.getSelection();
				String outString = s[0];

				selectedCourse = outString;
			}
		});

		prefShell.pack();
		return c;

	}

	/**
	 * This method creates a Composite in which we store the 3 groups for the
	 * Choices (CM, TD, TP)
	 */
	private Composite createCompositeForChoices() {
		compositeChoices = new Composite(prefShell, SWT.CENTER);

		GridLayout f = new GridLayout(3, true);
		compositeChoices.setLayout(f);

		return compositeChoices;
	}

	/**
	 * This method creates a group of buttons for the CM choices
	 */
	private Group createGroupButtonsCM() {
		// Composite c = new Composite(compositeChoices, SWT.SHADOW_OUT);
		Group group2 = new Group(compositeChoices, SWT.NONE);
		group2.setText("Step 4 : Choose your preferences for CM");
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
				System.out.println(selectedCMCHoice);

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
	 * This method creates a group of buttons for the TD choices
	 */
	private Group createGroupButtonsTD() {
		// Composite c = new Composite(compositeChoices, SWT.SHADOW_OUT);
		Group group2 = new Group(compositeChoices, SWT.NONE);
		group2.setText("Step 5 : Choose your preferences for TD");
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
				System.out.println(selectedTDCHoice);

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
		// Composite c = new Composite(compositeChoices, SWT.SHADOW_OUT);
		Group group2 = new Group(compositeChoices, SWT.NONE);
		group2.setText("Step 6 : Choose your preferences for TD");
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
				System.out.println(selectedTPCHoice);

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
	 * This methods open the file explorer
	 */
	private String openFileExplorer() {
		Shell shellFE = new Shell(display);

		FileDialog fd = new FileDialog(shellFE, SWT.OPEN);
		fd.setText("Open");
		// fd.setFilterPath("C:/");

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
		dlg.setFilterPath("C:/");
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

	private Button createButtonSubmitPreference() {
		// Create a horizontal separator
		Label lblSeparator = new Label(prefShell, SWT.HORIZONTAL | SWT.SEPARATOR);
		lblSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Button to submit the preferences for a specified course
		Button buttonSubmit;
		buttonSubmit = new Button(prefShell, SWT.NONE);
		buttonSubmit.setText("Submit your preferences for the course : " + selectedCourse);
		buttonSubmit.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		buttonSubmit.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// =====================================================
				// on button press, create a child Shell object passing
				// the main Display. The child could also access the
				// display itself by calling Display.getDefault()
				// =====================================================
				if (!(selectedCMCHoice == Choice.NA) || !(selectedTDCHoice == Choice.NA)
						|| !(selectedTPCHoice == Choice.NA)) {
					CoursePref cp = submitPreference(selectedYearStudy, selectedSemester, selectedCourse,
							selectedCMCHoice, selectedTDCHoice, selectedTPCHoice);
					teach.updatePref(cp);
					resetComposite();
					resetSelectedItems();
				} else {
					MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
					messageBox
							.setMessage("You must select your preferences choices for the course : " + selectedCourse);
					messageBox.setText("Error on the submission");
					messageBox.open();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		return buttonSubmit;
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
}