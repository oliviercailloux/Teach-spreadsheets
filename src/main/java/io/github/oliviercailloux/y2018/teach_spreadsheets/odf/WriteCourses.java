/**
 * 
 */
package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.File;
import java.util.ArrayList;

import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.style.Border;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions;
import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.table.Table;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;

/**
 * @author Tuan Nam Davaux (tuannamdavaux), Samuel Cohen (samuelcohen75) This
 *         class allows to write courses informations in an odf spreadsheet
 *         Version : 2.0 Last update : 11/05/2018
 */
public class WriteCourses {

	/**
	 * @param coursesList
	 * @param col
	 * @param line
	 * @param file
	 * @throws Exception
	 */
	public static void WriteSemesterCourses(ArrayList<Course> coursesList,
			int col, int line, File file) throws Exception {
		try (SpreadsheetDocument workbook = SpreadsheetDocument
				.loadDocument(file)) {
			Border border = new Border(Color.BLACK, 1,
					StyleTypeDefinitions.SupportedLinearMeasure.PT);
			Table sheet = workbook
					.getSheetByName(coursesList.get(0).getYearOfStud());
			int lineCursor = line;
			for (Course course : coursesList) {
				// Name
				sheet.getCellByPosition(col, lineCursor)
						.setStringValue(course.getName());

				// apogeeCode
				sheet.getCellByPosition(col + 1, lineCursor)
						.setStringValue(course.getapogeeCode());

				// superVisor
				if (course.getSupervisor().equals(""))
					sheet.getCellByPosition(col + 2, lineCursor)
							.setBorders(CellBordersType.DIAGONALBLTR, border);
				else
					sheet.getCellByPosition(col + 2, lineCursor)
							.setStringValue(course.getSupervisor());

				// teachers
				if (course.getTeachers().equals(""))
					sheet.getCellByPosition(col + 3, lineCursor)
							.setBorders(CellBordersType.DIAGONALBLTR, border);
				else
					sheet.getCellByPosition(col + 3, lineCursor)
							.setStringValue(course.getTeachers());

				// CM hours
				if (course.getCM_Hour() == 0.0)
					sheet.getCellByPosition(col + 4, lineCursor)
							.setBorders(CellBordersType.DIAGONALBLTR, border);
				else
					sheet.getCellByPosition(col + 4, lineCursor)
							.setStringValue(course.getCM_Hour() + "h CM");
				// TD and CMTD

				if (course.getCMTD_Hour() == 0.0
						&& course.getTD_Hour() == 0.0) {
					sheet.getCellByPosition(col + 5, lineCursor)
							.setBorders(CellBordersType.DIAGONALBLTR, border);
				} else {
					if (course.getCMTD_Hour() != 0.0) {
						sheet.getCellByPosition(col + 5, lineCursor)
								.setStringValue(
										course.getCMTD_Hour() + "h CMTD");
					} else {
						sheet.getCellByPosition(col + 5, lineCursor)
								.setStringValue(course.getTD_Hour() + "h TD");

					}
				}

				// TP and CMTP
				if (course.getCMTP_Hour() == 0.0
						&& course.getTP_Hour() == 0.0) {
					sheet.getCellByPosition(col + 6, lineCursor)
							.setBorders(CellBordersType.DIAGONALBLTR, border);
				} else {
					if (course.getCMTP_Hour() != 0.0) {
						sheet.getCellByPosition(col + 6, lineCursor)
								.setStringValue(
										course.getCMTP_Hour() + "h CMTP");
					} else {
						sheet.getCellByPosition(col + 6, lineCursor)
								.setStringValue(course.getTP_Hour() + "h TP");

					}
				}

				// groups number
				if (course.getGrpsNumber().equals(""))
					sheet.getCellByPosition(col + 7, lineCursor)
							.setBorders(CellBordersType.DIAGONALBLTR, border);
				else
					sheet.getCellByPosition(col + 7, lineCursor)
							.setStringValue(course.getGrpsNumber());
				lineCursor++;
			}

			workbook.save(file);
		}
	}

	public static void WriteYearOfCourses(String completeYearOfStudyName,
			int yearBegin, int yearEnd, int studentsNumber,
			int firstSemesterNumber, ArrayList<Course> firstSemester,
			ArrayList<Course> secondSemester, File file) throws Exception {
		try (SpreadsheetDocument workbook = SpreadsheetDocument
				.loadDocument(file)) {

			// Design handling
			Font headerBoldFont = new Font("Calibri",
					StyleTypeDefinitions.FontStyle.BOLD, 12, Color.BLACK);
			Font headerFont = new Font("Calibri",
					StyleTypeDefinitions.FontStyle.REGULAR, 12, Color.BLACK);
			Font cellContentFont = new Font("Calibri",
					StyleTypeDefinitions.FontStyle.REGULAR, 11, Color.BLACK);
			Border border1 = new Border(Color.BLACK, 1,
					StyleTypeDefinitions.SupportedLinearMeasure.PT);
			Border border2 = new Border(Color.BLACK, 2,
					StyleTypeDefinitions.SupportedLinearMeasure.PT);
			Color headerColor = new Color(224, 224, 224);

			int firstArraySize = firstSemester.size();
			int secondArraySize = secondSemester.size();

			Table sheet = workbook
					.appendSheet(firstSemester.get(0).getYearOfStud());
			// Header
			sheet.getCellByPosition("B1").setFont(headerBoldFont);
			sheet.getCellByPosition("B1")
					.setStringValue(completeYearOfStudyName);
			sheet.getCellRangeByPosition("B1", "E1").merge();
			sheet.getCellByPosition("B1").setCellBackgroundColor(headerColor);
			sheet.getCellByPosition("B1").setHorizontalAlignment(
					StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
			sheet.getCellByPosition("B1").setTextWrapped(true);

			sheet.getCellByPosition("G1").setFont(headerFont);
			sheet.getCellByPosition("G1")
					.setStringValue("prévision " + yearBegin + "/" + yearEnd);
			sheet.getCellRangeByPosition("G1", "I1").merge();
			sheet.getCellByPosition("G1").setHorizontalAlignment(
					StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
			sheet.getCellByPosition("G1").setTextWrapped(true);

			sheet.getCellByPosition("J1").setFont(headerFont);
			sheet.getCellByPosition("J1")
					.setStringValue(": " + studentsNumber + " étudiants");
			sheet.getCellRangeByPosition("J1", "L1").merge();
			sheet.getCellByPosition("J1").setHorizontalAlignment(
					StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
			sheet.getCellByPosition("J1").setTextWrapped(true);

			sheet.getCellByPosition("B2").setFont(headerBoldFont);
			sheet.getCellByPosition("B2").setStringValue(
					"ENSEIGNEMENTS SEMESTRE " + firstSemesterNumber);
			sheet.getCellRangeByPosition("B2", "N2").merge();
			sheet.getCellByPosition("B2").setCellBackgroundColor(headerColor);
			sheet.getCellByPosition("B2").setHorizontalAlignment(
					StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
			sheet.getCellByPosition("B2").setBorders(CellBordersType.ALL_FOUR,
					border2);
			sheet.getCellByPosition("B2").setTextWrapped(true);
			// sheet.getCellByPosition("B2").setBorders(CellBordersType.LEFT,
			// border2);
			sheet.getCellByPosition("P2").setFont(headerBoldFont);
			sheet.getCellByPosition("P2").setStringValue(
					"ENSEIGNEMENTS SEMESTRE " + (firstSemesterNumber + 1));
			sheet.getCellRangeByPosition("P2", "AB2").merge();
			sheet.getCellByPosition("P2").setCellBackgroundColor(headerColor);
			sheet.getCellByPosition("P2").setHorizontalAlignment(
					StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
			sheet.getCellByPosition("P2").setBorders(CellBordersType.ALL_FOUR,
					border2);
			sheet.getCellByPosition("P2").setTextWrapped(true);

			// Arrays cells Format
			// First Array
			for (int i = 2; i <= firstArraySize + 2; i++) {
				for (int j = 1; j <= 13; j++) {
					sheet.getCellByPosition(j, i)
							.setBorders(CellBordersType.ALL_FOUR, border1);
					sheet.getCellByPosition(j, i).setTextWrapped(true);
					sheet.getCellByPosition(j, i).setFont(cellContentFont);
					sheet.getCellByPosition(j, i).setVerticalAlignment(
							StyleTypeDefinitions.VerticalAlignmentType.MIDDLE);
					if (j == 1) {
						sheet.getCellByPosition(j, i).setHorizontalAlignment(
								StyleTypeDefinitions.HorizontalAlignmentType.LEFT);
						sheet.getCellByPosition(j, i)
								.setBorders(CellBordersType.LEFT, border2);
					} else
						sheet.getCellByPosition(j, i).setHorizontalAlignment(
								StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
					if (j == 8 || j == 13)
						sheet.getCellByPosition(j, i)
								.setBorders(CellBordersType.RIGHT, border2);
					if (i == firstArraySize + 2)
						sheet.getCellByPosition(j, i)
								.setBorders(CellBordersType.BOTTOM, border2);
				}
			}

			// Second Array
			for (int i = 2; i <= secondArraySize + 2; i++) {
				for (int j = 15; j <= 27; j++) {
					sheet.getCellByPosition(j, i)
							.setBorders(CellBordersType.ALL_FOUR, border1);
					sheet.getCellByPosition(j, i).setTextWrapped(true);
					sheet.getCellByPosition(j, i).setFont(cellContentFont);
					sheet.getCellByPosition(j, i).setVerticalAlignment(
							StyleTypeDefinitions.VerticalAlignmentType.MIDDLE);
					if (j == 15) {
						sheet.getCellByPosition(j, i).setHorizontalAlignment(
								StyleTypeDefinitions.HorizontalAlignmentType.LEFT);
						sheet.getCellByPosition(j, i)
								.setBorders(CellBordersType.LEFT, border2);
					} else
						sheet.getCellByPosition(j, i).setHorizontalAlignment(
								StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
					if (j == 22 || j == 27)
						sheet.getCellByPosition(j, i)
								.setBorders(CellBordersType.RIGHT, border2);
					if (i == secondArraySize + 2)
						sheet.getCellByPosition(j, i)
								.setBorders(CellBordersType.BOTTOM, border2);
				}
			}

			// Arrays Header
			String[] arrayHeader = {"Matière", "code Apogée",
					"Responsable UE " + yearBegin + "-" + yearEnd,
					"Intervenants " + yearBegin + "-" + yearEnd, "COURS",
					"TD ou cours-TD", "TP ou cours-TP",
					"Nombre de groupes indicatif", "Choix Cours",
					"Choix TD/CMTD", "Choix TP/CMTP",
					"Nombre de TD/TP souhaités",
					"Nombre d'années d'enseignement de la matière"};
			for (int j = 1; j <= 13; j++) {
				sheet.getCellByPosition(j, 2)
						.setStringValue(arrayHeader[j - 1]);
				sheet.getCellByPosition(j, 2)
						.setCellBackgroundColor(headerColor);
			}
			for (int j = 15; j <= 27; j++) {
				sheet.getCellByPosition(j, 2)
						.setStringValue(arrayHeader[j - 15]);
				sheet.getCellByPosition(j, 2)
						.setCellBackgroundColor(headerColor);
			}
			// Comment text zone
			sheet.getCellByPosition("B" + (firstArraySize + 5))
					.setTextWrapped(true);
			sheet.getCellByPosition("B" + (firstArraySize + 5))
					.setFont(cellContentFont);
			sheet.getCellByPosition("B" + (firstArraySize + 5))
					.setStringValue("COMMENTAIRES");
			sheet.getCellRangeByPosition("C" + (firstArraySize + 5),
					"N" + (firstArraySize + 9)).merge();

			sheet.getCellByPosition("P" + (secondArraySize + 5))
					.setTextWrapped(true);
			sheet.getCellByPosition("P" + (secondArraySize + 5))
					.setFont(cellContentFont);
			sheet.getCellByPosition("P" + (secondArraySize + 5))
					.setStringValue("COMMENTAIRES");
			sheet.getCellRangeByPosition("Q" + (secondArraySize + 5),
					"AB" + (secondArraySize + 9)).merge();

			workbook.save(file);

		}
		WriteCourses.WriteSemesterCourses(firstSemester, 1, 3, file);
		WriteCourses.WriteSemesterCourses(secondSemester, 15, 3, file);
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ArrayList<Course> semestre1 = new ArrayList<>();
		ArrayList<Course> semestre2 = new ArrayList<>();

		Course course0 = new Course("PRE-RENTREE : \r\n" + "Mathématiques",
				"A1PREMA", "DE1", "Pasquignon",
				"Huveneers\r\n" + "Lamboley\r\n" + "Vialard\r\n" + "Legendre");
		course0.setCMTD_Hour(15);
		course0.setGrpsNumber("6 CMTD");

		Course course1 = new Course("Analyse1", "A1DEM01", "DE1", "Simenhaus",
				"Legendre\r\n" + "Lamboley\r\n" + "Attouchi\r\n"
						+ "Simenhaus\r\n" + "Boussion\r\n" + "Truchot");
		course1.setCMTD_Hour(72);
		course1.setGrpsNumber("6 CMTD");

		Course course2 = new Course("Algèbre linéaire 1", "A1DEM02", "DE1",
				"Pasquignon",
				"Tamzali-Lafond\r\n" + "Henon\r\n" + "Pasquignon\r\n"
						+ "Taleng\r\n" + "Pasquignon\r\n" + "Vialard");
		course2.setCMTD_Hour(72);
		course2.setGrpsNumber("6 CMTD");

		Course course3 = new Course("Algorithmique et programmation 1",
				"A1DEM03", "DE1", "Colazzo",
				"Colazzo CM\r\n" + "Raksanyi\r\n" + "Gabrel-Willemin\r\n"
						+ "Murat\r\n" + "Manouvrier\r\n" + "Arib");
		course3.setCMTD_Hour(30);
		course3.setCMTP_Hour(30);
		course3.setGrpsNumber("6 CMTD et 6 CMTP");

		Course course4 = new Course("Introduction à la microéconomie",
				"A1DEM04", "DE1", "De Vreyer", "De Vreyer CM\r\n"
						+ "Brembilla\r\n" + "Laffineur\r\n" + "Morcillo");
		course4.setCM_Hour(19.5);
		course4.setTD_Hour(19.5);
		course4.setGrpsNumber("6 TD");

		Course course6 = new Course("Problèmes économiques", "A1DEM06", "DE1",
				"Dieudonne", "Dieudonne");
		course6.setCM_Hour(36);

		Course course7 = new Course("Organisation des entreprisess", "A1DEM07",
				"DE1", "Servel", "Carcassonne CM\r\n" + "Servel CM\r\n"
						+ "Zouaneb\r\n" + "Diedhiou");
		course7.setCM_Hour(6);
		course7.setTD_Hour(30);
		course7.setGrpsNumber("2 TD");

		Course course5 = new Course("Anglais 1", "A1DEM05", "DE1", "Bourrel",
				"Lytle-Hassan\r\n" + "Countcham\r\n" + "Joyce\r\n" + "Roque\r\n"
						+ "Pattie\r\n" + "Whiteside\r\n" + "Levy-Alimi");
		course5.setCMTD_Hour(18);
		course5.setGrpsNumber("12");

		Course courseL = new Course("LV2 (Allemand / Espagnol)",
				"A1ALL01\r\n" + "A1ESP01", "DE1",
				"Quinchon-Caudal\r\n" + "Amisse",
				"Tudoran\r\n" + "Barrera Guarin\r\n" + "Jareno Gila");
		courseL.setTD_Hour(18);
		courseL.setGrpsNumber("2 all\r\n" + "3 esp");

		Course courseAS = new Course("Anglais soutien S1", "A1SOU01", "DE1",
				"Bourrel", "Joyce");
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

		Course course8 = new Course("Analyse 2", "A1DEM08", "DE1", "Lebourg",
				"Lebourg CM\r\n" + "Rammal\r\n" + "Schaison\r\n"
						+ "Hadikhanloo\r\n" + "Massetti");
		course8.setCM_Hour(19.5);
		course8.setCMTD_Hour(39);
		course8.setGrpsNumber("6");

		Course course9 = new Course("Algèbre linéaire 2", "A1DEM09", "DE1",
				"Vialard", "Vialard CM\r\n" + "Rammal\r\n" + "Fu Ying\r\n"
						+ "Coron\r\n" + "Macasieb\r\n" + "Schaison\r\n" + "Fu");
		course9.setCM_Hour(19.5);
		course9.setCMTD_Hour(39);
		course9.setGrpsNumber("6");

		Course course11 = new Course("Algorithmique et programmation 2",
				"A1DEM11", "DE1", "Cazenave",
				"Cazenave CM\r\n" + "Jamain\r\n" + "Pontoizeau\r\n"
						+ "Ternier\r\n" + "Labernia\r\n" + "Haddad\r\n"
						+ "Tlilane");
		course11.setCM_Hour(19.5);
		course11.setTD_Hour(19.5);
		course11.setGrpsNumber("6");

		Course course10 = new Course(
				"Modélisation et applications des mathématiques", "A1DEM10",
				"DE1", "Boussion", "Boussion CM\r\n" + "Genadot\r\n"
						+ "Leboucher\r\n" + "Le Cousin");
		course10.setCM_Hour(19.5);
		course10.setTD_Hour(19.5);
		course10.setGrpsNumber("6");

		Course course14 = new Course(
				"Outils en informatique\r\n" + "- Outils de l'internet",
				"A1DEM14", "DE1", "Mayag", "Mayag CM\r\n" + "Belhoul\r\n"
						+ "Jamain\r\n" + "Magnouche\r\n" + "Arib");
		course14.setCM_Hour(15);
		course14.setTP_Hour(15);
		course14.setGrpsNumber("6");

		Course course141 = new Course("Outils en informatique\r\n"
				+ "- Initiation à la recherche opérationnelle avec Excel",
				"A1DEM14", "DE1", "Mayag",
				"Mayag CM\r\n" + "Belhoul\r\n" + "Jamain\r\n" + "Magnouche\r\n"
						+ "Mouhoub\r\n" + "Haddad");
		course141.setCM_Hour(3);
		course141.setTP_Hour(12);
		course141.setGrpsNumber("6");

		Course course142 = new Course(
				"Outils en informatique\r\n"
						+ "- Bases de données élémentaires",
				"A1DEM14", "DE1", "Mayag", "Mayag CM\r\n" + "Belhoul\r\n"
						+ "Fu Liangliang\r\n" + "Monnet\r\n" + "Arib");
		course142.setCM_Hour(3);
		course142.setTP_Hour(12);
		course142.setGrpsNumber("6");

		Course course12 = new Course("Macroéconomie : analyse de long terme",
				"A1DEM12", "DE1", "Chagny",
				"Chagny CM\r\n" + "Lorre\r\n" + "Laffineur");
		course12.setCM_Hour(19.5);
		course12.setTD_Hour(19.5);
		course12.setGrpsNumber("6");

		Course course13 = new Course("Anglais 2", "A1DEM13", "DE1", "Bourrel",
				"Lytle-Hassan\r\n" + "Countcham\r\n" + "Joyce\r\n" + "Roque\r\n"
						+ "Pattie\r\n" + "Whiteside\r\n" + "Orgeret");
		course13.setCMTD_Hour(19.5);
		course13.setGrpsNumber("12");

		Course courseL2 = new Course("LV2 (Allemand / Espagnol)", "", "DE1", "",
				"");
		courseL2.setTD_Hour(19.5);
		courseL2.setGrpsNumber("2 all\r\n" + "3 esp");

		Course courseAS2 = new Course("Anglais soutien S2", "A1SOU02", "DE1",
				"Bourrel", "Joyce");
		courseAS2.setTD_Hour(19.5);
		courseAS2.setGrpsNumber("1");

		Course courseD = new Course("DROIT C2I", "A0MID21", "DE1", "",
				"Batusanski");
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

		WriteYearOfCourses("1ère année de licence", 2016, 2017, 200, 1,
				semestre1, semestre2, new File(
						"src/main/resources/Saisie_voeux_dauphine_Testing.ods"));

	}

}