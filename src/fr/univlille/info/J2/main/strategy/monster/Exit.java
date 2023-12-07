/**
 * Le package main.strategy.monster contient les classes relatives à la gestion
 * des monstres dans le jeu. Il propose des stratégies pour le comportement des monstres
 * et les informations concernant la sortie du labyrinthe.
 */
package fr.univlille.info.J2.main.strategy.monster;

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
public class Exit {
	/**
	 * Les coordonées de la sortie du labyrinthe.
	 */
	ICoordinate coord;
	/**
     * Crée un objet Exit avec les coordonnées de la sortie du labyrinthe.
     *
     * @param coord Les coordonnées de la sortie.
     */
	public Exit(ICoordinate coord) {
		this.coord=coord;
	}
	/**
     * Récupère la ligne de la sortie.
     *
     * @return  un entier représentant la ligne de la coordonnée de la sortie.
     */
	public int getRow() {
		return this.coord.getRow();
	}
	/**
     * Récupère la colonne de la sortie.
     *
     * @return un entier représentant la colonne de la coordonnée de la sortie.
     */
	public int getCol() {
		return this.coord.getCol();
	}
	/**
     * Récupère les coordonnées de la sortie.
     *
     * @return un objet ICoordinate contenant les coordonnées de la sortie.
     */
	public ICoordinate getCoord() {
		return this.coord;
	}
}

