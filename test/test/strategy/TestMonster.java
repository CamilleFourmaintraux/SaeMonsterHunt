package test.strategy;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.maze.cells.Coordinate;
import main.strategy.monster.Monster;

public class TestMonster {

	
	//pas de test pour les m�thodes play & update car pas encore impl�ment�.
	
	
	/*@Test
	public void test_constructor_monster() {
		// Instanciation d'un monstre.
		boolean[][] walls = {{true, false, true}, {false, true, true}, {false, false, true}};
		ICoordinate coord = new Coordinate(1, 1);
		ICoordinate coord_exit = new Coordinate(2, 2);
		ICoordinate coord_hunted = new Coordinate(0, 0);
		Monster monster = new Monster(walls, coord, coord_exit, coord_hunted);

		// Test de la bonne instanciation du monstre.
		assertTrue(monster.isMonsterTurn());
		assertArrayEquals(walls, monster.getWalls());
		assertEquals(coord, monster.getCoord());
		assertEquals(coord_exit, monster.getCoord_exit());
		assertEquals(coord_hunted, monster.getCoord_hunted());
	}*/

	/*@Test
	public void test_initialize_walls() {
		Monster monster = new Monster(null, null, null, null);

		// Cr�ation un tableau de murs de test
		boolean[][] testWalls = {{true, false, true}, {false, true, true}, {false, false, true}};
		// Appel � la m�thode initialize avec le tableau de murs de test
		monster.initialize(testWalls);
		// Test que la propri�t� walls de l'objet Monster a �t� correctement initialis�e
		assertArrayEquals(testWalls, monster.getWalls());
	}*/

	/*@Test
	public void test_move_action() {
		// Instanciation d'un monsre
		Monster monster = new Monster(null, null, null, null);
		// Cr�ation un objet ICoordinate de test
		ICoordinate newCoord = new Coordinate(2, 2);
		// Appel � la m�thode move avec la nouvelle coordonn�e de test
		monster.move(newCoord);
		// Test que la coordonn�e de l'objet Monster a �t� mise � jour correctement
		assertEquals(newCoord, monster.getCoord());
		// Test que la propri�t� monsterTurn a �t� correctement mise � false
		assertFalse(monster.isMonsterTurn());
	}*/

	/*@Test
    public void test_actualizeShot_action() {
        // Instanciation d'un Monstre.
        Monster monster = new Monster(null, null, null, null);
        // Coordonn�es du dernier tir du chasseur(newCoord).
        ICoordinate newCoord = new Coordinate(2, 2);
        // Appel � la m�thode actualizeShot avec les nouvelles coordonn�es.
        monster.actualizeShot(newCoord);
        // Test si les coordonn�es du dernier tir du chasseur ont �t� mises � jour correctement.
        assertEquals(newCoord, monster.getCoord_hunted());
        assertTrue(monster.isMonsterTurn());
    }*/
	
	
	// Test des Getters
	
	/*@Test
    public void testGetRow() {
        ICoordinate coord = new Coordinate(2, 3);
        Monster monster = new Monster(null, coord, null, null);
        assertEquals(2, monster.getRow());
    }*/

   /* @Test
    public void testGetCol() {
        ICoordinate coord = new Coordinate(2, 3);
        Monster monster = new Monster(null, coord, null, null);
        assertEquals(3, monster.getCol());
    }*/

    /*@Test
    public void testGetWalls() {
        boolean[][] walls = {{true, false, true}, {false, true, true}, {false, false, true}};
        Monster monster = new Monster(walls, new Coordinate(0, 0), new Coordinate(1, 1), new Coordinate(2, 2));
        assertArrayEquals(walls, monster.getWalls());
    }*/

    /*@Test
    public void testGetCoord() {
        ICoordinate coord = new Coordinate(2, 3);
        Monster monster = new Monster(null, coord, null, null);
        assertEquals(coord, monster.getCoord());
    }*/

    /*@Test
    public void testGetCoordExit() {
        ICoordinate coord_exit = new Coordinate(1, 1);
        Monster monster = new Monster(null, null, coord_exit, null);
        assertEquals(coord_exit, monster.getCoord_exit());
    }*/

    /*@Test
    public void testGetCoordHunted() {
        ICoordinate coord_hunted = new Coordinate(3, 3);
        Monster monster = new Monster(null, null, null, coord_hunted);
        assertEquals(coord_hunted, monster.getCoord_hunted());
    }*/

    /*@Test
    public void testIsMonsterTurn() {
        Monster monster = new Monster(null, null, null, null);
        assertTrue(monster.isMonsterTurn());
    }*/
}
