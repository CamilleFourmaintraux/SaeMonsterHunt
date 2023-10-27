package main.utils;

import java.util.Random;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class Utils {
	public static Random random = new Random();
	public static Background setBackGroungFill(Color fill) {
		return new Background(new BackgroundFill(fill, new CornerRadii(0), Insets.EMPTY));
	}
}
