package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;

public class ReadCourses {

	private static String COURSTD = "CMTD";
	private static String TD = "TD";

	private ODSReader reader;

	public ReadCourses(ODSReader reader) {
		super();
		this.reader = reader;
	}

	public List<Course> readCourses(String cellPosition) {
		List<Course> courses = new ArrayList<>();
		try (SpreadsheetDocument document = reader.getDocument()) {

			Table tableCourante = reader.getSheet();

			String yearOfStudy = tableCourante.getTableName();

			Cell startCell = tableCourante.getCellByPosition(cellPosition);

			int startCellColumnIndex = startCell.getColumnIndex();
			int startCellRowIndex = startCell.getRowIndex();

			Cell actualCell = startCell;

			for (int i = startCellRowIndex; i < tableCourante.getRowCount(); i++) {
				Course course = new Course();

				for (int j = startCellColumnIndex; j < tableCourante.getColumnCount(); j++) {
					actualCell = tableCourante.getCellByPosition(j, i);

					if (actualCell.getDisplayText().equals("")) {
						break;
					}

					int columnRelativeNumber = j - startCellColumnIndex;

					switch (columnRelativeNumber) {
					case 0:
						course.setName(actualCell.getDisplayText());
						break;
					case 1:
						course.setapogeeCode(actualCell.getDisplayText());
						break;
					case 2:
						if (this.reader.isDiagonalBorder(yearOfStudy, cellPosition)) {
							course.setCM_Hour(0);
						} else {
							String hourStr = actualCell.getDisplayText();
							String[] hourTab = hourStr.split("h");
							course.setCM_Hour(Double.parseDouble(hourTab[0]));
						}
						break;

					case 3:
						if (this.reader.isDiagonalBorder(yearOfStudy, cellPosition)) {
							course.setTD_Hour(0);
							course.setCMTD_Hour(0);
						} else {
							String hourStr = actualCell.getDisplayText();
							String[] hourTab = hourStr.split("h");
							if (hourStr.contains(COURSTD)) {
								course.setCMTD_Hour(Double.parseDouble(hourTab[0]));
							} else if (hourStr.contains(TD)) {
								course.setTD_Hour(Double.parseDouble(hourTab[0]));
							}
						}
						break;
					case 4:
						if (this.reader.isDiagonalBorder(yearOfStudy, cellPosition)) {
							course.setTP_Hour(0);
						} else {
							String hourStr = actualCell.getDisplayText();
							String[] hourTab = hourStr.split("h");
							course.setTP_Hour(Double.parseDouble(hourTab[0]));
						}
						break;
					case 5:
						if (this.reader.isDiagonalBorder(yearOfStudy, cellPosition)) {
							course.setNbGrpCM(0);
							course.setNbGrpCMTD(0);
							course.setNbGrpTD(0);
							course.setNbGrpTP(0);
						} else {
							String hourStr = actualCell.getDisplayText();
							int hour = Integer.parseInt(hourStr);
							course.setNbGrpCM(hour);
							course.setNbGrpCMTD(hour);
							course.setNbGrpTD(hour);
							course.setNbGrpTP(hour);
						}
					default:
						break;
					}
					if (actualCell.getDisplayText().equals("")) {
						break;
					}
				}
				courses.add(course);
			}

		}
		return courses;
	}

	public static void main(String[] args) throws Exception {
		InputStream is = ReadCourses.class.getClassLoader().getResourceAsStream("Saisie_voeux_dauphine.ods");
		SpreadsheetDocument sd = SpreadsheetDocument.loadDocument(is);
		String yearOfStudy = "L3_Informatique";
		ODSReader odsR = new ODSReader(sd, yearOfStudy);

		List<Course> courses = new ArrayList<>();

		ReadCourses reader = new ReadCourses(odsR);
		String cellPosition = "B4";
		courses = reader.readCourses(cellPosition);

		System.out.println(courses);
	}
}
