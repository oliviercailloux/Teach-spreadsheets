package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CourseSheet;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Teacher;

public class SpreadSheetWriter {

	private final static Logger LOGGER = LoggerFactory.getLogger(SpreadSheetWriter.class);

	public static void writeSpreadSheet(InputStream source, OutputStream destination, List<CourseSheet> courseSheetList,
			Teacher teacher) throws Exception {

		try (SpreadsheetDocument spreadsheetDocument = SpreadsheetDocument.loadDocument(source)) {

			TeacherWriter teacherWriter = new TeacherWriter(spreadsheetDocument, destination);
			boolean save = false;
			teacherWriter.write(teacher, save);

			save = true;

			CourseSheetWriter.writeCourseSheets(spreadsheetDocument, destination, courseSheetList, save);

			LOGGER.info("The spreadsheet has been written successfully");
		}
	}

}
