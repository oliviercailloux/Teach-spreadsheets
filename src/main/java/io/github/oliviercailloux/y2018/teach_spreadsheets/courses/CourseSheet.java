package io.github.oliviercailloux.y2018.teach_spreadsheets.courses;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.common.base.Preconditions;

/**
 * This class represents a sheet of courses, with all the metadata
 *
 */
public class CourseSheet {

	private static int yearBegin = 2017;
	private String yearOfStud = "";
	private String completeYearOfStudyName = "";
	private int studentNumber = 0;
	private int firstSemesterNumber = 0;

	private List<CoursePref> coursePrefS1;
	private List<CoursePref> coursePrefS2;

	/**
	 * All parameters are supposed not null
	 */
	public CourseSheet(String yearOfStud, String completeYearOfStudyName,
			int studentNumber, int firstSemesterNumber,
			List<CoursePref> coursePrefS1, List<CoursePref> coursePrefS2) {
		this.yearOfStud = Objects.requireNonNull(yearOfStud);
		this.completeYearOfStudyName = Objects
				.requireNonNull(completeYearOfStudyName);
		Preconditions.checkArgument(studentNumber >= 0,
				"The number of student can't be negative");
		this.studentNumber = studentNumber;
		Preconditions.checkArgument(studentNumber >= 0,
				"The number of the first semester is incorrect");
		this.firstSemesterNumber = firstSemesterNumber;

		this.coursePrefS1 = Objects.requireNonNull(coursePrefS1);
		this.coursePrefS2 = Objects.requireNonNull(coursePrefS2);
	}

	public List<Course> getCourses(int order) {
		List<Course> courses = new ArrayList<>();
		List<CoursePref> coursesPref;
		if (order == 1) {
			coursesPref = coursePrefS1;
		} else {
			coursesPref = coursePrefS2;
		}
		for (CoursePref coursePref : coursesPref) {
			courses.add(coursePref.getCourse());
		}
		return courses;
	}

	public static int getYearBegin() {
		return yearBegin;
	}

	public static void setYearBegin(int yearBegin) {
		CourseSheet.yearBegin = yearBegin;
	}

	public String getYearOfStud() {
		return yearOfStud;
	}

	public void setYearOfStud(String yearOfStud) {
		this.yearOfStud = yearOfStud;
	}

	public String getCompleteYearOfStudyName() {
		return completeYearOfStudyName;
	}

	public void setCompleteYearOfStudyName(String completeYearOfStudyName) {
		this.completeYearOfStudyName = completeYearOfStudyName;
	}

	public int getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(int studentNumber) {
		this.studentNumber = studentNumber;
	}

	public int getFirstSemesterNumber() {
		return firstSemesterNumber;
	}

	public void setFirstSemesterNumber(int firstSemesterNumber) {
		this.firstSemesterNumber = firstSemesterNumber;
	}

	public List<CoursePref> getCoursePrefS1() {
		return coursePrefS1;
	}

	public void setCoursePrefS1(List<CoursePref> courseS1) {
		this.coursePrefS1 = courseS1;
	}

	public List<CoursePref> getCoursePrefS2() {
		return coursePrefS2;
	}

	public void setCoursePrefS2(List<CoursePref> courseS2) {
		this.coursePrefS2 = courseS2;
	}

	@Override
	public String toString() {
		return "CourseSheet [yearOfStud=" + yearOfStud
				+ ", completeYearOfStudyName=" + completeYearOfStudyName
				+ ", studentNumber=" + studentNumber + ", firstSemesterNumber="
				+ firstSemesterNumber + ", coursePrefS1=" + coursePrefS1
				+ ", coursePrefS2=" + coursePrefS2 + "]";
	}

}
