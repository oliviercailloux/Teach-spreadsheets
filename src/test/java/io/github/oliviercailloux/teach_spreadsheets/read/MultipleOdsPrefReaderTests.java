package io.github.oliviercailloux.teach_spreadsheets.read;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.URL;
import java.nio.file.Path;
import java.util.Set;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.teach_spreadsheets.base.TeacherPrefs;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Preference;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class MultipleOdsPrefReaderTests {
	@Test
	void testReadFilesFromFolder() throws Exception {
		URL resourceUrl = PrefsInitializer.class.getResource("testFolder");
		Set<TeacherPrefs> teacherPrefsSet = MultipleOdsPrefReader.readFilesFromFolder(Path.of(resourceUrl.toURI())).getTeacherPrefsSet();
		assertEquals(2, teacherPrefsSet.size());
		for (TeacherPrefs teacherPrefs : teacherPrefsSet) {
			switch (teacherPrefs.getTeacher().getFirstName()) {
			case "John":
				checkInformationOfTeacherPrefsJohn(teacherPrefs);
				break;
			case "Jane":
				checkInformationOfTeacherPrefsJane(teacherPrefs);
				break;
			default:
				fail("The file read doesn't correspond to John nor Jane");
				break;
			}
		}
	}

	/**
	 * Checks information read from the John Ods Pref file.
	 */
	private void checkInformationOfTeacherPrefsJohn(TeacherPrefs teacherPrefs) {
		/** Checking the informations of the teacher: */
		Teacher actualTeacher = teacherPrefs.getTeacher();
		assertEquals("Doe", actualTeacher.getLastName());
		assertEquals("John", actualTeacher.getFirstName());
		assertEquals("19 rue Jacques Louvel-Tessier", actualTeacher.getAddress());
		assertEquals("75010", actualTeacher.getPostCode());
		assertEquals("Paris", actualTeacher.getCity());
		assertEquals("123456789", actualTeacher.getPersonalPhone());
		assertEquals("987654321", actualTeacher.getMobilePhone());
		assertEquals("john.doe@outlook.com", actualTeacher.getPersonalEmail());
		assertEquals("john.doe@dauphine.eu", actualTeacher.getDauphineEmail());
		assertEquals("MCF", actualTeacher.getStatus());
		assertEquals("1928373645", actualTeacher.getDauphinePhoneNumber());
		assertEquals("B048", actualTeacher.getOffice());

		/** Checking the information of the course in the cell P11 of the sheet DE1: */
		CoursePref actualCoursePref = teacherPrefs.getCoursePref("Macroéconomie : analyse de long terme");
		Course actualCourse = actualCoursePref.getCourse();
		Teacher acutalTeacherInPref = actualCoursePref.getTeacher();
		assertEquals(Preference.UNSPECIFIED, actualCoursePref.getPrefCM());
		assertEquals(Preference.B, actualCoursePref.getPrefTD());
		assertEquals(Preference.UNSPECIFIED, actualCoursePref.getPrefCMTD());
		assertEquals(Preference.UNSPECIFIED, actualCoursePref.getPrefTP());
		assertEquals(Preference.UNSPECIFIED, actualCoursePref.getPrefCMTP());
		assertEquals(0, actualCoursePref.getPrefNbGroupsCM());
		assertEquals(2, actualCoursePref.getPrefNbGroupsTD());
		assertEquals(0, actualCoursePref.getPrefNbGroupsCMTD());
		assertEquals(0, actualCoursePref.getPrefNbGroupsTP());
		assertEquals(0, actualCoursePref.getPrefNbGroupsCMTP());
		assertEquals("Macroéconomie : analyse de long terme", actualCourse.getName());
		assertEquals(6, actualCourse.getCountGroupsTD());
		assertEquals(0, actualCourse.getCountGroupsCMTD());
		assertEquals(0, actualCourse.getCountGroupsTP());
		assertEquals(0, actualCourse.getCountGroupsCMTP());
		assertEquals(1, actualCourse.getCountGroupsCM());
		assertEquals(1170, actualCourse.getNbMinutesTD());
		assertEquals(0, actualCourse.getNbMinutesCMTD());
		assertEquals(0, actualCourse.getNbMinutesTP());
		assertEquals(0, actualCourse.getNbMinutesCMTP());
		assertEquals(1170, actualCourse.getNbMinutesCM());
		assertEquals(2016, actualCourse.getStudyYear());
		assertEquals(2, actualCourse.getSemester());
		assertEquals(actualTeacher, acutalTeacherInPref);
	}

	/**
	 * Checks the information read from the Jane Ods Pref file.
	 */
	private void checkInformationOfTeacherPrefsJane(TeacherPrefs teacherPrefs) {
		/** Checking the informations of the teacher: */
		Teacher actualTeacher = teacherPrefs.getTeacher();
		assertEquals("Doe", actualTeacher.getLastName());
		assertEquals("Jane", actualTeacher.getFirstName());
		assertEquals("19 rue Jacques Louvel-Tessier", actualTeacher.getAddress());
		assertEquals("75010", actualTeacher.getPostCode());
		assertEquals("Paris", actualTeacher.getCity());
		assertEquals("123456789", actualTeacher.getPersonalPhone());
		assertEquals("987654321", actualTeacher.getMobilePhone());
		assertEquals("jane.doe@outlook.com", actualTeacher.getPersonalEmail());
		assertEquals("jane.doe@dauphine.eu", actualTeacher.getDauphineEmail());
		assertEquals("MCF", actualTeacher.getStatus());
		assertEquals("1928373645", actualTeacher.getDauphinePhoneNumber());
		assertEquals("B048", actualTeacher.getOffice());

		/** Checking the information of the course in the cell P11 of the sheet DE1: */
		CoursePref actualCoursePref = teacherPrefs.getCoursePref("Macroéconomie : analyse de long terme");
		Course actualCourse = actualCoursePref.getCourse();
		Teacher acutalTeacherInPref = actualCoursePref.getTeacher();
		assertEquals(Preference.UNSPECIFIED, actualCoursePref.getPrefCM());
		assertEquals(Preference.C, actualCoursePref.getPrefTD());
		assertEquals(Preference.UNSPECIFIED, actualCoursePref.getPrefCMTD());
		assertEquals(Preference.UNSPECIFIED, actualCoursePref.getPrefTP());
		assertEquals(Preference.UNSPECIFIED, actualCoursePref.getPrefCMTP());
		assertEquals(0, actualCoursePref.getPrefNbGroupsCM());
		assertEquals(3, actualCoursePref.getPrefNbGroupsTD());
		assertEquals(0, actualCoursePref.getPrefNbGroupsCMTD());
		assertEquals(0, actualCoursePref.getPrefNbGroupsTP());
		assertEquals(0, actualCoursePref.getPrefNbGroupsCMTP());
		assertEquals("Macroéconomie : analyse de long terme", actualCourse.getName());
		assertEquals(6, actualCourse.getCountGroupsTD());
		assertEquals(0, actualCourse.getCountGroupsCMTD());
		assertEquals(0, actualCourse.getCountGroupsTP());
		assertEquals(0, actualCourse.getCountGroupsCMTP());
		assertEquals(1, actualCourse.getCountGroupsCM());
		assertEquals(1170, actualCourse.getNbMinutesTD());
		assertEquals(0, actualCourse.getNbMinutesCMTD());
		assertEquals(0, actualCourse.getNbMinutesTP());
		assertEquals(0, actualCourse.getNbMinutesCMTP());
		assertEquals(1170, actualCourse.getNbMinutesCM());
		assertEquals(2016, actualCourse.getStudyYear());
		assertEquals(2, actualCourse.getSemester());
		assertEquals(actualTeacher, acutalTeacherInPref);
	}
}
