package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import fr.univlille.info.J2.main.utils.Utils;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class TestUtils {
	@Test
	public void test_utils() {
		int i = Utils.random.nextInt(1);
		assertEquals(i,0);
		Background b = new Background(new BackgroundFill(Color.RED, new CornerRadii(0), Insets.EMPTY));
		assertEquals(b,Utils.setBackGroungFill(Color.RED));
	}
}
