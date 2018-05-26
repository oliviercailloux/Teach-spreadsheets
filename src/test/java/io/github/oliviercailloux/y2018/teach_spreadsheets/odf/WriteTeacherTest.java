package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Teacher;
import io.github.oliviercailloux.y2018.teach_spreadsheets.csv.CsvFileReader;

public class WriteTeacherTest {

	private static Teacher teacher;

	@Before
	public void setUp() throws URISyntaxException, FileNotFoundException, IOException {

		String filename = "oneTeacherTest.csv";

		List<Teacher> teachers = new ArrayList<>();

		File file = new File(WriteTeacherTest.class.getResource(filename).toURI());

		try (Reader fileReader = new FileReader(file)) {
			CsvFileReader.readTeachersFromCSVfile(fileReader, teachers);
		}

		teacher = teachers.get(0);

	}

	@Test
	public void writeTest() throws Exception {

		File file = new File(WriteTeacherTest.class.getResource("Saisie_voeux_dauphine_WriteTeacher.ods").toURI());

		WriteTeacher writeTeacher = new WriteTeacher(file);

		writeTeacher.write(teacher);

		Assert.assertTrue(true);

	}
}
