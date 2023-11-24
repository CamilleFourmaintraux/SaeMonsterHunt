package maze.cell;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.univlille.info.J2.main.management.cells.CellEvent;
import fr.univlille.info.J2.main.management.cells.Coordinate;
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
