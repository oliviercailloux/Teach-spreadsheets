package io.github.oliviercailloux.y2018.teach_spreadsheets.courses;

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

	private List<Course> courseS1;
	private List<Course> courseS2;

	/**
	 * All parameters are supposed not null
	 */
	public CourseSheet(String yearOfStud, String completeYearOfStudyName, int studentNumber, int firstSemesterNumber,
			List<Course> courseS1, List<Course> courseS2) {
		this.yearOfStud = Objects.requireNonNull(yearOfStud);
		this.completeYearOfStudyName = Objects.requireNonNull(completeYearOfStudyName);
		Preconditions.checkArgument(studentNumber >= 0, "The number of student can't be negative");
		this.studentNumber = studentNumber;
		Preconditions.checkArgument(studentNumber >= 0, "The number of the first semester is incorrect");
		this.firstSemesterNumber = firstSemesterNumber;

		this.courseS1 = Objects.requireNonNull(courseS1);
		this.courseS2 = Objects.requireNonNull(courseS2);
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

	public List<Course> getCourseS1() {
		return courseS1;
	}

	public void setCourseS1(List<Course> courseS1) {
		this.courseS1 = courseS1;
	}

	public List<Course> getCourseS2() {
		return courseS2;
	}

	public void setCourseS2(List<Course> courseS2) {
		this.courseS2 = courseS2;
	}

	@Override
	public String toString() {
		return "CourseSheet [yearOfStud=" + yearOfStud + ", completeYearOfStudyName=" + completeYearOfStudyName
				+ ", studentNumber=" + studentNumber + ", firstSemesterNumber=" + firstSemesterNumber + ", courseS1="
				+ courseS1 + ", courseS2=" + courseS2 + "]";
	}

}
