package main.maze.cells;

import java.util.Objects;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
/**
 * Une classe repr�sentant les coordonn�es (ligne & colonne) d'une cellule dans un labyrinthe.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class Coordinate implements ICoordinate{
	/**
	 * Le num�ro de la ligne.
	 */
	int row;
	/**
	 * Le num�ro de colonne.
	 */
	int col;
	
	/**
     * Constructeur par d�faut.
     */
	public Coordinate() {}

	/**
     * Constructeur avec des coordonn�es sp�cifi�es.
     * Cr�e des coordonn�es avec le num�ro de ligne & colonne.
     *
     * @param row Le num�ro de ligne.
     * @param col Le num�ro de colonne.
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
     * Obtient le num�ro de la ligne de la coordonn�e.
     *
     * @return Le num�ro de la ligne de la coordonn�e.
     */
	@Override
	public int getRow() {
		// TODO Auto-generated method stub
		return this.row;
	}

	/**
     * Obtient le num�ro de la colonne de la coordonn�e.
     *
     * @return Le num�ro de la colonne de la coordonn�e.
     */
	@Override
	public int getCol() {
		// TODO Auto-generated method stub
		return this.col;
	}
	
	 /**
     * D�finit les coordonn�es � partir d'une ligne et d'une colonne sp�cifi�es.
     *
     * @param row Le nouveau num�ro de ligne de la coordonn�e.
     * @param col Le nouveau num�ro de colonne de la coordonn�e.
     */
	public void setCoordinate(int row, int col) {
		this.row = row;
		this.col = col;
	}

	 /**
     * D�finit le nouveau num�ro de ligne de la coordonn�.
     *
     * @param row Le nouveau num�ro de ligne de la coordonn�e.
     */
	public void setRow(int row) {
		this.row = row;
	}

	/**
     * D�finit le nouveau num�ro de colonne de la coordonn�.
     *
     * @param row Le nouveau num�ro de colonne de la coordonn�e.
     */
	public void setCol(int col) {
		this.col = col;
	}

	/**
     * Compare si cet objet Coordinate est �gal � un autre objet sp�cifi�.
     *
     * @param obj L'objet � comparer � cette instance de Coordinate.
     * @return true si les objets sont �gaux en termes de coordonn�es de ligne et de colonne, sinon false.
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		return col == other.col && row == other.row;
	}
	
	

}
