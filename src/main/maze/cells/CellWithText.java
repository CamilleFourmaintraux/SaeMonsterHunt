package main.maze.cells;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class CellWithText extends Cell{
	protected Text text;
	
	public CellWithText(int x, int y, int zoom, Color fill,int gap_X, int gap_Y, Text text) {
		this(x, y, zoom, fill, fill, 0,  gap_X, gap_Y, text);
	}
	
	public CellWithText(int x, int y, int zoom, Color fill,int gap_X, int gap_Y, String text) {
		this(x, y, zoom, fill, fill, 0,  gap_X, gap_Y, new Text(text));
	}
	
	public CellWithText(ICoordinate c, int zoom, Color fill,int gap_X, int gap_Y, String text) {
		this(c.getCol(), c.getRow(), zoom, fill, fill, 0,  gap_X, gap_Y, new Text(text));
	}
	
	public CellWithText(int x, int y, int zoom, Color fill, Color stroke, int strokeWidth, int gap_X, int gap_Y, Text text) {
		super(x, y, zoom, fill, stroke, strokeWidth, gap_X, gap_Y);
		this.text=text;
		this.text.setX((x*zoom+gap_X)+(zoom/3));
		this.text.setY((y*zoom+gap_Y)+(zoom/2));
		this.text.setVisible(true);
		this.text.toFront();
	}
	
	public CellWithText(int x, int y, int zoom, Color fill, Color stroke, int strokeWidth, int gap_X, int gap_Y, String text) {
		this(x, y, zoom, fill, stroke, strokeWidth, gap_X, gap_Y, new Text(text));
	}
	
	public CellWithText(ICoordinate c, int zoom, Color fill, Color stroke, int strokeWidth, int gap_X, int gap_Y, String text) {
		this(c.getCol(), c.getRow(), zoom, fill, stroke, strokeWidth, gap_X, gap_Y, new Text(text));
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
	public void setTextX(double x) {
		this.text.setX(x);
	}
	public void setTextY(double y) {
		this.text.setY(y);
	}
}
