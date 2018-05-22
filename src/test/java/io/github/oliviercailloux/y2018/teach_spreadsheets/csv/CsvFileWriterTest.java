package io.github.oliviercailloux.y2018.teach_spreadsheets.csv;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;

public class CsvFileWriterTest {

	@SuppressWarnings("unused")
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
		String filename = "src/test/resources/io/github/oliviercailloux/y2018/teach_spreadsheets/oneCourseWritten.csv";

		try (Writer fileWriter = new FileWriter(new File(filename))) {
			CsvFileWriter.writeInCSV(expected, fileWriter);
		}

		try (Reader fileReader = new FileReader(new File(filename))) {
			CsvFileReader.readCourseCSVfile(fileReader, actual);

		}

		Assert.assertTrue(actual.get(0).equals((expected.get(0))));
	}

	@After
	public void deleteTestFile() {
		File f = new File("src/test/resources/io/github/oliviercailloux/y2018/teach_spreadsheets/oneCourseWritten.csv");
		if (f.exists())
			f.delete();
	}

}
