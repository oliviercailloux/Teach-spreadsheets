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
import com.microsoft.graph.models.WorkbookRangeFont;
import com.microsoft.graph.models.WorkbookRangeFormat;
import com.microsoft.graph.models.WorkbookRangeMergeParameterSet;
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
		checkNotNull(sheetRequestBuilder);
		this.sheetRequestBuilder = sheetRequestBuilder;
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
	 * @throws WriteException
	 */
	public static OnlineWorksheetWriter loadExistingSheet(String fileId, String workSheetName,
			GraphServiceClient<Request> graphClient) throws WriteException {

		boolean sheetExist = checkExistingSheet(fileId, workSheetName, graphClient);

		if (!sheetExist) {
			throw new IllegalStateException("This worksheet doesn't exist ");
		}

		try {
			graphClient.me().drive().items(fileId).workbook().worksheets(workSheetName).buildRequest().get();
		} catch (ClientException e) {
			throw new WriteException("Loading of online worksheet failed", e);
		}

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
	 * @throws WriteException
	 * 
	 */

	public static OnlineWorksheetWriter loadNewSheet(String fileId, String workSheetName,
			GraphServiceClient<Request> graphClient) throws WriteException {

		boolean sheetExist = checkExistingSheet(fileId, workSheetName, graphClient);

		if (sheetExist) {
			throw new IllegalStateException("A Worksheet with this name already exist ");
		}

		try {
			graphClient.me().drive().items(fileId).workbook().worksheets()
					.add(WorkbookWorksheetAddParameterSet.newBuilder().withName(workSheetName).build()).buildRequest()
					.post();
		} catch (Exception e) {
			throw new WriteException("Creation of new online worksheet failed", e);
		}

		LOGGER.info("Worsheet created succesfully");
		return new OnlineWorksheetWriter(graphClient.me().drive().items(fileId).workbook().worksheets(workSheetName));

	}

	public static boolean checkExistingSheet(String fileId, String workSheetName,
			GraphServiceClient<Request> graphClient) throws WriteException {
		checkNotNull(fileId);
		checkNotNull(workSheetName);
		checkNotNull(graphClient);

		boolean sheetExist = false;

		try {
			List<WorkbookWorksheet> listWorksheetExist = graphClient.me().drive().items(fileId).workbook().worksheets()
					.buildRequest().get().getCurrentPage();

			Iterator<WorkbookWorksheet> it = listWorksheetExist.iterator();

			while (it.hasNext() && !sheetExist) {
				if (it.next().name.equals(workSheetName)) {
					sheetExist = true;
				}
			}
		} catch (ClientException e) {
			throw new WriteException("An online write failure occurred", e);
		}

		return sheetExist;
	}

	@Override
	public void setValueAt(int row, int column, String content) throws WriteException {
		checkArgument(row >= 0, column >= 0);

		String url = sheetRequestBuilder.buildRequest().getRequestUrl().toString();

		WorkbookRange wR = new WorkbookRange();
		wR.values = new JsonPrimitive(content);
		CustomRequest<WorkbookRange> request = new CustomRequest<>(
				url + "/microsoft.graph.cell(row=" + row + ",column=" + column + ")", sheetRequestBuilder.getClient(),
				null, WorkbookRange.class);

		try {
			request.patch(wR);
		} catch (ClientException e) {
			throw new WriteException("An online write failure occurred", e);
		}

	}

	/**
	 * @see <a
	 *      href="https://github.com/microsoftgraph/msgraph-sdk-java/issues/744 ">
	 *      The issue that helped us to implement this function online </a>
	 */
	@Override
	public void setBackgroundColor(int row, int column, String color) throws WriteException {
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

		try {
			request.patch(workbookRangeFill);
		} catch (ClientException e) {
			throw new WriteException("An online write failure occurred", e);
		}

	}

	/**
	 * @see <a href=
	 *      "https://docs.microsoft.com/fr-fr/graph/api/resources/rangeformat?view=graph-rest-1.0">
	 *      The Microsoft doc that helped to implement this function online </a>
	 */
	@Override
	public void setFont(int row, int column, boolean bold, String color, double size, String name)
			throws WriteException {
		checkArgument(row >= 0, column >= 0);
		WorkbookRangeFont workbookRangeFont = new WorkbookRangeFont();
		workbookRangeFont.bold = bold;
		workbookRangeFont.color = color;
		workbookRangeFont.size = size;
		workbookRangeFont.name = name;
		String url = sheetRequestBuilder.range(WorkbookWorksheetRangeParameterSet.newBuilder().withAddress("").build())
				.format().font().buildRequest().getRequestUrl().toString();
		checkState(url.contains("microsoft.graph.range"), "Error with MS Graph Url");
		String urlRequest = url.replace("microsoft.graph.range",
				"microsoft.graph.cell(row=" + row + ",column=" + column + ")");

		CustomRequest<WorkbookRangeFont> request = new CustomRequest<>(urlRequest, sheetRequestBuilder.getClient(),
				null, WorkbookRangeFont.class);
		request.patch(workbookRangeFont);
		try {
			request.patch(workbookRangeFont);
		} catch (ClientException e) {
			throw new WriteException("An online write failure occurred", e);
		}

	}

	@Override
	public void setFormat(int row, int column, double columnWidth, String alignmentHorizontal, String alignmentVertical)
			throws WriteException {
		checkArgument(row >= 0, column >= 0);
		WorkbookRangeFormat workbookRangeFormat = new WorkbookRangeFormat();
		workbookRangeFormat.columnWidth = columnWidth;
		workbookRangeFormat.horizontalAlignment = alignmentHorizontal;
		workbookRangeFormat.verticalAlignment = alignmentVertical;
		String url = sheetRequestBuilder.range(WorkbookWorksheetRangeParameterSet.newBuilder().withAddress("").build())
				.format().buildRequest().getRequestUrl().toString();
		checkState(url.contains("microsoft.graph.range"), "Error with MS Graph Url");
		String urlRequest = url.replace("microsoft.graph.range",
				"microsoft.graph.cell(row=" + row + ",column=" + column + ")");

		CustomRequest<WorkbookRangeFormat> request = new CustomRequest<>(urlRequest, sheetRequestBuilder.getClient(),
				null, WorkbookRangeFormat.class);

		try {
			request.patch(workbookRangeFormat);
		} catch (ClientException e) {
			throw new WriteException("An online write failure occurred", e);
		}

	}

	@Override
	public void cellFusion(int firstRow, int firstColumn, int secondRow, int secondColumn) throws WriteException {
		checkArgument(firstRow >= 0, firstColumn >= 0);

		WorkbookRangeMergeParameterSet wr = new WorkbookRangeMergeParameterSet();
		wr.across = false;

		String url = sheetRequestBuilder.range(WorkbookWorksheetRangeParameterSet.newBuilder().withAddress("").build())
				.buildRequest().getRequestUrl().toString();

		checkState(url.contains("microsoft.graph.range"), "Error with MS Graph Url");
		String urlRequest = url.replace("microsoft.graph.range",
				"microsoft.graph.range(address='" + coordinateToCell(firstRow, firstColumn) + ":"
						+ coordinateToCell(secondRow, secondColumn) + "')/merge");

		CustomRequest<WorkbookRangeMergeParameterSet> request = new CustomRequest<>(urlRequest,
				sheetRequestBuilder.getClient(), null, WorkbookRangeMergeParameterSet.class);

		try {
			request.post(wr);
		} catch (ClientException e) {
			throw new WriteException("An online write failure occurred", e);
		}

	}

	/**
	 * convert a numerical number to an Excel column name
	 * 
	 * The resulting values should be in the form of excel column names, e.g. A, AA,
	 * AAA etc.
	 * 
	 * @param row    The numerical number of the row of cell
	 * @param colomn The numerical number of the column of cell
	 * 
	 */
	public static String coordinateToCell(int row, int column) {
		int dividend = column + 1;
		String columnName = "";
		int modulo;

		while (dividend > 0) {
			modulo = (dividend - 1) % 26;

			columnName = Character.toString((char) (65 + modulo)) + columnName;
			dividend = ((dividend - modulo) / 26);
		}

		return columnName + (row + 1);
	}
}
