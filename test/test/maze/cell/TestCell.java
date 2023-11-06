package test.maze.cell;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import main.maze.cells.Coordinate;
import main.maze.cells.Cell;
import javafx.scene.paint.Color;

public class TestCell {
	
	
	Cell cell= new Cell(1,2,50,Color.RED,10,10);
	
	@Test
	public void test_constructor_cell() {
		assertEquals(new Coordinate(2,1), cell.getCoord());
		assertEquals(2, cell.getRow());
		assertEquals(1, cell.getCol());
		assertEquals(Color.RED, cell.getFill());
	}
	
	@Test
	public void test_setters_cell() {
		cell.setCoord(new Coordinate(8,8));
		assertEquals(new Coordinate(8,8), cell.getCoord());
	}
	
	@Test
	public void test_setters2_cell() {
		assertEquals(new Coordinate(2,1), cell.getCoord());
		cell.setCoord(new Coordinate(-1,-1));
		assertEquals(new Coordinate(-1,-1), cell.getCoord());
	}
	
	@Test
	public void test_equals_cell() {
		assertTrue(cell.equals(cell));
		assertFalse(cell.equals(null));
		assertFalse(cell.equals(new Cell(5,2,50,Color.RED,10,10)));
		assertTrue(cell.equals(new Cell(1,2,50,Color.RED,10,10)));
	}
	

}
