package strategy;

import static org.junit.Assert.assertEquals;

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
	}
}
