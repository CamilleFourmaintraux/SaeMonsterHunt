package maze;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.univlille.info.J2.main.management.MazeEditor;

public class TestMazeEditor {
	
	@Test
    public void testReset() {
		boolean[][] walls = new boolean[10][10];
		for(boolean[] line:walls) {
			for(boolean b:line) {
				assertFalse(b);
			}
		}
		MazeEditor.resetWalls(walls);
		for(boolean[] line:walls) {
			for(boolean b:line) {
				assertTrue(b);
			}
		}
	}
}
