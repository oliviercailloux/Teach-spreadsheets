
package io.github.oliviercailloux.y2018.teach_spreadsheets.csv;

import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
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
		String inputStream = IOUtils.toString(
				CsvFileReaderTest.class.getResourceAsStream(filename),
				StandardCharsets.UTF_8);

		Reader stringReader = new StringReader(inputStream);

		List<Course> actual = new ArrayList<>();
		CsvFileReader.readCourseCSVfile(stringReader, actual);

		Assert.assertTrue(actual.get(0).equals((expected.get(0))));
	}

	@Test
	public void testReadTeachersFromCSVfile() throws Exception {

		Teacher expected = new Teacher();
		expected.setId(1);
		expected.setAdress("52 rue des laurents");
		expected.setCity("Paris");
		expected.setDauphineMail("toto.dupont@dauphine.eu");
		expected.setDauphinePhone("0154669563");
		expected.setDesk("B14");
		expected.setFirstName("toto");
		expected.setMobilePhone("0645895632");
		expected.setName("dupont");
		expected.setPersonalMail("toto.dupont@gmail.com");
		expected.setPersonalPhone("0145895522");
		expected.setPostCode("75016");
		expected.setStatus("maitre de conférence");

		String filename = "oneTeacherTest.csv";

		Teacher actual = null;

		String inputStream = IOUtils.toString(
				CsvFileReaderTest.class.getResourceAsStream(filename),
				StandardCharsets.UTF_8);

		Reader stringReader = new StringReader(inputStream);
		actual = CsvFileReader.readTeacherFromCSVfile(stringReader);

		Assert.assertTrue(actual.equals((expected)));
	}
}
