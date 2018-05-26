package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CourseSheet;

public class WriteCoursesTest {

	@Before
	public void deleteYearOfStudySheet() throws Exception {
		try (SpreadsheetDocument workbook = SpreadsheetDocument
				.loadDocument(WriteCoursesTest.class.getResourceAsStream("Saisie_voeux_dauphine_WriteCourses.ods"))) {
			Table sheet = workbook.getTableByName("DE1");
			if (sheet != null) {
				sheet.remove();
				File file = new File(
						WriteCoursesTest.class.getResource("Saisie_voeux_dauphine_WriteCourses.ods").toURI());
				workbook.save(file);

			}
		}

	}

	@Test
	public void testWriteCoursesOfYear() throws Exception {
		List<Course> semestre1 = new ArrayList<>();
		List<Course> semestre2 = new ArrayList<>();

		String sheetName = "DE1";

		Course course0 = new Course("PRE-RENTREE : " + "Mathématiques", "A1PREMA", sheetName, "Pasquignon",
				"Huveneers" + "Lamboley" + "Vialard" + "Legendre", 1);
		course0.setCMTD_Hour(15);
		course0.setGrpsNumber("6 CMTD");

		Course course1 = new Course("Analyse1", "A1DEM01", sheetName, "Simenhaus",
				"Legendre" + "Lamboley" + "Attouchi" + "Simenhaus" + "Boussion" + "Truchot", 1);
		course1.setCMTD_Hour(72);
		course1.setGrpsNumber("6 CMTD");

		Course course2 = new Course("Algèbre linéaire 1", "A1DEM02", sheetName, "Pasquignon",
				"Tamzali-Lafond" + "Henon" + "Pasquignon" + "Taleng" + "Pasquignon" + "Vialard", 1);
		course2.setCMTD_Hour(72);
		course2.setGrpsNumber("6 CMTD");

		Course course3 = new Course("Algorithmique et programmation 1", "A1DEM03", sheetName, "Colazzo",
				"Colazzo CM" + "Raksanyi" + "Gabrel-Willemin" + "Murat" + "Manouvrier" + "Arib", 1);
		course3.setCMTD_Hour(30);
		course3.setCMTP_Hour(30);
		course3.setGrpsNumber("6 CMTD et 6 CMTP");

		Course course4 = new Course("Introduction à la microéconomie", "A1DEM04", sheetName, "De Vreyer",
				"De Vreyer CM" + "Brembilla" + "Laffineur" + "Morcillo", 1);
		course4.setCM_Hour(19.5);
		course4.setTD_Hour(19.5);
		course4.setGrpsNumber("6 TD");

		Course course6 = new Course("Problèmes économiques", "A1DEM06", sheetName, "Dieudonne", "Dieudonne", 1);
		course6.setCM_Hour(36);

		Course course7 = new Course("Organisation des entreprisess", "A1DEM07", sheetName, "Servel",
				"Carcassonne CM" + "Servel CM" + "Zouaneb" + "Diedhiou", 1);
		course7.setCM_Hour(6);
		course7.setTD_Hour(30);
		course7.setGrpsNumber("2 TD");

		Course course5 = new Course("Anglais 1", "A1DEM05", sheetName, "Bourrel",
				"Lytle-Hassan" + "Countcham" + "Joyce" + "Roque" + "Pattie" + "Whiteside" + "Levy-Alimi", 1);
		course5.setCMTD_Hour(18);
		course5.setGrpsNumber("12");

		Course courseL = new Course("LV2 (Allemand / Espagnol)", "A1ALL01" + "A1ESP01", sheetName,
				"Quinchon-Caudal" + "Amisse", "Tudoran" + "Barrera Guarin" + "Jareno Gila", 1);
		courseL.setTD_Hour(18);
		courseL.setGrpsNumber("2 all" + "3 esp");

		Course courseAS = new Course("Anglais soutien S1", "A1SOU01", sheetName, "Bourrel", "Joyce", 1);
		courseAS.setTD_Hour(19.5);
		courseAS.setGrpsNumber("1");

		semestre1.add(course0);
		semestre1.add(course1);
		semestre1.add(course2);
		semestre1.add(course3);
		semestre1.add(course4);
		semestre1.add(course6);
		semestre1.add(course7);
		semestre1.add(course5);
		semestre1.add(courseL);
		semestre1.add(courseAS);

		Course course8 = new Course("Analyse 2", "A1DEM08", sheetName, "Lebourg",
				"Lebourg CM" + "Rammal" + "Schaison" + "Hadikhanloo" + "Massetti", 1);
		course8.setCM_Hour(19.5);
		course8.setCMTD_Hour(39);
		course8.setGrpsNumber("6");

		Course course9 = new Course("Algèbre linéaire 2", "A1DEM09", sheetName, "Vialard",
				"Vialard CM" + "Rammal" + "Fu Ying" + "Coron" + "Macasieb" + "Schaison" + "Fu", 1);
		course9.setCM_Hour(19.5);
		course9.setCMTD_Hour(39);
		course9.setGrpsNumber("6");

		Course course11 = new Course("Algorithmique et programmation 2", "A1DEM11", sheetName, "Cazenave",
				"Cazenave CM" + "Jamain" + "Pontoizeau" + "Ternier" + "Labernia" + "Haddad" + "Tlilane", 1);
		course11.setCM_Hour(19.5);
		course11.setTD_Hour(19.5);
		course11.setGrpsNumber("6");

		Course course10 = new Course("Modélisation et applications des mathématiques", "A1DEM10", sheetName, "Boussion",
				"Boussion CM" + "Genadot" + "Leboucher" + "Le Cousin", 1);
		course10.setCM_Hour(19.5);
		course10.setTD_Hour(19.5);
		course10.setGrpsNumber("6");

		Course course14 = new Course("Outils en informatique" + "- Outils de l'internet", "A1DEM14", sheetName, "Mayag",
				"Mayag CM" + "Belhoul" + "Jamain" + "Magnouche" + "Arib", 1);
		course14.setCM_Hour(15);
		course14.setTP_Hour(15);
		course14.setGrpsNumber("6");

		Course course141 = new Course(
				"Outils en informatique" + "- Initiation à la recherche opérationnelle avec Excel", "A1DEM14",
				sheetName, "Mayag", "Mayag CM" + "Belhoul" + "Jamain" + "Magnouche" + "Mouhoub" + "Haddad", 1);
		course141.setCM_Hour(3);
		course141.setTP_Hour(12);
		course141.setGrpsNumber("6");

		Course course142 = new Course("Outils en informatique" + "- Bases de données élémentaires", "A1DEM14",
				sheetName, "Mayag", "Mayag CM" + "Belhoul" + "Fu Liangliang" + "Monnet" + "Arib", 1);
		course142.setCM_Hour(3);
		course142.setTP_Hour(12);
		course142.setGrpsNumber("6");

		Course course12 = new Course("Macroéconomie : analyse de long terme", "A1DEM12", sheetName, "Chagny",
				"Chagny CM" + "Lorre" + "Laffineur", 1);
		course12.setCM_Hour(19.5);
		course12.setTD_Hour(19.5);
		course12.setGrpsNumber("6");

		Course course13 = new Course("Anglais 2", "A1DEM13", sheetName, "Bourrel",
				"Lytle-Hassan" + "Countcham" + "Joyce" + "Roque" + "Pattie" + "Whiteside" + "Orgeret", 1);
		course13.setCMTD_Hour(19.5);
		course13.setGrpsNumber("12");

		Course courseL2 = new Course("LV2 (Allemand / Espagnol)", "", sheetName, "", "", 1);
		courseL2.setTD_Hour(19.5);
		courseL2.setGrpsNumber("2 all" + "3 esp");

		Course courseAS2 = new Course("Anglais soutien S2", "A1SOU02", sheetName, "Bourrel", "Joyce", 1);
		courseAS2.setTD_Hour(19.5);
		courseAS2.setGrpsNumber("1");

		Course courseD = new Course("DROIT C2I", "A0MID21", sheetName, "", "Batusanski", 1);
		courseD.setCM_Hour(18);
		courseD.setGrpsNumber("1");

		semestre2.add(course8);
		semestre2.add(course9);
		semestre2.add(course11);
		semestre2.add(course10);
		semestre2.add(course14);
		semestre2.add(course141);
		semestre2.add(course142);
		semestre2.add(course12);
		semestre2.add(course13);
		semestre2.add(courseL2);
		semestre2.add(courseAS2);
		semestre2.add(courseD);

		CourseSheet courseSheet = new CourseSheet(sheetName, "1ère année de licence", 200, 1, semestre1, semestre2);
		File file = new File(WriteCoursesTest.class.getResource("Saisie_voeux_dauphine_WriteCourses.ods").toURI());
		WriteCourses yearOfStudyWriter = new WriteCourses(file, courseSheet);

		yearOfStudyWriter.WriteCoursesOfYear();

		List<Course> actualSemester1 = new ArrayList<>();
		List<Course> actualSemester2 = new ArrayList<>();

		String yearOfStudy = "DE1";

		ReadCourses reader = new ReadCourses(file);
		Table currentTable = reader.getReader().getDocument().getTableByName(yearOfStudy);

		String cellStartPosition1 = "B4";
		String cellStartPosition2 = "P4";

		actualSemester1 = reader.readCoursesFromCell(cellStartPosition1, currentTable);
		actualSemester2 = reader.readCoursesFromCell(cellStartPosition2, currentTable);

		Assert.assertTrue((actualSemester1).equals(semestre1));
		Assert.assertTrue((actualSemester2).equals(semestre2));

	}
}
