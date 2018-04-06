package io.github.oliviercailloux.y2018.teach_spreadsheets.csv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;

public class Main {

	/**
	 * @author tuannamdavaux, kantoki <br>
	 *         </br>
	 *         Show start-courses.csv content obtained with CsvFileReader.
	 */
	public static void main(String[] args)
			throws NumberFormatException, FileNotFoundException, IllegalArgumentException, IOException {
		List<Course> courses2 = new ArrayList<>();
		System.out.println("\n");
		ClassLoader classLoader = Main.class.getClassLoader();
		URL ressourceUrl = classLoader.getResource("start-courses.csv");
		System.out.println(ressourceUrl.getPath());
		// CsvFileReader.readCourseCSVfile(ressourceUrl.getPath(), courses2);
		// System.out.println(courses2);

	}

}
