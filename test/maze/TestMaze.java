package maze;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import fr.univlille.info.J2.main.management.Maze;
import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

public class TestMaze {
	
	Maze maze_defaultMap = new Maze();
	Maze maze_randomMap = new Maze();
	
	
	@Test
	public void test_constructor_Maze() {
		assertEquals(maze_defaultMap.isGameOver,false);
		assertEquals(maze_defaultMap.isMonsterTurn,false);
		assertEquals(maze_defaultMap.turn,2);
		assertEquals(maze_randomMap.isGameOver,false);
		assertEquals(maze_randomMap.isMonsterTurn,false);
		assertEquals(maze_randomMap.turn,2);
	}
	
	//TODO faire tous les tests sur Maze
	
	 @Test
	 public void testToString() {
	        
	        String expected = "X . X . . X . X . X \n"+
	        				  "X . . . . X . X . . \n"+ 
	        				  ". . . . X X . X X . \n"+ 
	        				  ". . X . . X . . . . \n"+ 
	        				  "X . X X . X . . . X \n"+ 
	        				  "X . . X X X . . . X \n"+ 
	        				  "X . . . X . . . . . \n"+ 
	        				  ". . . . . . . X . X \n"+ 
	        				  ". . X . . X . X X X \n"+ 
	        				  "X X . . X X . . . X";

	        assertEquals(expected, maze_defaultMap.toString());
	 }
	 
	 @Test
	 public void testGetTrace() {
		 assertEquals(-1, maze_defaultMap.getTrace(new Coordinate(6,0)));
		 assertEquals(0, maze_defaultMap.getTrace(new Coordinate(3,1)));
	 }
	 
	 @Test
	 public void testInReach() {
		 ICoordinate coord1 = new Coordinate(1, 1); // Coordonnée de départ
		 ICoordinate coord2 = new Coordinate(2, 2); // Coordonnée à une distance de 1

		 // Vérifiez si les coordonnées sont à portée de 1 (devraient renvoyer true)
		 assertTrue(maze_defaultMap.inReach(coord1, coord2, 1));

		 ICoordinate coord3 = new Coordinate(1, 1); // Coordonnée de départ
		 ICoordinate coord4 = new Coordinate(4, 4); // Coordonnée à une distance de 3
		 
		 // Vérifiez si les coordonnées ne sont pas à portée de 1 (devraient renvoyer false)
		 assertFalse(maze_defaultMap.inReach(coord3, coord4, 1));
	 }
	 
	 @Test
	 public void testIsWall() {
		 assertTrue(maze_defaultMap.isFloor(new Coordinate(7, 6)));
		 assertFalse(maze_defaultMap.isFloor(new Coordinate(6, 0)));
	 }
	 
	 @Test
	 public void testInvalidSboot() {
		 maze_defaultMap.isMonsterTurn=true;
		 assertFalse(maze_defaultMap.shoot(maze_defaultMap.monster.getCoord()));
	 }
	 
	 @Test
	 public void testInvalidMove() {
		 ICoordinate invalidCoorMonster = new Coordinate(maze_defaultMap.monster.getRow()+3,maze_defaultMap.monster.getCol()+3);
		 assertFalse(maze_defaultMap.move(invalidCoorMonster));
	 }
	 
	 @Test
	 public void testCanMonsterMoveAt() {
		 ICoordinate coorMonster = maze_defaultMap.monster.getCoord();
		 maze_defaultMap.isMonsterTurn=true;
		 assertTrue(maze_defaultMap.canMonsterMoveAt(new Coordinate(coorMonster.getRow()+1,coorMonster.getCol()+1)));
		 assertFalse(maze_defaultMap.canMonsterMoveAt(new Coordinate(coorMonster.getRow()+2,coorMonster.getCol()+2)));
		 assertThrows(IndexOutOfBoundsException.class, () -> maze_defaultMap.canMonsterMoveAt(new Coordinate(32,42)));
	 }
	 
	 @Test
	 public void testExitMove() {
		 maze_defaultMap.isMonsterTurn=true;
		 ICoordinate exitCoorMonster = new Coordinate(maze_defaultMap.exit.getRow(),maze_defaultMap.exit.getCol());
		 maze_defaultMap.monster.setCoord(new Coordinate(exitCoorMonster.getRow()-1,exitCoorMonster.getCol()-1));
		 assertTrue(maze_defaultMap.move(exitCoorMonster));
		 assertTrue(maze_defaultMap.isGameOver);
	 }
	 
	 @Test
	 public void testValidSboot() {
		 maze_defaultMap.isMonsterTurn=false;
		 assertTrue(maze_defaultMap.shoot(maze_defaultMap.monster.getCoord()));
		 assertTrue(maze_defaultMap.isGameOver);
	 }
}
