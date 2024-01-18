package utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.univlille.info.J2.main.utils.resources.Generators;
import javafx.scene.shape.Rectangle;

public class TestGenerators {
	@Test
	public void test_setLayout() {
		Rectangle rect = new Rectangle();
		rect.setLayoutX(200);
		rect.setLayoutY(300);
		assertEquals(200,(int)rect.getLayoutX());
		assertEquals(300,(int)rect.getLayoutY());
		Generators.setLayout(rect, 100.0, 120.0);
		assertEquals(100,(int)rect.getLayoutX());
		assertEquals(120,(int)rect.getLayoutY());
	}
}
