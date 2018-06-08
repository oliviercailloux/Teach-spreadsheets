package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Choice;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CoursePref;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CourseSheet;

public class WritePrefTest {

	@Test
	public void writeSemesterCoursesPrefTest() throws Exception {
		List<CoursePref> semestre1 = new ArrayList<>();
		List<CoursePref> semestre2 = new ArrayList<>();

		String sheetName = "DE1";

		Course course0 = new Course("PRE-RENTREE : " + "Mathématiques",
				"A1PREMA", sheetName, "Pasquignon",
				"Huveneers" + "Lamboley" + "Vialard" + "Legendre", 1);
		course0.setCMTD_Hour(15);
		course0.setGrpsNumber("6 CMTD");

		semestre1.add(new CoursePref(course0));

		Course course8 = new Course(
				"Analyse 2", "A1DEM08", sheetName, "Lebourg", "Lebourg CM"
						+ "Rammal" + "Schaison" + "Hadikhanloo" + "Massetti",
				1);
		course8.setCM_Hour(19.5);
		course8.setCMTD_Hour(39);
		course8.setGrpsNumber("6");

		semestre2.add(new CoursePref(course8));

		CoursePref coursePref = semestre1.get(0);
		coursePref.setTdChoice(Choice.B);
		coursePref.setNbrGrpTd(2);
		coursePref.setNbrExp(5);

		CoursePref coursePref2 = semestre2.get(0);
		coursePref2.setCmChoice(Choice.A);
		coursePref2.setTdChoice(Choice.B);
		coursePref2.setNbrGrpCm(1);
		coursePref2.setNbrGrpTd(2);
		coursePref2.setNbrExp(5);

		CourseSheet courseSheet = new CourseSheet(sheetName,
				"1ère année de licence", 200, 1, semestre1, semestre2);

		try (InputStream is = WritePrefTest.class
				.getResourceAsStream("Saisie_voeux_dauphine_WritePref.ods")) {
			ByteArrayOutputStream tmpWriter = new ByteArrayOutputStream();

			WritePref writer = new WritePref(is, tmpWriter, courseSheet);

			writer.writeSheetCoursesPref();

			// If no exceptions are thrown, then the writing works.

			Assert.assertTrue(true);
		}
	}
}
