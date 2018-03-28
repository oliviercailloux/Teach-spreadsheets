package io.github.oliviercailloux.y2018.teach_spreadsheets;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CsvFileWriter {
	
	//Delimiter used in CSV file
	private static final String NEW_LINE_SEPARATOR = "\n";

	//CSV file header
	private static final Object [] FILE_HEADER = {"name","apogeeCode","yearOfStud"};
	
	public static void writeCsvFile(String fileName) throws IOException {
		//Create new students objects
		Course c1 = new Course("Proba", "14", "2018");
		Course c2 = new Course("Statistiques", "15", "2018");
		
		List<Course> courses = new ArrayList<Course>();
		courses.add(c1);
		courses.add(c2);
		
		FileWriter fileWriter = new FileWriter(fileName);

		CSVPrinter csvFilePrinter = null;

		//Create the CSVFormat object with "\n" as a record delimiter
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
		
		try {
			//initialize FileWriter object
			fileWriter = new FileWriter(fileName);
			
			//initialize CSVPrinter object
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
			
			//Create CSV file header
			csvFilePrinter.printRecord(FILE_HEADER);
			
			for (Course c : courses) {
				List coursesRecord = new ArrayList();
				coursesRecord.add(c.getName());
				coursesRecord.add(c.getapogeeCode());
				coursesRecord.add(c.getYearOfStud());
				
				csvFilePrinter.printRecord(coursesRecord);
			}
			System.out.println("CSV file was created successfully !!!");

		} catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
        		try {
			fileWriter.flush();
			fileWriter.close();
			// csvFilePrinter.close();
        		}  catch (IOException e) {
        			System.out.println("Error while flushing/closing fileWriter/csvPrinter !!!");
        			e.printStackTrace();

        		}
		}
	}
}
