package fr.dauphine.courses;

import java.util.ArrayList;

/**
 * @author Victor CHEN (Kantoki), Louis Fontaine (fontlo15)
 * Contract 2
 */

public class CoursePref {

	private Course course;
	private String teacher;
	private String cmChoice;
	private String tdChoice;
	private String cmtdChoice;
	private String tpChoice;
	private ArrayList<String> choicesPossible = new ArrayList<String>();

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
		initListChoicesPossible();
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
	
	public String getTeacher() {
		return teacher;
	}

	public String getCMChoice() {
		return cmChoice;
	}
	
	public String getTDChoice() {
		return tdChoice;
	}

	public String getCMTDChoice() {
		return cmtdChoice;
	}

	public String getTPChoice() {
		return tpChoice;
	}

}
