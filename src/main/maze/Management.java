package main.maze;

import main.strategy.hunter.HunterView;
import main.strategy.monster.MonsterView;
import javafx.scene.paint.Color;

public class Management {
	protected int window_height; //500 par défault
	protected int window_width; //500 par défault
	protected int gap_X; //0 par défault
	protected int gap_y; //0 par défault
	protected int zoom; //30 par défault
	protected Color colorOfWalls;
	protected Color colorOfFloors;
	
	protected Maze maze;
	public MonsterView mv;
	public HunterView hv;
	
	public Management(int window_height, int window_width, int gap_X, int gap_y, int zoom, Color colorOfWalls,
		Color colorOfFloors) {
		this.window_height = window_height;
		this.window_width = window_width;
		this.gap_X = gap_X;
		this.gap_y = gap_y;
		this.zoom = zoom;
		this.colorOfWalls = colorOfWalls;
		this.colorOfFloors = colorOfFloors;
		this.maze=new Maze();
		this.mv=new MonsterView(this.window_height,this.window_width,this.gap_X,this.gap_y,this.zoom,this.colorOfWalls,this.colorOfFloors,this.maze.monster);
		this.hv=new HunterView(this.window_height,this.window_width,this.gap_X,this.gap_y,this.zoom,this.colorOfWalls,this.colorOfFloors,this.maze.hunter);
	}
	
}
