package io.github.oliviercailloux.y2018.teach_spreadsheets.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;
import io.github.oliviercailloux.y2018.teach_spreadsheets.csv.CsvFileReader;

public class MainResource1 {

	/**
	 * @author tuannamdavaux, kantoki <br>
	 *         </br>
	 *         Show start-courses.csv content obtained with CsvFileReader.
	 */
	public static void main(String[] args) throws NumberFormatException,
			FileNotFoundException, IllegalArgumentException, IOException {
		List<Course> courses2 = new ArrayList<>();

		String filename = "start-courses.csv";
		InputStream inputStream = MainResource1.class
				.getResourceAsStream(filename);

		try (Reader stringReader = new InputStreamReader(inputStream)) {

			CsvFileReader.readCourseCSVfile(stringReader, courses2);

			System.out.println(courses2);
		}

	}

}
