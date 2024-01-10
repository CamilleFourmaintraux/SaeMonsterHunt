package utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.univlille.info.J2.main.utils.resources.Generators;
import javafx.scene.shape.Rectangle;

public class TestGenerators {
	/*@Test
	public void test_generators_buttons() {
		 Button button = Generators.generateButton("test", 0, 0, Color.RED, Color.BLUE);
		 Button button2 = Generators.generateButton("test", Color.RED, Color.BLUE);
		 assertNotNull(button);
		 assertNotNull(button2);
		 assertEquals(button.getText(),"test");
		 assertEquals(button2.getText(),"test");
	}
	
	@Test
	public void test_generators_labels() {
		Label label = Generators.generateLabel("test");
		Label label2 = Generators.generateLabel("test", 0, 0);
		 assertNotNull(label);
		 assertNotNull(label2);
		 assertEquals(label.getText(),"test");
		 assertEquals(label2.getText(),"test");
	}
	
	@Test
	public void test_generators_slider() {
		Slider slider = Generators.generateSlider(1, 5, 3);
		assertEquals(1,(int)slider.getMin());
		assertEquals(5,(int)slider.getMax());
		assertEquals(3,(int)slider.getValue());
	}*/
	
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
