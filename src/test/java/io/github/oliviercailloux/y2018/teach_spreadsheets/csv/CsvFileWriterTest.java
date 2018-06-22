package io.github.oliviercailloux.y2018.teach_spreadsheets.csv;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;

public class CsvFileWriterTest {

	@Test
	public void testWriteCourseCSVfile() throws Exception {

		List<Course> expected = new ArrayList<>();

		Course course = new Course();
		course.setName("Statistiques");
		course.setapogeeCode("14");
		course.setYearOfStud("2008");
		course.setCM_Hour(8);
		course.setTD_Hour(4);
		course.setCMTD_Hour(8);
		course.setTP_Hour(8);
		course.setGrpsNumber("8");

		expected.add(course);

		List<Course> actual = new ArrayList<>();

		Writer stringWriter = new StringWriter();

		CsvFileWriter.writeInCSV(expected, stringWriter);

		Reader stringReader = new StringReader(stringWriter.toString());

		CsvFileReader.readCourseCSVfile(stringReader, actual);

		Assert.assertTrue(actual.equals(expected));
	}

}
