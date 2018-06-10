/**
 * 
 */
package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import org.odftoolkit.simple.table.Table;

/**
 * <b>This class allows to print data by using a SpreadsheetShower object.</b>
 * 
 * @author tuannamdavaux
 * @see io.github.oliviercailloux.y2018.teach_spreadsheets.odf.ODSReader
 */
public class SpreadsheetShower {
	/**
	 * ODSReader object for showing cell values
	 */
	private SpreadsheetReader reader = null;

	public SpreadsheetShower(SpreadsheetReader spreadSheetReader) {
		this.reader = Objects.requireNonNull(spreadSheetReader);
	}

	/**
	 * This method asks the user to enter cell position in standard input then print
	 * its value. And it will ask again until the user types End.
	 */
	@SuppressWarnings("resource")
	public void showCellValue(boolean fileNeeded) {

		if (reader instanceof ODSReader) {
			ODSReader odsReader = (ODSReader) reader;

			try (Scanner sc = new Scanner(System.in)) {
				Table currentSheet = null;
				String position = "";
				String cellValue = "";
				boolean typeEnd = false;
				InputStream file;
				if (fileNeeded) {
					do {
						try {
							System.out.println("Veuillez préciser le fichier d'entrée avec le chemin complet:");
							file = new FileInputStream(sc.nextLine());
						} catch (@SuppressWarnings("unused") FileNotFoundException e) {
							file = null;
						}
					} while (file == null);

					try {
						odsReader.setDocument(file);
					} catch (Exception e) {
						throw new IllegalStateException(e);
					}
				}
				System.out.println(
						"Ce programme peut lire les cellules de la feuille :\nEntrez une position par exemple A1 / Tapez Fin pour terminer la saisie.");
				do {
					System.out.println("Précisez la feuille.");
					currentSheet = odsReader.getTable(sc.nextLine());
				} while (currentSheet == null);
				do {
					System.out.println(cellValue);

					position = sc.nextLine();
					if (position.equals("Fin")) {
						System.out.println("End");
						typeEnd = !typeEnd;
					} else
						cellValue = odsReader.getCellValue(currentSheet.getTableName(), position);
				} while (!typeEnd);
			}
			reader.close();
		}
	}

}
