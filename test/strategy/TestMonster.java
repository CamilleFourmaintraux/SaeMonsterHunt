package strategy;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

import fr.univlille.info.J2.main.management.cells.CellEvent;
import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.info.J2.main.strategy.monster.Monster;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;

public class TestMonster {
	boolean[][] walls = {{true, false, true}, {false, true, true}, {false, false, true}};
	Monster monster = new Monster(walls,new CellEvent(new Coordinate(4,5),0,CellInfo.EMPTY),0,1,"Player");


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
