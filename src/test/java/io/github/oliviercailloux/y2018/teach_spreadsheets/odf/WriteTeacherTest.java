package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.odftoolkit.simple.SpreadsheetDocument;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Teacher;
import io.github.oliviercailloux.y2018.teach_spreadsheets.csv.CsvFileReader;

public class WriteTeacherTest {

	private static Teacher teacher;

	@Before
	public void setUp() throws FileNotFoundException, IOException {

		String filename = "oneTeacherTest.csv";

		String inputStream = IOUtils.toString(WriteTeacherTest.class.getResourceAsStream(filename),
				StandardCharsets.UTF_8);

		Reader stringReader = new StringReader(inputStream);

		teacher = CsvFileReader.readTeacherFromCSVfile(stringReader);

	}

	@Test
	public void writeTest() throws Exception {

		try (InputStream is = WriteTeacherTest.class.getResourceAsStream("Saisie_voeux_dauphine_WriteTeacher.ods")) {

			ByteArrayOutputStream tmpWriter = new ByteArrayOutputStream();

			WriteTeacher writeTeacher = new WriteTeacher(SpreadsheetDocument.loadDocument(is), tmpWriter);

			writeTeacher.write(teacher, true);

			Assert.assertTrue(true);

		}
	}
}
