package io.github.oliviercailloux.teach_spreadsheets.assignment;

import com.google.common.base.MoreObjects;
import com.google.common.base.VerifyException;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.SubCourseKind;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

/**
 * Immutable. This class provides, for a given course, a teacher that was
 * assigned and details the number of TD, TP, CMTD, CMTP and CM groups the
 * teacher will have to give lessons to.
 *
 */
public class TeacherAssignment {
	private final Course course;
	private final Teacher teacher;
	private int countGroupsTD;
	private int countGroupsTP;
	private int countGroupsCMTD;
	private int countGroupsCMTP;
	private int countGroupsCM;

	private TeacherAssignment(Course course, Teacher teacher) {
		this.course = checkNotNull(course, "The course must not be null.");
		this.teacher = checkNotNull(teacher, "The teacher assigned must not be null.");
		countGroupsTD = 0;
		countGroupsTP = 0;
		countGroupsCMTD = 0;
		countGroupsCMTP = 0;
		countGroupsCM = 0;
	}

	public static class Builder {
		private TeacherAssignment teacherAssignmentToBuild;

		private Builder(Course course, Teacher teacher) {
			teacherAssignmentToBuild = new TeacherAssignment(course, teacher);
		}

		public static Builder newInstance(Course course, Teacher teacher) {
			return new Builder(course, teacher);
		}

		/**
		 * Builds a TeacherAssignment.
		 * 
		 * @return teacherAssignmentToBuild - the TeacherAssignment that was built.
		 * 
		 */
		public TeacherAssignment build() {
			return teacherAssignmentToBuild;
		}

		/**
		 * Sets the value of the attribute countGroupsTD of teacherAssignmentToBuild.
		 * This value must be positive and inferior to the number of TD groups of the
		 * course.
		 * 
		 * @param countGroupsTD - the integer used to set the value of the attribute
		 *                      countGroupsTD
		 * 
		 * @return this - the object that called the method
		 * 
		 * @throws IllegalArgumentException if the parameter is negative or exceeds the
		 *                                  number of TD groups of the course
		 */
		public Builder setCountGroupsTD(int countGroupsTD) {
			checkArgument(countGroupsTD >= 0, "The number of TD groups must be positive.");
			checkArgument(countGroupsTD <= teacherAssignmentToBuild.course.getCountGroupsTD(),
					"The number of TD groups assigned must not exceed the number of TD groups of the course.");
			this.teacherAssignmentToBuild.countGroupsTD = countGroupsTD;
			return this;
		}

		/**
		 * Sets the value of the attribute countGroupsTP of teacherAssignmentToBuild.
		 * This value must be positive and inferior to the number of TP groups of the
		 * course.
		 * 
		 * @param countGroupsTP - the integer used to set the value of the attribute
		 *                      countGroupsTP
		 * 
		 * @return this - the object that called the method
		 * 
		 * @throws IllegalArgumentException if the parameter is negative or exceeds the
		 *                                  number of TP groups of the course
		 */
		public Builder setCountGroupsTP(int countGroupsTP) {
			checkArgument(countGroupsTP >= 0, "The number of TP groups must be positive.");
			checkArgument(countGroupsTP <= teacherAssignmentToBuild.course.getCountGroupsTP(),
					"The number of TP groups assigned must not exceed the number of TP groups of the course.");
			this.teacherAssignmentToBuild.countGroupsTP = countGroupsTP;
			return this;
		}

		/**
		 * Sets the value of the attribute countGroupsCMTD of teacherAssignmentToBuild.
		 * This value must be positive and inferior to the number of CMTD groups of the
		 * course.
		 * 
		 * @param countGroupsCMTD - the integer used to set the value of the attribute
		 *                        countGroupsCMTD
		 * 
		 * @return this - the object that called the method
		 * 
		 * @throws IllegalArgumentException if the parameter is negative or exceeds the
		 *                                  number of CMTD groups of the course
		 */
		public Builder setCountGroupsCMTD(int countGroupsCMTD) {
			checkArgument(countGroupsCMTD >= 0, "The number of CMTD groups must be positive.");
			checkArgument(countGroupsCMTD <= teacherAssignmentToBuild.course.getCountGroupsCMTD(),
					"The number of CMTD groups assigned must not exceed the number of CMTD groups of the course.");
			this.teacherAssignmentToBuild.countGroupsCMTD = countGroupsCMTD;
			return this;
		}

		/**
		 * Sets the value of the attribute countGroupsCMTP of teacherAssignmentToBuild.
		 * This value must be positive and inferior to the number of CMTP groups of the
		 * course.
		 * 
		 * @param countGroupsCMTP - the integer used to set the value of the attribute
		 *                        countGroupsCMTP
		 * 
		 * @return this - the object that called the method
		 * 
		 * @throws IllegalArgumentException if the parameter is negative or exceeds the
		 *                                  number of CMTP groups of the course
		 */
		public Builder setCountGroupsCMTP(int countGroupsCMTP) {
			checkArgument(countGroupsCMTP >= 0, "The number of CMTP groups must be positive.");
			checkArgument(countGroupsCMTP <= teacherAssignmentToBuild.course.getCountGroupsCMTP(),
					"The number of CMTP groups assigned must not exceed the number of CMTP groups of the course.");
			this.teacherAssignmentToBuild.countGroupsCMTP = countGroupsCMTP;
			return this;
		}

		/**
		 * Sets the value of the attribute countGroupsCM of teacherAssignmentToBuild.
		 * This value must be positive and inferior to the number of CM groups of the
		 * course.
		 * 
		 * @param countGroupsCM - the integer used to set the value of the attribute
		 *                      countGroupsCM
		 * 
		 * @return this - the object that called the method
		 * 
		 * @throws IllegalArgumentException if the parameter is negative or exceeds the
		 *                                  number of CM groups of the course
		 */
		public Builder setCountGroupsCM(int countGroupsCM) {
			checkArgument(countGroupsCM >= 0, "The number of CM groups must be positive.");
			checkArgument(countGroupsCM <= teacherAssignmentToBuild.course.getCountGroupsCM(),
					"The number of CM groups assigned must not exceed the number of CM groups of the course.");
			this.teacherAssignmentToBuild.countGroupsCM = countGroupsCM;
			return this;
		}

		public int getCountGroupsTD() {
			return this.teacherAssignmentToBuild.countGroupsTD;
		}

		public int getCountGroupsTP() {
			return this.teacherAssignmentToBuild.countGroupsTP;
		}

		public int getCountGroupsCMTD() {
			return this.teacherAssignmentToBuild.countGroupsCMTD;
		}

		public int getCountGroupsCMTP() {
			return this.teacherAssignmentToBuild.countGroupsCMTP;
		}

		public int getCountGroupsCM() {
			return this.teacherAssignmentToBuild.countGroupsCM;
		}

	}

	public Course getCourse() {
		return course;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public int getCountGroupsTD() {
		return countGroupsTD;
	}

	public int getCountGroupsTP() {
		return countGroupsTP;
	}

	public int getCountGroupsCMTD() {
		return countGroupsCMTD;
	}

	public int getCountGroupsCMTP() {
		return countGroupsCMTP;
	}

	public int getCountGroupsCM() {
		return countGroupsCM;
	}
	
	/**
	 * This method is a standardized getter for the groups.
	 * 
	 * @param group - the name of the group whose number we want to get.
	 * 
	 * @return - the number of CM or TD or TP or CMTD or CMTP groups depending on
	 *         the value of the parameter group.
	 * 
	 * @throws VerifyException if group is not equal to "CM" or "TD" or "TP" or
	 *                         "CMTD" or "CMTP".
	 */
	public int getCountGroups(SubCourseKind group) {
		switch (group) {
		case CM:
			return getCountGroupsCM();
		case TD:
			return getCountGroupsTD();
		case TP:
			return getCountGroupsTP();
		case CMTD:
			return getCountGroupsCMTD();
		case CMTP:
			return getCountGroupsCMTP();
		default:
			throw new VerifyException("The argument must be CM, TD, TP, CMTD or CMTP.");
		}
	}
	
	@Override
	public boolean equals(Object o2) {
		if (!(o2 instanceof TeacherAssignment)) {
			return false;
		}
		if (this == o2) {
			return true;
		}
		TeacherAssignment ta2 = (TeacherAssignment) o2;

		return course.equals(ta2.course) && teacher.equals(ta2.teacher) && countGroupsTD == ta2.countGroupsTD
				&& countGroupsTP == ta2.countGroupsTP && countGroupsCMTD == ta2.countGroupsCMTD
				&& countGroupsCMTP == ta2.countGroupsCMTP && countGroupsCM == ta2.countGroupsCM;
	}

	@Override
	public int hashCode() {
		return Objects.hash(course, teacher, countGroupsTD, countGroupsTP, countGroupsCMTD, countGroupsCMTP,
				countGroupsCM);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("Course", course.getName())
				.add("First name", teacher.getFirstName()).add("Last name", teacher.getLastName())
				.add("Number of TD groups", countGroupsTD).add("Number of TP groups", countGroupsTP)
				.add("Number of CMTD groups", countGroupsCMTD).add("Number of CMTP groups", countGroupsCMTP)
				.add("Number of CM groups", countGroupsCM).toString();
	}
}