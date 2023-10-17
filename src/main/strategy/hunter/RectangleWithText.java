package main.strategy.hunter;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class RectangleWithText extends Rectangle{//cette classe ne trouve utilité que dans la classe Hunter. Elle ne sert qu'à l'affichage de la view de Hunter.
	protected Text text;
	
	public RectangleWithText() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RectangleWithText(Text text, int x, int y, int w,int h, Color fill, Color stroke, int strokeWidth) {
		super(x,y,w,h);
		this.setFill(fill);
		this.setStroke(stroke);
		this.setStrokeWidth(strokeWidth);
		this.text=text;
		this.text.setX(x+(w/3));
		this.text.setY(y+(h/2));
	}
	public Text getText() {
		return this.text;
	}
	public void setText(Text text) {
		this.text = text;
	}
	public void setText(String text) {
		this.text.setText(text);
	}
	
	
	
	
	
	
}
