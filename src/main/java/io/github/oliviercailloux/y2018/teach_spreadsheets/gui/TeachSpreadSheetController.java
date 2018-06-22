package io.github.oliviercailloux.y2018.teach_spreadsheets.gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CoursePref;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CourseSheet;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Teacher;
import io.github.oliviercailloux.y2018.teach_spreadsheets.csv.CsvFileReader;
import io.github.oliviercailloux.y2018.teach_spreadsheets.odf.CourseReader;
import io.github.oliviercailloux.y2018.teach_spreadsheets.odf.SpreadSheetWriter;

/**
 * This class implements all the methods used by {@link GUIPref}. In a MVC
 * model, this class represents the Controller part.
 *
 */
public class TeachSpreadSheetController {

	private final static String TEMPLATE = "Template_Saisie_voeux_dauphine.ods";

	private final static Logger LOGGER = LoggerFactory
			.getLogger(TeachSpreadSheetController.class);

	private Teacher teacher = null;

	private InputStream source;

	private OutputStream destination;

	private List<CourseSheet> courseSheetList;

	public TeachSpreadSheetController(InputStream source,
			OutputStream destination) {
		this.source = source;
		this.destination = destination;
	}

	public TeachSpreadSheetController(InputStream teacherPath)
			throws FileNotFoundException, IOException {
		// Get teacher
		try (Reader fileReader = new InputStreamReader(teacherPath)) {
			this.teacher = CsvFileReader.readTeacherFromCSVfile(fileReader);
		}

	}

	// Main functions

	private void writeSpreadsheet() throws Exception {
		try (InputStream template = TeachSpreadSheetController.class
				.getResourceAsStream(TEMPLATE)) {
			SpreadSheetWriter.writeSpreadSheet(template, destination,
					courseSheetList, this.teacher);
		}
	}

	/**
	 * This method reads the courses sheets from the source
	 */
	private List<CourseSheet> getCourseSheets() throws Exception {
		List<CourseSheet> courseSheets = new ArrayList<>();
		try (CourseReader courseReader = new CourseReader(this.source)) {
			courseSheets = courseReader.readCourseSheets();
		}

		return courseSheets;
	}

	public String getTeacherName() {
		return teacher.getFirstName() + " " + teacher.getName();
	}

	/**
	 * This method gets all the semesters of a course sheet
	 */
	public List<Integer> getSemesters(String yearName) {
		return this.getCourseSheetByYear(yearName).getSemesters();
	}

	/**
	 * This method gets the list of the study year names
	 */
	public List<String> getYearNames() {
		List<String> yearNames = new ArrayList<>();

		for (CourseSheet courseSheet : courseSheetList) {
			yearNames.add(courseSheet.getYearOfStud());
		}
		return yearNames;
	}

	private CourseSheet getCourseSheetByYear(String yearName) {
		for (CourseSheet courseSheet : courseSheetList) {
			if (courseSheet.getYearOfStud().equals(yearName)) {
				return courseSheet;
			}
		}
		return null;
	}

	/**
	 * This method gets the list of all the courses of a year and a semester
	 */
	public List<String> getCoursesName(String yearName, int semester) {
		CourseSheet courseSheet = getCourseSheetByYear(yearName);
		return courseSheet.getCoursesName(semester);
	}

	/**
	 * This method gets the list of all the choices of a course
	 */
	public List<String> getPossibleChoice(String yearName, int semester,
			String courseName) {
		CourseSheet courseSheet = getCourseSheetByYear(yearName);
		return courseSheet.getPossibleChoice(semester, courseName);
	}

	/**
	 * This method is used after a submit of a course preference. It updates the
	 * list of course preferences.
	 */
	public void updatePref(CoursePref coursePref) {

		CoursePref updatedCoursePref = this.getCoursePref(
				coursePref.getCourse().getYearOfStud(),
				coursePref.getCourse().getSemester(),
				coursePref.getCourse().getName());

		updatedCoursePref.setCmChoice(coursePref.getCmChoice());
		updatedCoursePref.setTdChoice(coursePref.getTdChoice());
		updatedCoursePref.setTpChoice(coursePref.getTpChoice());
		LOGGER.info("Your preferences have been saved");

	}

	private CoursePref getCoursePref(String yearName, int semester,
			String courseName) {
		CourseSheet courseSheet = getCourseSheetByYear(yearName);

		if (semester % 2 == 1) {
			for (CoursePref coursePref : courseSheet.getCoursePrefS1()) {
				if (coursePref.getCourse().getName().equals(courseName)) {
					return coursePref;
				}
			}
		} else if (semester % 2 == 0) {
			for (CoursePref coursePref : courseSheet.getCoursePrefS2()) {
				if (coursePref.getCourse().getName().equals(courseName)) {
					return coursePref;
				}
			}
		}
		return null;
	}

	public InputStream getSource() {
		return source;
	}

	public void setSource(InputStream source) throws Exception {
		this.source = Objects.requireNonNull(source);
		this.courseSheetList = this.getCourseSheets();
	}

	public OutputStream getDestination() {
		return destination;
	}

	public void setDestination(OutputStream destination) throws Exception {
		this.destination = Objects.requireNonNull(destination);
		this.writeSpreadsheet();
	}

	public List<CourseSheet> getCourseSheetList() {
		return courseSheetList;
	}

}
