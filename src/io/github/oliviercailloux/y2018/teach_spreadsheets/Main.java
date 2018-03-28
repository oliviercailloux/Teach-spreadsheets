package io.github.oliviercailloux.y2018.teach_spreadsheets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
		List<Course> courses = new ArrayList<Course>();
		
		Course c1 = new Course("Probabilité","14","2008");
		Course c2 = new Course("Statistiques","14","2008");
		courses.add(c1);
		courses.add(c2);
		
				
		CoursePref cp = new CoursePref(c1, "Samuel");
		cp.setCmChoice(Choice.A);
		cp.setTdChoice(Choice.B);
		cp.setCmtdChoice(Choice.C);
		cp.setTpChoice(Choice.ABSENT);
		
		System.out.println(c1.getName() + " CM : " + cp.getCmChoice());
		System.out.println(c1.getName() + " TD : " + cp.getTdChoice());
		System.out.println(c1.getName() + " CMTD : " + cp.getCmtdChoice());
		System.out.println(c1.getName() + " TP : " + cp.getTpChoice());
		System.out.println("\n" + cp.toString());
		
		CsvFileWriter.writeCoursesCsvFile("TestCourses.csv", courses);
	}

}
