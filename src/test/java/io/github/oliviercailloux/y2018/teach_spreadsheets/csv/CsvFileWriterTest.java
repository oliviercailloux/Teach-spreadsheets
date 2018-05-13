package io.github.oliviercailloux.y2018.teach_spreadsheets.csv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.After;
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
		course.setGrpNbr("8");
		expected.add(course);

		String filename = "src/test/resources/oneCourseWritten.csv";

		File file = new File(filename);
		List<Course> actual = new ArrayList<>();

		CsvFileWriter.writeInCSV(expected, file);

		CsvFileReader.readCourseCSVfile(file, actual);

		// assertThat().equals() ne fonctionne pas car ce ne sont pas les mêmes
		// instances de classe
		Assertions.assertThat(actual.get(0)).isEqualToComparingFieldByField(expected.get(0));
	}

	@After
	public void deleteTestFile() {
		File f = new File("src/test/resources/oneCourseWritten.csv");
		if (f.exists())
			f.delete();
	}

}
