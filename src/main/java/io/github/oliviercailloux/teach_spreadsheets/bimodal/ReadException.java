package io.github.oliviercailloux.teach_spreadsheets.bimodal;

public class ReadException extends Exception {
	private static final long serialVersionUID = 6254301468790470484L;

	/**
	 * Constructs a {@code ReadException} with the specified detail message.
	 *
	 * @param message the detail message.
	 */
	public ReadException(String message, Throwable cause) {
		super(message, cause);
	}
}
