package strategy;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

import fr.univlille.info.J2.main.management.cells.CellEvent;
import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.info.J2.main.strategy.monster.Monster;

public class TestMonster {
	boolean[][] walls = {{true, false, true}, {false, true, true}, {false, false, true}};
	Monster monster = new Monster(walls,new Coordinate(4,5),"Player",0,1);
	
	
	@Test
	public void test_constructor_monster() {
		//assertEquals(monster, new Monster(walls,new Coordinate(4,5))); //Pas bvesoin de faire ce test, car pas besoin d'une fonction equals (pas plus de 1 monstre par map)
		assertEquals(monster.getCoord(),new Coordinate(4,5));
		assertEquals(monster.getRow(),4);
		assertEquals(monster.getCol(),5);
	}
	
	@Test
	public void test_initialize_walls_monster() {
		Monster monster = new Monster(null, null,null,0,0);
		assertArrayEquals(null, monster.getWalls());
		// Appel � la m�thode initialize avec le tableau de murs de test
		monster.initialize(walls);
		// Test que la propri�t� walls de l'objet Monster a �t� correctement initialis�e
		assertArrayEquals(walls, monster.getWalls());
	}
	
	@Test
	public void test_setters_monster() {
		assertEquals(monster.getCoord(),new Coordinate(4,5));
		monster.setCoord(new Coordinate(4,2));
		assertEquals(monster.getCoord(),new Coordinate(4,2));
		monster.setCoord(new Coordinate(9,6));
		assertEquals(monster.getCoord(),new Coordinate(9,6));
		
	}
	
	//pas de test pour les methodes play & update car pas encore implemente.
	@Test
	public void test_play() {
		assertNotEquals(monster.play(),null);
	}
	
	@Test
	public void test_update() {
		monster.update(new CellEvent(null, 0, null));
	}

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
