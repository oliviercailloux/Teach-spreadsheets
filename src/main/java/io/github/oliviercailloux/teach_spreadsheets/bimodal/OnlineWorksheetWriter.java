package io.github.oliviercailloux.teach_spreadsheets.bimodal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonPrimitive;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.http.CustomRequest;
import com.microsoft.graph.models.WorkbookRange;
import com.microsoft.graph.models.WorkbookRangeFill;
import com.microsoft.graph.models.WorkbookWorksheet;
import com.microsoft.graph.models.WorkbookWorksheetAddParameterSet;
import com.microsoft.graph.models.WorkbookWorksheetRangeParameterSet;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.WorkbookWorksheetRequestBuilder;

import okhttp3.Request;

public class OnlineWorksheetWriter implements WorksheetWriter {
	private WorkbookWorksheetRequestBuilder sheetRequestBuilder;
	private static final Logger LOGGER = LoggerFactory.getLogger(OnlineWorksheetWriter.class);

	private OnlineWorksheetWriter(WorkbookWorksheetRequestBuilder sheetRequestBuilder) {
		this.sheetRequestBuilder = sheetRequestBuilder;
	}

	@Override
	public void setValueAt(int row, int column, String content) throws WriteException {
		checkArgument(row >= 0, column >= 0);
		checkNotNull(sheetRequestBuilder);

		String url = sheetRequestBuilder.buildRequest().getRequestUrl().toString();

		WorkbookRange wR = new WorkbookRange();
		wR.values = new JsonPrimitive(content);
		CustomRequest<WorkbookRange> request = new CustomRequest<>(
				url + "/microsoft.graph.cell(row=" + row + ",column=" + column + ")", sheetRequestBuilder.getClient(),
				null, WorkbookRange.class);

		try {
			request.patch(wR);
		} catch (ClientException e) {
			throw new WriteException("An online write failure occurred");
		}

	}

	@Override
	public void setBackgroundColor(int row, int column, String color) {
		checkArgument(row >= 0, column >= 0);

		WorkbookRangeFill workbookRangeFill = new WorkbookRangeFill();
		workbookRangeFill.color = color;

		String url = sheetRequestBuilder.range(WorkbookWorksheetRangeParameterSet.newBuilder().withAddress("").build())
				.format().fill().buildRequest().getRequestUrl().toString();

		checkState(url.contains("microsoft.graph.range"), "Error with MS Graph Url");

		String urlRequest = url.replace("microsoft.graph.range",
				"microsoft.graph.cell(row=" + row + ",column=" + column + ")");

		CustomRequest<WorkbookRangeFill> request = new CustomRequest<>(urlRequest, sheetRequestBuilder.getClient(),
				null, WorkbookRangeFill.class);
		request.patch(workbookRangeFill);

	}

	public static boolean checkExistingSheet(String fileId, String workSheetName,
			GraphServiceClient<Request> graphClient) {
		checkNotNull(fileId);
		checkNotNull(workSheetName);
		checkNotNull(graphClient);

		boolean sheetExist = false;

		List<WorkbookWorksheet> listWorksheetExist = graphClient.me().drive().items(fileId).workbook().worksheets()
				.buildRequest().get().getCurrentPage();
		Iterator<WorkbookWorksheet> it = listWorksheetExist.iterator();

		while (it.hasNext() && !sheetExist) {
			if (it.next().name.equals(workSheetName)) {
				sheetExist = true;
			}
		}

		return sheetExist;
	}

	/**
	 * This method load an existing worksheet in the workbook identified by his
	 * fileId. The worksheet is stored in the variable sheetRequestBuilder.
	 * 
	 * @param fileId        - The fileId identifies the workbook where we want to
	 *                      create the worksheet
	 * @param worksheetName - The name of the worksheet we want to create
	 * @param graphClient   - The Microsoft's GraphServiceClient will allow to send
	 *                      the request to the Microsoft Graph API
	 */
	public static OnlineWorksheetWriter loadExistingSheet(String fileId, String workSheetName,
			GraphServiceClient<Request> graphClient) {

		boolean sheetExist = checkExistingSheet(fileId, workSheetName, graphClient);

		if (!sheetExist) {
			throw new IllegalStateException("This worksheet doesn't exist ");
		}

		graphClient.me().drive().items(fileId).workbook().worksheets(workSheetName).buildRequest().get();
		return new OnlineWorksheetWriter(graphClient.me().drive().items(fileId).workbook().worksheets(workSheetName));
	}

	/**
	 * This method creates an empty Worksheet in the workbook identified by his
	 * fileId and load it. The worksheet is stored in the variable
	 * sheetRequestBuilder.
	 * 
	 * @param fileId        - The fileId identifies the workbook where we want to
	 *                      create the worksheet
	 * @param worksheetName - The name of the worksheet we want to create
	 * @param graphClient   - The Microsoft's GraphServiceClient will allow to send
	 *                      the request to the Microsoft Graph API
	 * 
	 */

	public static OnlineWorksheetWriter loadNewSheet(String fileId, String workSheetName,
			GraphServiceClient<Request> graphClient) {

		boolean sheetExist = checkExistingSheet(fileId, workSheetName, graphClient);

		if (sheetExist) {
			throw new IllegalStateException("A Worksheet with this name already exist ");
		}

		WorkbookWorksheet wB = graphClient.me().drive().items(fileId).workbook().worksheets()
				.add(WorkbookWorksheetAddParameterSet.newBuilder().withName(workSheetName).build()).buildRequest()
				.post();
		if (wB == null) {
			throw new IllegalStateException("Creation of new worksheet failed");
		}

		LOGGER.info("Worsheet created succesfully");
		return new OnlineWorksheetWriter(graphClient.me().drive().items(fileId).workbook().worksheets(workSheetName));

	}

	@Override
	public void setFont(int row, int column, Boolean bold, String color, Double size, String name)
			throws WriteException {
		TODO();

	}

	@Override
	public void setFormat(int row, int column, double columnWidth, String alignmentHorizontal, String alignmentVertical)
			throws WriteException {
		TODO();

	}

	@Override
	public void cellFusion(int firstRow, int firstColumn, int secondRow, int secondColumn) throws WriteException {
		TODO();

	}
}
