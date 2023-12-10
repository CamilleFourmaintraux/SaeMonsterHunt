package fr.univlille.info.J2.main.utils.menuConception;

import java.io.File;

import java.util.Map;
import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;


public class Theme {
	private static final String IMG_DIRECTORY_PATH = "./res/img/";
	
	public static final String THEME_DUNGEON = "dungeon";
	public static final String THEME_CAVE = "cave";
	public static final String THEME_MEADOW = "meadow";
	public static final String THEME_FOREST = "forest";
	public static final String THEME_OCEAN = "ocean";
	
	public static Map<String,Theme> themesMap = new HashMap<>();
	
	private String name;
	private Image floorImg;
	private Image wallImg;
	private Image exitImg;
	private Image monsterImg;
	private Image hunterImg;
	private Color floorColor;
	private Color wallColor;
	private Color fogColor;
	private Color backgroundColor;
	
	public Theme(String key) {
		this.name=key;
		Theme.themesMap.put(key, this);
	}
	
	public static void initThemes() {
		Theme dungeon=new Theme(THEME_DUNGEON);
		Theme cave=new Theme(THEME_CAVE);
		Theme meadow=new Theme(THEME_MEADOW);
		Theme forest=new Theme(THEME_FOREST);
		Theme ocean=new Theme(THEME_OCEAN);
	
		dungeon.floorImg=loadImage(THEME_DUNGEON,"tiles.png");
		dungeon.wallImg=loadImage(THEME_DUNGEON,"bricks.png");
		dungeon.monsterImg=loadImage(THEME_DUNGEON,"slime.png");
		dungeon.exitImg=loadImage(THEME_DUNGEON,"stairs.png");
		dungeon.hunterImg=loadImage(THEME_DUNGEON,"magicScope.png");
		dungeon.floorColor=Color.LIGHTGREY;
		dungeon.wallColor=Color.DARKGREY;
		dungeon.fogColor=Color.BLACK;
		dungeon.backgroundColor=Color.BLACK;
		
		cave.floorImg=loadImage(THEME_CAVE,"stone.png");
		cave.wallImg=loadImage(THEME_CAVE,"rock.png");
		cave.monsterImg=loadImage(THEME_CAVE,"bat.png");
		cave.exitImg=loadImage(THEME_CAVE,"wayout.png");
		cave.hunterImg=loadImage(THEME_CAVE,"rifleScope.png");
		cave.floorColor=Color.LIGHTGRAY;
		cave.wallColor=Color.DARKGRAY;
		cave.fogColor=Color.BLACK;
		cave.backgroundColor=Color.BLACK;
		
		meadow.floorImg=loadImage(THEME_MEADOW,"grass.png");
		meadow.wallImg=loadImage(THEME_MEADOW,"tree.png");
		meadow.monsterImg=loadImage(THEME_MEADOW,"rabbit.png");
		meadow.exitImg=loadImage(THEME_MEADOW,"burrow.png");
		meadow.hunterImg=loadImage(THEME_MEADOW,"rifleScope.png");
		meadow.floorColor=Color.LIGHTGREEN;
		meadow.wallColor=Color.FORESTGREEN;
		meadow.fogColor=Color.DARKGREEN;
		meadow.backgroundColor=Color.BLACK;
		
		forest.floorImg=loadImage(THEME_FOREST,"humus.png");
		forest.wallImg=loadImage(THEME_FOREST,"stump.png");
		forest.monsterImg=loadImage(THEME_FOREST,"bambi.png");
		forest.exitImg=loadImage(THEME_FOREST,"bush.png");
		forest.hunterImg=loadImage(THEME_FOREST,"rifleScope.png");
		forest.floorColor=Color.LIGHTGREEN;
		forest.wallColor=Color.FORESTGREEN;
		forest.fogColor=Color.DARKGREEN;
		forest.backgroundColor=Color.BLACK;
		
		ocean.floorImg=loadImage(THEME_OCEAN,"sea.png");
		ocean.wallImg=loadImage(THEME_OCEAN,"boat.png");
		ocean.monsterImg=loadImage(THEME_OCEAN,"shark.png");
		ocean.exitImg=loadImage(THEME_OCEAN,"abyss.png");
		ocean.hunterImg=loadImage(THEME_OCEAN,"hook.png");
		ocean.floorColor=Color.AQUAMARINE;
		ocean.wallColor=Color.SEAGREEN;
		ocean.fogColor=Color.DARKBLUE;
		ocean.backgroundColor=Color.BLACK;
	}
	
	public static Image loadImage(String theme, String name) {
		return new Image(new File(IMG_DIRECTORY_PATH+theme+"/"+name).toURI().toString());
	}
	
	public String getName() {
		return this.name;
	}
	
	public Image getFloorImg() {
		return this.floorImg;
	}

	public Image getWallImg() {
		return this.wallImg;
	}

	public Image getExitImg() {
		return this.exitImg;
	}

	public Image getMonsterImg() {
		return this.monsterImg;
	}

	public Image getHunterImg() {
		return this.hunterImg;
	}

	public Color getFloorColor() {
		return this.floorColor;
	}

	public Color getWallColor() {
		return this.wallColor;
	}

	public Color getFogColor() {
		return this.fogColor;
	}

	public Color getBackgroundColor() {
		return this.backgroundColor;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	
}	
