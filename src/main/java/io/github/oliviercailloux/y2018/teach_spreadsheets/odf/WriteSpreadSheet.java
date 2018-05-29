package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CourseSheet;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Teacher;

public class WriteSpreadSheet {

	private final static Logger LOGGER = LoggerFactory.getLogger(WriteSpreadSheet.class);

	public static void writeSpreadSheet(File destination, List<CourseSheet> courseSheetList, Teacher teacher)
			throws Exception {

		WriteTeacher writeTeacher = new WriteTeacher(destination);
		writeTeacher.write(teacher);

		for (CourseSheet courseSheet : courseSheetList) {
			WriteCourseSheet.writeCourseSheet(destination, courseSheet);
		}

		LOGGER.info("The spreadsheet has been written successfully");
	}

	public static void main(String[] args) {

	}

}
