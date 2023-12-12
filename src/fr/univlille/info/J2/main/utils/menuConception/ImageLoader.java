package fr.univlille.info.J2.main.utils.menuConception;

import java.io.File;

import javafx.scene.image.Image;


/**
 * 
 * Classe permettant de charger des fichiers image en objet à partir du nom de fichier.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class ImageLoader {
	//public static final String IMG_DIRECTORY_PATH = "../res/img/";
	
	/**
	 * Image du sol pour le thème Donjon.
	 */
	public static Image floor_dungeon = initImage("floor_dungeon.png");
	/**
	 * Image du mur pour le thème Donjon.
	 */
	public static Image wall_dungeon = initImage("wall_dungeon.png");
	/**
	 * Image de la sortie pour le thème Donjon.
	 */
	public static Image exit_dungeon = initImage("exit_dungeon.png");

	
	/**
	 * Image du sol pour le thème Forêt.
	 */
	public static Image floor_forest = initImage("floor.jpg");
	/**
	 * Image du mur pour le thème Forêt.
	 */
	public static Image wall_forest = initImage("wall.jpg");

	
	/**
	 * Image du sol pour le thème Océan.
	 */
	public static Image floor_ocean = initImage("floor.jpg");
	/**
	 * Image du mur pour le thème Océan.
	 */
	public static Image wall_ocean = initImage("wall.jpg");
	/**
	 * Image du monstre pour le thème Ocèan.
	 */
	public static Image monster_ocean = initImage("krakenV2.png");

	/**
	 * Image représentant le viseur du chasseur (la ou il a tir pour la dernière fois du point de vue du monstre).
	 */
	public static Image scope = initImage("scope.png");
	/**
	 * Image vide.
	 */
	public static Image empty = initImage("empty.png");
	
	/**
	 * Initialise un objet Image avec un fichier image donné.
	 * 
	 * @param imgName nom du fichier image.
	 * @return Objet Image avec l'image chargé.
	 */
	public static Image initImage(String imgName) {
		return new Image(new File("res/img/"+imgName).toURI().toString());
	}

}
