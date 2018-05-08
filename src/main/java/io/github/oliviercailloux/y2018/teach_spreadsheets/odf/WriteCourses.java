/**
 * 
 */
package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;

/**
 * @author tuannamdavaux
 *
 */
public class WriteCourses {

	private ArrayList<Course> coursesList = new ArrayList<Course>();

	private String cellCursor = "";

	private Workbook workbook = null;

	public WriteCourses(ArrayList<Course> coursesList, String cellCursor,
			File file) throws EncryptedDocumentException,
			InvalidFormatException, IOException {
		this.coursesList = Objects.requireNonNull(coursesList);
		this.cellCursor = Objects.requireNonNull(cellCursor);
		this.workbook = WorkbookFactory.create(file);
	}

	public void writeCoursesList() {
		Sheet sheet = this.workbook
				.getSheet(this.coursesList.get(0).getYearOfStud());
		CellReference cellReference = new CellReference(cellCursor);
		// sheet.shiftRows(cellReference.getRow(), sheet.getLastRowNum(), 1);
		// Row newRow = sheet.createRow(cellReference.getRow());
		Row newRow = sheet.createRow(cellReference.getRow() + 1);
		newRow.createCell(1).setCellValue(this.workbook.getCreationHelper()
				.createRichTextString("samcoh"));
		/*
		 * newRow.createCell(1).setCellValue(this.workbook.getCreationHelper()
		 * .createRichTextString(coursesList.get(0).getName()));
		 */
		newRow.createCell(2).setCellValue(coursesList.get(0).getapogeeCode());
		newRow.createCell(3)
				.setCellValue(coursesList.get(0).getCM_Hour() + "h CM");
		newRow.createCell(4)
				.setCellValue(coursesList.get(0).getCMTD_Hour() + "h CMTD");
		newRow.createCell(5)
				.setCellValue(coursesList.get(0).getTP_Hour() + "h TP");
		newRow.createCell(6)
				.setCellValue(coursesList.get(0).getNbGrCMTD() + " CMTD");
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws EncryptedDocumentException
	 */
	public static void main(String[] args) throws EncryptedDocumentException,
			InvalidFormatException, IOException {
		Course course = new Course();
		course.setName("Statistiques");
		course.setapogeeCode("14");
		course.setYearOfStud("DE1");
		course.setCM_Hour(8);
		course.setTD_Hour(4);
		course.setCMTD_Hour(8);
		course.setTP_Hour(8);
		course.setNbGrpCM(8);
		course.setNbGrpTD(2);
		course.setNbGrpCMTD(7);
		course.setNbGrpTP(5);
		ArrayList<Course> courses = new ArrayList();
		courses.add(course);
		WriteCourses wC = new WriteCourses(courses, "B13", new File(
				"src/main/resources/Saisie_voeux_dauphine_Testing.xlsx"));
		wC.writeCoursesList();
	}

}
