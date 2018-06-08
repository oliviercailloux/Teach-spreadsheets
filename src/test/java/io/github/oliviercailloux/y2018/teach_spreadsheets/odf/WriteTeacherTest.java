package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Teacher;
import io.github.oliviercailloux.y2018.teach_spreadsheets.csv.CsvFileReader;

public class WriteTeacherTest {

	private static Teacher teacher;

	@Before
	public void setUp()
			throws URISyntaxException, FileNotFoundException, IOException {

		String filename = "oneTeacherTest.csv";

		File file = new File(
				WriteTeacherTest.class.getResource(filename).toURI());

		try (Reader fileReader = new FileReader(file)) {
			teacher = CsvFileReader.readTeacherFromCSVfile(fileReader);
		}

	}

	@Test
	public void writeTest() throws Exception {

		try (InputStream is = WriteTeacherTest.class.getResourceAsStream(
				"Saisie_voeux_dauphine_WriteTeacher.ods")) {

			ByteArrayOutputStream tmpWriter = new ByteArrayOutputStream();

			WriteTeacher writeTeacher = new WriteTeacher(is, tmpWriter);

			writeTeacher.write(teacher);

			Assert.assertTrue(true);

		}
	}
}
