package fr.univlille.info.J2.main.management;

import fr.univlille.info.J2.main.management.cells.Cell;
import fr.univlille.info.J2.main.utils.Utils;
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
	
	boolean[][] walls;
	
	int editor_height;
	int editor_width;
	
	public MazeEditor(int map_height, int map_width, double window_height, double window_width, int gap_X, int gap_Y){
		this.editor_height=map_height;
		this.editor_width=map_width;
		this.group_map = new Group();
		this.group_img = new Group();
		this.group = new Group();
		this.draw(map_height, map_width, window_height, window_width, gap_X, gap_Y);
		this.group.getChildren().addAll(group_img,group_map);
	}
	
	public void draw(int map_height, int map_width, double window_height, double window_width, int gap_X, int gap_Y) {
		//Adaptation du zoom
		double height = (window_height / (map_height*2.7));
		double width = (window_width / (map_width*1.2));
		this.zoom=(int) Math.min(height, width);
		/*if(window_height>window_width) {
			this.zoom=(int)((window_width/((map_height*((window_height/window_width)*2))+map_width)/2));
		}else {
			this.zoom=(int)((window_height/((map_height*((window_height/window_width)*2))+map_width)/2));
		}*/
		this.group_map.getChildren().clear();
		this.group_img.getChildren().clear();
		this.walls = new boolean[map_height][map_width];
		this.resetWalls();
		for(int h=0; h<this.walls.length; h++) {
			for(int l=0; l<this.walls[h].length; l++) {
				Cell cell = new Cell(l, h, this.zoom, Color.TRANSPARENT, gap_X, gap_Y, Utils.floor_dungeon);
				cell.setStroke(Color.VIOLET);
				cell.setOnMouseEntered(ev->{
					cell.setStrokeWidth(1);
				});
				cell.setOnMouseExited(e->{
					cell.setStrokeWidth(0);
				});
				cell.setOnMouseClicked(e->{
					if(this.walls[cell.getRow()][cell.getCol()]) {
						this.walls[cell.getRow()][cell.getCol()]=false;
						cell.setImage(Utils.wall_dungeon);
					}else {
						this.walls[cell.getRow()][cell.getCol()]=true;
						cell.setImage(Utils.floor_dungeon);
					}
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
	
}