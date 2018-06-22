package io.github.oliviercailloux.y2018.teach_spreadsheets.courses;

/**
 * Choices possible for a course preference
 *
 */
public enum Choice {

	A("A"), B("B"), C("C"), ABSENT("Absent"), NA("X");

	private final String fullChoiceName;

	private Choice(String fullChoiceName) {
		this.fullChoiceName = fullChoiceName;
	}

	/**
	 * retrieves the full choice name
	 **/
	@Override
	public String toString() {
		return fullChoiceName;
	}

}
