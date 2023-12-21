package fr.univlille.info.J2.main.management.cells;

import java.util.Objects;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * La classe CellWithText représente une cellule d'un labyrinthe avec un texte associé.
 * Elle hérite de Cell et ajoute la capacité d'afficher du texte.
 */
public class CellWithText extends Cell{
	/**
     * Le texte associé à la cellule.
     */
	protected Text text;

	/**
     * Constructeur pour initialiser une cellule avec du texte.
     *
     * @param x        La coordonnée X de la cellule.
     * @param y        La coordonnée Y de la cellule.
     * @param zoom     Le facteur de zoom de la cellule.
     * @param fill     La couleur de remplissage de la cellule.
     * @param gap_X    L'espacement horizontal de la cellule.
     * @param gap_Y    L'espacement vertical de la cellule.
     * @param text     Le texte associé à la cellule.
     */
	public CellWithText(int x, int y, double zoom, Color fill,double gap_X, double gap_Y, Text text) {
		this(x, y, zoom, fill, fill, 0,  gap_X, gap_Y, text);
	}

	/**
     * Constructeur pour initialiser une cellule avec du texte.
     *
     * @param x        La coordonnée X de la cellule.
     * @param y        La coordonnée Y de la cellule.
     * @param zoom     Le facteur de zoom de la cellule.
     * @param fill     La couleur de remplissage de la cellule.
     * @param gap_X    L'espacement horizontal de la cellule.
     * @param gap_Y    L'espacement vertical de la cellule.
     * @param text     Le texte associé à la cellule.
     */
	public CellWithText(int x, int y, double zoom, Color fill, double gap_X, double gap_Y, String text) {
		this(x, y, zoom, fill, fill, 0,  gap_X, gap_Y, new Text(text));
	}

	/**
     * Constructeur pour initialiser une cellule avec du texte.
     *
     * @param c        Les coordonnées de la cellule.
     * @param zoom     Le facteur de zoom de la cellule.
     * @param fill     La couleur de remplissage de la cellule.
     * @param gap_X    L'espacement horizontal de la cellule.
     * @param gap_Y    L'espacement vertical de la cellule.
     * @param text     Le texte associé à la cellule.
     */
	public CellWithText(ICoordinate c, double zoom, Color fill,double gap_X, double gap_Y, String text) {
		this(c.getCol(), c.getRow(), zoom, fill, fill, 0,  gap_X, gap_Y, new Text(text));
	}

	/**
     * Constructeur pour initialiser une cellule avec du texte.
     *
     * @param x          La coordonnée X de la cellule.
     * @param y          La coordonnée Y de la cellule.
     * @param zoom       Le facteur de zoom de la cellule.
     * @param fill       La couleur de remplissage de la cellule.
     * @param stroke     La couleur de contour de la cellule.
     * @param strokeWidth L'épaisseur du contour de la cellule.
     * @param gap_X      L'espacement horizontal de la cellule.
     * @param gap_Y      L'espacement vertical de la cellule.
     * @param text       Le texte associé à la cellule.
     */
	public CellWithText(int x, int y, double zoom, Color fill, Color stroke, int strokeWidth, double gap_X, double gap_Y, Text text) {
		super(x, y, zoom, fill, stroke, strokeWidth, gap_X, gap_Y);
		this.text=text;
		this.text.setX((x*zoom+gap_X)+(zoom/3));
		this.text.setY((y*zoom+gap_Y)+(zoom/2));
		this.text.setVisible(true);
		this.text.toFront();
	}

	/**
     * Constructeur pour initialiser une cellule avec du texte.
     *
     * @param x          La coordonnée X de la cellule.
     * @param y          La coordonnée Y de la cellule.
     * @param zoom       Le facteur de zoom de la cellule.
     * @param fill       La couleur de remplissage de la cellule.
     * @param stroke     La couleur de contour de la cellule.
     * @param strokeWidth L'épaisseur du contour de la cellule.
     * @param gap_X      L'espacement horizontal de la cellule.
     * @param gap_Y      L'espacement vertical de la cellule.
     * @param text       Le texte associé à la cellule.
     */
	public CellWithText(int x, int y, double zoom, Color fill, Color stroke, int strokeWidth, double gap_X, double gap_Y, String text) {
		this(x, y, zoom, fill, stroke, strokeWidth, gap_X, gap_Y, new Text(text));
	}

	/**
     * Constructeur pour initialiser une cellule avec du texte.
     *
     * @param c          Les coordonnées de la cellule.
     * @param zoom       Le facteur de zoom de la cellule.
     * @param fill       La couleur de remplissage de la cellule.
     * @param stroke     La couleur de contour de la cellule.
     * @param strokeWidth L'épaisseur du contour de la cellule.
     * @param gap_X      L'espacement horizontal de la cellule.
     * @param gap_Y      L'espacement vertical de la cellule.
     * @param text       Le texte associé à la cellule.
     */
	public CellWithText(ICoordinate c, double zoom, Color fill, Color stroke, int strokeWidth, double gap_X, double gap_Y, String text) {
		this(c.getCol(), c.getRow(), zoom, fill, stroke, strokeWidth, gap_X, gap_Y, new Text(text));
	}

	/**
     * Retourne le texte associé à la cellule.
     *
     * @return Le texte de la cellule.
     */
	public Text getText() {
		return this.text;
	}

	/**
     * Définit le texte associé à la cellule.
     *
     * @param text Le texte a afficher dans la cellule.
     */
	public void setText(Text text) {
		this.text = text;
	}

	 /**
     * Définit le texte associé à la cellule en utilisant une chaîne de caractères.
     *
     * @param text La chaîne de caractères a afficher dans la cellule.
     */
	public void setText(String text) {
		this.text.setText(text);
	}

	/**
     * Définit la position horizontale du texte dans la cellule.
     *
     * @param x La position horizontale du texte.
     */
	public void setPosTextX(double x) {
		this.text.setX(x);
	}

	/**
     * Définit la position verticale du texte dans la cellule.
     *
     * @param y La position verticale du texte.
     */
	public void setPosTextY(double y) {
		this.text.setY(y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj) || (getClass() != obj.getClass()))
			return false;
		CellWithText other = (CellWithText) obj;
		return Objects.equals(text.getText(), other.text.getText());
	}



}
