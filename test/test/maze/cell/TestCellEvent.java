package test.maze.cell;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import main.maze.cells.Coordinate;
import main.utils.Utils;
import main.maze.cells.CellEvent;

public class TestCellEvent {
	
	CellEvent ce = new CellEvent(new Coordinate(4,4), 5,CellInfo.EMPTY);
	
	@Test
	public void test_constructor_cellEvent() {
		assertEquals(new Coordinate(4,4), ce.getCoord());
		assertEquals(5, ce.getTurn());
		assertEquals(CellInfo.EMPTY, ce.getState());
	}
}
