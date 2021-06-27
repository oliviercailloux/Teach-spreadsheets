package io.github.oliviercailloux.teach_spreadsheets.bimodal;

public class WriteException extends Exception {
	private static final long serialVersionUID = 6254301468790470484L;

	/**
	 * Constructs a {@code WriteException} with the specified detail message.
	 *
	 * @param message the detail message.
	 */
	public WriteException(String message, Throwable cause) {
		super(message, cause);
	}
}
