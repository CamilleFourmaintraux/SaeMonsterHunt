package fr.univlille.info.J2.main.management.exit;

import java.io.Serializable;

/**
 * La classe SaveExitData représente les données de sauvegarde associées à la sortie d'un labyrinthe.
 * Ces données comprennent les coordonnées (ligne, colonne) de la sortie.
 * Cette classe implémente l'interface Serializable pour permettre la sérialisation des instances.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 * 
 */
public class SaveExitData implements Serializable{
	private static final long serialVersionUID = 4790349523153458010L;
	
	/**
     * La coordonnée en ligne de la sortie.
     */
	private int row;
	
	/**
     * La coordonnée en colonne de la sortie.
     */
	private int col;
	
	/**
     * Constructeur de la classe SaveExitData.
     *
     * @param row La coordonnée en ligne de la sortie.
     * @param col La coordonnée en colonne de la sortie.
     */
	public SaveExitData(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
     * Obtient le numéro de version de la sérialisation.
     *
     * @return Le numéro de version de la sérialisation.
     */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
     * Obtient la coordonnée en ligne de la sortie.
     *
     * @return La coordonnée en ligne de la sortie.
     */
	public int getRow() {
		return row;
	}
	
	/**
     * Obtient la coordonnée en colonne de la sortie.
     *
     * @return La coordonnée en colonne de la sortie.
     */
	public int getCol() {
		return col;
	}
	
}
