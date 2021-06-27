package io.github.oliviercailloux.teach_spreadsheets.online.authentication;

import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.requests.GraphServiceClient;
import io.github.oliviercailloux.teach_spreadsheets.base.AggregatedPrefs;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.TeacherPrefs;
import io.github.oliviercailloux.teach_spreadsheets.json.JsonSerializer;
import io.github.oliviercailloux.teach_spreadsheets.online.read.WorksheetReader;
import io.github.oliviercailloux.teach_spreadsheets.online.write.XlsSummarizer;
import io.github.oliviercailloux.teach_spreadsheets.read.MultipleOdsPrefReader;
import io.github.oliviercailloux.teach_spreadsheets.write.OdsSummarizer;
import io.github.oliviercailloux.teach_spreadsheets.gui.Controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;
import okhttp3.Request;
import org.junit.jupiter.api.Test;
import org.odftoolkit.simple.SpreadsheetDocument;

public class XlsSummarizerTest {

	static private Path outputFolderPath = Path.of("output");
	static private Path inputFolderPath = Path.of("input");
	static private Path odsPrefSummaryDocumentPath = outputFolderPath.resolve(Path.of("odsPrefSummary.ods"));
	static private Path coursesJsonPath = outputFolderPath.resolve("courses.json");
	
	@Test
	void test() throws IOException, Exception {

		
		/**
		 * Reading part.
		 */
		AggregatedPrefs aggregatedPrefs = MultipleOdsPrefReader.readFilesFromFolder(inputFolderPath);
		/**
		 * Writing the Ods summary and the Json courses part.
		 */
		Set<Course> courses = aggregatedPrefs.getCourses();
		Set<CoursePref> CoursePrefs = new LinkedHashSet<>();
		
		for (Course course : courses) {
			CoursePrefs.addAll(aggregatedPrefs.getCoursePrefs(course));
		}

		OdsSummarizer odsPrefSummary = OdsSummarizer.newInstance(courses);
		odsPrefSummary.addPrefs(CoursePrefs);

		

		try (SpreadsheetDocument odsPrefSummaryDocument = odsPrefSummary.createSummary()) {
			if (!Files.exists(outputFolderPath)) {
				Files.createDirectory(outputFolderPath);
			}
			odsPrefSummaryDocument.save(odsPrefSummaryDocumentPath.toString());
		}

		String coursesJson = JsonSerializer.serializeSet(courses);
		Files.writeString(coursesJsonPath, coursesJson);

		Set<TeacherPrefs> teacherPrefs = aggregatedPrefs.getTeacherPrefsSet();
		Controller.initializeAndLaunchGui(teacherPrefs, courses, CoursePrefs, outputFolderPath);
		
		IAuthenticationProvider token = Authenticator.getAuthenticationProvider();

		GraphServiceClient<Request> graphClient = GraphServiceClient.builder().authenticationProvider(token)
				.buildClient();

		// Get the file Id of Book1.xlsx
		String fileId = WorksheetReader.getFileId("Book1.xlsx", graphClient);
		
		XlsSummarizer xlsSum = XlsSummarizer.newInstance(courses, CoursePrefs);

		xlsSum.createSummary(fileId, "Sheet3", graphClient);

	}

}
