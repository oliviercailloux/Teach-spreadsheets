package io.github.oliviercailloux.teach_spreadsheets.online.write;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;

import org.junit.jupiter.api.Test;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableSet;
import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.requests.GraphServiceClient;

import io.github.oliviercailloux.teach_spreadsheets.online.authentication.Authenticator;
import io.github.oliviercailloux.teach_spreadsheets.online.read.WorksheetReader;
import io.github.oliviercailloux.teach_spreadsheets.online.write.AssignmentPerTeacher;
import okhttp3.Request;
import io.github.oliviercailloux.teach_spreadsheets.bimodal.OnlineWorksheetWriter;
import io.github.oliviercailloux.teach_spreadsheets.bimodal.WorksheetWriter;

import io.github.oliviercailloux.teach_spreadsheets.assignment.CourseAssignment;
import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class AssignmentPerTeacherTests {

	@Test
	void testCreateAssignmentPerTeacher() throws Exception {
		String teacherFirstName = "TestFN";
		String teacherLastName = "TestLN";

		Teacher teacher1 = Teacher.Builder.newInstance().setFirstName(teacherFirstName).setLastName(teacherLastName)
				.setAddress("Place de Maréchal de Lattre de Tassigny, 75016 PARIS").setDauphineEmail("teacher1@dauphine.fr")
				.setPersonalEmail("teacher1@gmail.com").setOffice("B512").setPersonalPhone("07 06 05 04 03")
				.setMobilePhone("06 12 34 56 78").setDauphinePhoneNumber("06 07 08 09 10").setStatus("MCF").build();

		Course course1 = Course.Builder.newInstance().setName("testcourse1").setStudyYear(2016).setStudyLevel("DE1")
				.setSemester(1).setCountGroupsCM(3).setCountGroupsTD(4).setNbMinutesCM(60).setNbMinutesTD(60).build();
		Course course2 = Course.Builder.newInstance().setName("testcourse2").setStudyYear(2016).setStudyLevel("DE2")
				.setSemester(1).setCountGroupsCM(5).setCountGroupsTD(5).setNbMinutesCM(300).setNbMinutesTD(600).build();

		TeacherAssignment teacherAssignment = TeacherAssignment.Builder.newInstance(course1, teacher1)
				.setCountGroupsTD(1).build();
		CourseAssignment.Builder courseAssignmentBuilder = CourseAssignment.Builder.newInstance(course1);
		courseAssignmentBuilder.addTeacherAssignment(teacherAssignment);

		TeacherAssignment teacherAssignment2 = TeacherAssignment.Builder.newInstance(course2, teacher1)
				.setCountGroupsTD(1).build();
		CourseAssignment.Builder courseAssignmentBuilder2 = CourseAssignment.Builder.newInstance(course2);
		courseAssignmentBuilder2.addTeacherAssignment(teacherAssignment2);

		CourseAssignment courseAssignment = courseAssignmentBuilder.build();
		CourseAssignment courseAssignment2 = courseAssignmentBuilder2.build();

		ImmutableSet<CourseAssignment> allCoursesAssigned = ImmutableSet.of(courseAssignment,courseAssignment2);

		IAuthenticationProvider token = Authenticator.getAuthenticationProvider();

		GraphServiceClient<Request> graphClient = GraphServiceClient.builder().authenticationProvider(token)
				.buildClient();

		String fileId = WorksheetReader.getFileId("AssignmentPerTeacher.xlsx", graphClient);

		AssignmentPerTeacher.assignmentPerTeacher(fileId, graphClient, teacher1, allCoursesAssigned);
		assertTrue(OnlineWorksheetWriter.checkExistingSheet(fileId, teacher1.getFirstName()+"_"+teacher1.getLastName(), graphClient));
	}
}