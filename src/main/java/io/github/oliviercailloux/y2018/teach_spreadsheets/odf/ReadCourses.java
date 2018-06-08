package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CourseSheet;

/**
 * This class allow you to read a file of courses for Dauphine University
 * teachers from an instance of {@link ODSReader}.
 * 
 * WARNING ! This class only read sheets following the template of "DE1", where
 * the tables of courses starts at cells "B4" and "P4".
 * 
 * @author Victor CHEN (Kantoki), Louis FONTAINE (fontlo15)
 * @version Version 1.0 Last Update : 13/05/2018.
 * 
 */
public class ReadCourses {
	private final static String COURSTD = "CMTD";
	private final static String COURSTP = "CMTP";
	private final static String TD = "TD";
	private final static String TP = "TP";
	/**
	 * Assuming that the Begin year and the end are at cells CELLYEAR.
	 */
	private final static String CELLYEAR = "G1";

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ReadCourses.class);

	/**
	 * Assuming that the tables of courses starts at cells STARTCELL1 and
	 * STARTCELL2 for each sheet
	 */
	private final static String STARTCELL1 = "B4";
	/**
	 * Assuming that the tables of courses starts at cells STARTCELL1 and
	 * STARTCELL2 for each sheet
	 */
	private final static String STARTCELL2 = "P4";

	private ODSReader reader;

	public ReadCourses(InputStream source) throws Exception {
		this.reader = new ODSReader(SpreadsheetDocument.loadDocument(source));
	}

	public List<CourseSheet> readCourseSheets() {
		List<CourseSheet> courseSheets = new ArrayList<>();

		return courseSheets;
	}

	/**
	 * This method returns a List of {@link Course} from the ODS file read by
	 * the {@link ODSReader} in the attribute.
	 * 
	 * This method reads all the Courses from all the sheets of the ODF document
	 *
	 */
	@SuppressWarnings("resource")
	public List<Course> readCourses() {
		List<Course> courses = new ArrayList<>();

		List<Table> tables = reader.getSheetList();

		for (Table table : tables) {
			courses.addAll(this.readCoursesFromSheet(table));
		}

		return courses;
	}

	/**
	 * This method returns a List of {@link Course} from the ODS file read by
	 * the {@link ODSReader} in the attribute and a sheet of the file.
	 * 
	 * This method returns a void list if the sheet has not the correct format
	 * 
	 * @see the template in resources
	 * 
	 * @param sheet:
	 *            the sheet of the spreadsheet document where you want to read
	 *            the courses.
	 * 
	 */
	public List<Course> readCoursesFromSheet(Table actualSheet) {
		List<Course> courses = new ArrayList<>();

		// if the table format is correct
		if (actualSheet.getCellByPosition("B3").getDisplayText()
				.equals("Matière")) {
			courses.addAll(this.readCoursesFromCell(STARTCELL1, actualSheet));
			courses.addAll(this.readCoursesFromCell(STARTCELL2, actualSheet));
			LOGGER.info("Table " + actualSheet.getTableName()
					+ " courses have been added successfully\n");
		} else {
			LOGGER.info("Table " + actualSheet.getTableName()
					+ " doesn't have courses or the table format is wrong\n");
		}

		return courses;
	}

	/**
	 * This method returns a List of {@link Course} from the ODS file read by
	 * the {@link ODSReader} in the attribute and the cell Position in argument.
	 * 
	 * @param cellPosition:
	 *            the position of the first Cell of the Table where are the
	 *            courses(ex: B2)
	 * 
	 */
	public List<Course> readCoursesFromCell(String cellPosition,
			Table currentSheet) {
		List<Course> courses = new ArrayList<>();
		String yearOfStudy = currentSheet.getTableName();

		Cell startCell = currentSheet.getCellByPosition(cellPosition);

		int startCellColumnIndex = startCell.getColumnIndex();
		int startCellRowIndex = startCell.getRowIndex();

		Cell actualCell = startCell;
		String cellContent = reader.getCellValue(currentSheet.getTableName(),
				CELLYEAR);

		Integer yearBegin = Integer
				.parseInt(cellContent.split(" ")[1].split("/")[0]);

		Course.setYearBegin(yearBegin);
		for (int i = startCellRowIndex; i < currentSheet.getRowCount(); i++) {
			Course course = new Course();
			course.setYearOfStud(yearOfStudy);

			int j = startCellColumnIndex;
			actualCell = currentSheet.getCellByPosition(j, i);
			String cellText = actualCell.getDisplayText();

			if ("".equals(cellText) && j == startCellColumnIndex) {
				break;
			}
			course.setName(cellText);

			j++;
			actualCell = currentSheet.getCellByPosition(j, i);
			cellText = actualCell.getDisplayText();

			course.setapogeeCode(cellText);

			j++;
			actualCell = currentSheet.getCellByPosition(j, i);
			cellText = actualCell.getDisplayText();

			course.setSupervisor(cellText);

			j++;
			actualCell = currentSheet.getCellByPosition(j, i);
			cellText = actualCell.getDisplayText();

			course.setTeachers(cellText);

			j++;
			actualCell = currentSheet.getCellByPosition(j, i);
			cellText = actualCell.getDisplayText();

			if (this.reader.isDiagonalBorder(currentSheet.getTableName(), j, i)
					|| "".equals(cellText)) {
				course.setCM_Hour(0);
			} else {
				String hourStr = cellText.replaceAll(",", ".");
				String[] hourTab = hourStr.split("h");
				course.setCM_Hour(Double.parseDouble(hourTab[0]));
			}

			j++;
			actualCell = currentSheet.getCellByPosition(j, i);
			cellText = actualCell.getDisplayText();

			if (this.reader.isDiagonalBorder(currentSheet.getTableName(), j, i)
					|| "".equals(cellText)) {
				course.setTD_Hour(0);
				course.setCMTD_Hour(0);
			} else {
				String hourStr = cellText.replaceAll(",", ".");
				String[] hourTab = hourStr.split("h");
				if (hourStr.contains(COURSTD)) {
					course.setCMTD_Hour(Double.parseDouble(hourTab[0]));
				} else if (hourStr.contains(TD)) {
					course.setTD_Hour(Double.parseDouble(hourTab[0]));
				}
			}

			j++;
			actualCell = currentSheet.getCellByPosition(j, i);
			cellText = actualCell.getDisplayText();

			if (this.reader.isDiagonalBorder(currentSheet.getTableName(), j, i)
					|| "".equals(cellText)) {
				course.setTP_Hour(0);
				course.setCMTP_Hour(0);
			} else {
				String hourStr = cellText.replaceAll(",", ".");
				String[] hourTab = hourStr.split("h");
				if (hourStr.contains(COURSTP)) {
					course.setCMTP_Hour(Double.parseDouble(hourTab[0]));
				} else if (hourStr.contains(TP)) {
					course.setTP_Hour(Double.parseDouble(hourTab[0]));
				}

			}

			j++;
			actualCell = currentSheet.getCellByPosition(j, i);
			cellText = actualCell.getDisplayText();

			if (this.reader.isDiagonalBorder(currentSheet.getTableName(), j, i)
					|| "".equals(cellText)) {
				course.setGrpsNumber("");
			} else {
				course.setGrpsNumber(cellText);
			}
			if (j == startCellColumnIndex) {
				break;
			}
			courses.add(course);

		}

		return courses;

	}

	public ODSReader getReader() {
		return reader;
	}

	public void setReader(ODSReader reader) {
		this.reader = Objects.requireNonNull(reader);
	}

	// public static void main(String[] args) throws Exception {
	// try (InputStream is =
	// ReadCourses.class.getResourceAsStream("Saisie_voeux_dauphine.ods")) {
	// try (SpreadsheetDocument sd = SpreadsheetDocument.loadDocument(is)) {
	// String yearOfStudy = "L3_Informatique";
	// ODSReader odsR = new ODSReader(sd, yearOfStudy);
	//
	// List<Course> courses = new ArrayList<>();
	//
	// ReadCourses reader = new ReadCourses(odsR);
	// courses = reader.readCourses();
	//
	// for (Course course : courses) {
	// System.out.println(course + "\n");
	// }
	// System.out.println(Course.getYearBegin());
	// }
	//
	// }
	// }
}
