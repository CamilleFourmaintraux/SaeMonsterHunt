package management;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import fr.univlille.info.J2.main.management.Management;
import fr.univlille.info.J2.main.management.Maze;

public class TestManagement {

	@Test
	public void test_Management_Constants() {
		assertEquals("Player",Management.getDefaultIaPlayer());
		assertEquals("Monster",Management.getDefaultNameMonster());
		assertEquals("Hunter",Management.getDefaultNameHunter());
	}
	
	@Test
	public void test_Management_Percentage() {
		assertEquals(Double.valueOf(200),Double.valueOf(Management.calculPercentage(20,1000)));
		assertEquals(Double.valueOf(20),Double.valueOf(Management.calculPercentage(20,100)));
		assertEquals(Double.valueOf(-20),Double.valueOf(Management.calculPercentage(-20,100)));
		assertEquals(Double.valueOf(-20),Double.valueOf(Management.calculPercentage(20,-100)));
		assertEquals(Double.valueOf(20),Double.valueOf(Management.calculPercentage(-20,-100)));
	}
	
	@Test
	public void test_Management_Save() {
		assertNotEquals(null,Management.createSave(new Maze()));
	}
	
}
