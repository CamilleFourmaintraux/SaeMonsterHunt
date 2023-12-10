package fr.univlille.info.J2.main.management.view;

import java.util.logging.Logger;

import fr.univlille.info.J2.main.management.Maze;
import fr.univlille.info.J2.main.utils.menuConception.DisplayValues;
import fr.univlille.info.J2.main.utils.menuConception.Theme;
import fr.univlille.info.J2.main.utils.patrons.Observer;
import javafx.scene.Scene;

public abstract class View implements Observer{

	protected static final Logger LOGGER = Logger.getLogger(View.class.getName());
	/**
	 * Contient toute les informations sur comment doit s'afficher la fenetre et le jeu
	 */
	protected DisplayValues display;
	
	/**
	 * Contient toute les informations à propos de la manière dont doit s'afficher le jeu
	 */
	protected Theme theme;

	/**
	 * Nom du joueur utilisant la view
	 */
	protected String playerName;

	/**
	 * Sujet (pour le modèle observé)
	 */
	protected Maze maze;
	
	/**
	 * Scène pour l'affichage.
	 */
	protected Scene scene;

	/**
     * Calcule la position de dessin X en fonction d'une coordonnée.

	 * @param x La coordonnée X.
	 * @return  La position de dessin X calculée.
	 */
	public int calculDrawX(int x) {
		return (int)(x*this.display.getZoom()+this.display.getGapX());
	}

	/**
     * Calcule la position de dessin Y en fonction d'une coordonnée.
     *
	 * @param y La coordonnée Y.
	 * @return  La position de dessin Y calculée.
	 */
	public int calculDrawY(int y) {
		return (int)(y*this.display.getZoom()+this.display.getGapY());
	}

	public DisplayValues getDisplay() {
		return display;
	}

	public Theme getTheme() {
		return theme;
	}

	public String getPlayerName() {
		return playerName;
	}

	public Scene getScene() {
		return scene;
	}
	
	public abstract void draw();
	
	protected abstract void initiateSprites();
	
	public abstract void actualize();
	
}
