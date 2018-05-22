/**
 * 
 */
package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.File;
import java.util.List;
import java.util.Objects;

import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.style.Border;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions;
import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;

/**
 * @author Tuan Nam Davaux (tuannamdavaux), Samuel Cohen (samuelcohen75) This
 *         class allows to write a year of study as a new excel sheet in an odf
 *         spreadsheet Version : 2.1 Last update : 13/05/2018
 */
public class WriteCourses {
	private File excelFile = null;
	private SpreadsheetDocument workbook = null;

	private String completeYearOfStudyName = "";
	private int studentsNumber = 0, firstSemesterNumber = 1;
	private List<Course> firstSemesterCourses = null;
	private List<Course> secondSemesterCourses = null;
	private final static Logger LOGGER = LoggerFactory.getLogger(WriteCourses.class);

	public WriteCourses(File excelFile, String completeYearOfStudyName, int studentsNumber, int firstSemesterNumber,
			List<Course> firstSemester, List<Course> secondSemester) throws Exception {
		this.excelFile = Objects.requireNonNull(excelFile);
		this.workbook = SpreadsheetDocument.loadDocument(this.excelFile);

		LOGGER.info("File" + excelFile + "has been correctly loaded");

		this.completeYearOfStudyName = Objects.requireNonNull(completeYearOfStudyName);
		this.firstSemesterCourses = Objects.requireNonNull(firstSemester);
		this.secondSemesterCourses = Objects.requireNonNull(secondSemester);
		if (studentsNumber < 0)
			throw new IllegalArgumentException("Le nombre d'étudiants ne peut pas être négatif");
		this.studentsNumber = studentsNumber;
		if (firstSemesterNumber <= 0)
			throw new IllegalArgumentException("Le numéro du premier semestre est incorrect");
		this.firstSemesterNumber = firstSemesterNumber;
	}

	/**
	 * This method fills one semester Array by using column and line cursors.
	 * 
	 * @param courseList
	 *            is a list of courses that will be fill in the array
	 * @param col
	 *            is the number of the column where we begin the filling
	 * @param line
	 *            is the number of the line where we begin the filling
	 * @throws Exception
	 */
	private void WriteSemesterCourses(List<Course> coursesList, int col, int line) throws Exception {

		// Design handling
		Border border = new Border(Color.BLACK, 1, StyleTypeDefinitions.SupportedLinearMeasure.PT);
		Table sheet = this.workbook.getSheetByName(coursesList.get(0).getYearOfStud());

		int lineCursor = line;

		// Writing course by row
		for (Course course : coursesList) {
			// Name
			sheet.getCellByPosition(col, lineCursor).setStringValue(course.getName());

			// apogeeCode
			sheet.getCellByPosition(col + 1, lineCursor).setStringValue(course.getapogeeCode());

			// superVisor
			if (course.getSupervisor().equals(""))
				sheet.getCellByPosition(col + 2, lineCursor).setBorders(CellBordersType.DIAGONALBLTR, border);
			else
				sheet.getCellByPosition(col + 2, lineCursor).setStringValue(course.getSupervisor());

			// teachers
			if (course.getTeachers().equals(""))
				sheet.getCellByPosition(col + 3, lineCursor).setBorders(CellBordersType.DIAGONALBLTR, border);
			else
				sheet.getCellByPosition(col + 3, lineCursor).setStringValue(course.getTeachers());

			// CM hours
			if (course.getCM_Hour() == 0.0)
				sheet.getCellByPosition(col + 4, lineCursor).setBorders(CellBordersType.DIAGONALBLTR, border);
			else
				sheet.getCellByPosition(col + 4, lineCursor).setStringValue(course.getCM_Hour() + "h CM");
			// TD and CMTD

			if (course.getCMTD_Hour() == 0.0 && course.getTD_Hour() == 0.0) {
				sheet.getCellByPosition(col + 5, lineCursor).setBorders(CellBordersType.DIAGONALBLTR, border);
			} else {
				if (course.getCMTD_Hour() != 0.0) {
					sheet.getCellByPosition(col + 5, lineCursor).setStringValue(course.getCMTD_Hour() + "h CMTD");
				} else {
					sheet.getCellByPosition(col + 5, lineCursor).setStringValue(course.getTD_Hour() + "h TD");

				}
			}

			// TP and CMTP
			if (course.getCMTP_Hour() == 0.0 && course.getTP_Hour() == 0.0) {
				sheet.getCellByPosition(col + 6, lineCursor).setBorders(CellBordersType.DIAGONALBLTR, border);
			} else {
				if (course.getCMTP_Hour() != 0.0) {
					sheet.getCellByPosition(col + 6, lineCursor).setStringValue(course.getCMTP_Hour() + "h CMTP");
				} else {
					sheet.getCellByPosition(col + 6, lineCursor).setStringValue(course.getTP_Hour() + "h TP");

				}
			}

			// groups number
			if (course.getGrpsNumber().equals(""))
				sheet.getCellByPosition(col + 7, lineCursor).setBorders(CellBordersType.DIAGONALBLTR, border);
			else
				sheet.getCellByPosition(col + 7, lineCursor).setStringValue(course.getGrpsNumber());
			lineCursor++;
		}

		this.workbook.save(this.excelFile);

		LOGGER.info("File" + excelFile + "has been correctly saved");

	}

	/**
	 * This method appends a new sheet corresponding to courses year of study.
	 * Then it writes the year of study sheet template, with all headers
	 * informations, the 2 semester arrays and comment text zone. It also
	 * configures cells format. At the end it calls WriteSemesterCourses to fill
	 * the Arrays. We assume that all the courses of firstSemesterCourses and
	 * secondSemesterCourses correspond to the courses of the same year of
	 * study. We assume that the corresponding sheet doesn't exist yet.
	 */
	public void WriteCoursesOfYear() throws Exception {

		// Design handling
		Font headerBoldFont = new Font("Calibri", StyleTypeDefinitions.FontStyle.BOLD, 12, Color.BLACK);
		Font headerFont = new Font("Calibri", StyleTypeDefinitions.FontStyle.REGULAR, 12, Color.BLACK);
		Font cellContentFont = new Font("Calibri", StyleTypeDefinitions.FontStyle.REGULAR, 11, Color.BLACK);
		Border border1 = new Border(Color.BLACK, 1, StyleTypeDefinitions.SupportedLinearMeasure.PT);
		Border border2 = new Border(Color.BLACK, 2, StyleTypeDefinitions.SupportedLinearMeasure.PT);
		Color headerColor = new Color(224, 224, 224);

		int firstArraySize = this.firstSemesterCourses.size();
		int secondArraySize = this.secondSemesterCourses.size();

		// Adding new sheeth
		Table sheet = this.workbook.appendSheet(firstSemesterCourses.get(0).getYearOfStud());
		int yearBegin = Course.getYearBegin();
		int yearEnd = yearBegin + 1;
		// Creating template
		// Header
		sheet.getCellByPosition("B1").setFont(headerBoldFont);
		sheet.getCellByPosition("B1").setStringValue(this.completeYearOfStudyName);
		sheet.getCellRangeByPosition("B1", "E1").merge();
		sheet.getCellByPosition("B1").setCellBackgroundColor(headerColor);
		sheet.getCellByPosition("B1").setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
		sheet.getCellByPosition("B1").setTextWrapped(true);

		sheet.getCellByPosition("G1").setFont(headerFont);
		sheet.getCellByPosition("G1").setStringValue("prévision " + yearBegin + "/" + yearEnd);
		sheet.getCellRangeByPosition("G1", "I1").merge();
		sheet.getCellByPosition("G1").setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
		sheet.getCellByPosition("G1").setTextWrapped(true);

		sheet.getCellByPosition("J1").setFont(headerFont);
		sheet.getCellByPosition("J1").setStringValue(": " + studentsNumber + " étudiants");
		sheet.getCellRangeByPosition("J1", "L1").merge();
		sheet.getCellByPosition("J1").setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
		sheet.getCellByPosition("J1").setTextWrapped(true);

		sheet.getCellByPosition("B2").setFont(headerBoldFont);
		sheet.getCellByPosition("B2").setStringValue("ENSEIGNEMENTS SEMESTRE " + firstSemesterNumber);
		sheet.getCellRangeByPosition("B2", "N2").merge();
		sheet.getCellByPosition("B2").setCellBackgroundColor(headerColor);
		sheet.getCellByPosition("B2").setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
		sheet.getCellByPosition("B2").setBorders(CellBordersType.ALL_FOUR, border2);
		sheet.getCellByPosition("B2").setTextWrapped(true);
		sheet.getCellByPosition("P2").setFont(headerBoldFont);
		sheet.getCellByPosition("P2").setStringValue("ENSEIGNEMENTS SEMESTRE " + (firstSemesterNumber + 1));
		sheet.getCellRangeByPosition("P2", "AB2").merge();
		sheet.getCellByPosition("P2").setCellBackgroundColor(headerColor);
		sheet.getCellByPosition("P2").setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
		sheet.getCellByPosition("P2").setBorders(CellBordersType.ALL_FOUR, border2);
		sheet.getCellByPosition("P2").setTextWrapped(true);

		// Semester Arrays

		// Arrays cells Format
		// First Array
		for (int i = 2; i <= firstArraySize + 2; i++) {
			for (int j = 1; j <= 13; j++) {
				sheet.getCellByPosition(j, i).setBorders(CellBordersType.ALL_FOUR, border1);
				sheet.getCellByPosition(j, i).setTextWrapped(true);
				sheet.getCellByPosition(j, i).setFont(cellContentFont);
				sheet.getCellByPosition(j, i).setVerticalAlignment(StyleTypeDefinitions.VerticalAlignmentType.MIDDLE);
				if (j == 1) {
					sheet.getCellByPosition(j, i)
							.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.LEFT);
					sheet.getCellByPosition(j, i).setBorders(CellBordersType.LEFT, border2);
				} else
					sheet.getCellByPosition(j, i)
							.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
				if (j == 8 || j == 13)
					sheet.getCellByPosition(j, i).setBorders(CellBordersType.RIGHT, border2);
				if (i == firstArraySize + 2)
					sheet.getCellByPosition(j, i).setBorders(CellBordersType.BOTTOM, border2);
			}
		}

		// Second Array
		for (int i = 2; i <= secondArraySize + 2; i++) {
			for (int j = 15; j <= 27; j++) {
				sheet.getCellByPosition(j, i).setBorders(CellBordersType.ALL_FOUR, border1);
				sheet.getCellByPosition(j, i).setTextWrapped(true);
				sheet.getCellByPosition(j, i).setFont(cellContentFont);
				sheet.getCellByPosition(j, i).setVerticalAlignment(StyleTypeDefinitions.VerticalAlignmentType.MIDDLE);
				if (j == 15) {
					sheet.getCellByPosition(j, i)
							.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.LEFT);
					sheet.getCellByPosition(j, i).setBorders(CellBordersType.LEFT, border2);
				} else
					sheet.getCellByPosition(j, i)
							.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
				if (j == 22 || j == 27)
					sheet.getCellByPosition(j, i).setBorders(CellBordersType.RIGHT, border2);
				if (i == secondArraySize + 2)
					sheet.getCellByPosition(j, i).setBorders(CellBordersType.BOTTOM, border2);
			}
		}

		// Arrays Header
		String[] arrayHeader = { "Matière", "code Apogée", "Responsable UE " + yearBegin + "-" + yearEnd,
				"Intervenants " + yearBegin + "-" + yearEnd, "COURS", "TD ou cours-TD", "TP ou cours-TP",
				"Nombre de groupes indicatif", "Choix Cours", "Choix TD/CMTD", "Choix TP/CMTP",
				"Nombre de TD/TP souhaités", "Nombre d'années d'enseignement de la matière" };
		for (int j = 1; j <= 13; j++) {
			sheet.getCellByPosition(j, 2).setStringValue(arrayHeader[j - 1]);
			sheet.getCellByPosition(j, 2).setCellBackgroundColor(headerColor);
		}
		for (int j = 15; j <= 27; j++) {
			sheet.getCellByPosition(j, 2).setStringValue(arrayHeader[j - 15]);
			sheet.getCellByPosition(j, 2).setCellBackgroundColor(headerColor);
		}
		// Comment text zone
		sheet.getCellByPosition("B" + (firstArraySize + 5)).setTextWrapped(true);
		sheet.getCellByPosition("B" + (firstArraySize + 5)).setFont(cellContentFont);
		sheet.getCellByPosition("B" + (firstArraySize + 5)).setStringValue("COMMENTAIRES");
		sheet.getCellRangeByPosition("C" + (firstArraySize + 5), "N" + (firstArraySize + 9)).merge();

		sheet.getCellByPosition("P" + (secondArraySize + 5)).setTextWrapped(true);
		sheet.getCellByPosition("P" + (secondArraySize + 5)).setFont(cellContentFont);
		sheet.getCellByPosition("P" + (secondArraySize + 5)).setStringValue("COMMENTAIRES");
		sheet.getCellRangeByPosition("Q" + (secondArraySize + 5), "AB" + (secondArraySize + 9)).merge();

		this.workbook.save(this.excelFile);

		LOGGER.info("File" + excelFile + "has been correctly saved");

		// To fill the Arrays with courses informations
		WriteSemesterCourses(this.firstSemesterCourses, 1, 3);
		WriteSemesterCourses(this.secondSemesterCourses, 15, 3);
	}

	// Setters & Getters

	public String getCompleteYearOfStudyName() {
		return completeYearOfStudyName;
	}

	public void setCompleteYearOfStudyName(String completeYearOfStudyName) {
		this.completeYearOfStudyName = completeYearOfStudyName;
	}

	public int getStudentsNumber() {
		return studentsNumber;
	}

	public void setStudentsNumber(int studentsNumber) {
		this.studentsNumber = studentsNumber;
	}

	public int getFirstSemesterNumber() {
		return firstSemesterNumber;
	}

	public void setFirstSemesterNumber(int firstSemesterNumber) {
		this.firstSemesterNumber = firstSemesterNumber;
	}

	public List<Course> getFirstSemesterCourses() {
		return firstSemesterCourses;
	}

	public void setFirstSemesterCourses(List<Course> firstSemesterCourses) {
		this.firstSemesterCourses = firstSemesterCourses;
	}

	public List<Course> getSecondSemesterCourses() {
		return secondSemesterCourses;
	}

	public void setSecondSemesterCourses(List<Course> secondSemesterCourses) {
		this.secondSemesterCourses = secondSemesterCourses;
	}

	public File getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	public SpreadsheetDocument getWorkbook() {
		return workbook;
	}
}