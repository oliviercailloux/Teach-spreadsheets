package io.github.oliviercailloux.y2018.teach_spreadsheets.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import io.github.oliviercailloux.y2018.teach_spreadsheets.gui.GUIPref;
import io.github.oliviercailloux.y2018.teach_spreadsheets.gui.TeachSpreadSheetController;

public class App {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		try (InputStream teacherFile = App.class.getResourceAsStream("teacher_info.csv")) {
			TeachSpreadSheetController controller = new TeachSpreadSheetController(teacherFile);
			GUIPref gui = new GUIPref(controller);
			gui.initializeMainMenu();
		}
	}

}
