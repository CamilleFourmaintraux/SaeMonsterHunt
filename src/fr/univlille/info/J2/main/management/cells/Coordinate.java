package fr.univlille.info.J2.main.management.cells;

import java.io.Serializable;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
/**
 * Une classe représentant les coordonnées (ligne et colonne) d'une cellule dans un labyrinthe.
 *
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class Coordinate implements ICoordinate, Serializable{
	private static final long serialVersionUID = 1171241478876210063L;
	/**
	 * Le numéro de la ligne.
	 */
	int row;
	/**
	 * Le numéro de colonne.
	 */
	int col;

	/**
     * Constructeur par défaut.
     */
	public Coordinate() {}

	/**
     * Constructeur avec des coordonnées spécifiées.
     * Crée des coordonnées avec le numéro de ligne et colonne.
     *
     * @param row Le numéro de ligne.
     * @param col Le numéro de colonne.
     */
	public Coordinate(int row, int col) {
		if(row<0) {
			row=0;
		}
		if(col<0) {
			col=0;
		}
		this.row = row;
		this.col = col;
	}

	/**
     * Obtient le numéro de la ligne de la coordonnée.
     *
     * @return Le numéro de la ligne de la coordonnée.
     */
	@Override
	public int getRow() {
		return this.row;
	}

	/**
     * Obtient le numéro de la colonne de la coordonnée.
     *
     * @return Le numéro de la colonne de la coordonnée.
     */
	@Override
	public int getCol() {
		return this.col;
	}

	 /**
     * Définit les coordonnées à partir d'une ligne et d'une colonne spécifiées.
     *
     * @param row Le nouveau numéro de ligne de la coordonnée.
     * @param col Le nouveau numéro de colonne de la coordonnée.
     */
	public void setCoordinate(int row, int col) {
		this.row = row;
		this.col = col;
	}

	 /**
     * Définit le nouveau numéro de ligne de la coordonnée.
     *
     * @param row Le nouveau numéro de ligne de la coordonnée.
     */
	public void setRow(int row) {
		this.row = row;
	}

	/**
     * Définit le nouveau numéro de colonne de la coordonnée.
     *
     * @param col Le nouveau numéro de colonne de la coordonnée.
     */
	public void setCol(int col) {
		this.col = col;
	}

	/**
     * Compare si cet objet Coordinate est égal à un autre objet spécifié.
     *
     * @param obj L'objet a comparer à cette instance de Coordinate.
     * @return true si les objets sont égaux en termes de coordonnées de ligne et de colonne, sinon false.
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Coordinate other = (Coordinate) obj;
		return col == other.col && row == other.row;
	}


	@Override
	public String toString() {
		return "("+this.getRow()+";"+this.getCol()+")";
	}



}
