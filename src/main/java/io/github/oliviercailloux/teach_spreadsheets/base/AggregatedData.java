package io.github.oliviercailloux.teach_spreadsheets.base;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * Immutable. This class aggregates the TeacherPrefs built from the reading of
 * several input vows files.
 *
 */
public class AggregatedData {
	private ImmutableSet<TeacherPrefs> TeacherPrefsSet;
	private ImmutableSet<Course> courses;

	private AggregatedData(Set<TeacherPrefs> TeacherPrefsSet, Set<Course> courses) {
		this.TeacherPrefsSet = ImmutableSet.copyOf(TeacherPrefsSet);
		this.courses = ImmutableSet.copyOf(courses);
	}

	public static class Builder {

		/**
		 * Set of TeacherPrefs : it is used to build (by adding) TeacherPrefsSet attribute.
		 */
		private Set<TeacherPrefs> tempTeacherPrefsSet;
		private Map<Teacher, Set<CoursePref>> tempCoursePrefs;
		private Set<Course> courses;

		private Builder(Set<Course> courses) {
			tempTeacherPrefsSet = new LinkedHashSet<>();
			tempCoursePrefs = new LinkedHashMap<>();
			this.courses = courses;
		}

		/**
		 * This is the static factory method of the class AggregatedData.Builder. To
		 * build an AggregatedData, possibly from only CoursePrefs, a set of Course is
		 * necessary to serve as a reference.
		 * 
		 */
		public static Builder newInstance(Set<Course> courses) {
			checkNotNull(courses, "The set of courses must not be null.");
			return new Builder(courses);
		}

		public AggregatedData build() {
			mapToTeacherPrefs();
			checkState(tempTeacherPrefsSet.size() >= 1, "An AggregatedData must contain at least one TeacherPrefs.");
			return new AggregatedData(tempTeacherPrefsSet, courses);
		}

		/**
		 * This method adds a TeacherPrefs to the tempTeacherPrefsSet object.
		 * 
		 * @param teacherPrefs - the TeacherPrefs to be added
		 * 
		 * @throws IllegalArgumentException if the TeacherPrefs we want to add does not
		 *                                  contain the expected courses or contains the
		 *                                  preferences of a Teacher that was already
		 *                                  added.
		 */
		public void addTeacherPrefs(TeacherPrefs teacherPrefs) {
			checkNotNull(teacherPrefs, "The TeacherPrefs must not be null");

			Set<Course> teacherPrefsCourses = teacherPrefs.getCoursePrefs().stream().map(CoursePref::getCourse)
					.collect(Collectors.toSet());
			checkArgument(courses.equals(teacherPrefsCourses), "There must be the same courses in the teacherPrefs.");
			for (TeacherPrefs elt : tempTeacherPrefsSet) {
				checkArgument(!elt.getTeacher().equals(teacherPrefs.getTeacher()),
						"You cannot add twice all the preferences of the teacher : " + teacherPrefs.getTeacher());
			}

			tempTeacherPrefsSet.add(teacherPrefs);
		}
		
		public void addTeacherPrefsSet(Set<TeacherPrefs> teacherPrefsSet) {
			checkNotNull(teacherPrefsSet, "The set of TeacherPrefs must not be null.");
			
			for (TeacherPrefs teacherPrefs : teacherPrefsSet) {
				addTeacherPrefs(teacherPrefs);
			}
		}
		
		/**
		 * This method allows to build an AggregatedData by adding a set of CoursePref.
		 * 
		 * @param coursePrefs - the Set of coursePref to be added
		 * 
		 */
		public void addCoursePrefs(Set<CoursePref> coursePrefs) {
			checkNotNull(coursePrefs, "The set of CoursePref must not be null.");
			for (CoursePref coursePref : coursePrefs) {
				checkNotNull(coursePref);
				if(tempCoursePrefs.containsKey(coursePref.getTeacher())) {
					tempCoursePrefs.get(coursePref.getTeacher()).add(coursePref);
				}
				else {
					tempCoursePrefs.put(coursePref.getTeacher(), Set.of(coursePref));
				}
			}
		}
		
		/**
		 * This method builds complete TeacherPrefs objects, using the CoursePrefs given
		 * and stored in tempCoursePrefs, to complete tempTeacherPrefsSet the Set of TeacherPrefs
		 * stored in the AggregatedData object.
		 */
		private void mapToTeacherPrefs() {
			for (Teacher teacher : tempCoursePrefs.keySet()) {
				Set<CoursePref> coursePrefs = tempCoursePrefs.get(teacher);
				
				Set<Course> courses = Set.copyOf(this.courses);
				Set<Course> coursesInCoursePrefs = coursePrefs.stream().map(CoursePref::getCourse)
				.collect(Collectors.toSet());
				courses.removeAll(coursesInCoursePrefs);
				
				for (Course course : courses) {
					CoursePref coursePref = CoursePref.Builder.newInstance(course, teacher)
							.setPrefCM(Preference.UNSPECIFIED).setPrefTD(Preference.UNSPECIFIED)
							.setPrefTP(Preference.UNSPECIFIED).setPrefCMTD(Preference.UNSPECIFIED)
							.setPrefCMTP(Preference.UNSPECIFIED).build();
					coursePrefs.add(coursePref);
				}
				
				TeacherPrefs teacherPrefs = TeacherPrefs.newInstance(coursePrefs, teacher);
				
				this.addTeacherPrefs(teacherPrefs);
			}
		}
	}

	public ImmutableSet<TeacherPrefs> getTeacherPrefsSet() {
		return TeacherPrefsSet;
	}

	public ImmutableSet<Course> getCourses() {
		return courses;
	}

	/**
	 * This methods gets all the preferences of a given Teacher.
	 * 
	 * @param teacher - the Teacher whose preferences we want to get.
	 * 
	 * @return all the preferences of the given Teacher
	 * 
	 * @throws IllegalArgumentException if there is no preference referenced for the
	 *                                  given teacher.
	 */
	public ImmutableSet<CoursePref> getTeacherPrefs(Teacher teacher) {
		checkNotNull(teacher, "The teacher must not be null.");
		for (TeacherPrefs teacherPrefs : TeacherPrefsSet) {
			if (teacher.equals(teacherPrefs.getTeacher())) {
				return teacherPrefs.getCoursePrefs();
			}
		}
		throw new IllegalArgumentException("There is no preference referenced for this teacher.");
	}

	/**
	 * This method gets all the preferences expressed for a given Course.
	 * 
	 * @param course - the course whose preferences for we want to get.
	 * 
	 * @return all the preferences expressed for the given Course.
	 * 
	 */
	public ImmutableSet<CoursePref> getCoursePrefs(Course course) {
		checkNotNull(course, "The course must not be null.");
		Set<CoursePref> coursePrefs = new LinkedHashSet<>();
		for (TeacherPrefs teacherPrefs : TeacherPrefsSet) {
			coursePrefs.add(teacherPrefs.getCoursePref(course));
		}
		return ImmutableSet.copyOf(coursePrefs);
	}

}