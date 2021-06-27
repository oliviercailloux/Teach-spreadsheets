package io.github.oliviercailloux.teach_spreadsheets.online.write;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;
import com.microsoft.graph.requests.GraphServiceClient;

import io.github.oliviercailloux.teach_spreadsheets.assignment.CourseAssignment;
import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.SubCourseKind;
import io.github.oliviercailloux.teach_spreadsheets.bimodal.OnlineWorksheetWriter;
import io.github.oliviercailloux.teach_spreadsheets.bimodal.WorksheetWriter;
import io.github.oliviercailloux.teach_spreadsheets.bimodal.WriteException;
import okhttp3.Request;

public class XlsSummarizer {
	private final static int TITLE_POSITION = 0;
	private final static int TEAM = 5;
	private final static String STUDY_LEVEL_POSITION = "A3";
	private final static String SEMESTER_POSITION = "B3";
	private final static int COURSE_TYPE_POSITION = 2;
	private final static String GROUPS_NUMBER_POSITION = "D3";
	private final static int NUMBER_HOURS_POSITION = 4;
	private final static int CANDIDATES__POSITION = 6;
	private final static String CHOICES_POSITION = "H3";
	private final static int ASSIGNMENT_POSITION = 7;
	private final static int COMMENTS_POSITION = 8;
	private static WorksheetWriter wWriter;

	private int line;

	private ImmutableSet<Course> allCourses;
	private Set<CoursePref> prefs;
	private Set<CourseAssignment> allCoursesAssigned;

	private XlsSummarizer(Set<Course> allCourses, Set<CoursePref> prefsToBeSet) {
		this.allCourses = ImmutableSet.copyOf(allCourses);
		prefs = new LinkedHashSet<>();
		allCoursesAssigned = new LinkedHashSet<>();
		addPrefs(prefsToBeSet);
	}

	public static XlsSummarizer newInstance(Set<Course> allCourses, Set<CoursePref> prefsToBeSet) {
		checkNotNull(allCourses, "The set of courses should not be null.");
		checkNotNull(prefsToBeSet, "The preferences must not be null.");
		return new XlsSummarizer(allCourses, prefsToBeSet);
	}

	/**
	 * This method adds new teacher's preferences for courses.
	 * 
	 * @param prefsToBeSet - These are the courses preferences to be written in the
	 *                     FichierAgrege
	 * 
	 * @throws IllegalArgumentException if we want to add a teacher's preference for
	 *                                  a course that is not in allCourses or if we
	 *                                  want to add a teacher's preferences for a
	 *                                  course while there is already one in prefs.
	 */
	public void addPrefs(Set<CoursePref> prefsToBeSet) {

		Set<Course> coursesInPrefs = new LinkedHashSet<>();
		for (CoursePref coursePref : prefsToBeSet) {
			coursesInPrefs.add(coursePref.getCourse());
		}
		checkArgument(allCourses.containsAll(coursesInPrefs),
				"The preferences must be for courses specified in allCourses attribute.");

		for (CoursePref pref1 : prefs) {
			for (CoursePref pref2 : prefsToBeSet) {
				checkArgument(
						!(pref1.getTeacher().equals(pref2.getTeacher()) && pref1.getCourse().equals(pref2.getCourse())),
						"The preferences of a teacher for a course should be set once.");
			}
		}

		prefs.addAll(prefsToBeSet);
	}

	/**
	 * This method sets all the assignments for the courses.
	 * 
	 * @param assignmentsToBeSet - These are all the courses' assignments to be
	 *                           written in the FichierAgrege
	 * 
	 */
	public void setAllCoursesAssigned(Set<CourseAssignment> assignmentsToBeSet) {
		checkNotNull(assignmentsToBeSet, "The course assignments should not be null.");

		Set<Course> coursesInAssignments = assignmentsToBeSet.stream().map(CourseAssignment::getCourse)
				.collect(Collectors.toSet());

		checkArgument(allCourses.containsAll(coursesInAssignments),
				"The assignments must be for courses specified in allCourses attribute.");

		allCoursesAssigned = assignmentsToBeSet;
	}

	/**
	 * This method adds the headers to the sheet.
	 * @throws WriteException 
	 * 
	 */
	public static void setSummarizerSheetHeader() throws WriteException {
		wWriter.setValueAt(1, COURSE_TYPE_POSITION, "Type de cours");
		wWriter.setValueAt(1, NUMBER_HOURS_POSITION, "Nbre hetd");
		wWriter.setValueAt(1, TEAM, "Equipe 2010-2021");
		wWriter.setValueAt(1, CANDIDATES__POSITION, "Candidat");
		wWriter.setValueAt(1, ASSIGNMENT_POSITION, "AFFECTATION");
		wWriter.setValueAt(1, COMMENTS_POSITION, "Commentaire");

		formatHeaders();
	}

	private static void formatHeaders() throws WriteException {
		
		Set<Integer> headersPositions = Set.of(COURSE_TYPE_POSITION, NUMBER_HOURS_POSITION, CANDIDATES__POSITION,
				ASSIGNMENT_POSITION, COMMENTS_POSITION, TEAM,3);

		for (int position : headersPositions) {
			wWriter.setBackgroundColor(1, position, "#98d454");
			wWriter.setFont(1, position, true, "Black", 12.0, "Arial");
			wWriter.setFormat(1, position, 100.0, "center",null);

		}

	}

	/**
	 * This method creates a summarized Xls in your online repository. For each
	 * course, it writes all the teachers who want to teach the course, their
	 * preferences and, possibly, their assignments.
	 * @throws WriteException 
	 * 
	 */
	public void createSummary(String fileId, String worksheetName, GraphServiceClient<Request> graphClient) throws WriteException {
		wWriter = OnlineWorksheetWriter.loadExistingSheet(fileId, worksheetName, graphClient);

		setSummarizerSheetHeader();

		line = 1;
		int compteur;
		
		LinkedList<Course> allcourse = new LinkedList<>(allCourses);
		Collections.sort(allcourse, new Comparator<Course>() {
			@Override
			public int compare(Course c1, Course c2) {
				return Integer.valueOf(c1.getSemester()).compareTo(c2.getSemester());
			}
		});
		 
		int semester = allcourse.getFirst().getSemester();
		Course lastCourse = allcourse.getLast();
		int semesterRow = 1 ;
		
		for (Course course : allcourse) {
			compteur = 0;
			line++;
			if(course.getSemester() > semester) {
				wWriter.setValueAt(semesterRow, 1, "S " + semester );
				wWriter.cellFusion(semesterRow, 1,line-1 ,1 );
				wWriter.setFont(semesterRow, 1, true, null, null, null);
				wWriter.setFormat(semesterRow, 1, 50.0, "center","center");
				semester =  course.getSemester()  ;
				semesterRow = line ;
			}
			if(lastCourse.equals(course)) {
				wWriter.setValueAt(semesterRow, 1, "S " + semester );
				wWriter.setFormat(semesterRow, 1, 50.0, "center","center");
				wWriter.setFont(semesterRow, 1, true, null, null, null);
				wWriter.cellFusion(semesterRow, 1,line ,1 );
			}
			
			wWriter.setValueAt(line, 2, course.getName());
			wWriter.setBackgroundColor(line, 2, "#e0dcdc");
			wWriter.cellFusion(line, COURSE_TYPE_POSITION,line ,COMMENTS_POSITION );

			// formatCourseHeader();

			Set<TeacherAssignment> teachersAssigned = new LinkedHashSet<>();

			for (CourseAssignment courseAssignment : allCoursesAssigned) {
				if (course.equals(courseAssignment.getCourse())) {
					teachersAssigned.addAll(courseAssignment.getTeacherAssignments());
				}
			}

			Set<CoursePref> prefsForGroup;
			for (SubCourseKind group : SubCourseKind.values()) {
				prefsForGroup = new LinkedHashSet<>();

				if (course.getCountGroups(group) > 0) {

					for (CoursePref p : prefs) {
						if (course.equals(p.getCourse()) && !p.getPref(group).toString().equals("UNSPECIFIED")) {
							prefsForGroup.add(p);
						}
					}
					if (group.toString() != "CM") {
						compteur++;
					}

					setSummarizedFileForGroup(course, group, prefsForGroup, teachersAssigned, compteur);
				}

			}
			
		}
		

	}

	

	private void setSummarizedFileForGroup(Course course, SubCourseKind group, Set<CoursePref> prefsForGroup,
			Set<TeacherAssignment> teachersAssigned, int compteur) throws WriteException {
		checkNotNull(course, "The course should not be null.");
		checkNotNull(group, "The group should not be null.");
		checkNotNull(prefsForGroup, "The set of preferences for the group should not be null.");
		checkNotNull(teachersAssigned, "The teachers' assignments should not be null.");
		boolean courseHasTeacher = false;
		line++;

		wWriter.setValueAt(line, 2, group.toString());
		wWriter.setValueAt(line, 4, String.valueOf(course.getNbMinutes(group) / 60.0));

		if (group.toString() != "CM") {
			wWriter.setValueAt(line, 3, compteur + "");

		}

		for (CoursePref p : prefsForGroup) {

			courseHasTeacher = true;

			wWriter.setValueAt(line, 6, "Choix " + p.getPref(group).toString() + " - " + p.getTeacher().getFirstName()
					+ " " + p.getTeacher().getLastName());

			for (TeacherAssignment ta : teachersAssigned) {
				if (p.getTeacher().equals(ta.getTeacher()) && ta.getCountGroups(group) != 0) {
					wWriter.setValueAt(line, 7, ta.getTeacher().getFirstName() + " " + ta.getTeacher().getLastName());

				}
			}

		}

	}
}