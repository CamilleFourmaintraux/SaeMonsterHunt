package test.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import main.maze.Maze;

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
}
