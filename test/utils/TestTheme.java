package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import fr.univlille.info.J2.main.utils.resources.Theme;
import javafx.scene.paint.Color;

public class TestTheme {
	

	Theme theme = new Theme("plage");
	
	@Test
	public void test_Theme_constructor() {
		assertEquals(true,theme.isWithImages());
		assertEquals(Theme.themesMap.get("plage"),theme);
	}

    @Test
    public void testConstructorAndGetters() {
        // Vérification des valeurs initiales avec les getters
        assertEquals("plage", theme.getName());
        assertTrue(theme.isWithImages());
   
		theme.setFloorColor(Color.LIGHTGREY);
		theme.setWallColor(Color.DARKGREY);
		theme.setFogColor(Color.BLACK);
		theme.setBackgroundColor(Color.BLACK);
		theme.setTextColor(Color.WHITE);
		theme.setSound_monster("move.wav");
		theme.setSound_hunter("shot.wav");

        // Vérification des images
        assertNull(theme.getFloorImg());
        assertNull(theme.getWallImg());
        assertNull(theme.getExitImg());
        assertNull(theme.getMonsterImg());
        assertNull(theme.getHunterImg());

        // Vérification des couleurs
        assertNotNull(theme.getFloorColor());
        assertNotNull(theme.getWallColor());
        assertNotNull(theme.getFogColor());
        assertNotNull(theme.getBackgroundColor());
        assertNotNull(theme.getTextColor());

        // Vérification des sons
        assertNotNull(theme.getSound_hunter());
        assertNotNull(theme.getSound_monster());
    }

    @Test
    public void testSetters() {
        // Modification des valeurs avec les setters
        theme.setWithImages(false);

        // Vérification de la nouvelle valeur avec le getter
        assertFalse(theme.isWithImages());
    }

    @Test
    public void testToString() {
        // Vérification de la représentation sous forme de chaîne
        assertEquals("plage", theme.toString());
    }
}
