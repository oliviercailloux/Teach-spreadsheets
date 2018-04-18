package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import org.odftoolkit.simple.SpreadsheetDocument;

/**
 * 
 * @author tuannamdavaux, samuelcohen75
 *
 */
public interface SpreadsheetReader {
	
	public String getCellValue(String cellPosition);
	
	public boolean isDiagonalBorder(SpreadsheetDocument sd, String yearOfStudy, String cellPosition);
	
	public String getCurrentSheet();

	
	
	

}
