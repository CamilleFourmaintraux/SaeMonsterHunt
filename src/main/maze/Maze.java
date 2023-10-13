package main.maze;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import main.utils.Utils;

public class Maze {
	public boolean[][] walls;
	Coordinate coord_exit;
	Coordinate coord_monster;
	Coordinate coord_hunter;
	public int hauteur;
	public int largeur;
	public int size=30;
	public int turn=0;
	
	
	public Maze() {
		this.walls=this.generateBasicMap();
		this.hauteur = walls.length;
		this.largeur = walls[0].length;
		this.coord_exit=new Coordinate();
		this.coord_monster=new Coordinate();
		this.coord_hunter=new Coordinate();
		this.initMonsterExit();
	}
	public Maze(int hauteur, int largeur,int probability) {
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.walls=this.generateWalls(probability);
		this.coord_exit=new Coordinate();
		this.coord_monster=new Coordinate();
		this.coord_hunter=new Coordinate();
		this.initMonsterExit();
	}

	public boolean[][] generateBasicMap() {
		return new boolean[][] {{false,false,false,true,true,false,true,false,true,false},{false,true,true,true,true,false,true,false,true,true},{true,true,true,true,false,false,true,true,false,true},{true,true,false,true,true,false,true,true,true,true},{false,true,true,false,true,false,true,true,true,false},{false,true,true,true,false,true,true,true,true,false},{false,true,true,true,false,true,true,true,true,true},{true,true,true,true,true,true,true,false,true,false},{true,true,false,true,true,false,true,false,false,false},{false,true,true,true,false,false,true,true,true,false}};
	}
	
	public boolean[][] generateWalls(int probability) {
		boolean[][] maze = new boolean[hauteur][largeur];
		for(int h=0; h<maze.length; h++) {
			for(int l=0; l<maze[h].length; l++) {
				if(probability>Utils.random.nextInt(100)) {
					maze[h][l]=false;
				}else {
					maze[h][l]=true;
				}
					
			}
		}
		return maze;
	}
	public void initMonsterExit() {
		this.coord_monster.setCoordinate(4, 4);
		this.coord_exit.setCoordinate(0, 3);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int h=0; h<this.walls.length; h++) {
			for(int l=0; l<this.walls[h].length; l++) {
				if(this.walls[h][l]) {
					sb.append('.');
				}else {
					sb.append('X');
				}
				sb.append(' ');
			}
			sb.append('\n');
			
		}
		return sb.toString();
		
	}
	
	public Group draw() {
		Group root = new Group();
		Color paint;
		for(int h=0; h<this.hauteur; h++) {
			for(int l=0; l<this.largeur; l++) {
				if(this.walls[h][l]) {
					paint=Color.AQUA;
				}else {
					paint=Color.BLUE;
				}
				Rectangle r = Utils.makeRectangle(h*size, l*size, size,size, paint);
				r.setOnMouseClicked(e->{
					System.out.println("("+r.getY()+","+r.getX()+")");
				});
				root.getChildren().add(r);
			}
		}
		return root;
	}
 }
