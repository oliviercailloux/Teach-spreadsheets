package io.github.oliviercailloux.teach_spreadsheets.write;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CellTests {

	@Test
	void testCell() throws Exception {
		Cell cell1 = new Cell("AAA111");
		Cell cell2 = new Cell(101,27);
		assertEquals(cell1.getColumn(),702);
		assertEquals(cell1.getRow(),110);
		assertEquals(cell2.getCell(),"AB102");
		assertThrows(IllegalArgumentException.class, () -> new Cell("AD:/12"));
	}

}