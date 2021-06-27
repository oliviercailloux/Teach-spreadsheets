package io.github.oliviercailloux.teach_spreadsheets.bimodal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.IOException;

import org.odftoolkit.simple.Document;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OdsWorksheetWriter implements WorksheetWriter {

	private Table worksheet;
	private File workbook;

	private static final Logger LOGGER = LoggerFactory.getLogger(OdsWorksheetWriter.class);

	private OdsWorksheetWriter(Table tableGiven, File workbookGiven) {
		worksheet = tableGiven;
		workbook = workbookGiven;
	}

	/**
	 * This method load an existing worksheet in the workbook identified by his url.
	 * The worksheet is store in the variable worksheet.
	 * 
	 * @param urlWorkbook   - The fileId identifies the workbook where we want to
	 *                      create the worksheet
	 * @param worksheetName - The name of the worksheet we want to create
	 * @throws IOException if the Ods file could not be saved
	 */
	public static OdsWorksheetWriter loadExistingSheet(File workbookGiven, String worksheetName) throws ReadException {

		checkNotNull(workbookGiven);
		checkNotNull(worksheetName);

		SpreadsheetDocument workbook;
		try {
			workbook = SpreadsheetDocument.loadDocument(workbookGiven);
			workbook.close();

		} catch (Exception e) {
			throw new ReadException("A local read failure occurred", e);
		}

		Table worksheet = workbook.getTableByName(worksheetName);

		if (worksheet == null) {
			throw new IllegalStateException("failure to retrieve the worksheet");
		}

		return new OdsWorksheetWriter(worksheet, workbookGiven);
	}

	/**
	 * This method creates an empty Worksheet in the workbook identified by his url
	 * and load it. The worksheet is stored in the variable worksheet
	 * 
	 * @param urlWorkbook   - The fileId identifies the workbook where we want to
	 *                      create the worksheet
	 * @param worksheetName - The name of the worksheet we want to create
	 * @throws ReadException if the Ods file could not be reached
	 */
	public static OdsWorksheetWriter loadNewSheet(File workbookGiven, String worksheetName) throws ReadException {

		checkNotNull(workbookGiven);
		checkNotNull(worksheetName);

		SpreadsheetDocument workbook;
		try {
			workbook = SpreadsheetDocument.loadDocument(workbookGiven);
		} catch (Exception e) {
			throw new ReadException("A local read failure occurred", e);
		}

		Table worksheet = workbook.appendSheet(worksheetName);
		workbook.close();

		if (worksheet == null) {
			throw new IllegalStateException("failure to create the worksheet");
		}

		LOGGER.info("Worsheet created succesfully");
		return new OdsWorksheetWriter(worksheet, workbookGiven);
	}

	@Override
	public void setValueAt(int row, int column, String content) throws WriteException {
		checkArgument(row >= 0, column >= 0);
		checkNotNull(content);

		Cell cell = worksheet.getCellByPosition(column, row);

		cell.setDisplayText(content);

		Document ownerDocument = worksheet.getOwnerDocument();

		try {
			ownerDocument.save(workbook);
			ownerDocument.close();

		} catch (Exception e) {
			throw new WriteException("A local write failure ocured", e);
		}

	}

	@Override
	public void setBackgroundColor(int row, int column, String color) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFont(int row, int column, boolean bold, String color, double size, String name)
			throws WriteException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setFormat(int row, int column, double columnWidth, String alignmentHorizontal, String alignmentVertical)
			throws WriteException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void cellFusion(int firstRow, int firstColumn, int secondRow, int secondColumn) throws WriteException {
		throw new UnsupportedOperationException();

	}

	public Table getWorksheet() {
		return worksheet;
	}

}