/**
 * 
 */
package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.File;
import java.util.ArrayList;

import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.style.Border;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions;
import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.table.Table;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;

/**
 * @author Tuan Nam Davaux (tuannamdavaux), Samuel Cohen (samuelcohen75) This
 *         class allows to write courses informations in an odf spreadsheet
 *         Version : 1.2 Last update : 11/05/2018
 */
public class WriteCourses {

	/**
	 * @param coursesList
	 * @param col
	 * @param line
	 * @param file
	 * @throws Exception
	 */
	public static void WriteCoursesList(ArrayList<Course> coursesList, int col,
			int line, File file) throws Exception {
		try (SpreadsheetDocument workbook = SpreadsheetDocument
				.loadDocument(file)) {
			Font font = new Font("Calibri",
					StyleTypeDefinitions.FontStyle.REGULAR, 11, Color.BLACK);
			Border border = new Border(Color.BLACK, 1,
					StyleTypeDefinitions.SupportedLinearMeasure.PT);
			Table sheet = workbook
					.getSheetByName(coursesList.get(0).getYearOfStud());

			for (int i = 0; i <= 7; i++) {
				if (i == 0) {
					sheet.getCellByPosition(col + i, line).setVerticalAlignment(
							StyleTypeDefinitions.VerticalAlignmentType.MIDDLE);
				} else if (!(i == 2 && i == 3)) {
					sheet.getCellByPosition(col + i, line).setVerticalAlignment(
							StyleTypeDefinitions.VerticalAlignmentType.MIDDLE);
					sheet.getCellByPosition(col + i, line)
							.setHorizontalAlignment(
									StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
				}
				sheet.getCellByPosition(col + i, line).setFont(font);
			}

			// Name
			sheet.getCellByPosition(col, line)
					.setStringValue(coursesList.get(0).getName());

			// apogeeCode
			sheet.getCellByPosition(col + 1, line)
					.setStringValue(coursesList.get(0).getapogeeCode());

			// superVisor
			if (coursesList.get(0).getSupervisor().equals(""))
				sheet.getCellByPosition(col + 2, line)
						.setBorders(CellBordersType.DIAGONALBLTR, border);
			else
				sheet.getCellByPosition(col + 2, line)
						.setStringValue(coursesList.get(0).getSupervisor());

			// teachers
			if (coursesList.get(0).getTeachers().equals(""))
				sheet.getCellByPosition(col + 3, line)
						.setBorders(CellBordersType.DIAGONALBLTR, border);
			else
				sheet.getCellByPosition(col + 3, line)
						.setStringValue(coursesList.get(0).getTeachers());

			// CM hours
			if (coursesList.get(0).getCM_Hour() == 0.0)
				sheet.getCellByPosition(col + 4, line)
						.setBorders(CellBordersType.DIAGONALBLTR, border);
			else
				sheet.getCellByPosition(col + 4, line).setStringValue(
						coursesList.get(0).getCM_Hour() + "h CM");
			// TD and CMTD

			if (coursesList.get(0).getCMTD_Hour() == 0.0
					&& coursesList.get(0).getTD_Hour() == 0.0) {
				sheet.getCellByPosition(col + 5, line)
						.setBorders(CellBordersType.DIAGONALBLTR, border);
			} else {
				if (coursesList.get(0).getCMTD_Hour() != 0.0) {
					sheet.getCellByPosition(col + 5, line).setStringValue(
							coursesList.get(0).getCMTD_Hour() + "h CMTD");
				} else {
					sheet.getCellByPosition(col + 5, line).setStringValue(
							coursesList.get(0).getTD_Hour() + "h TD");

				}
			}

			// TP and CMTP
			if (coursesList.get(0).getCMTP_Hour() == 0.0
					&& coursesList.get(0).getTP_Hour() == 0.0) {
				sheet.getCellByPosition(col + 6, line)
						.setBorders(CellBordersType.DIAGONALBLTR, border);
			} else {
				if (coursesList.get(0).getCMTP_Hour() != 0.0) {
					sheet.getCellByPosition(col + 6, line).setStringValue(
							coursesList.get(0).getCMTP_Hour() + "h CMTP");
				} else {
					sheet.getCellByPosition(col + 6, line).setStringValue(
							coursesList.get(0).getTP_Hour() + "h TP");

				}
			}

			// groups number
			if (coursesList.get(0).getGrpsNumber().equals(""))
				sheet.getCellByPosition(col + 7, line)
						.setBorders(CellBordersType.DIAGONALBLTR, border);
			else
				sheet.getCellByPosition(col + 7, line)
						.setStringValue(coursesList.get(0).getGrpsNumber());
			workbook.save(file);
		}

	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		Course course = new Course();
		course.setName("Statistiques");
		course.setapogeeCode("A1PREMA500");
		course.setSupervisor("Tuan Nam");
		course.setTeachers("Xu \nL");
		course.setYearOfStud("DE1");
		course.setCM_Hour(8.5);
		course.setCMTD_Hour(8.6);
		course.setTP_Hour(8.7);
		course.setGrpsNumber("2 all + 3 esp");
		ArrayList<Course> courses = new ArrayList<>();
		courses.add(course);
		WriteCourses.WriteCoursesList(courses, 1, 4, new File(
				"src/main/resources/Saisie_voeux_dauphine_Testing.ods"));

	}

}