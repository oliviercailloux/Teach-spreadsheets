package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.table.Table;

public class ODSReader implements SpreadsheetReader {

	@Override
	public String getCellValue(String cellPosition) {
		TODO();
		return null;
	}

	@Override
	public boolean isDiagonalBorder(SpreadsheetDocument sd, String yearOfStudy, String cellPosition) {
		Table sheet = sd.getSheetByName(yearOfStudy);
			try {
				sheet.getCellByPosition(cellPosition).getBorder(CellBordersType.DIAGONALBLTR);
			} catch (NullPointerException e) {
				return false;
			} catch (NumberFormatException z) {
			return true;
		}
		return false;
	}

	@Override
	public String getCurrentSheet() {
		TODO();
		return null;
	}

}
