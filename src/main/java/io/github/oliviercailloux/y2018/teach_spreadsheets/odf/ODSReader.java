package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.util.Objects;

import org.odftoolkit.simple.SpreadsheetDocument;
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
	public boolean isSlash(String cellPosition) {
		TODO();
		return false;
	}

	@Override
	public String getCurrentSheet() {
		TODO();
		return null;
	}

}
