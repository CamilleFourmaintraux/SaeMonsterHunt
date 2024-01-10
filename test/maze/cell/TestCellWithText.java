package maze.cell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import fr.univlille.info.J2.main.management.cells.Cell;
import fr.univlille.info.J2.main.management.cells.CellWithText;
import fr.univlille.info.J2.main.management.cells.Coordinate;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TestCellWithText {
	CellWithText cwt1 = new CellWithText(1,2,50,Color.RED,10,10, new Text("test"));
	CellWithText cwt2 = new CellWithText(3,4,50,Color.RED,10,10, "test");
	CellWithText cwt3 = new CellWithText(new Coordinate(2,1),50,Color.RED,10,10, "test");
	CellWithText cwt4 = new CellWithText(5,6,50,Color.RED,Color.BLUE,5,10,10, "test");
	CellWithText cwt5 = new CellWithText(new Coordinate(8,7),50,Color.RED,Color.BLUE,5,10,10, "test");

	@Test
	public void test_constructor_cellWithText() {
		assertEquals(new Coordinate(2,1), cwt1.getCoord());
		assertEquals(new Coordinate(4,3), cwt2.getCoord());
		assertEquals(2, cwt1.getRow());
		assertEquals(4, cwt2.getRow());
		assertEquals(1, cwt1.getCol());
		assertEquals(3, cwt2.getCol());
		assertEquals(Color.RED, cwt1.getFill());
		assertEquals(Color.RED, cwt2.getFill());
		assertEquals("test", cwt1.getText().getText());
		assertEquals("test", cwt2.getText().getText());
		assertEquals(cwt1.getText().getText(), cwt2.getText().getText());
	}

	@Test
	public void test_setters_cellWithText_textValue() {
		assertEquals("test", cwt1.getText().getText());
		cwt1.setText("poire");
		assertEquals("poire", cwt1.getText().getText());
		cwt1.setText(new Text("pomme"));
		assertEquals("pomme", cwt1.getText().getText());
	}

	@Test
	public void test_setters_cellWithText_textPosition() {
		double verificationX=(1*50.0+10.0)+(50.0/3);
		double verificationY=(2*50.0+10.0)+(50.0/2);
		assertEquals(Double.valueOf(cwt1.getText().getX()),Double.valueOf(verificationX));
		assertEquals(Double.valueOf(cwt1.getText().getY()),Double.valueOf(verificationY));
		cwt1.setPosTextX(10);
		cwt1.setPosTextY(-10);
		assertEquals(Double.valueOf(cwt1.getText().getX()),Double.valueOf(10));
		assertEquals(Double.valueOf(cwt1.getText().getY()),Double.valueOf(-10));
	}

	@Test
	public void test_equals_cellWithText() {
		assertTrue(cwt1.equals( new CellWithText(1,2,50,Color.RED,10,10, new Text("test"))));
		assertTrue(cwt2.equals( new CellWithText(3,4,50,Color.RED,10,10, "test")));
		assertTrue(cwt1.equals(cwt3));
		assertFalse(cwt1==null);
		assertFalse(cwt1.equals(new Object()));
		assertFalse(cwt2.equals(new Cell(3,4,50,Color.RED,10,10)));
		assertFalse(cwt3.equals(cwt4));
		assertFalse(cwt4.equals(cwt5));
	}
}
