package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class TeachSpreadsheetDocumentTest {

	@SuppressWarnings("unused")
	@Test
	public void testOpenODS() {
		String fileName = "Saisie_voeux_dauphine.ods";
		String tableName = "L3_Informatique";
		String cellPosition = "B4";

		try {
			TeachSpreadsheetDocument.openODS(fileName, tableName, cellPosition);
			Assert.assertTrue(true);
		} catch (NullPointerException e) {
			Assert.assertTrue(false);
		} catch (IOException e) {
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}

	}

}
