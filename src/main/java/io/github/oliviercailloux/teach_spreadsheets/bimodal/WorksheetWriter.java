package io.github.oliviercailloux.teach_spreadsheets.bimodal;

public interface WorksheetWriter {

	/**
	 * This method allow to write in a worksheet using the row and the column of the
	 * cell (example : A1 => (row=0,column=0)
	 * 
	 * @param row     - the row of the cell which we want to read : the range of the
	 *                value of row and column are >= 0
	 * @param column  - the column of the cell which we want to read : In excel the
	 *                column's name are letter, here the letter is is translated by
	 *                its alphabetical rank starting with 0 (ex : A -> 0, B->1 ...
	 *                ZA-> 26)
	 * @param content - String we want to write
	 */
	public void setValueAt(int row, int column, String content) throws WriteException;

	/**
	 * This method allow to change the background color of a cell using the row and
	 * the column of the cell (example : a1 =>(row=0,column=0))
	 * 
	 * @param row    - the row of the cell which we want to read : the range of the
	 *               value of row and column are >= 0
	 * @param column - the column of the cell which we want to read : In excel the
	 *               column's name are letter, here the letter is is translated by
	 *               its alphabetical rank starting with 0 (ex : A -> 0, B->1 ...
	 *               ZA-> 26)
	 * @param color  - color of the cell of the form #RRGGBB (e.g. 'FFA500') or as a
	 *               named HTML color (e.g. 'orange')
	 * 
	 */
	public void setBackgroundColor(int row, int column, String color) throws WriteException;

	/**
	 * This method allow to change the font of a cell using the row and the column
	 * of the cell (example : a1 =>(row=0,column=0))
	 * 
	 * @param row    - the row of the cell which we want to change its font : the
	 *               range of the value of row and column are >= 0
	 * @param column - the column of the cell which we want to its font : In excel
	 *               the column's name are letter, here the letter is is translated
	 *               by its alphabetical rank starting with 0 (ex : A -> 0, B->1 ...
	 *               ZA-> 26)
	 * @param color  - color of the cell of the form #RRGGBB (e.g. 'FFA500') or as a
	 *               named HTML color (e.g. 'orange')
	 * @param bold
	 * @param size   - size of the cell
	 * @param name   - police name
	 * 
	 */
	public void setFont(int row, int column, boolean bold, String color, double size, String name)
			throws WriteException;

	/**
	 * This method allow to change the format of a cell using the row and the column
	 * of the cell (example : a1 =>(row=0,column=0))
	 * 
	 * @param row                 - the row of the cell which we want to change its
	 *                            format : the range of the value of row and column
	 *                            are >= 0
	 * @param column              - the column of the cell which we want to change
	 *                            its format : In excel the column's name are
	 *                            letter, here the letter is translated by its
	 *                            alphabetical rank starting with 0 (ex : A -> 0,
	 *                            B->1 ... ZA-> 26)
	 * @param columnWidth         - Width of the column
	 * @param alignmentHorizontal -text horizontal aligmnent
	 * @param alignmentVertical   - text vertical aligmnent
	 * 
	 *
	 * 
	 */
	public void setFormat(int row, int column, double columnWidth, String alignmentHorizontal, String alignmentVertical)
			throws WriteException;

	/**
	 * This method allow to merge the range cells into one region in the worksheet.
	 * 
	 * @param firstRow     - the row of the first cell which we want to merge : the
	 *                     range of the value of row and column are >= 0
	 * @param firstColumn  - the column of the first cell which we want to merge :
	 *                     In excel the column's name are letter, here the letter is
	 *                     is translated by its alphabetical rank starting with 0
	 *                     (ex : A -> 0, B->1 ... ZA-> 26)
	 * @param secondRow    - the row of the second cell which we want to merge : the
	 *                     range of the value of row and column are >= 0
	 * @param secondColumn - the column of the second cell which we want to merge :
	 *                     In excel the column's name are letter, here the letter is
	 *                     is translated by its alphabetical rank starting with 0
	 *                     (ex : A -> 0, B->1 ... ZA-> 26)
	 */
	void cellFusion(int firstRow, int firstColumn, int secondRow, int secondColumn) throws WriteException;
}
