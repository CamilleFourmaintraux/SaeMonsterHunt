/**
 * Le package main.strategy.monster contient les classes relatives à la gestion
 * des monstres dans le jeu. Il propose des stratégies pour le comportement des monstres
 * et les informations concernant la sortie du labyrinthe.
 */
package fr.univlille.info.J2.main.management.exit;


import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * La classe Exit représente la sortie du labyrinthe, définie par des coordonnées spécifiques (ICoordinate).
 * Cette classe permet de stocker les coordonnées de la sortie et de fournir des méthodes pour accéder à ces coordonnées.
 *
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class Exit{
	/**
	 * L'objet contenant les coordonées de la sortie du labyrinthe.
	 */
	SaveExitData data;
	
	/**
     * Crée un objet Exit avec les coordonnées de la sortie du labyrinthe.
     *
     * @param coord Les coordonnées de la sortie.
     */
	public Exit(ICoordinate coord) {
		this.data = new SaveExitData(coord.getRow(), coord.getCol());
	}
	/**
     * Récupère la ligne de la sortie.
     *
     * @return  un entier représentant la ligne de la coordonnée de la sortie.
     */
	public int getRow() {
		return this.data.getRow();
	}
	/**
     * Récupère la colonne de la sortie.
     *
     * @return un entier représentant la colonne de la coordonnée de la sortie.
     */
	public int getCol() {
		return this.data.getCol();
	}
	/**
     * Récupère les coordonnées de la sortie.
     *
     * @return un objet ICoordinate contenant les coordonnées de la sortie.
     */
	public ICoordinate getCoord() {
		return new Coordinate(getRow(),getCol());
	}
	
	/**
	 * Renvoie les données associées à la sortie sauvegardée.
	 *
	 * @return Les données de la sortie sauvegardée.
	 */
	public SaveExitData getData() {
		return this.data;
	}
}

