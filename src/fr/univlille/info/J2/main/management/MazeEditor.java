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

	boolean[][] walls;

	int editor_height;
	int editor_width;

	File map_import;
	
	Theme theme;

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

	public void resetWalls() {
		for(int h=0; h<this.walls.length; h++) {
			for(int l=0; l<this.walls[h].length; l++){
				this.walls[h][l]=true;
			}
		}
	}

	public void resetDrawing(int map_height, int map_width, DisplayValues display) {
		this.walls = new boolean[map_height][map_width];
		this.resetWalls();
		this.draw(map_height,map_width, display);
	}

	public void modify(Cell cell, boolean withImages) {
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