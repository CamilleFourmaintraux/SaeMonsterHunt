package saves;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.io.File;

import fr.univlille.info.J2.main.application.system.SaveLoadSystemMaps;
import fr.univlille.info.J2.main.management.Maze;

public class TestSaveMap {
	@Test
	public void test_SaveMap() {
		File file = new File(SaveLoadSystemMaps.MAZES_DIRECTORY+"t_e_s_t"+".dat");
		assertDoesNotThrow(() -> SaveLoadSystemMaps.saveMap(Maze.DEFAULT_MAP, "t_e_s_t"));
		try {
			boolean[][] map = SaveLoadSystemMaps.loadMap("t_e_s_t");
			for(int row=0; row<map.length;row++) {
				for(int col=0; col<map[row].length;col++) {
					assertTrue(Maze.DEFAULT_MAP[row][col]==map[row][col]);
				}
			}
			boolean[][] map2 = SaveLoadSystemMaps.loadMap(file);
			for(int row=0; row<map2.length;row++) {
				for(int col=0; col<map2[row].length;col++) {
					assertTrue(Maze.DEFAULT_MAP[row][col]==map2[row][col]);
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			fail("An Exception was thrown.");
		} finally{
			assertTrue(file.delete());
		}
	}
}
