package utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import fr.univlille.info.J2.main.utils.resources.DisplayValues;

public class TestDisplayValues {
	@Test
    public void testConstructorAndGetters() {
        // Création d'une instance de la classe DisplayValues avec des valeurs initiales
        DisplayValues displayValues = new DisplayValues(500, 500, 0, 0, 1.0, 0, 0);

        // Vérification des valeurs initiales avec les getters
        assertEquals(500, (int)displayValues.getWindowHeight());
        assertEquals(500, (int)displayValues.getWindowWidth());
        assertEquals(0, (int)displayValues.getWindowX());
        assertEquals(0, (int)displayValues.getWindowY());
        assertEquals(1, (int)displayValues.getZoom());
        assertEquals(0,(int) displayValues.getGapX());
        assertEquals(0, (int)displayValues.getGapY());
    }

    @Test
    public void testSetters() {
        // Création d'une instance de la classe DisplayValues avec des valeurs initiales
        DisplayValues displayValues = new DisplayValues(500, 500, 0, 0, 1.0, 0, 0);

        // Modification des valeurs avec les setters
        displayValues.setWindowHeight(600);
        displayValues.setWindowWidth(800);
        displayValues.setWindowX(10);
        displayValues.setWindowY(20);
        displayValues.setZoom(2.0);
        displayValues.setGapX(5);
        displayValues.setGapY(10);

        // Vérification des nouvelles valeurs avec les getters
        assertEquals(600, (int)displayValues.getWindowHeight());
        assertEquals(800, (int)displayValues.getWindowWidth());
        assertEquals(10, (int)displayValues.getWindowX());
        assertEquals(20, (int)displayValues.getWindowY());
        assertEquals(2, (int)displayValues.getZoom());
        assertEquals(5, (int)displayValues.getGapX());
        assertEquals(10, (int)displayValues.getGapY());
    }
}
