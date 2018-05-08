package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Column;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;

public class ReadCourses {

	private List<Course> courses;

	private ODSReader reader;

	public ReadCourses(List<Course> courses, ODSReader reader) {
		super();
		this.courses = courses;
		this.reader = reader;
	}

	public List<Course> readCourses(String cellPosition) {
		SpreadsheetDocument document = reader.getDocument();

		Table tableCourante = reader.getSheet();
		
		String yearOfStudy = tableCourante.getTableName();
		
		Cell startCell = tableCourante.getCellByPosition(cellPosition);

		Row startRow = startCell.getTableRow();

		int startCellColumnIndex = startCell.getColumnIndex();
		int startCellRowIndex = startCell.getRowIndex();

		Cell actualCell = startCell;
		Course course = new Course();
		
		for (int i = startCellRowIndex; i < tableCourante.getRowCount(); i++) {
			for (int j = startCellColumnIndex; j < tableCourante.getColumnCount(); j++) {
				actualCell = tableCourante.getCellByPosition(j, i);

				if (actualCell.getDisplayText().equals("")) {
					break;
				}
				
				int columnRelativeNumber = j-startCellColumnIndex;
				
				switch(columnRelativeNumber) {
				case 0:
					course.setName(actualCell.getDisplayText());
					break;
				case 1:
					course.setapogeeCode(actualCell.getDisplayText());
					break;
				case 2:
					if(this.reader.isDiagonalBorder(yearOfStudy, cellPosition))
					break;
					
				case 3:
					
					break;
				}
				case 4:
					
					break;
				default:
					break;
			}
			if(actualCell.getDisplayText().equals("")) {
				break;
			}
		}

		return courses;
	}

	public void iterateBeginCells() {
		Scanner sc = new Scanner(System.in);

		String[] beginCell;
		String cell = sc.next();

		while (cell != "STOP") {

		}
	}

	public static void main(String[] args) throws Exception {
		InputStream is = ReadCourses.class.getClassLoader().getResourceAsStream("Saisie_voeux_dauphine.ods");
		SpreadsheetDocument sd = SpreadsheetDocument.loadDocument(is);
		String yearOfStudy = "L3_Informatique";
		ODSReader odsR = new ODSReader(sd, yearOfStudy);

		List<Course> courses = new ArrayList<>();

		ReadCourses reader = new ReadCourses(courses, odsR);
		String cellPosition = "B4";
		reader.readCourses(cellPosition);

	}
}
