package fr.univlille.info.J2.main.management;

import java.io.File;

import fr.univlille.info.J2.main.application.cells.Cell;
import fr.univlille.info.J2.main.utils.menuConception.ImageLoader;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public class MazeEditor {

	/**
	 * niveau de zoom
	 */
	int zoom;

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
		this.editor_height=map_height;
		this.editor_width=map_width;
		this.group_map = new Group();
		this.group_img = new Group();
		this.group = new Group();
		this.resetDrawing(map_height, map_width, window_height, window_width, gap_X, gap_Y);
		this.group.getChildren().addAll(group_img,group_map);
	}

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
		//Adaptation du zoom
		double height = (window_height / (map_height*2.7));
		double width = (window_width / (map_width*1.2));
		this.zoom=(int) Math.min(height, width);

		this.group_map.getChildren().clear();
		this.group_img.getChildren().clear();

		for(int h=0; h<this.walls.length; h++) {
			for(int l=0; l<this.walls[h].length; l++) {
				Cell cell = new Cell(l, h, this.zoom, Color.TRANSPARENT, gap_X, gap_Y, ImageLoader.floor_dungeon);
				if(!this.walls[h][l]) {
					cell.setImage(ImageLoader.wall_dungeon);
				}
				cell.setStroke(Color.VIOLET);
				cell.setOnMouseEntered(e->{
					cell.setStrokeWidth(1);
					if(e.isShiftDown()) {
						modify(cell);
					}
				});
				cell.setOnMouseExited(e->{
					cell.setStrokeWidth(0);
				});
				cell.setOnMouseClicked(e->{
					modify(cell);
				});
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
		this.walls = new boolean[map_height][map_width];
		this.resetWalls();
		this.draw(map_height, map_width, window_height, window_width, gap_X, gap_Y);
	}

	/**
	 * Méthode permettant de définir une image pour la cellule selon si c'est un mur ou un sol.
	 * 
	 * @param cell Cellule du labyrinthe.
	 */
	public void modify(Cell cell) {
		if(this.walls[cell.getRow()][cell.getCol()]) {
			this.walls[cell.getRow()][cell.getCol()]=false;
			cell.setImage(ImageLoader.wall_dungeon);
		}else {
			this.walls[cell.getRow()][cell.getCol()]=true;
			cell.setImage(ImageLoader.floor_dungeon);
		}
	}
}