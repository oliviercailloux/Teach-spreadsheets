package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CourseSheet;

public class WriteCourseSheet {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(WriteCourseSheet.class);

	public static void writeCourseSheet(InputStream source,
			OutputStream destination, CourseSheet courseSheet)
			throws Exception {
		WriteCourses writer = new WriteCourses(source, destination,
				courseSheet);
		writer.writeCoursesOfYear();

		WritePref prefWriter = new WritePref(source, destination, courseSheet);
		prefWriter.writeSheetCoursesPref();

	}
}
