package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;

public class ReadCourses {

	private List<Course> courses;

	private ODSReader reader;

	public ReadCourses(List<Course> courses, ODSReader reader) {
		super();
		this.courses = courses;
		this.reader = reader;
	}
	
	public List<Course> readCourses(){
		SpreadsheetDocument sd = reader.getDocument();
		
		
		return courses;
	}

	public static void main(String[] args) throws Exception {
		InputStream is = ReadCourses.class.getClassLoader().getResourceAsStream("Saisie_voeux_dauphine.ods");
		SpreadsheetDocument sd = SpreadsheetDocument.loadDocument(is);
		String yearOfStudy = "L3_Informatique";
		ODSReader odsR = new ODSReader(sd, yearOfStudy);

		List<Course> courses = new ArrayList<>();

		ReadCourses reader = new ReadCourses(courses, odsR);
		String cellPosition = "B4";
		reader.readCourses(cellPosition);
	}
}
