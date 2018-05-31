package io.github.oliviercailloux.y2018.teach_spreadsheets.csv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Teacher;

/**
 * This class use an existing CsvReader (common-csv). It allows you to read a
 * CSV file of {@link Course} (see below) and set a list of {@link Course}. And
 * it allows you to read a CSV file of {@link Teacher} (see below) and set a
 * list of {@link Teacher}.
 * 
 * @author Samuel COHEN (samuelcohen75) and Louis FONTAINE (fontlo15) and Tuan
 *         Nam Davaux (tuannamdavaux)
 * @see librarySource https://commons.apache.org/proper/commons-csv/
 */
public class CsvFileReader {

	private final static Logger LOGGER = LoggerFactory.getLogger(CsvFileReader.class);

	/**
	 * This method read a CSV file of {@link Course} and add them to the parameter
	 * {@link Course} list.
	 * 
	 * @param fileReader
	 *            a reader set to a CSV file
	 * @param courses
	 *            a list of Course to set with the CSV file
	 * 
	 */
	public static void readCourseCSVfile(Reader fileReader, List<Course> courses)
			throws FileNotFoundException, IOException, NumberFormatException, IllegalArgumentException {

		try (CSVParser parser = CSVParser.parse(fileReader, CSVFormat.EXCEL)) {
			LOGGER.info("File has been correctly parsed");

			boolean line = true;
			Course c = new Course();

			// collect every line of the CSV file in a CSVRecord object - the first line is
			// the header
			for (CSVRecord csvRecord : parser) {
				if (line) {
					line = false;
				} else {
					c = new Course();
					c.setName(csvRecord.get(0));
					c.setapogeeCode(csvRecord.get(1));
					c.setYearOfStud(csvRecord.get(2));
					c.setCM_Hour(Double.parseDouble(csvRecord.get(3)));
					c.setTD_Hour(Double.parseDouble(csvRecord.get(4)));
					c.setCMTD_Hour(Double.parseDouble(csvRecord.get(5)));
					c.setTP_Hour(Double.parseDouble(csvRecord.get(6)));
					c.setGrpsNumber(csvRecord.get(7));

					courses.add(c);
				}
			}
			LOGGER.info("The Course list has been updated successfully with the CSV file. ");
		}

	}

	/**
	 * This method read a CSV file of a {@link Teacher} and return the teacher
	 * 
	 * @param fileReader
	 *            a reader set to a CSV file
	 * 
	 */
	public static Teacher readTeacherFromCSVfile(Reader fileReader)
			throws FileNotFoundException, IOException, NumberFormatException, IllegalArgumentException {

		try (CSVParser parser = CSVParser.parse(fileReader, CSVFormat.EXCEL)) {
			LOGGER.info("File has been correctly parsed");

			boolean line = true;
			Teacher c = new Teacher();

			// collect every line of the CSV file in a CSVRecord object - the first line is
			// the header
			for (CSVRecord csvRecord : parser) {
				if (line) {
					line = false;
				} else {
					c = new Teacher();

					c.setId(Integer.parseInt(csvRecord.get(0)));
					c.setName(csvRecord.get(1));
					c.setFirstName(csvRecord.get(2));
					c.setAdress(csvRecord.get(3));
					c.setPostCode(csvRecord.get(4));
					c.setCity(csvRecord.get(5));
					c.setPersonalPhone(csvRecord.get(6));
					c.setMobilePhone(csvRecord.get(7));
					c.setPersonalMail(csvRecord.get(8));
					c.setDauphineMail(csvRecord.get(9));
					c.setStatus(csvRecord.get(10));
					c.setDauphinePhone(csvRecord.get(11));
					c.setDesk(csvRecord.get(12));

					return c;
				}
			}
			LOGGER.info("The Teacher list has been updated successfully with the CSV file. ");
		}
		return null;

	}
}
