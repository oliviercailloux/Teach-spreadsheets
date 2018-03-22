package fr.losprofesoresdedauphine.courses;

import java.util.Objects;

/**
 * @author Victor CHEN (Kantoki), Louis Fontaine (fontlo15), Samuel COHEN
 * Contract 2 - Version 2.0
 * Last Update : 22/03/2018
 * This class stores a teacher's preferences for a course: it allows him to indicate a preference for CM, TD, CMTD, TP (A, B, C or Absent, for each).
 */

public class CoursePref {

	private Course course;
	private String teacher;
	private Choice cmChoice;
	private Choice tdChoice;
	private Choice cmtdChoice;
	private Choice tpChoice;

	
	public CoursePref(Course course, String teacher) {
		this.course = Objects.requireNonNull(course);
		this.teacher = Objects.requireNonNull(teacher);
	}
	
	@Override
	public String toString() {
		return "Preferences for the course " + course.getName() + ", whose teacher is " + teacher + ", for CM : " + cmChoice.toString() + " ; TD : "
				+ tdChoice.toString() + " ; CMTD : " + cmtdChoice.toString() + " ; TP : " + tpChoice.toString() + ".";
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = Objects.requireNonNull(course);
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = Objects.requireNonNull(teacher);
	}

	public Choice getCmChoice() {
		return cmChoice;
	}

	public void setCmChoice(Choice cmChoice) {
		this.cmChoice = Objects.requireNonNull(cmChoice);
	}

	public Choice getTdChoice() {
		return tdChoice;
	}

	public void setTdChoice(Choice tdChoice) {
		this.tdChoice = Objects.requireNonNull(tdChoice);
	}

	public Choice getCmtdChoice() {
		return cmtdChoice;
	}

	public void setCmtdChoice(Choice cmtdChoice) {
		this.cmtdChoice = Objects.requireNonNull(cmtdChoice);
	}

	public Choice getTpChoice() {
		return tpChoice;
	}

	public void setTpChoice(Choice tpChoice) {
		this.tpChoice = Objects.requireNonNull(tpChoice);
	}
	
}
