 package main.maze;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import main.maze.cells.Cell;
import main.maze.cells.CellWithText;
//import main.maze.cells.ICellEvent.CellInfo; //Inutilisé
import main.utils.Observer;
import main.utils.Subject;

public class MonsterView implements Observer{
	
	final Color MONSTER_COLOR = Color.CRIMSON;
	final Color EXIT_COLOR = Color.VIOLET;
	
	
	//Affichage
	public int window_height;
	public int window_width;
	public int gap_X;
	public int gap_Y;
	public int zoom;
	public Color colorOfWalls=Color.DARKGRAY;
	public Color colorOfFloors=Color.LIGHTGRAY;
	
	//Subject
	Maze maze;
	
	//Sprites (Rectangles pour le moment)
	CellWithText sprite_monster;
	CellWithText  sprite_shot;
	CellWithText  sprite_exit;
	CellWithText  selection;
	
	Group group_stage;
	Group group_sprite;
	Group group_map;
	
	Scene scene;
	
	public MonsterView(int window_height, int window_width, int gap_X, int gap_Y, int zoom, Color colorOfWalls, Color colorOfFloors, Maze maze) {
		this.window_height = window_height;
		this.window_width = window_width;
		this.gap_X = gap_X;
		this.gap_Y = gap_Y;
		this.zoom = zoom;
		this.colorOfWalls = colorOfWalls;
		this.colorOfFloors = colorOfFloors;
		this.maze = maze;
		this.maze.attach(this);
		this.group_sprite=new Group();
		this.initiateSprites();
		this.group_map=this.draw();
		this.group_stage=new Group();
		this.group_stage.getChildren().add(this.group_map);
		this.group_stage.getChildren().add(this.group_sprite);
		this.scene=new Scene(this.group_stage,this.window_height,this.window_width);
	}

	@Override
	public void update(Subject s) {
		this.sprite_monster.setX(this.calculDrawX(this.maze.monster.getCol()));
		this.sprite_monster.setY(this.calculDrawY(this.maze.monster.getRow()));
		this.sprite_monster.setCoord(this.maze.monster.coord);
		this.sprite_shot.setX(this.calculDrawX(this.maze.hunter.getCol()));
		this.sprite_shot.setY(this.calculDrawY(this.maze.hunter.getRow()));
		this.sprite_shot.setCoord(this.maze.hunter.getCoord());
		this.sprite_shot.setVisible(true);
	}

	@Override
	public void update(Subject s, Object o) {
		this.sprite_monster.setX(this.calculDrawX(this.maze.monster.getCol()));
		this.sprite_monster.setY(this.calculDrawY(this.maze.monster.getRow()));
		this.sprite_monster.setCoord(this.maze.monster.coord);
		this.sprite_monster.setVisible(true);
		this.sprite_shot.setX(this.calculDrawX(this.maze.hunter.getCol()));
		this.sprite_shot.setY(this.calculDrawY(this.maze.hunter.getRow()));
		this.sprite_shot.setCoord(this.maze.hunter.getCoord());
		this.sprite_shot.setVisible(true);
	}
	
	
	
	private void initiateSprites() {
		//Initialisation du sprite du monstre
		this.sprite_monster=new CellWithText(this.maze.monster.coord, this.zoom, this.MONSTER_COLOR, this.gap_X, this.gap_Y, "Monster");
		this.sprite_monster.setOnMouseClicked(e->{
			this.select(e, this.sprite_monster.getCoord());
		});
		
		//initialisation du sprite du dernier tir du chasseur
		this.sprite_shot=new CellWithText(this.maze.hunter.getCoord(), this.zoom, Color.TRANSPARENT, Color.YELLOW, 3, this.gap_X, this.gap_Y, "Hunter");
		this.sprite_shot.setOnMouseClicked(e->{
			this.select(e, this.sprite_shot.getCoord());
		});
		this.sprite_shot.setVisible(false);
		
		//Initialisation du sprite de la sortie
		this.sprite_exit=new CellWithText(this.maze.exit.getCoord(), this.zoom, this.EXIT_COLOR, this.gap_X, this.gap_Y, "Exit");
		this.sprite_exit.setOnMouseClicked(e->{
			this.sprite_exit.toBack();
			this.select(e, this.sprite_exit.getCoord());
		});
		
		//initialisation du rectangle de sélection
		this.selection=new CellWithText(0,0, this.zoom, Color.TRANSPARENT, Color.RED, 3, this.gap_X, this.gap_Y, "Selection");
		this.selection.setOnMouseClicked(e->{
			this.select(e, this.selection.getCoord());
		});
		this.selection.setVisible(false);
		this.group_sprite.getChildren().add(this.selection);
		this.group_sprite.getChildren().add(this.sprite_monster);
		this.group_sprite.getChildren().add(this.sprite_exit);
		this.group_sprite.getChildren().add(this.sprite_shot);
		
	}
	
	public Group draw() {
		Group root = new Group();
		for(int h=0; h<this.maze.walls.length; h++) {
			for(int l=0; l<this.maze.walls[h].length; l++) {
				Cell r = new Cell(l, h, this.zoom, this.colorOfFloors, this.gap_X, this.gap_Y);
				//Codage des rectangles
				if(this.maze.walls[h][l]) {
					//Code à calculer si c'est un sol : obsolète car la manière de faire les rectangles à changer
				}else {
					r.setFill(this.colorOfWalls);
					r.setStroke(this.colorOfWalls);
					r.setStrokeWidth(1);
				}
				r.setOnMouseClicked(e->{
					this.select(e, r.getCoord());
				});
				
				root.getChildren().add(r);
			}
		}
		return root;
	}
	
	public void select(MouseEvent e, ICoordinate c) {
		int row=c.getRow();
		int col=c.getCol();
		this.selection.setY(this.calculDrawY(row));
		this.selection.setX(this.calculDrawX(col));
		this.selection.setCoord(c);
		this.selection.setVisible(true);
		this.selection.toFront();
		if(this.maze.canMonsterMoveAt(c)) {
			this.validSelection();	
			if(e.isShiftDown()) {
				this.maze.move(c);
			}
		}else{
			this.invalidSelection();
			
		}
	}
	
	public void invalidSelection() {
		this.selection.setStroke(Color.DARKRED);
		this.selection.setStrokeWidth(1);
	}
	
	public void validSelection() {
		this.selection.setStroke(Color.RED);
		this.selection.setStrokeWidth(3);
	}



	public int getWindow_height() {
		return window_height;
	}



	public void setWindow_height(int window_height) {
		this.window_height = window_height;
	}



	public int getWindow_width() {
		return window_width;
	}



	public void setWindow_width(int window_width) {
		this.window_width = window_width;
	}



	public int getGap_X() {
		return gap_X;
	}



	public void setGap_X(int gap_X) {
		this.gap_X = gap_X;
	}



	public int getGap_y() {
		return gap_Y;
	}



	public void setGap_y(int gap_Y) {
		this.gap_Y = gap_Y;
	}



	public int getZoom() {
		return zoom;
	}



	public void setZoom(int zoom) {
		this.zoom = zoom;
	}



	public Color getColorOfWalls() {
		return colorOfWalls;
	}



	public void setColorOfWalls(Color colorOfWalls) {
		this.colorOfWalls = colorOfWalls;
	}



	public Color getColorOfFloors() {
		return colorOfFloors;
	}



	public void setColorOfFloors(Color colorOfFloors) {
		this.colorOfFloors = colorOfFloors;
	}
	
	public int calculDrawX(int x) {
		return x*zoom+gap_X;
	}
	
	public int calculDrawY(int y) {
		return y*zoom+gap_Y;
	}
}
