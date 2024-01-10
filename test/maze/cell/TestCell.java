package maze.cell;

import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.univlille.info.J2.main.management.cells.Cell;
import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.info.J2.main.utils.resources.Theme;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class TestCell {

    @Test
    void testConstructeur() {
        Cell cell = new Cell(1, 2, 10, Color.RED, Color.BLACK, 1, 0, 0);
        //(x,y,zoom,Color fill, color stroke, width stroke, gapX, gapY)

        Assertions.assertEquals(2, cell.getRow());
        Assertions.assertEquals(1, cell.getCol());
        Assertions.assertEquals(Color.RED, cell.getFill());
        Assertions.assertEquals(Color.BLACK, cell.getStroke());
        Assertions.assertEquals(1, cell.getStrokeWidth());
        Assertions.assertEquals(10, cell.getX());
        Assertions.assertEquals(20, cell.getY()); //Calculé (zoom*x+gapX = pos_x, zoom*y+gapY=pos_y)
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

        Assertions.assertEquals(new Coordinate(3, 4), cell.getCoord());
    }

    @Test
    void testGetRow() {
        Cell cell = new Cell(1, 2, 10, Color.RED, Color.BLACK, 1, 0, 0);

        Assertions.assertEquals(2, cell.getRow());
    }

    @Test
    void testGetCol() {
        Cell cell = new Cell(1, 2, 10, Color.RED, Color.BLACK, 1, 0, 0);

        Assertions.assertEquals(1, cell.getCol());
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

    /*@Test RuntimeException : Internal Graphic not initialized yet
    void testSetImage() {
        Cell cell = new Cell(1, 2, 10, Color.RED, Color.BLACK, 1, 0, 0);
        Image image = Theme.loadImage(Theme.THEME_CAVE, "bat.png" );

        cell.setImage(image);

        Assertions.assertEquals(image, cell.getImgv().getImage());
    }*/

    @Test
    void testSetXY() {
        Cell cell = new Cell(1, 2, 10, Color.RED, Color.BLACK, 1, 0, 0);

        cell.setXY(10, 20);

        Assertions.assertEquals(10, cell.getX());
        Assertions.assertEquals(20, cell.getY());
    }
}

