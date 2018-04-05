package io.github.oliviercailloux.y2018.teach_spreadsheets.java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
		ressources1();

	}

	/**
	 * @author tuannamdavaux, kantoki <br>
	 *         </br>
	 *         Show start-courses.csv content obtained with CsvFileReader.
	 */
	private static void ressources1()
			throws IOException, FileNotFoundException {
		List<Course> courses = new ArrayList<>();

		Course c1 = new Course("Probabilité", "14", "2008");
		c1.setCM_Hour(8);
		c1.setTD_Hour(4);
		c1.setCMTD_Hour(8);
		c1.setTP_Hour(8);
		c1.setNbGrpCM(8);
		c1.setNbGrpTD(2);
		c1.setNbGrpCMTD(7);
		c1.setNbGrpTP(5);

		Course c2 = new Course("Statistiques", "14", "2008");
		c2.setCM_Hour(8);
		c2.setTD_Hour(4);
		c2.setCMTD_Hour(8);
		c2.setTP_Hour(8);
		c2.setNbGrpCM(8);
		c2.setNbGrpTD(2);
		c2.setNbGrpCMTD(7);
		c2.setNbGrpTP(5);

		courses.add(c1);
		courses.add(c2);

		List<Course> courses2 = new ArrayList<>();
		System.out.println("\n");
		CsvFileReader.readCourseCSVfile(
				"src\\io\\github\\oliviercailloux\\y2018\\teach_spreadsheets\\ressources\\start-courses.csv",
				courses2);
		System.out.println(courses2);
	}

}
