package io.github.oliviercailloux.y2018.teach_spreadsheets.main;

import io.github.oliviercailloux.y2018.teach_spreadsheets.gui.GUIPref;
import io.github.oliviercailloux.y2018.teach_spreadsheets.gui.TeachSpreadSheetController;

public class App {

	public static void main(String[] args) {
		TeachSpreadSheetController controller = new TeachSpreadSheetController();
		GUIPref gui = new GUIPref(controller);
	}

}
