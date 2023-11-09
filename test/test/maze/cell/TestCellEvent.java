package test.maze.cell;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.univlille.info.J2.main.maze.cells.CellEvent;
import fr.univlille.info.J2.main.maze.cells.Coordinate;
import fr.univlille.info.J2.main.utils.Utils;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;

public class TestCellEvent {
	
	CellEvent ce = new CellEvent(new Coordinate(4,4), 5,CellInfo.EMPTY);
	
	@Test
	public void test_constructor_cellEvent() {
		assertEquals(new Coordinate(4,4), ce.getCoord());
		assertEquals(5, ce.getTurn());
		assertEquals(CellInfo.EMPTY, ce.getState());
	}
}
