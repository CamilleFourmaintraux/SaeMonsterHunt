package fr.univlille.info.J2.main.management;

import java.io.File;

import fr.univlille.info.J2.main.management.cells.Cell;
import fr.univlille.info.J2.main.utils.resources.DisplayValues;
import fr.univlille.info.J2.main.utils.resources.Theme;
import javafx.scene.Group;
import javafx.scene.paint.Color;

/**
 * Classe pour la gestion de l'éditeur de labyrinthes.
 * Cette classe permet de créer et de modifier des labyrinthes via une interface graphique.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 * 
 */
public class MazeEditor {
	/**
	 * Groupe contenant permettant d'éditer, de sélectionner
	 */
	Group group_map;

	/**
	 * Groupe contenant les images
	 */
	Group group_img;

	/**
	 * Groupe représentant l'éditeur
	 */
	Group group;

	 /**
     * Tableau représentant les murs du labyrinthe.
     */
	boolean[][] walls;

	/**
     * Hauteur de l'éditeur de labyrinthe.
     */
	int editor_height;
	
	/**
     * Largeur de l'éditeur de labyrinthe.
     */
	int editor_width;

	/**
     * Fichier représentant le labyrinthe importé.
     */
	File map_import;
	
	/**
     * Thème utilisé pour l'affichage de l'éditeur.
     */
	Theme theme;

	/**
     * Constructeur de la classe MazeEditor.
     * 
     * @param map_height Hauteur du labyrinthe.
     * @param map_width  Largeur du labyrinthe.
     * @param display    Valeurs d'affichage.
     * @param theme      Thème utilisé.
     */
	public MazeEditor(int map_height, int map_width, DisplayValues display, Theme theme){
		this.editor_height=map_height;
		this.editor_width=map_width;
		this.group_map = new Group();
		this.group_img = new Group();
		this.group = new Group();
		this.theme=theme;
		this.resetDrawing(map_height, map_width, display);
		this.group.getChildren().addAll(group_img,group_map);
	}

	/**
     * Dessine le labyrinthe.
     * 
     * @param map_height Hauteur du labyrinthe.
     * @param map_width  Largeur du labyrinthe.
     * @param display    Valeurs d'affichage.
     */
	public void draw(int map_height, int map_width, DisplayValues display) {
		//Adaptation du zoom
		double height = (display.getWindowHeight() / (map_height*2.7));
		double width = (display.getWindowWidth()  / (map_width*1.2));
		int zoom=(int) Math.min(height, width);

		this.group_map.getChildren().clear();
		this.group_img.getChildren().clear();

		for(int h=0; h<this.walls.length; h++) {
			for(int l=0; l<this.walls[h].length; l++) {
				Cell cell = new Cell(l, h, zoom, Color.TRANSPARENT, display.getGapX(), display.getGapY());
				this.setCellDisplay(theme.isWithImages(), !this.walls[h][l], cell);
				cell.setStroke(Color.VIOLET);
				cell.setOnMouseEntered(e->{
					cell.setStrokeWidth(1);
					if(e.isShiftDown()) {
						modify(cell,theme.isWithImages());
					}
				});
				cell.setOnMouseExited(e-> cell.setStrokeWidth(0) );
				cell.setOnMouseClicked(e-> modify(cell,theme.isWithImages()) );
				this.group_map.getChildren().add(cell);
				this.group_img.getChildren().add(cell.getImgv());
			}
		}
	}

	/**
     * Réinitialise les murs du labyrinthe.
     */
	public void resetWalls() {
		for(int h=0; h<this.walls.length; h++) {
			for(int l=0; l<this.walls[h].length; l++){
				this.walls[h][l]=true;
			}
		}
	}

	/**
     * Réinitialise le dessin du labyrinthe.
     * 
     * @param map_height Hauteur du labyrinthe.
     * @param map_width  Largeur du labyrinthe.
     * @param display    Valeurs d'affichage.
     */
	public void resetDrawing(int map_height, int map_width, DisplayValues display) {
		this.walls = new boolean[map_height][map_width];
		this.resetWalls();
		this.draw(map_height,map_width, display);
	}

	/**
     * Modifie une cellule du labyrinthe.
     * 
     * @param cell       Cellule à modifier.
     * @param withImages Indique si les images sont utilisées.
     */
	public void modify(Cell cell, boolean withImages) {
		if(this.walls[cell.getRow()][cell.getCol()]) {
			this.walls[cell.getRow()][cell.getCol()]=false;
			
		}else {
			this.walls[cell.getRow()][cell.getCol()]=true;
		}
		this.setCellDisplay(withImages, !this.walls[cell.getRow()][cell.getCol()], cell);
	}
	
	/**
     * Définit l'affichage d'une cellule en fonction du thème et de l'état du mur.
     * 
     * @param withImages Indique si les images sont utilisées.
     * @param isAWall    Indique si la cellule est un mur.
     * @param cell       Cellule à afficher.
     */
	public void setCellDisplay(boolean withImages, boolean isAWall, Cell cell) {
		if(isAWall) {
			if(withImages) {
				cell.setImage(this.theme.getWallImg());
				cell.setFill(Color.TRANSPARENT);
			}else {
				cell.setFill(this.theme.getWallColor());
			}
		}else {
			if(withImages) {
				cell.setImage(this.theme.getFloorImg());
				cell.setFill(Color.TRANSPARENT);
			}else {
				cell.setFill(this.theme.getFloorColor());
			}
		}
	}
}