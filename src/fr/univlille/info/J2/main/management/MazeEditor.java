package fr.univlille.info.J2.main.management;

import java.io.File;

import fr.univlille.info.J2.main.management.cells.Cell;
import fr.univlille.info.J2.main.utils.menuConception.DisplayValues;
import fr.univlille.info.J2.main.utils.menuConception.Theme;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public class MazeEditor {
	/**
	 * Groupe contenant permettant le controle
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
	 * Labyrinthe.
	 */
	boolean[][] walls;

	/**
	 * Hauteur de l'éditeur.
	 */
	int editor_height;
	/**
	 * Largeur de l'éditeur.
	 */
	int editor_width;

	/**
	 * Objet File réprésentant le fichier d'importation de labyrinthe de l'utilisateur.
	 */
	File map_import;
	
	Theme theme;

<<<<<<< HEAD
	/**
	 * Constructeur de la classe MazeEditor.
	 * 
	 * @param map_height	Hauteur de l'éditeur.
	 * @param map_width		Largeur de l'éditeur.
	 * @param window_height Hauteur de la fenêtre.
	 * @param window_width  Largeur de la fenêtre.
	 * @param gap_X			Position horizontale.
	 * @param gap_Y			Position verticale.
	 */
	public MazeEditor(int map_height, int map_width, double window_height, double window_width, int gap_X, int gap_Y){
=======
	public MazeEditor(int map_height, int map_width, DisplayValues display, Theme theme){
>>>>>>> master
		this.editor_height=map_height;
		this.editor_width=map_width;
		this.group_map = new Group();
		this.group_img = new Group();
		this.group = new Group();
		this.theme=theme;
		this.resetDrawing(map_height, map_width, display);
		this.group.getChildren().addAll(group_img,group_map);
	}

<<<<<<< HEAD
	/**
	 * Méthode permettant dans l'éditeur de lorsque l'on clique sur une cellule, placer un mur et si il est déjà existant le supprimer.
	 * 
	 * @param map_height	Hauteur de l'éditeur.
	 * @param map_width		Largeur de l'éditeur.
	 * @param window_height Hauteur de la fenêtre.
	 * @param window_width  Largeur de la fenêtre.
	 * @param gap_X			Position horizontale.
	 * @param gap_Y			Position verticale.
	 */
	public void draw(int map_height, int map_width, double window_height, double window_width, int gap_X, int gap_Y) {
=======
	public void draw(int map_height, int map_width, DisplayValues display) {
>>>>>>> master
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
	 * Méthode permettant de supprimer les murs du labyrinthe (ne laissant que des sols).
	 */
	public void resetWalls() {
		for(int h=0; h<this.walls.length; h++) {
			for(int l=0; l<this.walls[h].length; l++){
				this.walls[h][l]=true;
			}
		}
	}

<<<<<<< HEAD
	/**
	 * Méthode permettant de réinitialiser l'éditeur.
	 * 
	 * @param map_height	Hauteur de l'éditeur.
	 * @param map_width		Largeur de l'éditeur.
	 * @param window_height Hauteur de la fenêtre.
	 * @param window_width  Largeur de la fenêtre.
	 * @param gap_X			Position horizontale.
	 * @param gap_Y			Position verticale.
	 */
	public void resetDrawing(int map_height, int map_width, double window_height, double window_width, int gap_X, int gap_Y) {
=======
	public void resetDrawing(int map_height, int map_width, DisplayValues display) {
>>>>>>> master
		this.walls = new boolean[map_height][map_width];
		this.resetWalls();
		this.draw(map_height,map_width, display);
	}

<<<<<<< HEAD
	/**
	 * Méthode permettant de définir une image pour la cellule selon si c'est un mur ou un sol.
	 * 
	 * @param cell Cellule du labyrinthe.
	 */
	public void modify(Cell cell) {
=======
	public void modify(Cell cell, boolean withImages) {
>>>>>>> master
		if(this.walls[cell.getRow()][cell.getCol()]) {
			this.walls[cell.getRow()][cell.getCol()]=false;
			
		}else {
			this.walls[cell.getRow()][cell.getCol()]=true;
		}
		this.setCellDisplay(withImages, !this.walls[cell.getRow()][cell.getCol()], cell);
	}
	
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