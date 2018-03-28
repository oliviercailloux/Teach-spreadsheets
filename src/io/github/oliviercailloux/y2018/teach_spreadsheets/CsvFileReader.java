package io.github.oliviercailloux.y2018.teach_spreadsheets;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CsvFileReader {

	public static void readCourseCSVfile(String filename, List<Course> courses)
			throws FileNotFoundException, IOException {

		try (FileReader filereader = new FileReader(filename)) {
			try (CSVParser parser = CSVParser.parse(filereader, CSVFormat.EXCEL)) {
				boolean line = true;
				Course c = new Course();

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
