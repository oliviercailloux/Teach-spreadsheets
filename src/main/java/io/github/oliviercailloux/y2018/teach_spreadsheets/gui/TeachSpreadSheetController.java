package io.github.oliviercailloux.y2018.teach_spreadsheets.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.List;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CourseSheet;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Teacher;
import io.github.oliviercailloux.y2018.teach_spreadsheets.csv.CsvFileReader;
import io.github.oliviercailloux.y2018.teach_spreadsheets.odf.ReadCourses;

public class TeachSpreadSheetController {

	private final static String template = "Saisie_voeux_dauphine_Template.ods";

	private File destination;

	private List<CourseSheet> courseSheetList;

	public TeachSpreadSheetController(File destination,
			List<CourseSheet> courseSheetList) {
		super();
		this.destination = destination;
		this.courseSheetList = courseSheetList;
	}

	public static void main(String[] args) throws Exception {

		String teacherPath = "C:\\Users\\lf947\\Documents\\JAVA\\L3_MIAGE\\JAVA\\WORKSPACE_1\\Projets\\Teach-spreadsheets\\src\\test\\resources\\io\\github\\oliviercailloux\\y2018\\teach_spreadsheets\\odf\\oneTeacherTest.csv";
		String spreadSheetInitialPath = "C:\\Users\\lf947\\Documents\\JAVA\\L3_MIAGE\\JAVA\\WORKSPACE_1\\Projets\\Teach-spreadsheets\\src\\main\\resources\\io\\github\\oliviercailloux\\y2018\\teach_spreadsheets\\odf\\Saisie_voeux_dauphine.ods";

		// Get teacher
		FileReader fileReader = new FileReader(teacherPath);
		CsvFileReader teacherReader = new CsvFileReader();
		Teacher teacher = CsvFileReader.readTeacherFromCSVfile(fileReader);

		// Read courses from spreadsheet

		ReadCourses courseReader = new ReadCourses(
				new FileInputStream(spreadSheetInitialPath));

		List<CourseSheet> currentCourseSheet = courseReader.readCourseSheets();

		//

	}

}
