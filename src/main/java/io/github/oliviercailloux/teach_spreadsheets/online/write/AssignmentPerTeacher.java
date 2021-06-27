package io.github.oliviercailloux.teach_spreadsheets.online.write;

import java.io.IOException;
import java.util.Set;

import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.style.Border;
import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.style.StyleTypeDefinitions.SupportedLinearMeasure;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableSet;

import static com.google.common.base.Preconditions.checkNotNull;

import io.github.oliviercailloux.teach_spreadsheets.base.SubCourseKind;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;
import io.github.oliviercailloux.teach_spreadsheets.assignment.CourseAssignment;
import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;
import io.github.oliviercailloux.teach_spreadsheets.online.authentication.Authenticator;
import io.github.oliviercailloux.teach_spreadsheets.online.read.WorksheetReader;
import io.github.oliviercailloux.teach_spreadsheets.write.Cell;
import io.github.oliviercailloux.teach_spreadsheets.bimodal.OnlineWorksheetWriter;
import io.github.oliviercailloux.teach_spreadsheets.bimodal.WorksheetWriter;
import io.github.oliviercailloux.teach_spreadsheets.bimodal.WriteException;

import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.requests.GraphServiceClient;
import okhttp3.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonParser;

public class AssignmentPerTeacher {

	private static OnlineWorksheetWriter wWriter;

	/**
	 * These Strings are the positions in the Summarized Xlsx of the Teachers'
	 * personal information and the document's headers
	 */

	private final static Cell TITLE_POSITION = new Cell("A1");

	private final static Cell TEACHER_FIRST_NAME_POSITION = new Cell("A3");
	private final static Cell TEACHER_FIRST_NAME_POSITION_VALUE = new Cell("A4");

	private final static Cell TEACHER_LAST_NAME_POSITION = new Cell("C3");
	private final static Cell TEACHER_LAST_NAME_POSITION_VALUE = new Cell("C4");

	private final static Cell STATUS_POSITION = new Cell("A6");
	private final static Cell STATUS_POSITION_VALUE = new Cell("B6");

	private final static Cell OFFICE_POSITION = new Cell("C6");
	private final static Cell OFFICE_POSITION_VALUE = new Cell("D6");

	private final static Cell PERSONAL_EMAIL_POSITION = new Cell("A8");
	private final static Cell PERSONAL_EMAIL_POSITION_VALUE = new Cell("C8");

	private final static Cell DAUPHINE_EMAIL_POSITION = new Cell("A10");
	private final static Cell DAUPHINE_EMAIL_POSITION_VALUE = new Cell("C10");

	private final static Cell PERSONAL_PHONE_POSITION = new Cell("A12");
	private final static Cell PERSONAL_PHONE_POSITION_VALUE = new Cell("A13");

	private final static Cell MOBILE_PHONE_POSITION = new Cell("C12");
	private final static Cell MOBILE_PHONE_POSITION_VALUE = new Cell("C13");

	private final static Cell DAUPHINE_PHONE_NUMBER_POSITION = new Cell("E12");
	private final static Cell DAUPHINE_PHONE_NUMBER_POSITION_VALUE = new Cell("E13");

	private final static Cell STUDY_LEVEL_POSITION = new Cell("A16");

	private final static Cell SEMESTER_POSITION = new Cell("B16");

	private final static Cell COURSE_POSITION = new Cell("C16");

	private final static Cell TYPE_POSITION = new Cell("D16");

	private final static Cell NUMBER_HOURS_POSITION = new Cell("E16");

	/**
	 * This method formats the headers of the table in the sheet.
	 * @throws WriteException
	 */
	private static void formatHeaders() throws WriteException {
		Set<Cell> personalInfoPositions = Set.of(TEACHER_FIRST_NAME_POSITION, TEACHER_LAST_NAME_POSITION,
				STATUS_POSITION, OFFICE_POSITION, PERSONAL_EMAIL_POSITION, DAUPHINE_EMAIL_POSITION,
				PERSONAL_PHONE_POSITION, MOBILE_PHONE_POSITION, DAUPHINE_PHONE_NUMBER_POSITION);		

		Set<Cell> valuesPositions = Set.of(TEACHER_FIRST_NAME_POSITION_VALUE, TEACHER_LAST_NAME_POSITION_VALUE,
				STATUS_POSITION_VALUE, OFFICE_POSITION_VALUE, PERSONAL_EMAIL_POSITION_VALUE,
				DAUPHINE_EMAIL_POSITION_VALUE, PERSONAL_PHONE_POSITION_VALUE, MOBILE_PHONE_POSITION_VALUE,
				DAUPHINE_PHONE_NUMBER_POSITION_VALUE);

		Set<Cell> headersPositions = Set.of(TITLE_POSITION, STUDY_LEVEL_POSITION, SEMESTER_POSITION, COURSE_POSITION,
				TYPE_POSITION, NUMBER_HOURS_POSITION);

		for (Cell cell : personalInfoPositions) {
			wWriter.setFont(cell.getRow(), cell.getColumn(), true, "Black", 9d, "Arial");
		}

		for (Cell cell : valuesPositions) {
			wWriter.setAllBordersBlack(cell.getRow(), cell.getColumn());
		}

		for (Cell cell : headersPositions) {
			wWriter.setFont(cell.getRow(), cell.getColumn(), true, "#2A6099", 12d, "Arial");
			if (!cell.equals(TITLE_POSITION)) {
				wWriter.setFormat(cell.getRow(), cell.getColumn(),100.0,"center","center");
				wWriter.setAllBordersBlack(cell.getRow(), cell.getColumn());
			}
		}

	}

	/**
	 * This method writes the headers in the online document.
	 * @param teacher       - the teacher for who we want to do the summarized Fiche de
	 *                			service
	 * @throws WriteException
	 */
	private static void headersToXlsx(Teacher teacher) throws WriteException {
		checkNotNull(teacher, "The teacher should not be null.");

		wWriter.setValueAt(TITLE_POSITION.getRow(),TITLE_POSITION.getColumn(),"Assignment per Teacher");

		wWriter.setValueAt(TEACHER_FIRST_NAME_POSITION.getRow(),TEACHER_FIRST_NAME_POSITION.getColumn(),"FIRST NAME");
		wWriter.setValueAt(TEACHER_FIRST_NAME_POSITION_VALUE.getRow(),TEACHER_FIRST_NAME_POSITION_VALUE.getColumn(),teacher.getFirstName());

		wWriter.setValueAt(TEACHER_LAST_NAME_POSITION.getRow(),TEACHER_LAST_NAME_POSITION.getColumn(),"LAST NAME");
		wWriter.setValueAt(TEACHER_LAST_NAME_POSITION_VALUE.getRow(),TEACHER_LAST_NAME_POSITION_VALUE.getColumn(),teacher.getLastName());

		wWriter.setValueAt(STATUS_POSITION.getRow(),STATUS_POSITION.getColumn(),"STATUS");
		wWriter.setValueAt(STATUS_POSITION_VALUE.getRow(),STATUS_POSITION_VALUE.getColumn(),teacher.getStatus());

		wWriter.setValueAt(OFFICE_POSITION.getRow(),OFFICE_POSITION.getColumn(),"OFFICE");
		wWriter.setValueAt(OFFICE_POSITION_VALUE.getRow(),OFFICE_POSITION_VALUE.getColumn(),teacher.getOffice());

		wWriter.setValueAt(PERSONAL_EMAIL_POSITION.getRow(),PERSONAL_EMAIL_POSITION.getColumn(),"PERSONAL E-MAIL");
		wWriter.setValueAt(PERSONAL_EMAIL_POSITION_VALUE.getRow(),PERSONAL_EMAIL_POSITION_VALUE.getColumn(),teacher.getPersonalEmail());

		wWriter.setValueAt(DAUPHINE_EMAIL_POSITION.getRow(),DAUPHINE_EMAIL_POSITION.getColumn(),"DAUPHINE E-MAIL");
		wWriter.setValueAt(DAUPHINE_EMAIL_POSITION_VALUE.getRow(),DAUPHINE_EMAIL_POSITION_VALUE.getColumn(),teacher.getDauphineEmail());

		wWriter.setValueAt(PERSONAL_PHONE_POSITION.getRow(),PERSONAL_PHONE_POSITION.getColumn(),"PERSONAL PHONE");
		wWriter.setValueAt(PERSONAL_PHONE_POSITION_VALUE.getRow(),PERSONAL_PHONE_POSITION_VALUE.getColumn(),teacher.getPersonalPhone());

		wWriter.setValueAt(MOBILE_PHONE_POSITION.getRow(),MOBILE_PHONE_POSITION.getColumn(),"MOBILE PHONE");
		wWriter.setValueAt(MOBILE_PHONE_POSITION_VALUE.getRow(),MOBILE_PHONE_POSITION_VALUE.getColumn(),teacher.getMobilePhone());

		wWriter.setValueAt(DAUPHINE_PHONE_NUMBER_POSITION.getRow(),DAUPHINE_PHONE_NUMBER_POSITION.getColumn(),"DAUPHINE PHONE NUMBER");
		wWriter.setValueAt(DAUPHINE_PHONE_NUMBER_POSITION_VALUE.getRow(),DAUPHINE_PHONE_NUMBER_POSITION_VALUE.getColumn(),teacher.getDauphinePhoneNumber());

		wWriter.setValueAt(STUDY_LEVEL_POSITION.getRow(),STUDY_LEVEL_POSITION.getColumn(),"STUDY LEVEL");

		wWriter.setValueAt(SEMESTER_POSITION.getRow(),SEMESTER_POSITION.getColumn(),"SEMESTER");

		wWriter.setValueAt(COURSE_POSITION.getRow(),COURSE_POSITION.getColumn(),"COURSE");

		wWriter.setValueAt(TYPE_POSITION.getRow(),TYPE_POSITION.getColumn(),"TYPE");

		wWriter.setValueAt(NUMBER_HOURS_POSITION.getRow(),NUMBER_HOURS_POSITION.getColumn(),"Nbr H");

		formatHeaders();
	}

	/**
	 * This method writes the courses of the teacher in the online document.
	 * @param line          - the line where we start to write
	 * @param assignments   - the assignments that we will write in the sheet
	 * @throws WriteException
	 */
	private static void completeCourses(int line, ImmutableSet<TeacherAssignment> assignments) throws WriteException {
		checkNotNull(line, "The line given should not be null.");
		checkNotNull(assignments, "The assignments should not be null.");

		int totalNumberMinutes;

		int line_tmp = line;
		totalNumberMinutes = 0;
		for (TeacherAssignment ta : assignments) {

			wWriter.setValueAt(line_tmp, 0,String.valueOf(ta.getCourse().getStudyLevel()));
			wWriter.setBorder(line_tmp, 0, "Black", "EdgeRight","Thin");
			wWriter.setValueAt(line_tmp, 1,String.valueOf(ta.getCourse().getSemester()));
			wWriter.setBorder(line_tmp, 1, "Black", "EdgeRight","Thin");
			wWriter.setValueAt(line_tmp, 2,ta.getCourse().getName());
			wWriter.setBorder(line_tmp, 2, "Black", "EdgeRight","Thin");

			for (SubCourseKind group : SubCourseKind.values()) {
				if (ta.getCountGroups(group) != 0) {
					wWriter.setValueAt(line_tmp, 3, group.toString());
					wWriter.setBorder(line_tmp, 3, "Black", "EdgeRight","Thin");
					wWriter.setValueAt(line_tmp, 4,String.valueOf(ta.getCourse().getNbMinutes(group) / 60.0));
					wWriter.setBorder(line_tmp, 4, "Black", "EdgeRight","Thin");
					totalNumberMinutes += ta.getCourse().getNbMinutes(group);
					line_tmp++;
				}
			}
		}
		wWriter.setBorder(line_tmp, 0, "Black", "EdgeTop","Thin");
		wWriter.setBorder(line_tmp, 1, "Black", "EdgeTop","Thin");
		wWriter.setBorder(line_tmp, 2, "Black", "EdgeTop","Thin");
		wWriter.setBorder(line_tmp, 3, "Black", "EdgeTop","Thin");
		wWriter.setBorder(line_tmp, 4, "Black", "EdgeTop","Thin");

		line_tmp += 3;

		wWriter.setValueAt(line_tmp, 3,"TOTAL");

		wWriter.setValueAt(line_tmp, 4,String.valueOf(totalNumberMinutes / 60d));
		wWriter.setAllBordersBlack(line_tmp, 4);

		totalNumberMinutes = 0;
	}
	/**
	 * This method creates new od and writes in an Excel Document already created online. For a given
	 * teacher, it writes all the informations about this teacher and the courses he/she will teach.
	 * 
	 * @param fileId        - the fileId needed to find the online sheet
	 * @param graphClient   - the graphClient needed to find the online sheet
	 * @param teacher            - the teacher for who we want to do the summarized
	 *                           Fiche de service
	 * @param allCoursesAssigned - a complete set of CourseAssignment (it represents
	 *                           all the assignments that were made).
	 * @throws WriteException
	 */
	public static void assignmentPerTeacher(String fileId, GraphServiceClient<Request> graphClient, Teacher teacher,
			Set<CourseAssignment> allCoursesAssigned) throws WriteException {
		checkNotNull(teacher, "The teacher should not be null.");
		checkNotNull(allCoursesAssigned, "The set of courses assigned should not be null.");

		String workSheetName = teacher.getFirstName()+"_"+teacher.getLastName();

		if(OnlineWorksheetWriter.checkExistingSheet(fileId, workSheetName, graphClient)) wWriter = OnlineWorksheetWriter.loadExistingSheet(fileId, workSheetName, graphClient);
		else wWriter = OnlineWorksheetWriter.loadNewSheet(fileId, workSheetName, graphClient);

		headersToXlsx(teacher);
		ImmutableSet<TeacherAssignment> assignments = CourseAssignment.getTeacherAssignments(teacher,
				allCoursesAssigned);
		int line = 16;
		completeCourses(line,assignments);
	}
}