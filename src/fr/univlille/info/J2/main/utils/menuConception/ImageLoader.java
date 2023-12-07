package fr.univlille.info.J2.main.utils.menuConception;

import java.io.File;

import javafx.scene.image.Image;

public class ImageLoader {
	public static final String IMG_DIRECTORY_PATH = "./res/img/";
	
	private ImageLoader() {}
	
	public static Image floor_dungeon = initImage("dungeon","floor_dungeon.png");
	public static Image wall_dungeon = initImage("dungeon","wall_dungeon.png");

	public static Image exit_dungeon = initImage("dungeon","exit_dungeon.png");

	public static Image floor_forest = initImage("dungeon","floor.jpg");
	public static Image wall_forest = initImage("dungeon","wall.jpg");


	public static Image floor_ocean = initImage("dungeon","floor.jpg");
	public static Image wall_ocean = initImage("dungeon","wall.jpg");

	public static Image monster_ocean = initImage("dungeon","krakenV2.png");


	public static Image scope = initImage("hud","scope.png");
	public static Image empty = initImage("hud","empty.png");
	
	public static Image initImage(String theme, String name) {
		return new Image(new File(IMG_DIRECTORY_PATH+theme+"/"+name).toURI().toString());
	}

}
