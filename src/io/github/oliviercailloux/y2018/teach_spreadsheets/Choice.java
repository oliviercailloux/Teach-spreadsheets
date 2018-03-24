package io.github.oliviercailloux.y2018.teach_spreadsheets;

public enum Choice {
	
	A("A"),
	B("B"),
	C("C"),
	ABSENT("Absent");
	
	private final String fullChoiceName;
	
	private Choice(String fullChoiceName) {
		this.fullChoiceName = fullChoiceName;
	}
	
	/**
	 * retrieves the full choice name
	 **/
	public String toString() {
		return fullChoiceName;
	}

}
