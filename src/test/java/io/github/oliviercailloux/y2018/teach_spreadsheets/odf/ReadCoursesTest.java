package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.odftoolkit.simple.SpreadsheetDocument;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;

/**
 * Tests to check that the methods from ReadCourses class work
 * 
 * @author Victor CHEN (Kantoki), Louis FONTAINE (fontlo15)
 * @version Version 2.0 Last Update : 14/05/2018.
 * 
 */

public class ReadCoursesTest {

	/**
	 * Test to check that the method iterates on all courses of the sheet if we specify which sheet will be in the ODSReader
	 */
	@Test
	public void testReadCourses() throws Exception {
		try (InputStream is = ODSReaderTest.class.getClassLoader()
				.getResourceAsStream("io/github/oliviercailloux/y2018/teach_spreadsheets/Saisie_voeux_dauphine.ods")) {
			try (SpreadsheetDocument sd = SpreadsheetDocument.loadDocument(is)) {
				String yearOfStudy = "L3_Informatique";
				ODSReader odsR = new ODSReader(sd, yearOfStudy);

				ReadCourses rd = new ReadCourses(odsR);
				List<Course> courses;
				courses = rd.readCoursesFromCell("B4");
				int nbCourses = courses.size();

				Assert.assertEquals(11, nbCourses);
			}
		}
	}

	/**
	 * Test to check that the method retrives the right courses
	 */
	@Test
	public void testSameCourseSheetAndReadCourses() throws Exception {
		try (InputStream is = ODSReaderTest.class.getClassLoader()
				.getResourceAsStream("io/github/oliviercailloux/y2018/teach_spreadsheets/Saisie_voeux_dauphine.ods")) {
			try (SpreadsheetDocument sd = SpreadsheetDocument.loadDocument(is)) {
				String yearOfStudy = "L3_Informatique";
				ODSReader odsR = new ODSReader(sd, yearOfStudy);

				ReadCourses rd = new ReadCourses(odsR);
				List<Course> courses;
				courses = rd.readCoursesFromCell("B4");

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

				Assertions.assertThat(c1).isEqualToComparingFieldByFieldRecursively(c2);
			}
		}
	}

	/**
	 * Test to check that the method retrives the right courses from a specified
	 * sheet
	 */
	@Test
	public void testReadCoursesFromSpecifiedSheet() throws Exception {
		try (InputStream is = ODSReaderTest.class.getClassLoader().getResourceAsStream(
				"io/github/oliviercailloux/y2018/teach_spreadsheets/Saisie_voeux_dauphine_TestForReadCourses_SingleSheet.ods")) {
			try (SpreadsheetDocument sd = SpreadsheetDocument.loadDocument(is)) {
				String yearOfStudy = "L3_Informatique";
				ODSReader odsR = new ODSReader(sd, yearOfStudy);

				ReadCourses rd = new ReadCourses(odsR);
				List<Course> courses;
				courses = rd.readCoursesFromSheet(odsR.getSheet());

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
				c2.setGrpNbr("");

				Assertions.assertThat(c1).isEqualToComparingFieldByFieldRecursively(c2);
			}
		}

	}

	/**
	 * Test to check that the method retrives the right courses from every sheet
	 */
	@Test
	public void testReadCoursesFromEverySheet() throws Exception {
		try (InputStream is = ODSReaderTest.class.getClassLoader().getResourceAsStream(
				"io/github/oliviercailloux/y2018/teach_spreadsheets/Saisie_voeux_dauphine_TestForReadCourses_TwoSheets.ods")) {
			try (SpreadsheetDocument sd = SpreadsheetDocument.loadDocument(is)) {
				ODSReader odsR = new ODSReader(sd);

				ReadCourses rd = new ReadCourses(odsR);
				List<Course> courses;
				courses = rd.readCourses();

				Course c1 = new Course();
				Course c2 = new Course();
				for (Course c : courses) {
					if (c.getName().equals("Pré-rentrée : Mise à niveau Probabilités")
							&& c.getYearOfStud().equals("L3_Informatique")) {
						c1 = c;
					}
					if (c.getName().equals("Introduction à la microéconomie") && c.getYearOfStud().equals("DE1")) {
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
				c3.setGrpNbr("");

				Course c4 = new Course();
				c4.setName("Introduction à la microéconomie");
				c4.setYearOfStud("DE1");
				c4.setapogeeCode("A1DEM04");
				c4.setSupervisor("De Vreyer");
				c4.setTeachers("De Vreyer CM\n" + "Brembilla\n" + "Laffineur\n" + "Morcillo");
				c4.setCM_Hour(19.5);
				c4.setTD_Hour(19.5);
				c4.setCMTD_Hour(0);
				c4.setTP_Hour(0);
				c4.setCMTP_Hour(0);
				c4.setGrpNbr("6 TD");

				Assertions.assertThat(c1).isEqualToComparingFieldByFieldRecursively(c3);
				Assertions.assertThat(c2).isEqualToComparingFieldByFieldRecursively(c4);
			}
		}

	}

}
