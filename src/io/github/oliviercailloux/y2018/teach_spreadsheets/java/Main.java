package io.github.oliviercailloux.y2018.teach_spreadsheets.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
		List<Course> courses = new ArrayList<>();

		Course c1 = new Course("Probabilité", "14", "2008");
		c1.setCM_Hour(8);
		c1.setTD_Hour(4);
		c1.setCMTD_Hour(8);
		c1.setTP_Hour(8);
		c1.setNbGrpCM(8);
		c1.setNbGrpTD(2);
		c1.setNbGrpCMTD(7);
		c1.setNbGrpTP(5);

		Course c2 = new Course("Statistiques", "14", "2008");
		c2.setCM_Hour(8);
		c2.setTD_Hour(4);
		c2.setCMTD_Hour(8);
		c2.setTP_Hour(8);
		c2.setNbGrpCM(8);
		c2.setNbGrpTD(2);
		c2.setNbGrpCMTD(7);
		c2.setNbGrpTP(5);

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

		CsvFileWriter.writeInCSV(courses, "TestCourses.csv");

		List<Course> courses2 = new ArrayList<>();
		CsvFileReader.readCourseCSVfile("TestCourses.csv", courses2);
		System.out.println(courses2);

	}

}
