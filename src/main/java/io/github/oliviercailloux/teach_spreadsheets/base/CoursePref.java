package io.github.oliviercailloux.teach_spreadsheets.base;

import com.google.common.base.MoreObjects;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * ImmutableSet. Class used to store a teacher's preference for one course. Uses
 * Builder pattern implementation.
 * 
 * @see https://codereview.stackexchange.com/questions/127391/simple-builder-pattern-implementation-for-building-immutable-objects/127509#127509
 */
public class CoursePref {
	private static final String EXCEPTION = "A preferred number of groups needs to be positive.";
	private Course course;
	private Teacher teacher;

	/**
	 * Degree of preference of teacher for course, represented by an enum type
	 */
	private Preference prefCM;
	private Preference prefTD;
	private Preference prefCMTD;
	private Preference prefTP;
	private Preference prefCMTP;

	/**
	 * Number of groups that the teacher wants to have
	 */
	private int prefNbGroupsCM;
	private int prefNbGroupsTD;
	private int prefNbGroupsCMTD;
	private int prefNbGroupsTP;
	private int prefNbGroupsCMTP;

	/**
	 * Checks the coherence of these arguments. If it's not coherent this method will throw an IllegalArgumentException.
	 * 
	 * @param nbGroups     the number of groups to assign to this type of course
	 * @param nbGroupsPref the preferred number of groups for the teacher
	 * @param nbMinutes    the number of minutes for the sessions of this type of
	 *                     course
	 * @param preference   the preference of the teacher for this type of course
	 */
	private static void checkCoherence(int nbGroups, int nbGroupsPref, int nbMinutes, Preference preference) {
		checkArgument((nbGroups != 0 && nbMinutes != 0) || preference == Preference.UNSPECIFIED, "Preference can't be specified if there are 0 groups and 0 minutes for a given type of course.");
		checkArgument(nbGroupsPref == 0 || preference != Preference.UNSPECIFIED, "Preference needs to be specified if there is more than 1 group that the teacher wants to get for a given type of course.");
		checkArgument(nbGroupsPref <= nbGroups, "The number of groups the teacher wants can't be greater than the number of groups.");
	}

	/**
	 * checks if the values for the preferences are valid according to the course
	 */
	private void checkCoherences() {
		checkNotNull(course);
		checkCoherence(course.getCountGroupsCM(), getPrefNbGroupsCM(), course.getNbMinutesCM(), getPrefCM());
		checkCoherence(course.getCountGroupsCMTD(), getPrefNbGroupsCMTD(), course.getNbMinutesCMTD(), getPrefCMTD());
		checkCoherence(course.getCountGroupsCMTP(), getPrefNbGroupsCMTP(), course.getNbMinutesCMTP(), getPrefCMTP());
		checkCoherence(course.getCountGroupsTD(), getPrefNbGroupsTD(), course.getNbMinutesTD(), getPrefTD());
		checkCoherence(course.getCountGroupsTP(), getPrefNbGroupsTP(), course.getNbMinutesTP(), getPrefTP());
	}

	private CoursePref() {
		prefCM = Preference.UNSPECIFIED;
		prefTD = Preference.UNSPECIFIED;
		prefCMTD = Preference.UNSPECIFIED;
		prefTP = Preference.UNSPECIFIED;
		prefCMTP = Preference.UNSPECIFIED;
	}

	public Course getCourse() {
		return course;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public Preference getPrefCM() {
		return prefCM;
	}

	public Preference getPrefTD() {
		return prefTD;
	}

	public Preference getPrefCMTD() {
		return prefCMTD;
	}

	public Preference getPrefTP() {
		return prefTP;
	}

	public Preference getPrefCMTP() {
		return prefCMTP;
	}

	public int getPrefNbGroupsCM() {
		return prefNbGroupsCM;
	}

	public int getPrefNbGroupsTD() {
		return prefNbGroupsTD;
	}

	public int getPrefNbGroupsCMTD() {
		return prefNbGroupsCMTD;
	}

	public int getPrefNbGroupsTP() {
		return prefNbGroupsTP;
	}

	public int getPrefNbGroupsCMTP() {
		return prefNbGroupsCMTP;
	}

	public static class Builder {
		private CoursePref coursePrefToBuild;

		public static Builder newInstance(Course course, Teacher teacher) {
			Builder builder = new Builder();
			builder.setCourse(course);
			builder.setTeacher(teacher);
			return builder;
		}

		private Builder() {
			coursePrefToBuild = new CoursePref();
		}

		public CoursePref build() {
			coursePrefToBuild.checkCoherences();
			CoursePref coursePrefBuilt = coursePrefToBuild;
			coursePrefToBuild = new CoursePref();
			return coursePrefBuilt;
		}

		private Builder setCourse(Course course) {
			checkNotNull(course, "Course needs to be instanciated.");
			this.coursePrefToBuild.course = course;
			return this;
		}

		private Builder setTeacher(Teacher teacher) {
			checkNotNull(teacher, "Teacher needs to be instanciated.");
			this.coursePrefToBuild.teacher = teacher;
			return this;
		}

		public Builder setPrefCM(Preference prefCM) {
			checkNotNull(prefCM);
			this.coursePrefToBuild.prefCM = prefCM;
			return this;
		}

		public Builder setPrefTD(Preference prefTD) {
			checkNotNull(prefTD);
			this.coursePrefToBuild.prefTD = prefTD;
			return this;
		}

		public Builder setPrefCMTD(Preference prefCMTD) {
			checkNotNull(prefCMTD);
			this.coursePrefToBuild.prefCMTD = prefCMTD;
			return this;
		}

		public Builder setPrefTP(Preference prefTP) {
			checkNotNull(prefTP);
			this.coursePrefToBuild.prefTP = prefTP;
			return this;
		}

		public Builder setPrefCMTP(Preference prefCMTP) {
			checkNotNull(prefCMTP);
			this.coursePrefToBuild.prefCMTP = prefCMTP;
			return this;
		}

		public Builder setPrefNbGroupsCM(int prefNbGroupsCM) {
			checkArgument(prefNbGroupsCM >= 0, EXCEPTION);
			this.coursePrefToBuild.prefNbGroupsCM = prefNbGroupsCM;
			return this;
		}

		public Builder setPrefNbGroupsTD(int prefNbGroupsTD) {
			checkArgument(prefNbGroupsTD >= 0, EXCEPTION);
			this.coursePrefToBuild.prefNbGroupsTD = prefNbGroupsTD;
			return this;
		}

		public Builder setPrefNbGroupsCMTD(int prefNbGroupsCMTD) {
			checkArgument(prefNbGroupsCMTD >= 0, EXCEPTION);
			this.coursePrefToBuild.prefNbGroupsCMTD = prefNbGroupsCMTD;
			return this;
		}

		public Builder setPrefNbGroupsTP(int prefNbGroupsTP) {
			checkArgument(prefNbGroupsTP >= 0, EXCEPTION);
			this.coursePrefToBuild.prefNbGroupsTP = prefNbGroupsTP;
			return this;
		}

		public Builder setPrefNbGroupsCMTP(int prefNbGroupsCMTP) {
			checkArgument(prefNbGroupsCMTP >= 0, EXCEPTION);
			this.coursePrefToBuild.prefNbGroupsCMTP = prefNbGroupsCMTP;
			return this;
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("prefCM", prefCM).add("prefTD", prefTD).add("prefCMTD", prefCMTD)
				.add("prefTP", prefTP).add("prefCMTP", prefCMTP)

				.add("prefNbGroupsCM", prefNbGroupsCM).add("prefNbGroupsTD", prefNbGroupsTD)
				.add("prefNbGroupsCMTD", prefNbGroupsCMTD).add("prefNbGroupsTP", prefNbGroupsTP)
				.add("prefNbGroupsCMTP", prefNbGroupsCMTP)

				.add("Course", course.toString()).add("Teacher", teacher.toString())

				.toString();
	}
}
