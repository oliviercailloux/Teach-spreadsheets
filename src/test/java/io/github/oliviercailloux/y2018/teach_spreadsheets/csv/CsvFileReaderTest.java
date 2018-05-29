
package io.github.oliviercailloux.y2018.teach_spreadsheets.csv;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Teacher;

public class CsvFileReaderTest {

	@Test
	public void testReadCourseCSVfile() throws Exception {

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

		String filename = "oneCourseTest.csv";
		File file = new File(CsvFileReaderTest.class.getResource(filename).toURI());
		List<Course> actual = new ArrayList<>();
		try (Reader fileReader = new FileReader(file)) {
			CsvFileReader.readCourseCSVfile(fileReader, actual);

		}

		Assert.assertTrue(actual.get(0).equals((expected.get(0))));
	}

	@Test
	public void testReadTeachersFromCSVfile() throws Exception {

		Teacher expected = new Teacher(1, "dupont", "toto", "52 rue des laurents", "75016", "Paris", "0154669563",
				"0645895632", "toto.dupont@gmail.com", "toto.dupont@dauphine.eu", "maitre de conférence", "0145895522",
				"B14");

		String filename = "oneTeacherTest.csv";

		Teacher actual = null;

		File file = new File(CsvFileReaderTest.class.getResource(filename).toURI());
		try (Reader fileReader = new FileReader(file)) {
			actual = CsvFileReader.readTeacherFromCSVfile(fileReader);
		}

		Assert.assertTrue(actual.equals((expected)));
	}
}
