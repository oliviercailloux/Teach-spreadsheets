package io.github.oliviercailloux.y2018.teach_spreadsheets.main;

import java.io.IOException;

import io.github.oliviercailloux.y2018.teach_spreadsheets.odf.TeachSpreadsheetDocument;

public class MainResource2 {

	public static void main(String[] args) {
		String fileName = "Saisie_voeux_dauphine.ods";
		String tableName = "L3_Informatique";
		String cellPosition = "B4";

		try {
			TeachSpreadsheetDocument.openODS(fileName, tableName, cellPosition);
		} catch (NullPointerException e) {
			throw new IllegalStateException(e);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}

	}

}