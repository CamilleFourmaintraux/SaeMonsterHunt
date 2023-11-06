package test.maze.cell;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.maze.cells.Coordinate;
import main.maze.cells.CellEvent;

public class TestCellEvent {
	
	CellEvent ce = new CellEvent(new Coordinate(4,4), 5);
	
	@Test
	public void test_constructor_cellEvent() {
		assertEquals(new Coordinate(4,4), ce.getCoord());
		assertEquals(5, ce.getTurn());
		assertEquals(null, ce.getState());
	}
}
