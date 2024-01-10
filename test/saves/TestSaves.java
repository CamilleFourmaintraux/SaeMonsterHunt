package saves;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.univlille.info.J2.main.management.Maze;
import fr.univlille.info.J2.main.application.system.Save;
import fr.univlille.info.J2.main.management.SaveManagementData;
import fr.univlille.info.J2.main.management.SaveMazeData;
import fr.univlille.info.J2.main.management.exit.SaveExitData;
import fr.univlille.info.J2.main.strategy.hunter.GameplayHunterData;
import fr.univlille.info.J2.main.strategy.hunter.SaveHunterData;
import fr.univlille.info.J2.main.strategy.monster.GameplayMonsterData;
import fr.univlille.info.J2.main.strategy.monster.SaveMonsterData;
import fr.univlille.info.J2.main.utils.resources.Theme;

public class TestSaves {
	
	GameplayMonsterData gmd = new GameplayMonsterData("Monster","Player",false, 1, 1);
	GameplayHunterData ghd = new GameplayHunterData("Hunter","Player",0);
	
	SaveManagementData data_man = new SaveManagementData(Theme.THEME_CAVE, false, false);
	SaveMazeData data_maze = new SaveMazeData(Maze.DEFAULT_MAP,new int[Maze.DEFAULT_MAP.length][Maze.DEFAULT_MAP.length],0,true);
	SaveExitData data_exit = new SaveExitData(Maze.DEFAULT_MAP.length-1,Maze.DEFAULT_MAP.length-1);
	SaveMonsterData data_monster = new SaveMonsterData(gmd,new boolean[Maze.DEFAULT_MAP.length][Maze.DEFAULT_MAP.length],Maze.DEFAULT_MAP,0,0);
	SaveHunterData data_hunter = new SaveHunterData(ghd,new int[Maze.DEFAULT_MAP.length][Maze.DEFAULT_MAP.length],3,3);
	
	Save save = new Save(data_man,data_maze,data_exit,data_monster,data_hunter);
	

	@Test
	public void test_Saves_constructor() {
		assertEquals(data_exit,save.getData_exit());
		assertEquals(data_hunter,save.getData_hunter());
		assertEquals(data_man,save.getData_management());
		assertEquals(data_maze,save.getData_maze());
		assertEquals(data_monster,save.getData_monster());
	}
	
	@Test
	public void test_Saves_getters_Monster() {
		assertEquals(0,data_monster.getCol());
		assertEquals(0,data_monster.getRow());
		assertTrue(data_monster.getExplored()!=null);
		assertEquals(data_monster.getGameplay(),gmd);
		assertEquals("Monster",data_monster.getName());
		assertEquals("Player",data_monster.getIA());
		assertEquals(1,data_monster.getMovingRange());
		assertEquals(1,data_monster.getVisionRange());
		assertEquals(false,data_monster.isVisionLimited());
		assertTrue(data_monster.getWalls()!=null);
	}
	
	@Test
	public void test_Saves_setters_Monster() {
		assertEquals("Player",data_monster.getIA());
		gmd.setIA("Easy");
		assertEquals("Easy",data_monster.getIA());
		assertEquals(1,data_monster.getMovingRange());
		gmd.setMovingRange(5);
		assertEquals(5,data_monster.getMovingRange());
		assertEquals("Monster",data_monster.getName());
		gmd.setName("Bob");
		assertEquals("Bob",data_monster.getName());
		assertEquals(false,data_monster.isVisionLimited());
		gmd.setVisionLimited(true);
		assertEquals(true,data_monster.isVisionLimited());
		assertEquals(1,data_monster.getVisionRange());
		gmd.setVisionRange(3);
		assertEquals(3,data_monster.getVisionRange());
	}
	
	@Test
	public void test_Saves_getters_Hunter() {
		assertEquals(3,data_hunter.getCol());
		assertEquals(3,data_hunter.getRow());
		assertEquals(data_hunter.getGameplay(),ghd);
		assertEquals("Player",data_hunter.getIA());
		assertEquals("Hunter",data_hunter.getName());
		assertEquals(0,data_hunter.getBonusRange());
		assertTrue(data_hunter.getTraces()!=null);
	}
	
	@Test
	public void test_Saves_setters_Hunter() {
		assertEquals(0,data_hunter.getBonusRange());
		ghd.setBonusRange(1);
		assertEquals(1,data_hunter.getBonusRange());
		assertEquals("Player",data_hunter.getIA());
		ghd.setIA("Easy");
		assertEquals("Easy",data_hunter.getIA());
		assertEquals("Hunter",data_hunter.getName());
		ghd.setName("Kylian");
		assertEquals("Kylian",data_hunter.getName());
	}
	
	@Test
	public void test_Saves_getters_Exit() {
		assertEquals(Maze.DEFAULT_MAP.length-1,data_exit.getRow());
		assertEquals(Maze.DEFAULT_MAP.length-1,data_exit.getCol());
	}
	
	@Test
	public void test_Saves_getters_Maze() {
		assertTrue(data_maze.getTraces()!=null);
		assertEquals(0,data_maze.getTurn());
		assertEquals(true,data_maze.isMonsterTurn());
		for(int row=0; row<data_maze.getWalls().length;row++) {
			for(int col=0; col<data_maze.getWalls()[row].length;col++) {
				assertTrue(Maze.DEFAULT_MAP[row][col]==data_maze.getWalls()[row][col]);
			}
		}
	}
	
	
}
