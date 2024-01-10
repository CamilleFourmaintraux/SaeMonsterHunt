package maze;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import fr.univlille.info.J2.main.management.Management;
import fr.univlille.info.J2.main.management.Maze;
import fr.univlille.info.J2.main.management.SaveManagementData;
import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.info.J2.main.strategy.hunter.GameplayHunterData;
import fr.univlille.info.J2.main.strategy.monster.GameplayMonsterData;
import fr.univlille.info.J2.main.utils.resources.Theme;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

public class TestMaze {

	Maze maze_defaultMap = new Maze();
	Maze maze_randomMap = new Maze(20, 10, 10, new GameplayHunterData("Hunter","Player",0),new GameplayMonsterData("Monster","Player", false, 1, 1),new SaveManagementData(Theme.THEME_CAVE, false, false));


	@Test
	public void test_constructor_Maze() {
		assertEquals(maze_defaultMap.isGameOver(),false);
		assertEquals(maze_defaultMap.isMonsterTurn(),false);
		assertEquals(maze_defaultMap.getTurn(),2);
		assertEquals(maze_randomMap.isGameOver(),false);
		assertEquals(maze_randomMap.isMonsterTurn(),false);
		assertEquals(maze_randomMap.getTurn(),2);
	}
	
	@Test
	public void test_default_map() {
		int diff = 0;
		for(int row=0; row<Maze.DEFAULT_MAP.length; row++) {
			for(int col=0; col<Maze.DEFAULT_MAP[0].length; col++) {
				if(!this.maze_defaultMap.getWalls()[row][col]==Maze.DEFAULT_MAP[row][col]) {
					diff++;
				}
			}
		}
		assertTrue(diff<3);
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
	 public void testInvalidShoot() {
		 maze_defaultMap.setMonsterTurn(true);
		 assertFalse(maze_defaultMap.shoot(maze_defaultMap.getMonster().getCoord()));
	 }

	 @Test
	 public void testInvalidMove() {
		 ICoordinate invalidCoorMonster = new Coordinate(maze_defaultMap.getMonster().getRow()+3,maze_defaultMap.getMonster().getCol()+3);
		 assertFalse(maze_defaultMap.move(invalidCoorMonster));
	 }

	 @Test
	 public void testCanMonsterMoveAt() {
		 ICoordinate coorMonster = new Coordinate(0,0);
		 maze_defaultMap.getMonster().setCoord(coorMonster);;
		 maze_defaultMap.setMonsterTurn(true);
		 assertTrue(maze_defaultMap.canMonsterMoveAt(new Coordinate(coorMonster.getRow()+1,coorMonster.getCol()+1)));
		 assertTrue(maze_defaultMap.canMonsterMoveAt(new Coordinate(coorMonster.getRow()-1,coorMonster.getCol()-1))); //Out of bounds coordinate will be (0,0) and monster can move to (0,0).
		 assertTrue(maze_defaultMap.canMonsterMoveAt(new Coordinate(coorMonster.getRow(),coorMonster.getCol()+1)));
		 assertFalse(maze_defaultMap.canMonsterMoveAt(new Coordinate(320,420)));
	 }

	 @Test
	 public void testExitMove() {
		 maze_defaultMap.setMonsterTurn(true);
		 ICoordinate exitCoorMonster = new Coordinate(maze_defaultMap.getExit().getRow(),maze_defaultMap.getExit().getCol());
		 maze_defaultMap.getMonster().setCoord(new Coordinate(exitCoorMonster.getRow()-1,exitCoorMonster.getCol()-1));
		 assertTrue(maze_defaultMap.move(exitCoorMonster));
		 assertTrue(maze_defaultMap.isGameOver());
	 }

	 @Test
	 public void testValidSboot() {
		 maze_defaultMap.setMonsterTurn(false);
		 assertTrue(maze_defaultMap.shoot(maze_defaultMap.getMonster().getCoord()));
		 assertTrue(maze_defaultMap.isGameOver());
	 }
	 
	 @Test
	 public void test_Constructor_Maze_Save() {
		 Maze maze = new Maze(Management.createSave(maze_randomMap));
		 assertEquals(maze.getWalls(),maze_randomMap.getWalls());
		 
	 }
	 
	 @Test
	 public void test_initEmptyMaze() {
		 boolean[][] emptyMap = Maze.initEmptyMaze(10, 10);
		 for(int h=0; h<emptyMap.length; h++) {
				for(int l=0; l<emptyMap[h].length; l++) {
					assertTrue(emptyMap[h][l]);
				}
			}
	 }
	 
	 @Test
	 public void test_toString() {
		 String aff = maze_defaultMap.toString();
		 String aff2 = Maze.toString(Maze.DEFAULT_MAP);
		 int diff = 0;
		 for(int i=0; i<aff.length(); i++)  if(aff.charAt(i)!=aff2.charAt(i))  diff++;
		 assertTrue(diff<3);
	 }
	 
	 @Test
	 public void test_getCellInfo() {
		 CellInfo state;
		 maze_randomMap.getHunter().setCoord(new Coordinate(5,5));
		 for(int row=0; row<maze_randomMap.getWalls().length;row++) {
			 for(int col=0; col<maze_randomMap.getWalls()[row].length;col++) {
				 state=maze_randomMap.getCellInfo(new Coordinate(row,col));
				 if(row==maze_randomMap.getHunter().getRow() && col==maze_randomMap.getHunter().getCol()) {
					 assertTrue(state.equals(CellInfo.HUNTER));
				 }else if(row==maze_randomMap.getMonster().getRow() && col==maze_randomMap.getMonster().getCol()) {
					 assertTrue(state.equals(CellInfo.MONSTER));
				 } else if(row==maze_randomMap.getExit().getRow() && col==maze_randomMap.getExit().getCol()) {
					 assertTrue(state.equals(CellInfo.EXIT));
				 }else if(maze_randomMap.getWalls()[row][col]) {
					 assertTrue(state.equals(CellInfo.EMPTY));
				 }else {
					 assertTrue(state.equals(CellInfo.WALL));
				 }
			 }
		 }
	 }
	 
	 @Test
	 public void test_triggerGameOver() {
		 assertFalse(this.maze_randomMap.isGameOver());
		 this.maze_randomMap.triggersGameOver();
		 assertTrue(this.maze_randomMap.isGameOver());
	 }
	 
	 @Test
	    public void testIsSpotted() {
		 	maze_randomMap.setSpotted(true);
	        assertTrue(maze_randomMap.isSpotted());
	        
	        // Tester le cas où spotted est faux
	        maze_randomMap.setSpotted(false);
	        assertFalse(maze_randomMap.isSpotted());
	    }

	    @Test
	    public void testSetGameOver() {
	    	maze_randomMap.setGameOver(true);
	        assertTrue(maze_randomMap.isGameOver());
	        
	        maze_randomMap.setGameOver(false);
	        assertFalse(maze_randomMap.isGameOver());
	    }

	    @Test
	    public void testSetMonsterTurn() {
	        Maze instance = new Maze();
	        
	        // Tester la méthode setMonsterTurn avec différentes valeurs
	        instance.setMonsterTurn(true);
	        assertTrue(instance.getData().isMonsterTurn());
	        
	        instance.setMonsterTurn(false);
	        assertFalse(instance.getData().isMonsterTurn());
	    }

	    @Test
	    public void testGetMonsterIA() {
	        assertEquals(maze_randomMap.getMonsterIA(),"Player");
	    }

	    @Test
	    public void testGetHunterIA() {
	        assertEquals(maze_randomMap.getHunterIA(),"Player");
	    }

	    @Test
	    public void testGetIdWinner() {
	    	assertTrue(maze_randomMap.getIdWinner()<3);
	    }
	    
	    @Test
	    public void testGetVisionRange() {
	    	assertEquals(maze_randomMap.getVisionRange(),1);
	    }

	    @Test
	    public void testGetData() {
	        assertNotNull(maze_randomMap.getData());
	    }

	    @Test
	    public void testGetDataMan() {
	        assertNotNull(maze_randomMap.getDataMan());
	    }

	    @Test
	    public void testGetClosestDistanceToMonster() {
	    	assertEquals(maze_randomMap.getClosestDistanceToMonster(),maze_randomMap.getWalls().length*maze_randomMap.getWalls()[0].length);
	    	//Valeur par défaut
	    }
}
