package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CourseSheet;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Teacher;

public class WriteSpreadSheet {

	private final static Logger LOGGER = LoggerFactory.getLogger(WriteSpreadSheet.class);

	public static void writeSpreadSheet(InputStream source, OutputStream destination, List<CourseSheet> courseSheetList,
			Teacher teacher) throws Exception {

		WriteTeacher writeTeacher = new WriteTeacher(source, destination);
		writeTeacher.write(teacher);
		writeTeacher.close();

		WriteCourseSheet.writeCourseSheets(source, destination, courseSheetList);

		LOGGER.info("The spreadsheet has been written successfully");
	}

}
