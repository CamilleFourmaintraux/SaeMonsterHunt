package fr.univlille.info.J2.main.management.view;

import java.util.logging.Logger;

import fr.univlille.info.J2.main.management.Maze;
import fr.univlille.info.J2.main.utils.patrons.Observer;
import fr.univlille.info.J2.main.utils.resources.DisplayValues;
import fr.univlille.info.J2.main.utils.resources.Theme;
import javafx.scene.Scene;

/**
 * Une classe abstraite représentant une vue dans le modèle MVC (Modèle-Vue-Contrôleur).
 * Cette classe implémente l'interface Observer pour être informée des changements dans le modèle.
 */
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

	/**
     * Obtient les valeurs d'affichage associées à cette vue.
     *
     * @return Les valeurs d'affichage.
     */
	public DisplayValues getDisplay() {
		return display;
	}

	/**
     * Obtient le thème associé à cette vue.
     *
     * @return Le thème.
     */
	public Theme getTheme() {
		return theme;
	}

	/**
     * Obtient la scène associée à cette vue.
     *
     * @return La scène.
     */
	public Scene getScene() {
		return scene;
	}
	
	/**
     * Méthode abstraite pour dessiner la vue.
     */
	public abstract void draw();
	
	/**
     * Méthode abstraite pour initialiser les sprites de la vue.
     */
	protected abstract void initiateSprites();
	
	/**
     * Méthode abstraite pour actualiser la vue en réponse à des changements dans le modèle.
     */
	public abstract void actualize();
	
}
