package management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.info.J2.main.management.exit.Exit;

public class TestExit {

	@Test
	public void test_Exit() {
		Exit exit = new Exit(new Coordinate(4,5));
		assertEquals(new Coordinate(4,5), exit.getCoord());
		assertEquals(4, exit.getRow());
		assertEquals(5, exit.getCol());
		Exit exit2 = new Exit(new Coordinate(-42,-24));
		assertEquals(new Coordinate(-42,-24), exit2.getCoord());
		assertEquals(0, exit2.getRow());
		assertEquals(0, exit2.getCol());
		assertFalse(exit.equals(exit2));
		assertTrue(exit.equals(exit));
		assertFalse(exit.equals(null));
		assertFalse(exit.equals("exit"));
		assertNotEquals(exit.getData(),exit2.getData());
		Exit exit3 = new Exit(new Coordinate(4,50));
		Exit exit4 = new Exit(new Coordinate(40,5));
		assertFalse(exit.equals(exit2));
		assertFalse(exit.equals(exit3));
		assertFalse(exit.equals(exit4));
	}
	
	@Test
	public void test_ExitData() {
		Exit exit = new Exit(new Coordinate(4,5));
		Exit exit2 = new Exit(new Coordinate(-42,-24));
		assertNotEquals(exit.getData(),exit2.getData());
		assertTrue(exit.getData().equals(exit.getData()));
		assertFalse(exit.getData().equals(null));
		assertFalse(exit.getData().equals("dataExit"));
	}
}