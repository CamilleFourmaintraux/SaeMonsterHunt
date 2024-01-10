package fr.univlille.info.J2.main.utils.resources;

import java.io.File;

import java.util.Map;
import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * La classe Theme représente les thèmes disponibles pour le jeu Monster Hunt.
 * Chaque thème est associé à des images, des couleurs et d'autres propriétés visuelles.
 * Les thèmes disponibles sont : dungeon, cave, meadow, forest et ocean.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 * 
 */

public class Theme {
	/**
     * Chemin du répertoire où sont stockées les images des thèmes.
     */
	private static final String IMG_DIRECTORY_PATH = "./res/img/";
	
	/**
     * Clé du thème représentant le donjon.
     */
	public static final String THEME_DUNGEON = "dungeon";
	
	/**
     * Clé du thème représentant la caverne.
     */
	public static final String THEME_CAVE = "cave";
	
	/**
     * Clé du thème représentant la prairie.
     */
	public static final String THEME_MEADOW = "meadow";
	
	/**
     * Clé du thème représentant la forêt.
     */
	public static final String THEME_FOREST = "forest";
	
	/**
     * Clé du thème représentant l'océan.
     */
	public static final String THEME_OCEAN = "ocean";

	/**
     * Map associant chaque clé de thème à une instance de la classe Theme.
     */
	public static Map<String,Theme> themesMap = new HashMap<>();
	

	/**
	 * indique si le jeu affichera de images ou des simples couleurs
	 */
	private boolean isWithImages;
	/**
	 * Nom du thème
	 */
	private String name;
	/**
	 * Nom du thème
	 */
	private Image floorImg;
	/**
	 * Nom du thème
	 */
	private Image wallImg;
	/**
	 * Nom du thème
	 */
	private Image exitImg;
	/**
	 * Nom du thème
	 */
	private Image monsterImg;
	/**
	 * Image associé au chasseur
	 */
	private Image hunterImg;
	/**
	 * Couleur des sols
	 */
	private Color floorColor;
	/**
	 * Couleur des murs
	 */
	private Color wallColor;
	/**
	 * Couleur du brouillard
	 */
	private Color fogColor;
	/**
	 * Couleur du fond de l'écran
	 */
	private Color backgroundColor;
	/**
	 * Couleur du texte
	 */
	private Color textColor;
	
	/**
	 * Son associé au chasseur pour ce thème
	 */
	private String sound_hunter;
	/**
	 * Son associé au monstre pour ce thème
	 */
	private String sound_monster;
	
	/**
     * Constructeur de la classe Theme.
     *
     * @param key La clé unique du thème.
     */
	public Theme(String key) {
		this.name=key;
		this.isWithImages=true;
		Theme.themesMap.put(key, this);
	}
	
	/**
     * Initialise les thèmes avec leurs propriétés visuelles respectives.
     */
	public static void initThemes() {
		Theme dungeon=new Theme(THEME_DUNGEON);
		Theme cave=new Theme(THEME_CAVE);
		Theme meadow=new Theme(THEME_MEADOW);
		Theme forest=new Theme(THEME_FOREST);
		Theme ocean=new Theme(THEME_OCEAN);
	
		dungeon.floorImg=loadImage(THEME_DUNGEON,"tiles.png");
		dungeon.wallImg=loadImage(THEME_DUNGEON,"bricks.png");
		dungeon.monsterImg=loadImage(THEME_DUNGEON,"animation_slime.gif");
		dungeon.exitImg=loadImage(THEME_DUNGEON,"stairs.png");
		dungeon.hunterImg=loadImage(THEME_DUNGEON,"magicScope.png");
		dungeon.floorColor=Color.LIGHTGREY;
		dungeon.wallColor=Color.DARKGREY;
		dungeon.fogColor=Color.BLACK;
		dungeon.backgroundColor=Color.BLACK;
		dungeon.textColor=Color.WHITE;
		dungeon.sound_monster="move.wav";
		dungeon.sound_hunter="laser.wav";
		
		cave.floorImg=loadImage(THEME_CAVE,"stone.png");
		cave.wallImg=loadImage(THEME_CAVE,"rock.png");
		cave.monsterImg=loadImage(THEME_CAVE,"animation_bat.gif");
		cave.exitImg=loadImage(THEME_CAVE,"wayout.png");
		cave.hunterImg=loadImage(THEME_CAVE,"rifleScope.png");
		cave.floorColor=Color.DARKGRAY;
		cave.wallColor=Color.LIGHTGRAY;
		cave.fogColor=Color.BLACK;
		cave.backgroundColor=Color.BLACK;
		cave.textColor=Color.WHITE;
		cave.sound_monster="move.wav";
		cave.sound_hunter="shot.wav";
		
		meadow.floorImg=loadImage(THEME_MEADOW,"grass.png");
		meadow.wallImg=loadImage(THEME_MEADOW,"tree.png");
		meadow.monsterImg=loadImage(THEME_MEADOW,"animation_rabbit.gif");
		meadow.exitImg=loadImage(THEME_MEADOW,"burrow.png");
		meadow.hunterImg=loadImage(THEME_MEADOW,"rifleScope.png");
		meadow.floorColor=Color.GREENYELLOW;
		meadow.wallColor=Color.CORNFLOWERBLUE;
		meadow.fogColor=Color.DARKGREEN;
		meadow.backgroundColor=Color.DARKGREEN;
		meadow.textColor=Color.WHITE;
		meadow.sound_monster="move.wav";
		meadow.sound_hunter="shot.wav";
		
		forest.floorImg=loadImage(THEME_FOREST,"humus.png");
		forest.wallImg=loadImage(THEME_FOREST,"stump.png");
		forest.monsterImg=loadImage(THEME_FOREST,"bambi.png");
		forest.exitImg=loadImage(THEME_FOREST,"bush.png");
		forest.hunterImg=loadImage(THEME_FOREST,"rifleScope.png");
		forest.floorColor=Color.LIGHTGREEN;
		forest.wallColor=Color.FORESTGREEN;
		forest.fogColor=Color.DARKGREEN;
		forest.backgroundColor=Color.DARKGREEN;
		forest.textColor=Color.WHITE;
		forest.sound_monster="move.wav";
		forest.sound_hunter="shot.wav";
		
		ocean.floorImg=loadImage(THEME_OCEAN,"sea.png");
		ocean.wallImg=loadImage(THEME_OCEAN,"boat.png");
		ocean.monsterImg=loadImage(THEME_OCEAN,"shark.png");
		ocean.exitImg=loadImage(THEME_OCEAN,"abyss.png");
		ocean.hunterImg=loadImage(THEME_OCEAN,"hook.png");
		ocean.floorColor=Color.AQUAMARINE;
		ocean.wallColor=Color.SEAGREEN;
		ocean.fogColor=Color.DARKBLUE;
		ocean.backgroundColor=Color.DARKBLUE;
		ocean.textColor=Color.WHITE;
		ocean.sound_monster="move.wav";
		ocean.sound_hunter="laser.wav";
	}
	
	/**
     * Charge une image à partir du répertoire des images du thème spécifié.
     * 
     * @param theme le nom du thème.
     * @param name le nom de l'image à charger.
     * @return une instance de la classe Image représentant l'image chargée.
     */
	public static Image loadImage(String theme, String name) {
		return new Image(new File(IMG_DIRECTORY_PATH+theme+"/"+name).toURI().toString());
	}
	
	/**
     * Obtient le nom du thème.
     * 
     * @return le nom du thème.
     */
	public String getName() {
		return this.name;
	}
	
	/**
     * Renvoie l'image représentant le sol du labyrinthe pour ce thème.
     * @return L'image du sol.
     */
	public Image getFloorImg() {
		return this.floorImg;
	}

	/**
     * Renvoie l'image représentant les murs du labyrinthe pour ce thème.
     * @return L'image des murs.
     */
	public Image getWallImg() {
		return this.wallImg;
	}

	/**
     * Renvoie l'image représentant la sortie du labyrinthe pour ce thème.
     * @return L'image de la sortie.
     */
	public Image getExitImg() {
		return this.exitImg;
	}

	/**
     * Renvoie l'image représentant le monstre du labyrinthe pour ce thème.
     * @return L'image du monstre.
     */
	public Image getMonsterImg() {
		return this.monsterImg;
	}

	/**
     * Renvoie l'image représentant le chasseur du labyrinthe pour ce thème.
     * @return L'image du chasseur.
     */
	public Image getHunterImg() {
		return this.hunterImg;
	}

	/**
     * Renvoie la couleur du sol pour ce thème.
     * @return La couleur du sol.
     */
	public Color getFloorColor() {
		return this.floorColor;
	}

	/**
     * Renvoie la couleur des murs pour ce thème.
     * @return La couleur des murs.
     */
	public Color getWallColor() {
		return this.wallColor;
	}

	/**
     * Renvoie la couleur de la brume pour ce thème.
     * @return La couleur de la brume.
     */
	public Color getFogColor() {
		return this.fogColor;
	}

	/**
     * Renvoie la couleur de fond pour ce thème.
     * @return La couleur de fond.
     */
	public Color getBackgroundColor() {
		return this.backgroundColor;
	}
	
	/**
     * Obtient la couleur du texte associée au thème.
     * 
     * @return la couleur du texte.
     */
	public Color getTextColor() {
		return this.textColor;
	}

	/**
     * Retourne une représentation sous forme de chaîne de caractères du thème.
     * 
     * @return le nom du thème.
     */
	@Override
	public String toString() {
		return this.name;
	}

	/**
     * Indique si le thème utilise des images ou des simples couleurs.
     * 
     * @return true si le thème utilise des images, sinon false.
     */
	public boolean isWithImages() {
		return isWithImages;
	}

	/**
     * Définit si le thème utilise des images ou des simples couleurs.
     * 
     * @param isWithImages true si le thème utilise des images, sinon false.
     */
	public void setWithImages(boolean isWithImages) {
		this.isWithImages = isWithImages;
	}
	
	/**
     * Obtient le son associé au chasseur pour ce thème.
     *
     * @return Le chemin du fichier audio du chasseur pour ce thème.
     */
	public String getSound_hunter() {
		return sound_hunter;
	}

	/**
     * Obtient le son associé au chasseur pour ce thème.
     *
     * @return Le chemin du fichier audio du chasseur pour ce thème.
     */
	public String getSound_monster() {
		return sound_monster;
	}
	
	
}	
