package test.maze;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.Test;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import main.maze.Maze;
import main.maze.cells.CellWithText;
import main.maze.cells.Coordinate;

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
		 assertEquals(-1, maze_defaultMap.getTrace(new Coordinate(0,0)));
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
		 assertFalse(maze_defaultMap.isWall(new Coordinate(0, 0)));
		 assertTrue(maze_defaultMap.isWall(new Coordinate(0, 1)));
	 }
	 
	 // il faudrait qu'on ait un moyen de recuperer les coordonnées
	 // du chasseur pour verifier qu'il a bien tirer a cet endroit
	 // et verifier la condition de victoire.
	 
	 @Test
	 public void testShoot() {
		 ICoordinate target = new Coordinate(5,8); 
		 boolean shootResult = maze_defaultMap.shoot(target);

		 // Vérifiez si le tir a réussi
		 assertTrue(shootResult);
		 
		 maze_defaultMap.move(target);
		 boolean shootResultWin = maze_defaultMap.shoot(target);
		 
		 assertTrue(shootResult);
	 }
	 
	 // il faudrait qu'on ait un moyen de recuperer les coordonnées
	 // du monstre pour verifier qu'il s'est bien déplacer à cet endroit
	 // et verifier qu'il est dans la possibilité de le faire.
	 
	 @Test
	 public void testMove() {
		 ICoordinate destination = new Coordinate(4,8); 
		 boolean moveResult = maze_defaultMap.move(destination);

		 // Vérifiez si le déplacement a réussi
		 assertTrue(moveResult);
	 }
	 
	 /*@Test
	 public void testCanMonsterMoveAt() {
		 assertTrue(maze_defaultMap.canMonsterMoveAt(new Coordinate(0,1)));
		 assertFalse(maze_defaultMap.canMonsterMoveAt(new Coordinate(0,0)));
		 assertFalse(maze_defaultMap.canMonsterMoveAt(new Coordinate(32,42)));
	 }*/
}
