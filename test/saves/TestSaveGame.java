package saves;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import fr.univlille.info.J2.main.application.system.Save;
import fr.univlille.info.J2.main.application.system.SaveLoadSystemGames;
import fr.univlille.info.J2.main.management.Maze;
import fr.univlille.info.J2.main.management.SaveManagementData;
import fr.univlille.info.J2.main.management.SaveMazeData;
import fr.univlille.info.J2.main.management.exit.SaveExitData;
import fr.univlille.info.J2.main.strategy.hunter.GameplayHunterData;
import fr.univlille.info.J2.main.strategy.hunter.SaveHunterData;
import fr.univlille.info.J2.main.strategy.monster.GameplayMonsterData;
import fr.univlille.info.J2.main.strategy.monster.SaveMonsterData;
import fr.univlille.info.J2.main.utils.resources.Theme;

public class TestSaveGame {
	GameplayMonsterData gmd = new GameplayMonsterData("Monster","Player",false, 1, 1);
	GameplayHunterData ghd = new GameplayHunterData("Hunter","Player",0);
	
	SaveManagementData data_man = new SaveManagementData(Theme.THEME_CAVE, false, false);
	SaveMazeData data_maze = new SaveMazeData(Maze.DEFAULT_MAP,new int[Maze.DEFAULT_MAP.length][Maze.DEFAULT_MAP.length],0,true);
	SaveExitData data_exit = new SaveExitData(Maze.DEFAULT_MAP.length-1,Maze.DEFAULT_MAP.length-1);
	SaveMonsterData data_monster = new SaveMonsterData(gmd,new boolean[Maze.DEFAULT_MAP.length][Maze.DEFAULT_MAP.length],Maze.DEFAULT_MAP,0,0);
	SaveHunterData data_hunter = new SaveHunterData(ghd,new int[Maze.DEFAULT_MAP.length][Maze.DEFAULT_MAP.length],3,3);
	
	Save save = new Save(data_man,data_maze,data_exit,data_monster,data_hunter);
	
	
	@Test
	public void test_SaveGame() {
		File file = new File(SaveLoadSystemGames.GAMES_DIRECTORY+"t_e_s_t"+".obj");
		assertDoesNotThrow(() -> SaveLoadSystemGames.saveGame(save, "t_e_s_t"));
		try {
			Save save = SaveLoadSystemGames.loadGame("t_e_s_t");
			assertEquals(data_exit,save.getData_exit());
			assertEquals(data_hunter,save.getData_hunter());
			assertEquals(data_man,save.getData_management());
			assertEquals(data_maze,save.getData_maze());
			assertEquals(data_monster,save.getData_monster());
			Save save2 = SaveLoadSystemGames.loadGame(file);
			assertEquals(data_exit,save2.getData_exit());
			assertEquals(data_hunter,save2.getData_hunter());
			assertEquals(data_man,save2.getData_management());
			assertEquals(data_maze,save2.getData_maze());
			assertEquals(data_monster,save2.getData_monster());
		} catch (ClassNotFoundException | IOException e) {
			fail(e.getMessage());
		} finally{
			assertTrue(file.delete());
		}
		
	}
	
}
