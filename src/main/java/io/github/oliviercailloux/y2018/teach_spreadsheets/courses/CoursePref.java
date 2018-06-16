package io.github.oliviercailloux.y2018.teach_spreadsheets.courses;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.oliviercailloux.y2018.teach_spreadsheets.gui.GUIPref;

/**
 * @author Victor CHEN (Kantoki), Samuel COHEN Contract 2 - Version 2.0.2 Last
 *         Update : 22/03/2018 This class stores a teacher's preferences for a
 *         course: it allows him to indicate a preference for CM, TD, CMTD, TP
 *         (A, B, C or Absent, for each).
 */

public class CoursePref {

	/**
	 * Course is never <code> null </code>
	 */
	private Course course;

	private Choice cmChoice;
	private Choice tdChoice;
	private Choice tpChoice;

	private int nbrGrpCm;
	private int nbrGrpTd;
	private int nbrGrpTp;
	private int nbrExp;

	/**
	 * @param course
	 *            : the Course to insert the teacher's preferences. course not
	 *            <code> null </code>
	 * 
	 */
	public CoursePref(Course course) {
		this.course = Objects.requireNonNull(course);
		this.cmChoice = (course.getCM_Hour() == 0) ? Choice.NA : Choice.ABSENT;
		this.tdChoice = (course.getTD_Hour() == 0 && course.getCMTD_Hour() == 0) ? Choice.NA : Choice.ABSENT;
		this.tpChoice = (course.getTP_Hour() == 0 && course.getCMTP_Hour() == 0) ? Choice.NA : Choice.ABSENT;
	}

	/**
	 * Used for {@link GUIPref} to list all choices for a course. Get the possible
	 * choices possible for a course.
	 */
	public List<String> getPossibleChoice() {
		List<String> choices = new ArrayList<>();

		if (cmChoice != Choice.NA) {
			choices.add("CM");
		}
		if (tdChoice != Choice.NA) {
			choices.add("TD");
		}
		if (tpChoice != Choice.NA) {
			choices.add("TP");
		}
		return choices;
	}

	/**
	 * Transform a list of Courses to a list of CoursePref initialized by default
	 */
	public static List<CoursePref> toCoursePref(List<Course> courses) {
		List<CoursePref> coursePrefs = new ArrayList<>();

		for (Course course : courses) {
			coursePrefs.add(new CoursePref(course));
		}

		return coursePrefs;
	}

	@Override
	public String toString() {
		return "Preferences for the course " + course.getName() + ", for CM : " + cmChoice.toString() + " ; TD : "
				+ tdChoice.toString() + " ; TP : " + tpChoice.toString() + ", nbrGrpCM : " + nbrGrpCm + ", nbrGrpTd : "
				+ nbrGrpTd + ", nbrGrpTp : " + nbrGrpTp + ", course experience : " + nbrExp + ".";
	}

	public int getNbrGrpCm() {
		return nbrGrpCm;
	}

	public void setNbrGrpCm(int nbrGrpCm) {
		this.nbrGrpCm = nbrGrpCm;
	}

	public int getNbrGrpTd() {
		return nbrGrpTd;
	}

	public void setNbrGrpTd(int nbrGrpTd) {
		this.nbrGrpTd = nbrGrpTd;
	}

	public int getNbrGrpTp() {
		return nbrGrpTp;
	}

	public void setNbrGrpTp(int nbrGrpTp) {
		this.nbrGrpTp = nbrGrpTp;
	}

	public int getNbrExp() {
		return nbrExp;
	}

	public void setNbrExp(int nbrExp) {
		this.nbrExp = nbrExp;
	}

	@Override
	public int hashCode() {
		return Objects.hash(course);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoursePref other = (CoursePref) obj;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		return true;
	}

	public Course getCourse() {
		return course;
	}

	/**
	 * @param course
	 *            not <code> null </code>
	 */
	public void setCourse(Course course) {
		this.course = Objects.requireNonNull(course);
	}

	public Choice getCmChoice() {
		return cmChoice;
	}

	/**
	 * @param cmChoice
	 *            not <code> null </code>
	 */
	public void setCmChoice(Choice cmChoice) {
		this.cmChoice = Objects.requireNonNull(cmChoice);
	}

	public Choice getTdChoice() {
		return tdChoice;
	}

	/**
	 * @param tdChoice
	 *            not <code> null </code>
	 */
	public void setTdChoice(Choice tdChoice) {
		this.tdChoice = Objects.requireNonNull(tdChoice);
	}

	public Choice getTpChoice() {
		return tpChoice;
	}

	/**
	 * @param tpChoice
	 *            not <code> null </code>
	 */
	public void setTpChoice(Choice tpChoice) {
		this.tpChoice = Objects.requireNonNull(tpChoice);
	}

}