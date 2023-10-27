package main.maze.cells;

import java.util.Objects;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * La classe `CellWithText` repr�sente une cellule d'un labyrinthe avec un texte associ�.
 * Elle h�rite de `Cell` et ajoute la capacit� d'afficher du texte.
 */
public class CellWithText extends Cell{
	/**
     * Le texte associ� � la cellule.
     */
	protected Text text;
	
	public CellWithText(int x, int y, int zoom, Color fill,int gap_X, int gap_Y, Text text) {
		this(x, y, zoom, fill, fill, 0,  gap_X, gap_Y, text);
	}
	
	public CellWithText(int x, int y, int zoom, Color fill,int gap_X, int gap_Y, String text) {
		this(x, y, zoom, fill, fill, 0,  gap_X, gap_Y, new Text(text));
	}
	
	public CellWithText(ICoordinate c, int zoom, Color fill,int gap_X, int gap_Y, String text) {
		this(c.getCol(), c.getRow(), zoom, fill, fill, 0,  gap_X, gap_Y, new Text(text));
	}
	
	public CellWithText(int x, int y, int zoom, Color fill, Color stroke, int strokeWidth, int gap_X, int gap_Y, Text text) {
		super(x, y, zoom, fill, stroke, strokeWidth, gap_X, gap_Y);
		this.text=text;
		this.text.setX((x*zoom+gap_X)+(zoom/3));
		this.text.setY((y*zoom+gap_Y)+(zoom/2));
		this.text.setVisible(true);
		this.text.toFront();
	}
	
	public CellWithText(int x, int y, int zoom, Color fill, Color stroke, int strokeWidth, int gap_X, int gap_Y, String text) {
		this(x, y, zoom, fill, stroke, strokeWidth, gap_X, gap_Y, new Text(text));
	}
	
	public CellWithText(ICoordinate c, int zoom, Color fill, Color stroke, int strokeWidth, int gap_X, int gap_Y, String text) {
		this(c.getCol(), c.getRow(), zoom, fill, stroke, strokeWidth, gap_X, gap_Y, new Text(text));
	}
	
	/**
     * Retourne le texte associ� � la cellule.
     *
     * @return Le texte de la cellule.
     */
	public Text getText() {
		return this.text;
	}
	
	/**
     * D�finit le texte associ� � la cellule.
     *
     * @param text Le texte � afficher dans la cellule.
     */
	public void setText(Text text) {
		this.text = text;
	}
	
	 /**
     * D�finit le texte associ� � la cellule en utilisant une cha�ne de caract�res.
     *
     * @param text La cha�ne de caract�res � afficher dans la cellule.
     */
	public void setText(String text) {
		this.text.setText(text);
	}
	
	/**
     * D�finit la position horizontale du texte dans la cellule.
     *
     * @param x La position horizontale du texte.
     */
	public void setPosTextX(double x) {
		this.text.setX(x);
	}
	
	/**
     * D�finit la position verticale du texte dans la cellule.
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CellWithText other = (CellWithText) obj;
		return Objects.equals(text.getText(), other.text.getText());
	}
	
	
	
}
