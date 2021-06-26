package io.github.oliviercailloux.teach_spreadsheets.online.read;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.models.DriveSearchParameterSet;
import com.microsoft.graph.models.WorkbookWorksheetCellParameterSet;
import com.microsoft.graph.requests.DriveSearchCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;

import io.github.oliviercailloux.teach_spreadsheets.bimodal.WriteException;

public class WorksheetReader {

	/**
	 * Get the id of a workbook
	 * 
	 * @param fileName
	 * @param graphClient
	 * @return FileId
	 * @throws WriteException
	 */
	public static String getFileId(String fileName, GraphServiceClient graphClient) throws WriteException {
		checkNotNull(fileName);
		checkNotNull(graphClient);
		String fileId = "";
		try {
			DriveSearchCollectionPage search = graphClient.me().drive()
					.search(DriveSearchParameterSet.newBuilder().withQ(fileName).build()).buildRequest().get();
			Gson gson = new Gson();
			String item = gson.toJson(search);
			JsonObject itemJsonObect = (JsonObject) JsonParser.parseString(item);
			JsonArray content = (JsonArray) (itemJsonObect.get("pageContents"));
			checkState(content.size() == 1, "size of page contents is not equal to 1");
			fileId = content.get(0).getAsJsonObject().get("id").getAsString();
		} catch (ClientException e) {
			throw new WriteException(e.getMessage());
		}

		return fileId;

	}

	/**
	 * Read the value of worksheet cell, identified by his row and column
	 * 
	 * @param fileId
	 * @param worksheetName
	 * @param graphClient
	 * @param row           - the row of the cell which we want to read - the range
	 *                      of the value of row and column are >= 0
	 * @param column        - the column of the cell which we want to read - In
	 *                      excel the column's name are letter, here the letter is
	 *                      is translated by its alphabetical rank starting with 0
	 *                      (ex : A -> 0, B->1 ... ZA-> 26)
	 * @return - the value of the cell
	 * @throws WriteException
	 */
	public static String getCellValue(String fileId, String worksheetName, GraphServiceClient graphClient, int row,
			int column) throws WriteException {
		checkArgument(row >= 0, column >= 0);
		checkNotNull(fileId);
		checkNotNull(worksheetName);
		checkNotNull(graphClient);
		String cellValue = "";
		try {

			JsonElement contentCell = graphClient.me().drive().items(fileId).workbook().worksheets(worksheetName)
					.cell(WorkbookWorksheetCellParameterSet.newBuilder().withRow(row).withColumn(column).build())
					.buildRequest().get().values;
			cellValue = contentCell.getAsString();
		} catch (ClientException e) {
			throw new WriteException(e.getMessage());
		}

		return cellValue;
	}

}
