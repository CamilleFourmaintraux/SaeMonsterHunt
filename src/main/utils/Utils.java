package main.utils;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Utils {
	public static Random random = new Random();
	public static Rectangle makeRectangle(int x, int y, int w,int h, Color paint) {
		Rectangle rect = new Rectangle(x,y,w,h);
		rect.setFill(paint);
		return rect;
	}
}
