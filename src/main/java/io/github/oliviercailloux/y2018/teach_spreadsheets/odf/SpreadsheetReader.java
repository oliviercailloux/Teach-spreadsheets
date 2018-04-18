package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

/**
 * 
 * @author tuannamdavaux, samuelcohen75
 *
 */
public interface SpreadsheetReader {
	
	public String getCellValue(String cellPosition);
	
	public boolean isSlash(String cellPosition);
	
	public String getCurrentSheet();
	
	

}
