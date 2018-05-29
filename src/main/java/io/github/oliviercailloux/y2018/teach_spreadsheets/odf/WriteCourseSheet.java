package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CourseSheet;

public class WriteCourseSheet {

	private final static Logger LOGGER = LoggerFactory.getLogger(WriteCourseSheet.class);

	public static void writeCourseSheet(File odfFile, CourseSheet courseSheet) throws Exception {
		WriteCourses writer = new WriteCourses(odfFile, courseSheet);
		writer.writeCoursesOfYear();

		WritePref prefWriter = new WritePref(odfFile, courseSheet);
		prefWriter.writeSheetCoursesPref();

	}
}
