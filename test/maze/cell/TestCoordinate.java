package maze.cell;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import fr.univlille.info.J2.main.management.cells.Coordinate;

public class TestCoordinate {
Coordinate c = new Coordinate(1,3);

	@Test
	public void test_constructor_Cell() {
		assertEquals(new Coordinate(1,3), c);
	}

	@Test
	public void testgetters_Cell() {
		assertEquals(1, c.getRow());
		assertEquals(3, c.getCol());
	}

	@Test
	public void test_setters_Cell() {
		assertEquals(new Coordinate(1,3), c);
		c.setCoordinate(8,8);
		assertEquals(new Coordinate(8,8), c);
		c.setCol(4);
		assertEquals(new Coordinate(8,4), c);
		c.setRow(4);
		assertEquals(new Coordinate(4,4), c);
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void test_equals_Cell() {
		assertFalse(c==null);
		assertFalse(c.equals(Integer.valueOf(5)));
		assertFalse(c.equals(new Coordinate()));
		assertFalse(c.equals(new Coordinate(7,9)));
		assertFalse(c.equals(new Coordinate(1,9)));
		assertFalse(c.equals(new Coordinate(7,3)));
		assertTrue(c.equals(new Coordinate(1,3)));
	}
}
