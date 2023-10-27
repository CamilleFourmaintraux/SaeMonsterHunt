package main.strategy.monster;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * La classe Exit repr�sente la sortie du labyrinthe, d�finie par des coordonn�es sp�cifiques (ICoordinate).
 * Cette classe permet de stocker les coordonn�es de la sortie et de fournir des m�thodes pour acc�der � ces coordonn�es.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class Exit {
	/**
	 * Les coordon�es de la sortie du labyrinthe.
	 */
	ICoordinate coord;
	/**
     * Cr�e un objet Exit avec les coordonn�es de la sortie du labyrinthe.
     *
     * @param coord Les coordonn�es de la sortie.
     */
	public Exit(ICoordinate coord) {
		this.coord=coord;
	}
	/**
     * R�cup�re la ligne de la sortie.
     *
     * @return  un entier repr�sentant la ligne de la coordonn�e de la sortie.
     */
	public int getRow() {
		return this.coord.getRow();
	}
	/**
     * R�cup�re la colonne de la sortie.
     *
     * @return un entier repr�sentant la colonne de la coordonn�e de la sortie.
     */
	public int getCol() {
		return this.coord.getCol();
	}
	/**
     * R�cup�re les coordonn�es de la sortie.
     *
     * @return un objet ICoordinate contenant les coordonn�es de la sortie.
     */
	public ICoordinate getCoord() {
		return this.coord;
	}
}

