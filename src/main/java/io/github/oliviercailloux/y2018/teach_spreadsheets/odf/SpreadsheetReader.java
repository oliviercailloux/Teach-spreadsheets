package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

/**
 * <b>This interface allows to read data from a spreadsheet.</b>
 * 
 * @author tuannamdavaux, samuelcohen75
 * @see io.github.oliviercailloux.y2018.teach_spreadsheets.odf.SpreadsheetShower
 */
public interface SpreadsheetReader {
	/**
	 * get cell value at cellPosition
	 */
	public String getCellValue(String cellPosition);

	/**
	 * detect if there is a diagonal border in cell at cellPosition
	 */
	public boolean isSlash(String cellPosition);

}
