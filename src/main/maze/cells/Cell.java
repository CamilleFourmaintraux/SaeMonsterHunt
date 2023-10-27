package main.maze.cells;

import java.util.Objects;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * La classe `Cell` repr�sente une cellule rectangulaire utilis�e dans un labyrinthe. 
 * Elle extend la classe `Rectangle` de JavaFX pour repr�senter graphiquement une cellule.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class Cell extends Rectangle{
	/**
	 * Coordonn�es construite � partir du x & y.
	 */
	ICoordinate coord;
	
	/**
	 * Constructeur avec des param�tres limit�s. 
	 * Cr�e une cellule avec des valeurs de remplissage et d'�paisseur de contour par d�faut.
	 * @param x La coordonn�e en X de la cellule dans le labyrinthe.
	 * @param y La coordonn�e en Y de la cellule dans le labyrinthe.
	 * @param zoom Le facteur de zoom pour la taille de la cellule.
	 * @param fill La couleur de remplissage de la cellule.
	 * @param gap_X La valeur de d�calage en X pour la position de la cellule.
	 * @param gap_Y La valeur de d�calage en Y pour la position de la cellule.
	 */
	public Cell(int x, int y, int zoom, Color fill, int gap_X, int gap_Y) {
		this(x,y,zoom,fill,fill, 0, gap_X, gap_Y);
	}
	
	/**
	 * Constructeur avec des param�tres complets.
	 * Cr�e une cellule avec des valeurs de remplissage, couleur de contour, et �paisseur de contour personnalis�es.
	 * @see Cell#Cell(int, int, int, Color, int, int)
	 * @param x La coordonn�e en X de la cellule dans le labyrinthe.
	 * @param y La coordonn�e en Y de la cellule dans le labyrinthe.
	 * @param zoom Le facteur de zoom pour la taille de la cellule.
	 * @param fill La couleur de remplissage de la cellule.
	 * @param stroke La couleur de contour de la cellule.
	 * @param strokeWidth L'�paisseur du contour de la cellule.
	 * @param gap_X La valeur de d�calage en X pour la position de la cellule.
	 * @param gap_Y La valeur de d�calage en Y pour la position de la cellule.
	 */
	public Cell(int x, int y, int zoom, Color fill, Color stroke, int strokeWidth, int gap_X, int gap_Y) {
		super(x*zoom+gap_X,y*zoom+gap_X,zoom, zoom);
		this.coord=new Coordinate(y,x);
		this.setFill(fill);
		this.setStroke(stroke);
		this.setStrokeWidth(strokeWidth);
	}
	
	 /**
     * Retourne les coordonn�es associ�es � la cellule.
     *
     * @return Les coordonn�es de la cellule.
     */
	public ICoordinate getCoord() {
		return this.coord;
	}
	
	/**
     * D�finit les coordonn�es associ�es � la cellule.
     *
     * @param c Les nouvelles coordonn�es de la cellule.
     * @return Les coordonn�es mises � jour.
     */
	public ICoordinate setCoord(ICoordinate c) {
		return this.coord=c;
	}
	
	/**
     * Obtient le num�ro de ligne de la cellule dans le labyrinthe.
     *
     * @return un entier repr�sentant le num�ro de ligne de la cellule.
     */
	public int getRow() {
		return this.coord.getRow();
	}
	
	 /**
     * Obtient le num�ro de colonne de la cellule dans le labyrinthe.
     *
     * @return un entier repr�sentant le num�ro de colonne de la cellule.
     */
	public int getCol() {
		return this.coord.getCol();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		return Objects.equals(coord, other.coord);
	}
	
	
	
	
	
}
