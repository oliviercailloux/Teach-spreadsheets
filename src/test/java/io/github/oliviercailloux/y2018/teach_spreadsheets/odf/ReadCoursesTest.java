package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.odftoolkit.simple.table.Table;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;

/**
 * Tests to check that the methods from CourseReader class work
 * 
 * @author Victor CHEN (Kantoki), Louis FONTAINE (fontlo15)
 * @version Version 2.0 Last Update : 14/05/2018.
 * 
 */

public class ReadCoursesTest {

	/**
	 * Test to check that the method iterates on all courses of the sheet
	 */
	@Test
	public void testReadCourses() throws Exception {
		try (InputStream is = ReadCoursesTest.class
				.getResourceAsStream("Saisie_voeux_dauphine.ods")) {
			String yearOfStudy = "L3_Informatique";

			CourseReader rd = new CourseReader(is);
			Table currentTable = rd.getReader().getDocument()
					.getTableByName(yearOfStudy);
			List<Course> courses;
			courses = rd.readCoursesFromCell("B4", currentTable);
			int nbCourses = courses.size();

			Assert.assertEquals(11, nbCourses);
		}
	}

	/**
	 * Test to check that the method retrieves the right courses
	 */
	@Test
	public void testSameCourseSheetAndReadCourses() throws Exception {

		try (InputStream is = ReadCoursesTest.class
				.getResourceAsStream("Saisie_voeux_dauphine.ods")) {
			String yearOfStudy = "L3_Informatique";

			CourseReader rd = new CourseReader(is);
			Table currentTable = rd.getReader().getDocument()
					.getTableByName(yearOfStudy);
			List<Course> courses;
			courses = rd.readCoursesFromCell("B4", currentTable);

			Course c1 = new Course();
			for (int i = 1; i < courses.size(); i++) {
				c1 = courses.get(0);
			}

			Course c2 = new Course();
			c2.setName("Pré-rentrée : Mise à niveau Probabilités");
			c2.setYearOfStud("L3_Informatique");
			c2.setapogeeCode("A3MANPRO");
			c2.setSupervisor("Mayag");
			c2.setTeachers("Mayag");
			c2.setCM_Hour(0);
			c2.setTD_Hour(0);
			c2.setCMTD_Hour(15);
			c2.setTP_Hour(0);
			c2.setCMTP_Hour(0);
			c2.setGrpsNumber("");

			Assert.assertTrue((c1).equals(c2));
		}
	}

	/**
	 * Test to check that the method retrieves the right courses from a
	 * specified sheet
	 */
	@Test
	public void testReadCoursesFromSpecifiedSheet() throws Exception {

		try (InputStream is = ReadCoursesTest.class.getResourceAsStream(
				"Saisie_voeux_dauphine_TestForReadCourses_SingleSheet.ods")) {

			String yearOfStudy = "L3_Informatique";

			CourseReader rd = new CourseReader(is);
			Table currentTable = rd.getReader().getDocument()
					.getTableByName(yearOfStudy);

			List<Course> courses;
			courses = rd.readCoursesFromSheet(currentTable);

			Course c1 = new Course();
			for (int i = 1; i < courses.size(); i++) {
				c1 = courses.get(0);
			}

			Course c2 = new Course();
			c2.setName("Pré-rentrée : Mise à niveau Probabilités");
			c2.setYearOfStud("L3_Informatique");
			c2.setapogeeCode("A3MANPRO");
			c2.setSupervisor("Mayag");
			c2.setTeachers("Mayag");
			c2.setCM_Hour(0);
			c2.setTD_Hour(0);
			c2.setCMTD_Hour(15);
			c2.setTP_Hour(0);
			c2.setCMTP_Hour(0);
			c2.setGrpsNumber("");

			Assert.assertTrue((c1).equals(c2));
		}
	}

	/**
	 * Test to check that the method retrives the right courses from every sheet
	 */
	@Test
	public void testReadCoursesFromEverySheet() throws Exception {

		try (InputStream is = ReadCoursesTest.class.getResourceAsStream(
				"Saisie_voeux_dauphine_TestForReadCourses_TwoSheets.ods")) {

			CourseReader rd = new CourseReader(is);

			List<Course> courses;
			courses = rd.readCourses();

			Course c1 = new Course();
			Course c2 = new Course();
			for (Course c : courses) {
				if (c.getName()
						.equals("Pré-rentrée : Mise à niveau Probabilités")
						&& c.getYearOfStud().equals("L3_Informatique")) {
					c1 = c;
				}
				if (c.getName().equals("Introduction à la microéconomie")
						&& c.getYearOfStud().equals("DE1")) {
					c2 = c;
				}
			}

			Course c3 = new Course();
			c3.setName("Pré-rentrée : Mise à niveau Probabilités");
			c3.setYearOfStud("L3_Informatique");
			c3.setapogeeCode("A3MANPRO");
			c3.setSupervisor("Mayag");
			c3.setTeachers("Mayag");
			c3.setCM_Hour(0);
			c3.setTD_Hour(0);
			c3.setCMTD_Hour(15);
			c3.setTP_Hour(0);
			c3.setCMTP_Hour(0);
			c3.setGrpsNumber("");

			Course c4 = new Course();
			c4.setName("Introduction à la microéconomie");
			c4.setYearOfStud("DE1");
			c4.setapogeeCode("A1DEM04");
			c4.setSupervisor("De Vreyer");
			c4.setTeachers("De Vreyer CM\n" + "Brembilla\n" + "Laffineur\n"
					+ "Morcillo");
			c4.setCM_Hour(19.5);
			c4.setTD_Hour(19.5);
			c4.setCMTD_Hour(0);
			c4.setTP_Hour(0);
			c4.setCMTP_Hour(0);
			c4.setGrpsNumber("6 TD");

			Assert.assertTrue((c1).equals(c3));
			Assert.assertTrue((c2).equals(c4));
		}
	}

}
