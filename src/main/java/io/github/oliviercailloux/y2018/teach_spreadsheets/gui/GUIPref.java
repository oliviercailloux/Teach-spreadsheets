package io.github.oliviercailloux.y2018.teach_spreadsheets.gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.LoggerFactory;

public class GUIPref {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GUIPref.class);

	private void initializeMainMenu() throws IOException {

		String logoFileName = "logoGUI.png";

		try (InputStream inputStream = GUIPref.class.getResourceAsStream(logoFileName)) {
			if (inputStream == null) {
				LOGGER.error("File " + logoFileName + " not found.");
				throw new FileNotFoundException("File not found");
			}

			Display display = new Display();
			Shell shell = new Shell(display);
			shell.setText("Menu principal - Teach-spreadsheets");
			shell.setLayout(new GridLayout(1, false));
			shell.setSize(500, 700);

			// Display an image
			Image image = new Image(display, inputStream);
			Label labelImg = new Label(shell, SWT.BORDER);
			Rectangle clientArea = shell.getClientArea();
			labelImg.setLocation(clientArea.x, clientArea.y);
			labelImg.setImage(image);
			labelImg.pack();

			// Create a horizontal separator
			Label separator = new Label(shell, SWT.HORIZONTAL | SWT.SEPARATOR);
			separator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			// Label with teacher name
			Label lblCentered = new Label(shell, SWT.NONE);
			lblCentered.setText("Bienvenue");
			lblCentered.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false));

			// Create a horizontal separator
			Label separator2 = new Label(shell, SWT.HORIZONTAL | SWT.SEPARATOR);
			separator2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			Text text = new Text(shell, SWT.BORDER);
			text.setText("aaaa");

			// shell.pack ();
			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
			image.dispose();
			display.dispose();
		}
	}

	public static void main(String[] args) throws IOException {
		GUIPref gui = new GUIPref();
		gui.initializeMainMenu();
	}

}