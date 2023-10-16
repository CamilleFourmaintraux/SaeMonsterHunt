package main.utils;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.maze.cells.ICellEvent.CellInfo;

public class Utils {
	public static Random random = new Random();
	public static Rectangle makeRectangle(int x, int y, int w,int h, Color paint) {
		Rectangle rect = new Rectangle(x,y,w,h);
		rect.setFill(paint);
		return rect;
	}
	public static Rectangle makeRectangle(int x, int y, int w,int h, Color fill, Color stroke, int strokeWidth) {
		Rectangle rect = new Rectangle(x,y,w,h);
		rect.setFill(fill);
		rect.setStroke(stroke);
		rect.setStrokeWidth(strokeWidth);
		return rect;
	}
	public static Color getStateColor(CellInfo state) {
		Color paint;
		if(state.equals(CellInfo.MONSTER)) {
			paint=Color.MAROON;
		}else if(state.equals(CellInfo.HUNTER)) {
			paint=Color.YELLOW;
		}else if(state.equals(CellInfo.EXIT)) {
			paint=Color.GREEN;
		}else if(state.equals(CellInfo.EMPTY)) {
			paint=Color.AQUA;
		}else if(state.equals(CellInfo.WALL)) {
			paint=Color.BLUE;
		}else {
			return Color.BLACK;
		}
		return paint;
	}
}
