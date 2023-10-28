package test.strategy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.strategy.monster.Exit;
import main.maze.cells.Coordinate;

public class TestExit {
	
	@Test
	public void test_Exit() {
		Exit exit = new Exit(new Coordinate(4,5));
		assertEquals(new Coordinate(4,5), exit.getCoord());
		assertEquals(4, exit.getRow());
		assertEquals(5, exit.getCol());
		Exit exit2 = new Exit(new Coordinate(-42,-24));
		assertEquals(new Coordinate(-42,-24), exit2.getCoord());
		assertEquals(-42, exit2.getRow());
		assertEquals(-24, exit2.getCol());
	}
}
