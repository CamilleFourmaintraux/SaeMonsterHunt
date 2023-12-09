package fr.univlille.info.J2.main.utils.menuConception;

import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class ImageLoader {
	public static final String IMG_DIRECTORY_PATH = "./res/img/";
	
	public static final String THEME_DUNGEON = "dungeon";
	public static final String THEME_CAVE = "cave";
	public static final String THEME_MEADOW = "meadow";
	public static final String THEME_FOREST = "forest";
	public static final String THEME_OCEAN = "ocean";
	
	public final static String FLOOR = "floor";
	public final static String WALL = "wall";
	public final static String EXIT = "exit";
	public final static String MONSTER = "monster";
	
	
	public static final Map<String,Map<String,Image>> THEMES = new HashMap<>();
	private static final Map<String,Image> DUNGEON= new HashMap<>();
	private static final Map<String,Image> CAVE = new HashMap<>();
	private static final Map<String,Image> MEADOW = new HashMap<>();
	private static final Map<String,Image> FOREST = new HashMap<>();
	private static final Map<String,Image> OCEAN = new HashMap<>();
	
	public static final Image SCOPE = initImage("hud","scope.png");
	public static final  Image EMPTY = initImage("hud","empty.png");
	
	private ImageLoader() {}
	
	public static Image initImage(String theme, String name) {
		return new Image(new File(IMG_DIRECTORY_PATH+theme+"/"+name).toURI().toString());
	}
	
	public static void initThemes() {
		DUNGEON.put(FLOOR, ImageLoader.initImage(THEME_DUNGEON,"tiles.png"));
		DUNGEON.put(WALL, ImageLoader.initImage(THEME_DUNGEON,"bricks.png"));
		DUNGEON.put(MONSTER, ImageLoader.initImage(THEME_DUNGEON,"kraken.png"));
		DUNGEON.put(EXIT, ImageLoader.initImage(THEME_DUNGEON,"stairs.png"));
		
		CAVE.put(FLOOR, ImageLoader.initImage(THEME_CAVE,"stone.png"));
		CAVE.put(WALL, ImageLoader.initImage(THEME_CAVE,"rock.png"));
		CAVE.put(MONSTER, ImageLoader.initImage(THEME_CAVE,"slime.png"));
		CAVE.put(EXIT, ImageLoader.initImage(THEME_CAVE,"wayout.png"));
		
		MEADOW.put(FLOOR, ImageLoader.initImage(THEME_MEADOW,"grass.png"));
		MEADOW.put(WALL, ImageLoader.initImage(THEME_MEADOW,"tree.png"));
		MEADOW.put(MONSTER, ImageLoader.initImage(THEME_MEADOW,"rabbit.png"));
		MEADOW.put(EXIT, ImageLoader.initImage(THEME_MEADOW,"burrow.png"));
		
		OCEAN.put(FLOOR, ImageLoader.initImage(THEME_OCEAN,"sea.png"));
		OCEAN.put(WALL, ImageLoader.initImage(THEME_OCEAN,"boat.png"));
		OCEAN.put(MONSTER, ImageLoader.initImage(THEME_OCEAN,"shark.png"));
		OCEAN.put(EXIT, ImageLoader.initImage(THEME_OCEAN,"abyss.png"));
		
		THEMES.put("DUNGEON",DUNGEON);
		THEMES.put("CAVE",CAVE);
		THEMES.put("MEADOW",MEADOW);
		THEMES.put("FOREST",FOREST);
		THEMES.put("OCEAN",OCEAN);
	}

}
