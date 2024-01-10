package maze.cell;

import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.univlille.info.J2.main.management.cells.Cell;
import fr.univlille.info.J2.main.management.cells.Coordinate;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class TestCell {

    @Test
    void testConstructeur() {
        Cell cell = new Cell(1, 2, 10, Color.RED, Color.BLACK, 1, 0, 0);

        Assertions.assertEquals(1, cell.getRow());
        Assertions.assertEquals(2, cell.getCol());
        Assertions.assertEquals(Color.RED, cell.getFill());
        Assertions.assertEquals(Color.BLACK, cell.getStroke());
        Assertions.assertEquals(1, cell.getStrokeWidth());
        Assertions.assertEquals(0, cell.getX());
        Assertions.assertEquals(0, cell.getY());
    }

    @Test
    void testGetCoord() {
        Cell cell = new Cell(1, 2, 10, Color.RED, Color.BLACK, 1, 0, 0);

        Assertions.assertEquals(new Coordinate(2, 1), cell.getCoord());
    }

    @Test
    void testSetCoord() {
        Cell cell = new Cell(1, 2, 10, Color.RED, Color.BLACK, 1, 0, 0);

        cell.setCoord(new Coordinate(3, 4));

        Assertions.assertEquals(new Coordinate(4, 3), cell.getCoord());
    }

    @Test
    void testGetRow() {
        Cell cell = new Cell(1, 2, 10, Color.RED, Color.BLACK, 1, 0, 0);

        Assertions.assertEquals(1, cell.getRow());
    }

    @Test
    void testGetCol() {
        Cell cell = new Cell(1, 2, 10, Color.RED, Color.BLACK, 1, 0, 0);

        Assertions.assertEquals(2, cell.getCol());
    }

    @Test
    void testEquals() {
        Cell cell1 = new Cell(1, 2, 10, Color.RED, Color.BLACK, 1, 0, 0);
        Cell cell2 = new Cell(1, 2, 10, Color.RED, Color.BLACK, 1, 0, 0);
        Cell cell3 = new Cell(2, 1, 10, Color.RED, Color.BLACK, 1, 0, 0);

        Assertions.assertTrue(cell1.equals(cell2));
        Assertions.assertFalse(cell1.equals(cell3));
    }

  
    @Test
    void testGetImgv() {
        Cell cell = new Cell(1, 2, 10, Color.RED, Color.BLACK, 1, 0, 0);

        Assertions.assertNotNull(cell.getImgv());
    }

    @Test
    void testSetImage() {
        Cell cell = new Cell(1, 2, 10, Color.RED, Color.BLACK, 1, 0, 0);
        Image image = new Image("/res/img/cave/bat.png");

        cell.setImage(image);

        Assertions.assertEquals(image, cell.getImgv().getImage());
    }

    @Test
    void testSetXY() {
        Cell cell = new Cell(1, 2, 10, Color.RED, Color.BLACK, 1, 0, 0);

        cell.setXY(10, 20);

        Assertions.assertEquals(10, cell.getX());
        Assertions.assertEquals(20, cell.getY());
    }
}

