package fr.univlille.info.J2.main.management;

import java.io.File;

import fr.univlille.info.J2.main.management.cells.Cell;
import fr.univlille.info.J2.main.utils.menuConception.ImageLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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

	boolean[][] walls;

	int editor_height;
	int editor_width;

	File map_import;
	
	String theme;
	Color colorOfFloors;
	Color colorOfWalls;
	Image imgFloors;
	Image imgWalls;
	boolean isWithImages;

	public MazeEditor(int map_height, int map_width, double window_height, double window_width, int gap_X, int gap_Y, String theme, boolean isWithImages){
		this.editor_height=map_height;
		this.editor_width=map_width;
		this.group_map = new Group();
		this.group_img = new Group();
		this.group = new Group();
		this.theme=theme;
		this.isWithImages=isWithImages;
		this.resetDrawing(map_height, map_width, window_height, window_width, gap_X, gap_Y);
		this.group.getChildren().addAll(group_img,group_map);
	}

	public void draw(int map_height, int map_width, double window_height, double window_width, int gap_X, int gap_Y) {
		//Adaptation du zoom
		double height = (window_height / (map_height*2.7));
		double width = (window_width / (map_width*1.2));
		this.zoom=(int) Math.min(height, width);

		this.group_map.getChildren().clear();
		this.group_img.getChildren().clear();

		for(int h=0; h<this.walls.length; h++) {
			for(int l=0; l<this.walls[h].length; l++) {
				Cell cell = new Cell(l, h, this.zoom, Color.TRANSPARENT, gap_X, gap_Y);
				if(!this.walls[h][l]) {
					if(isWithImages) {
						cell.setImage(this.imgWalls);
						cell.setFill(Color.TRANSPARENT);
					}else {
						cell.setFill(this.colorOfWalls);
					}
				}else {
					if(isWithImages) {
						cell.setImage(this.imgFloors);
						cell.setFill(Color.TRANSPARENT);
					}else {
						cell.setFill(this.colorOfFloors);
					}
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

	public void resetWalls() {
		for(int h=0; h<this.walls.length; h++) {
			for(int l=0; l<this.walls[h].length; l++){
				this.walls[h][l]=true;
			}
		}
	}

	public void resetDrawing(int map_height, int map_width, double window_height, double window_width, int gap_X, int gap_Y) {
		this.walls = new boolean[map_height][map_width];
		this.resetWalls();
		this.draw(map_height, map_width, window_height, window_width, gap_X, gap_Y);
	}

	public void modify(Cell cell) {
		if(this.walls[cell.getRow()][cell.getCol()]) {
			this.walls[cell.getRow()][cell.getCol()]=false;
			if(isWithImages) {
				cell.setImage(this.imgWalls);
				cell.setFill(Color.TRANSPARENT);
			}else {
				cell.setFill(this.colorOfWalls);
			}
		}else {
			this.walls[cell.getRow()][cell.getCol()]=true;
			if(isWithImages) {
				cell.setImage(this.imgFloors);
				cell.setFill(Color.TRANSPARENT);
			}else {
				cell.setFill(this.colorOfFloors);
			}
		}
	}
	
	/**
     * Applique un thème spécifique au jeu en modifiant les couleurs d'affichage.
     *
	 * @param theme Le nom du thème à appliquer (parmi "Cave", "Forest", "Ocean").
	 */
	public void applyTheme(String theme) {
		if(theme.equals("Dungeon")) {
			this.colorOfFloors=Color.LIGHTGREY;
			this.colorOfWalls=Color.DARKGREY;
			imgFloors=null;
			imgWalls=null;
		}else if(theme.equals("Cave")) {
			this.colorOfFloors=Color.LIGHTGRAY;
			this.colorOfWalls=Color.DARKGRAY;
		}else if(theme.equals("Forest")) {
			this.colorOfFloors=Color.LIGHTGREEN;
			this.colorOfWalls=Color.FORESTGREEN;
		}else if(theme.equals("Ocean")) {
			this.colorOfFloors=Color.AQUAMARINE;
			this.colorOfWalls=Color.SEAGREEN;
		}
	}
}