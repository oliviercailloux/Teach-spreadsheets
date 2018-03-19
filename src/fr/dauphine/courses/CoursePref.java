package fr.dauphine.courses;

import java.util.ArrayList;

/**
 * @author Victor CHEN (Kantoki), Louis Fontaine (fontlo15)
 * Contract 2
 * This class stores a teacher's preferences for a course: it allows him to indicate a preference for CM, TD, CMTD, TP (A, B, C or Absent, for each).
 */

public class CoursePref {

	private Course course;
	private String teacher;
	private String cmChoice;
	private String tdChoice;
	private String cmtdChoice;
	private String tpChoice;
	private static ArrayList<String> choicesPossible = new ArrayList<String>();

	/**
	 * this method initializes the list of possible preferences choices for the courses
	 */
	public void initListChoicesPossible() {
		this.choicesPossible.add("A");
		this.choicesPossible.add("B");
		this.choicesPossible.add("C");
		this.choicesPossible.add("Absent");
	}
	
	/**
	 * this method checks if the choice entered exists in the list of possible choices
	 * @param s : the choice
	 * @return true if choice exists else false
	 */
	public boolean checkChoice(String s) {
		if (!choicesPossible.contains((s))) {
			return false;
		}
		return true;
	}
	
	public CoursePref(Course course, String teacher, String cmChoice, String tdChoice, String cmtdChoice,
			String tpChoice) {
		initListChoicesPossible(); // initializes the possible preferences choices for a type of course
		this.course = course;
		this.teacher = teacher;
		this.cmChoice = cmChoice;
		this.tdChoice = tdChoice;
		this.cmtdChoice = cmtdChoice;
		this.tpChoice = tpChoice;
				
		ArrayList<String> teacherChoices = new ArrayList<String>();
		teacherChoices.add(cmChoice);
		teacherChoices.add(tdChoice);
		teacherChoices.add(cmtdChoice);
		teacherChoices.add(tpChoice);
		
		for(String choices : teacherChoices) {
			if (!checkChoice(choices)) {
				throw new IllegalArgumentException("The choice " + choices + " doesn't exist ! Please insert a choice among the ones possible !");
			}
		}
	}
	
	@Override
	public String toString() {
		return "Preferences for the course " + course.getName() + ", whose teacher is " + teacher + ", for CM : " + cmChoice + " ; TD : "
				+ tdChoice + " ; CMTD : " + cmtdChoice + " ; TP : " + tpChoice + ".";
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getCmChoice() {
		return cmChoice;
	}

	public void setCmChoice(String cmChoice) {
		this.cmChoice = cmChoice;
	}

	public String getTdChoice() {
		return tdChoice;
	}

	public void setTdChoice(String tdChoice) {
		this.tdChoice = tdChoice;
	}

	public String getCmtdChoice() {
		return cmtdChoice;
	}

	public void setCmtdChoice(String cmtdChoice) {
		this.cmtdChoice = cmtdChoice;
	}

	public String getTpChoice() {
		return tpChoice;
	}

	public void setTpChoice(String tpChoice) {
		this.tpChoice = tpChoice;
	}

	public ArrayList<String> getChoicesPossible() {
		return choicesPossible;
	}

	public void setChoicesPossible(ArrayList<String> choicesPossible) {
		this.choicesPossible = choicesPossible;
	}
	

}
