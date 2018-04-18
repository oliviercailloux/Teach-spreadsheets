package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.util.Objects;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

public class ODSReader implements SpreadsheetReader {

	private SpreadsheetDocument document;

	private Table sheet;

	public ODSReader(SpreadsheetDocument spreadsheetDocument) {
		this.document = Objects.requireNonNull(spreadsheetDocument);
		this.sheet = Objects.requireNonNull(spreadsheetDocument.getTableList().get(0));
	}

	public ODSReader(SpreadsheetDocument spreadsheetDocument, String sheet) {
		this.document = Objects.requireNonNull(spreadsheetDocument);
		this.sheet = Objects.requireNonNull(spreadsheetDocument.getTableByName(sheet));
	}

	@Override
	public String getCellValue(String cellPosition) {
		Cell cell = sheet.getCellByPosition(cellPosition);
		if (cell == null) {
			return "";
		}
		return cell.getDisplayText();
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

}
