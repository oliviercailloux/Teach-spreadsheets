/**
 * 
 */
package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.File;
import java.util.ArrayList;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;

/**
 * @author tuannamdavaux
 *
 */
public class WriteCourses {

	public static void WriteCoursesList(ArrayList<Course> coursesList, int col,
			int line, File file) throws Exception {
		try (SpreadsheetDocument workbook = SpreadsheetDocument
				.loadDocument(file)) {
			Table sheet = workbook
					.getSheetByName(coursesList.get(0).getYearOfStud());
			sheet.getCellByPosition(col, line)
					.setDisplayText(coursesList.get(0).getName());
			sheet.getCellByPosition(col + 1, line)
					.setDisplayText(coursesList.get(0).getapogeeCode());
			sheet.getCellByPosition(col + 2, line)
					.setDisplayText(coursesList.get(0).getCM_Hour() + "h CM");
			sheet.getCellByPosition(col + 3, line).setDisplayText(
					coursesList.get(0).getCMTD_Hour() + "h CMTD");
			sheet.getCellByPosition(col + 4, line)
					.setDisplayText(coursesList.get(0).getTP_Hour() + "h TP");
			sheet.getCellByPosition(col + 5, line)
					.setDisplayText(coursesList.get(0).getNbGrCMTD() + " CMTD");
			workbook.save(file);
		}

	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Course course = new Course();
		course.setName("Analyse 1");
		course.setapogeeCode("A1DEM01");
		course.setYearOfStud("DE1");
		course.setSupervisor("Simenhaus\n");
		course.setCM_Hour(8.5);
		course.setTD_Hour(4);
		course.setCMTD_Hour(8.6);
		course.setTP_Hour(8.7);
		course.setNbGrpCM(8);
		course.setNbGrpTD(2);
		course.setNbGrpCMTD(7);
		course.setNbGrpTP(5);
		ArrayList<Course> courses = new ArrayList();
		courses.add(course);
		// WriteCourses.WriteCoursesList(courses, 1, 4, new File(
		// "src/main/resources/Saisie_voeux_dauphine_Testing.ods"));
		WriteCourses.WriteCoursesList(courses, 1, 4,
				new File("src/main/resources/WriteCoursesTest.ods"));

	}

}
