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
	private static int NBATTRIBUTE = 8;
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
				course.setYearOfStud(yearOfStudy);
				int j = -1;
				for (j = startCellColumnIndex; j < startCellColumnIndex + NBATTRIBUTE; j++) {
					actualCell = tableCourante.getCellByPosition(j, i);

					String cellText = actualCell.getDisplayText().replaceAll(",", ".");

					if ("".equals(cellText) && j == startCellColumnIndex) {
						break;
					}

					int columnRelativeNumber = j - startCellColumnIndex;
					switch (columnRelativeNumber) {
					case 0:
						course.setName(cellText);
						break;
					case 1:
						course.setapogeeCode(cellText);
						break;
					case 2:
						course.setSupervisor(cellText);
						break;
					case 3:
						course.setTeachers(cellText);
						break;
					case 4:
						if (this.reader.isDiagonalBorder(actualCell)) {
							course.setCM_Hour(0);
						} else {
							String hourStr = cellText;
							String[] hourTab = hourStr.split("h");
							course.setCM_Hour(Double.parseDouble(hourTab[0]));
						}
						break;

					case 5:
						if (this.reader.isDiagonalBorder(actualCell)) {
							course.setTD_Hour(0);
							course.setCMTD_Hour(0);
						} else {
							String hourStr = cellText;
							String[] hourTab = hourStr.split("h");
							if (hourStr.contains(COURSTD)) {
								course.setCMTD_Hour(Double.parseDouble(hourTab[0]));
							} else if (hourStr.contains(TD)) {
								course.setTD_Hour(Double.parseDouble(hourTab[0]));
							}
						}
						break;
					case 6:
						if (this.reader.isDiagonalBorder(actualCell)) {
							course.setTP_Hour(0);
						} else {
							String hourStr = cellText;
							String[] hourTab = hourStr.split("h");
							course.setTP_Hour(Double.parseDouble(hourTab[0]));
						}
						break;
					case 7:
						if (this.reader.isDiagonalBorder(actualCell) || "".equals(cellText)) {
							course.setNbGrpCM(0);
							course.setNbGrpCMTD(0);
							course.setNbGrpTD(0);
							course.setNbGrpTP(0);
						} else {
							String hourStr = cellText;
							int hour = Integer.parseInt(hourStr);
							course.setNbGrpCM(hour);
							course.setNbGrpCMTD(hour);
							course.setNbGrpTD(hour);
							course.setNbGrpTP(hour);
						}
						break;
					default:
						break;
					}
				}
				if (j == startCellColumnIndex) {
					break;
				}
				courses.add(course);
			}

		}
		return courses;
	}

	public static void main(String[] args) throws Exception {
		try (InputStream is = ReadCourses.class.getClassLoader().getResourceAsStream("Saisie_voeux_dauphine.ods")) {
			try (SpreadsheetDocument sd = SpreadsheetDocument.loadDocument(is)) {
				String yearOfStudy = "L3_Informatique";
				ODSReader odsR = new ODSReader(sd, yearOfStudy);

				List<Course> courses = new ArrayList<>();

				ReadCourses reader = new ReadCourses(odsR);
				String cellPosition = "B4";
				courses = reader.readCourses(cellPosition);

				System.out.println(courses);
			}
		}
	}
}
