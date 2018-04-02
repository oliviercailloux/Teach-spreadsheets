package io.github.oliviercailloux.y2018.teach_spreadsheets.java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * This class use an existing CsvReader (common-csv). It allows you to read a
 * CSV file of courses (see below) and set a list of courses
 * 
 * @author Samuel COHEN (samuelcohen75) and Louis FONTAINE (fontlo15)
 * @see librarySource https://commons.apache.org/proper/commons-csv/
 * @see Course
 */
public class CsvFileReader {

	/**
	 * This method read a CSV file of {@link Course} and add them to the parameter
	 * list.
	 * 
	 * @param filename
	 *            CSV file absolute or relative path
	 * @param courses
	 *            a list of Course to set with the CSV file
	 * 
	 */
	public static void readCourseCSVfile(String filename, List<Course> courses)
			throws FileNotFoundException, IOException, NumberFormatException, IllegalArgumentException {

		try (FileReader filereader = new FileReader(filename)) {
			try (CSVParser parser = CSVParser.parse(filereader, CSVFormat.EXCEL)) {
				boolean line = true;
				Course c = new Course();

				// collect every line of the CSV file in a CSVRecord object - the first line is
				// the header
				for (CSVRecord csvRecord : parser) {
					if (line) {
						line = false;
					} else {
						c = new Course();
						for (int i = 0; i < csvRecord.size(); i++) {
							c.set(i, csvRecord.get(i));
						}
						courses.add(c);

					}
				}
			}
		}
	}

}
