package fr.univlille.info.J2.main.utils.menuConception;

import java.io.File;
import java.util.Map;
import java.util.HashMap;

import javafx.scene.image.Image;

public class ImageLoader {
	public static final String IMG_DIRECTORY_PATH = "./res/img/";
	
	public static final String THEME_DUNGEON = "dungeon";
	public static final String THEME_CAVE = "cave";
	public static final String THEME_FOREST = "forest";
	public static final String THEME_OCEAN = "ocean";
	
	private ImageLoader() {}
	
	public static Image floor_dungeon = ;
	public static Image wall_dungeon = initImage("dungeon","wall_dungeon.png");
	public static Image monster_dungeon = initImage("dungeon","kraken.png");
	public static Image exit_dungeon = initImage("dungeon","exit_dungeon.png");

	public static Image floor_forest = initImage("dungeon","floor.jpg");
	public static Image wall_forest = initImage("dungeon","wall.jpg");
	public static Image monster_forest = initImage("dungeon","kraken.png");
	public static Image exit_forest = initImage("dungeon","exit_dungeon.png");

	public static Image floor_ocean = initImage("dungeon","floor.jpg");
	public static Image wall_ocean = initImage("dungeon","wall.jpg");
	public static Image monster_ocean= initImage("dungeon","kraken.png");
	public static Image exit_ocean = initImage("dungeon","exit_dungeon.png");
	
	
	


	public static Image scope = initImage("hud","scope.png");
	public static Image empty = initImage("hud","empty.png");
	
	public static void initThemes() {
		Map<String,Image> dungeon = new HashMap<String,Image>();
		dungeon.put("floor", ImageLoader.initImage(THEME_DUNGEON,"floor_dungeon.png"));
		dungeon.put("wall", ImageLoader.initImage(THEME_DUNGEON,"wall_dungeon.png"));
		dungeon.put("monster", ImageLoader.initImage(THEME_DUNGEON,"monster_dungeon.png"));
		dungeon.put("exit", ImageLoader.initImage(THEME_DUNGEON,"exit_dungeon.png"));
		
		Map<String,Image> cave = new HashMap<String,Image>();
		dungeon.put("floor", ImageLoader.initImage(THEME_CAVE,"floor_dungeon.png"));
		dungeon.put("wall", ImageLoader.initImage(THEME_CAVE,"wall_dungeon.png"));
		dungeon.put("monster", ImageLoader.initImage(THEME_CAVE,"monster_dungeon.png"));
		dungeon.put("exit", ImageLoader.initImage(THEME_CAVE,"exit_dungeon.png"));
	}
	
	public static Image initImage(String theme, String name) {
		return new Image(new File(IMG_DIRECTORY_PATH+theme+"/"+name).toURI().toString());
	}

}
