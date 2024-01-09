package strategy;

import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.info.J2.main.strategy.hunter.GameplayHunterData;
import fr.univlille.info.J2.main.strategy.hunter.Hunter;
import fr.univlille.info.J2.main.strategy.hunter.IAeasyHunter;
import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestHunter {

    private Hunter hunter;

    @BeforeEach
    void setUp() {
    	GameplayHunterData gameplay = new GameplayHunterData("Joueur 1", "IA-Easy", 2);
        hunter = new Hunter(10, 10, new Coordinate(0, 0), gameplay);
    }

    @Test
    void testConstructeur() {
        assertEquals(0, hunter.getRow());
        assertEquals(0, hunter.getCol());
        assertEquals("IA-Easy", hunter.getIA());
        assertEquals(10, hunter.getTraces().length);
        assertEquals(10, hunter.getTraces()[0].length);
        for (int i = 0; i < hunter.getTraces().length; i++) {
            for (int j = 0; j < hunter.getTraces()[i].length; j++) {
                assertEquals(-2, hunter.getTraces()[i][j]);
            }
        }
    }

    @Test
    void testGetRow() {
        assertEquals(0, hunter.getRow());
        hunter.setCoord(new Coordinate(1, 1));
        assertEquals(1, hunter.getRow());
    }

    @Test
    void testGetCol() {
        assertEquals(0, hunter.getCol());
        hunter.setCoord(new Coordinate(1, 1));
        assertEquals(1, hunter.getCol());
    }

    @Test
    void testGetCoord() {
        assertEquals(new Coordinate(0, 0), hunter.getCoord());
        hunter.setCoord(new Coordinate(1, 1));
        assertEquals(new Coordinate(1, 1), hunter.getCoord());
    }

    @Test
    void testSetCoord() {
        hunter.setCoord(new Coordinate(1, 1));
        assertEquals(1, hunter.getRow());
        assertEquals(1, hunter.getCol());
    }

    @Test
    void testInitTraces() {
        hunter.initTraces(5, 5);
        for (int i = 0; i < hunter.getTraces().length; i++) {
            for (int j = 0; j < hunter.getTraces()[i].length; j++) {
                assertEquals(-2, hunter.getTraces()[i][j]);
            }
        }
    }

    @Test
    void testSetTrace() {
        hunter.setTrace(new Coordinate(0, 0), 1);
        assertEquals(1, hunter.getTrace(new Coordinate(0, 0)));
    }

    @Test
    void testGetTrace() {
        assertEquals(-2, hunter.getTrace(new Coordinate(0, 0)));
        hunter.setTrace(new Coordinate(0, 0), 1);
        assertEquals(1, hunter.getTrace(new Coordinate(0, 0)));
    }

    @Test
    void testGetTraces() {
        assertEquals(10, hunter.getTraces().length);
        assertEquals(10, hunter.getTraces()[0].length);
        for (int i = 0; i < hunter.getTraces().length; i++) {
            for (int j = 0; j < hunter.getTraces()[i].length; j++) {
                assertEquals(-2, hunter.getTraces()[i][j]);
            }
        }
    }
    
    

    @Test
    void testSetStrategy() {
        IHunterStrategy strategy = new IAeasyHunter();
        hunter.setStrategy(strategy);
        assertSame(strategy, hunter.strategy);
    }
}

  
