package io.github.oliviercailloux.y2018.teach_spreadsheets.courses;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a sheet of courses, with all the metadata in the sheet
 * (number of student, current year, ...)
 *
 */
public class CourseSheet {

	private CourseSheetMetadata sheetMetadata;

	private List<CoursePref> coursePrefS1;
	private List<CoursePref> coursePrefS2;

	/**
	 * All parameters are supposed not null
	 */
	public CourseSheet(CourseSheetMetadata sheetMetadata, List<CoursePref> coursePrefS1,
			List<CoursePref> coursePrefS2) {
		this.sheetMetadata = Objects.requireNonNull(sheetMetadata);

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

	public String getYearOfStud() {
		return sheetMetadata.getYearOfStud();

	}

	public List<Integer> getSemesters() {
		List<Integer> semesters = new ArrayList<>();
		semesters.add(this.sheetMetadata.getFirstSemesterNumber());
		semesters.add(this.sheetMetadata.getFirstSemesterNumber() + 1);

		return semesters;
	}

	public List<String> getCoursesName(int semester) {
		List<String> coursesName = new ArrayList<>();
		if (semester % 2 == 0) {
			for (CoursePref coursePref : coursePrefS1) {
				coursesName.add(coursePref.getCourse().getName());
			}
		} else {
			for (CoursePref coursePref : coursePrefS2) {
				coursesName.add(coursePref.getCourse().getName());
			}
		}
		return coursesName;
	}

	private CoursePref getCoursePref(int semester, String courseName) {
		if (semester % 2 == 1) {
			for (CoursePref coursePref : coursePrefS1) {
				if (coursePref.getCourse().getName().equals(courseName)) {
					return coursePref;
				}
			}
		} else {
			for (CoursePref coursePref : coursePrefS2) {
				if (coursePref.getCourse().getName().equals(courseName)) {
					return coursePref;
				}
			}
		}
		return null;
	}

	public List<String> getPossibleChoice(int semester, String courseName) {
		CoursePref course = this.getCoursePref(semester, courseName);
		return course.getPossibleChoice();
	}

	public int getFirstSemesterNumber() {
		return this.sheetMetadata.getFirstSemesterNumber();
	}

	public List<CoursePref> getCoursePrefS1() {
		return coursePrefS1;
	}

	public List<CoursePref> getCoursePrefS2() {
		return coursePrefS2;
	}

	public CourseSheetMetadata getSheetMetadata() {
		return sheetMetadata;
	}

	@Override
	public String toString() {
		return "CourseSheet [sheetMetadata=" + sheetMetadata + ", coursePrefS1=" + coursePrefS1 + ", coursePrefS2="
				+ coursePrefS2 + "]";
	}

}
