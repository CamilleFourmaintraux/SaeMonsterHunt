/**
 * 
 */
package main.maze.cells;

import java.util.Objects;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.utils.Utils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * La classe `Cell` représente une cellule rectangulaire utilisée dans un labyrinthe. 
 * Elle extend la classe `Rectangle` de JavaFX pour représenter graphiquement une cellule.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class Cell extends Rectangle{
	
	//public final Image floor = new Image("/home/iutinfo/eclipse-workspace/J2_SAE3A/res/img/floor-Cave.jpg");
	
	/**
	 * Coordonnées construite à partir du x et y.
	 */
	ICoordinate coord;
	ImageView iv;
	
	/**
	 * Constructeur avec des paramètres limit�s. 
	 * Crée une cellule avec des valeurs de remplissage et d'épaisseur de contour par défaut.
	 * @param x La coordonnée en X de la cellule dans le labyrinthe.
	 * @param y La coordonnée en Y de la cellule dans le labyrinthe.
	 * @param zoom Le facteur de zoom pour la taille de la cellule.
	 * @param fill La couleur de remplissage de la cellule.
	 * @param gap_X La valeur de décalage en X pour la position de la cellule.
	 * @param gap_Y La valeur de décalage en Y pour la position de la cellule.
	 */
	public Cell(int x, int y, int zoom, Color fill, int gap_X, int gap_Y, Image img) {
		this(x,y,zoom,fill,fill, 0, gap_X, gap_Y, img);
		
		//System.out.println("TEST IMG:"+iv.getImage().getUrl());
	}
	
	/**
	 * Constructeur avec des paramètres complets.
	 * Crée une cellule avec des valeurs de remplissage, couleur de contour, et épaisseur de contour personnalisées.
	 * @see Cell#Cell(int, int, int, Color, int, int)
	 * @param x La coordonnée en X de la cellule dans le labyrinthe.
	 * @param y La coordonnée en Y de la cellule dans le labyrinthe.
	 * @param zoom Le facteur de zoom pour la taille de la cellule.
	 * @param fill La couleur de remplissage de la cellule.
	 * @param stroke La couleur de contour de la cellule.
	 * @param strokeWidth L'�paisseur du contour de la cellule.
	 * @param gap_X La valeur de décalage en X pour la position de la cellule.
	 * @param gap_Y La valeur de décalage en Y pour la position de la cellule.
	 */
	public Cell(int x, int y, int zoom, Color fill, Color stroke, int strokeWidth, int gap_X, int gap_Y, Image img) {
		super(x*zoom+gap_X,y*zoom+gap_X,zoom, zoom);
		this.coord=new Coordinate(y,x);
		this.iv = new ImageView(img);
		this.iv.setX(x*zoom+gap_X);
		this.iv.setY(y*zoom+gap_X);
		this.iv.setFitHeight(zoom);
		this.iv.setFitWidth(zoom);
		this.setFill(fill);
		this.setStroke(stroke);
		this.setStrokeWidth(strokeWidth);
	}
	
	 /**
     * Retourne les coordonnées associées à la cellule.
     *
     * @return Les coordonnées de la cellule.
     */
	public ICoordinate getCoord() {
		return this.coord;
	}
	
	/**
     * Définit les coordonnées associées à la cellule.
     *
     * @param c Les nouvelles coordonnées de la cellule.
     * @return Les coordonnées mises à jour.
     */
	public ICoordinate setCoord(ICoordinate c) {
		return this.coord=c;
	}
	
	/**
     * Obtient le numéro de ligne de la cellule dans le labyrinthe.
     *
     * @return un entier représentant le numéro de ligne de la cellule.
     */
	public int getRow() {
		return this.coord.getRow();
	}
	
	 /**
     * Obtient le numéro de colonne de la cellule dans le labyrinthe.
     *
     * @return un entier représentant le numéro de colonne de la cellule.
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
	
	
	public ImageView getImgv() {
		return this.iv;
	}
	
	public void setImage(Image img) {
		this.iv.setImage(img);
	}
	
	
	
	
}
