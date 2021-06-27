package io.github.oliviercailloux.teach_spreadsheets.write;

public class Cell {

	private int row;
	private int column;
	private String cellstr;

	public Cell(String cellstr) {
		this.cellstr = cellstr;
		strToInts(cellstr);
	}

	public Cell(int row, int column) {
		this(intsToStr(row,column));
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

	public static String intsToStr(int row, int column) {
		int dividend = column+1;
	    String columnName = "";
	    int modulo;
	    while (dividend > 0)
	    {
	        modulo = (dividend-1 ) % 26;

	        columnName = Character.toString((char)(65 + modulo)) + columnName;
	        dividend = ((dividend - modulo) / 26);
	    } 
	    return columnName+ (row+1);
	}

	public void strToInts(String cellString) {
		cellString.toUpperCase();
		String columnstr = "";
		String rowstr = "";
		for (int i=0; i<cellString.length(); i++) {
			char c = cellString.charAt(i);
			if(c >= 'A' && c <= 'Z') columnstr = columnstr + c;
			else if(c >= '0' && c <= '9') rowstr = rowstr + c;
			else throw new IllegalArgumentException(cellString);
		}
		int adder = 1;
		int valColumn = 0;
		for(int j=columnstr.length()-1; j>=0; j--) {
			char c = columnstr.charAt(j);
			int valC = c - 'A' + 1;
			valColumn += (valC * adder);
			adder *= 26;
		}
		this.row = Integer.parseInt(rowstr)-1;
		this.column = valColumn-1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cellstr == null) ? 0 : cellstr.hashCode());
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (cellstr == null) {
			if (other.cellstr != null)
				return false;
		} else if (!cellstr.equals(other.cellstr))
			return false;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
}