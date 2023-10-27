package main.maze.cells;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * La classe `CellWithText` représente une cellule d'un labyrinthe avec un texte associé.
 * Elle hérite de `Cell` et ajoute la capacité d'afficher du texte.
 */
public class CellWithText extends Cell{
	/**
     * Le texte associé à la cellule.
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
     * @param text Le texte à afficher dans la cellule.
     */
	public void setText(Text text) {
		this.text = text;
	}
	
	 /**
     * Définit le texte associé à la cellule en utilisant une chaîne de caractères.
     *
     * @param text La chaîne de caractères à afficher dans la cellule.
     */
	public void setText(String text) {
		this.text.setText(text);
	}
	
	/**
     * Définit la position horizontale du texte dans la cellule.
     *
     * @param x La position horizontale du texte.
     */
	public void setTextX(double x) {
		this.text.setX(x);
	}
	
	/**
     * Définit la position verticale du texte dans la cellule.
     *
     * @param y La position verticale du texte.
     */
	public void setTextY(double y) {
		this.text.setY(y);
	}
}
