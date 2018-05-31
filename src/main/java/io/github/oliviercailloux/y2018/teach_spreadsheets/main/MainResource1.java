package io.github.oliviercailloux.y2018.teach_spreadsheets.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;
import io.github.oliviercailloux.y2018.teach_spreadsheets.csv.CsvFileReader;

public class MainResource1 {

	/**
	 * @author tuannamdavaux, kantoki <br>
	 *         </br>
	 *         Show start-courses.csv content obtained with CsvFileReader.
	 */
	public static void main(String[] args)
			throws NumberFormatException, FileNotFoundException, IllegalArgumentException, IOException {
		List<Course> courses2 = new ArrayList<>();

		String filename = "start-courses.csv";
		String inputStream = IOUtils.toString(MainResource1.class.getResourceAsStream(filename),
				StandardCharsets.UTF_8);

		Reader stringReader = new StringReader(inputStream);

		CsvFileReader.readCourseCSVfile(stringReader, courses2);

		System.out.println(courses2);

	}

}
