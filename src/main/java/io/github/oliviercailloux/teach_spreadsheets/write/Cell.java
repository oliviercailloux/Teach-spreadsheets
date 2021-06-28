package io.github.oliviercailloux.teach_spreadsheets.write;

import java.util.Objects;

public class Cell {

	private int row;
	private int column;
	private String cellstr;

	public Cell(String cellstr) {
		this.cellstr = cellstr;
		strToInts(cellstr);
	}

	public Cell(int row, int column) {
		this(intsToStr(row, column));
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public String getCell() {
		return cellstr;
	}

	private static String intsToStr(int row, int column) {
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

	private void strToInts(String cellString) {
		cellString.toUpperCase();
		String columnstr = "";
		String rowstr = "";
		for (int i = 0; i < cellString.length(); i++) {
			char c = cellString.charAt(i);
			if (c >= 'A' && c <= 'Z')
				columnstr = columnstr + c;
			else if (c >= '0' && c <= '9')
				rowstr = rowstr + c;
			else
				throw new IllegalArgumentException(cellString);
		}
		int adder = 1;
		int valColumn = 0;
		for (int j = columnstr.length() - 1; j >= 0; j--) {
			char c = columnstr.charAt(j);
			int valC = c - 'A' + 1;
			valColumn += (valC * adder);
			adder *= 26;
		}
		this.row = Integer.parseInt(rowstr) - 1;
		this.column = valColumn - 1;
	}

	@Override
	public int hashCode() {

		return Objects.hash(this.row, this.column, this.cellstr);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Cell)) {
			return false;
		}
		Cell other = (Cell) obj;

		if (!cellstr.equals(other.cellstr))
			return false;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
}