package strategy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.univlille.info.J2.main.management.cells.CellEvent;
import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.info.J2.main.strategy.monster.GameplayMonsterData;
import fr.univlille.info.J2.main.strategy.monster.Monster;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

public class TestMonster {
	boolean[][] walls = {{true, false, true}, {false, true, true}, {false, false, true}};
	Monster monster = new Monster(walls,new Coordinate(4,5),new Coordinate(0,0),new GameplayMonsterData("Monster","Player",false,0,1));


	@Test
	public void test_constructor_monster() {
		//assertEquals(monster, new Monster(walls,new Coordinate(4,5))); //Pas bvesoin de faire ce test, car pas besoin d'une fonction equals (pas plus de 1 monstre par map)
		assertEquals(monster.getCoord(),new Coordinate(4,5));
		assertEquals(monster.getRow(),4);
		assertEquals(monster.getCol(),5);
	}

	@Test
	public void test_setters_monster() {
		assertEquals(monster.getCoord(),new Coordinate(4,5));
		monster.setCoord(new Coordinate(4,2));
		assertEquals(monster.getCoord(),new Coordinate(4,2));
		monster.setCoord(new Coordinate(9,6));
		assertEquals(monster.getCoord(),new Coordinate(9,6));

	}
	
    @Test
    public void testGetCoord() {
        assertEquals(new Coordinate(4, 5), monster.getCoord());
        monster.setCoord(new Coordinate(1, 1));
        assertEquals(new Coordinate(1, 1), monster.getCoord());
    }
    
    @Test
    public void testGetName() {
    	assertEquals("Monster", monster.getName());	
    }
    
    @Test
    public void testGetIA() {
    	assertEquals("Player", monster.getIA());
    }
    
    @Test
    public void testGetMovingRange() {
    	assertEquals(1, monster.getMovingRange());
    }
    
    @Test
    public void testGetVisionRange() {
    	assertEquals(0, monster.getVisionRange());
    }
    
    @Test
    public void testIsVisionLimited() {
    	assertEquals(false, monster.isVisionLimited());
    }

	//pas de test pour les methodes play & update car pas encore implemente.
	@Test
	public void test_play() {
		assertEquals(monster.play(),null);
	}

	@Test
	public void test_update() {
		monster.update(new CellEvent(null, 0, null));
	}
}
