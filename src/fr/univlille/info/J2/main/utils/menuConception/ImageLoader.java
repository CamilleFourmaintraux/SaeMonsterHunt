package fr.univlille.info.J2.main.utils.menuConception;

import java.io.File;

import javafx.scene.image.Image;

public class ImageLoader {
	//public static final String IMG_DIRECTORY_PATH = "../res/img/";
	
	public static Image floor_dungeon = initImage("floor_dungeon.png");
	public static Image wall_dungeon = initImage("wall_dungeon.png");

	public static Image exit_dungeon = initImage("exit_dungeon.png");

	public static Image floor_forest = initImage("floor.jpg");
	public static Image wall_forest = initImage("wall.jpg");


	public static Image floor_ocean = initImage("floor.jpg");
	public static Image wall_ocean = initImage("wall.jpg");

	public static Image monster_ocean = initImage("krakenV2.png");


	public static Image scope = initImage("scope.png");
	public static Image empty = initImage("empty.png");
	
	public static Image initImage(String imgName) {
		return new Image(new File("res/img/"+imgName).toURI().toString());
	}

}
