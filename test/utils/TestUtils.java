package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Time;

import org.junit.Test;

import fr.univlille.info.J2.main.utils.Utils;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class TestUtils {
	@Test
	public void test_utils_background() {
		int i = Utils.random.nextInt(1);
		assertEquals(i,0);
		Background b = new Background(new BackgroundFill(Color.RED, new CornerRadii(0), Insets.EMPTY));
		assertEquals(b,Utils.setBackGroungFill(Color.RED));
	}
	
	@Test
	public void test_utils_convertor() {
		assertEquals("#ff0000",Utils.convertToHex(Color.RED));
		assertEquals("#000000",Utils.convertToHex(Color.BLACK));
		assertEquals("#ffffff",Utils.convertToHex(Color.WHITE));
		assertEquals("#000000",Utils.convertToHex(Color.TRANSPARENT));
	}
	
	@Test
	public void test_utils_wait() {
		long start = System.currentTimeMillis();
		Utils.wait(1);
		long end = System.currentTimeMillis();
		assertTrue(end-start>=1000);
	}
}
