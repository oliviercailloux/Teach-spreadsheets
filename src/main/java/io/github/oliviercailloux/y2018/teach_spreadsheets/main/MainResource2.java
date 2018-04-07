package io.github.oliviercailloux.y2018.teach_spreadsheets.main;

import java.io.IOException;

import com.hp.hpl.jena.shared.NotFoundException;

import io.github.oliviercailloux.y2018.teach_spreadsheets.odf.TeachSpreadsheetDocument;

public class MainResource2 {

	public static void main(String[] args)
			throws NotFoundException, ClassCastException, NullPointerException, IOException, Exception {

		String fileName = "Saisie_voeux_dauphine.ods";
		String tableName = "L3_Informatique";
		String cellPosition = "B4";

		TeachSpreadsheetDocument.openODS(fileName, tableName, cellPosition);

	}

}