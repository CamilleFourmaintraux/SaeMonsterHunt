package main.maze.cells;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle{
	ICoordinate coord;
	int gap_X;
	int gap_Y;
	int zoom;
	
	public Cell(int x, int y, int zoom, Color fill, int gap_X, int gap_Y) {
		this(x,y,zoom,fill,fill, 0, gap_X, gap_Y);
	}
	
	public Cell(int x, int y, int zoom, Color fill, Color stroke, int strokeWidth, int gap_X, int gap_Y) {
		super(x*zoom+gap_X,y*zoom+gap_X,zoom, zoom);
		this.coord=new Coordinate(y,x);
		this.setFill(fill);
		this.setStroke(stroke);
		this.setStrokeWidth(strokeWidth);
		this.zoom=zoom;
		this.gap_X=gap_X;
		this.gap_Y=gap_Y;
	}
	public ICoordinate getCoord() {
		return this.coord;
	}
	public ICoordinate setCoord(ICoordinate c) {
		return this.coord=c;
	}
	
	public int getRow() {
		return this.coord.getRow();
	}
	
	public int getCol() {
		return this.coord.getCol();
	}
	
	
	
	
	
}
