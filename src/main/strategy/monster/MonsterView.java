 package main.strategy.monster;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.maze.cells.Coordinate;
import main.maze.cells.ICoordinate;
import main.maze.cells.Cell;
import main.maze.cells.CellWithText;
//import main.maze.cells.ICellEvent.CellInfo; //Inutilisé
import main.utils.Observer;
import main.utils.Subject;

public class MonsterView extends Stage implements Observer{
	//Affichage
	public int window_height = 500; //500 par défault
	public int window_width = 500; //500 par défault
	public int gap_X=0; //0 par défault
	public int gap_Y=0; //0 par défault
	public int zoom = 50 ; //50 par défault
	public Color colorOfWalls=Color.DARKGRAY;
	public Color colorOfFloors=Color.LIGHTGRAY;
	/*
	final int gap_X=this.window_width/10;
	final int gap_y=this.window_height/10;
	*/
	
	
	//Subject
	Monster monster;
	
	//Sprites (Rectangles pour le moment)
	CellWithText sprite_monster;
	CellWithText  sprite_shot;
	CellWithText  sprite_exit;
	CellWithText  selection;
	
	Group group_stage;
	Group group_sprite;
	Group group_map;
	
	public MonsterView(Monster monster) {
		this.monster=monster;
		this.monster.attach(this);
		this.initiateSprites();
	}
	
	
	
	public MonsterView(int window_height, int window_width, int gap_X, int gap_Y, int zoom, Color colorOfWalls,
			Color colorOfFloors, Monster monster) {
		this.window_height = window_height;
		this.window_width = window_width;
		this.gap_X = gap_X;
		this.gap_Y = gap_Y;
		this.zoom = zoom;
		this.colorOfWalls = colorOfWalls;
		this.colorOfFloors = colorOfFloors;
		this.monster = monster;
		this.monster.attach(this);
		this.group_sprite=new Group();
		this.initiateSprites();
		this.group_map=this.draw();
		this.group_stage=new Group();
		this.group_stage.getChildren().add(this.group_map);
		this.group_stage.getChildren().add(this.group_sprite);
		Scene scene = new Scene(this.group_stage,this.window_height,this.window_width);
		this.setScene(scene);
		this.setTitle("MONSTERHUNTER - MonsterView");
		
	}



	@Override
	public void update(Subject s) {
		this.sprite_monster.setX(this.calculDrawX(this.monster.getCol()));
		this.sprite_monster.setY(this.calculDrawY(this.monster.getRow()));
		this.sprite_monster.setCoord(this.monster.coord);
		this.sprite_shot.setX(this.calculDrawX(this.monster.coord_hunted.getCol()));
		this.sprite_shot.setY(this.calculDrawY(this.monster.coord_hunted.getRow()));
		this.sprite_shot.setCoord(this.monster.coord_hunted);
		this.sprite_shot.setVisible(true);
	}

	@Override
	public void update(Subject s, Object o) {
		this.sprite_monster.setX(this.calculDrawX(this.monster.getCol()));
		this.sprite_monster.setY(this.calculDrawY(this.monster.getRow()));
		this.sprite_monster.setCoord(this.monster.coord);
		this.sprite_monster.setVisible(true);
		this.sprite_shot.setX(this.calculDrawX(this.monster.coord_hunted.getCol()));
		this.sprite_shot.setY(this.calculDrawY(this.monster.coord_hunted.getRow()));
		this.sprite_shot.setCoord(this.monster.coord_hunted);
		this.sprite_shot.setVisible(true);
	}
	
	
	
	private void initiateSprites() {
		//Initialisation du sprite du monstre
		this.sprite_monster=new CellWithText(this.monster.coord, this.zoom, Color.MAROON, this.gap_X, this.gap_Y, "Monster");
		this.sprite_monster.setOnMouseClicked(e->{
			this.select(e, this.sprite_monster.getCoord());
		});
		this.sprite_monster.setOnKeyPressed(e->{
			if(e.getCode().equals(KeyCode.UP)) {
				System.out.println("UP");
			}
			else if(e.getCode().equals(KeyCode.DOWN)) {
				System.out.println("DOWN");
			}
			else if(e.getCode().equals(KeyCode.RIGHT)) {
				System.out.println("RIGHT");
			}
			else if(e.getCode().equals(KeyCode.LEFT)) {
				System.out.println("LEFT");
			}
		});
		
		//initialisation du sprite du dernier tir du chasseur
		this.sprite_shot=new CellWithText(this.monster.coord_hunted, this.zoom, Color.TRANSPARENT, Color.YELLOW, 3, this.gap_X, this.gap_Y, "Hunter");
		this.sprite_shot.setOnMouseClicked(e->{
			this.select(e, this.sprite_shot.getCoord());
		});
		this.sprite_shot.setVisible(false);
		
		//Initialisation du sprite de la sortie
		this.sprite_exit=new CellWithText(this.monster.coord_exit, this.zoom, Color.GREEN, this.gap_X, this.gap_Y, "Exit");
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
		for(int h=0; h<this.monster.walls.length; h++) {
			for(int l=0; l<this.monster.walls[h].length; l++) {
				Cell r = new Cell(l, h, this.zoom, this.colorOfFloors, this.gap_X, this.gap_Y);
				//Codage des rectangles
				if(this.monster.walls[h][l]) {
					//Code à calculer si c'est un sol : obsolète car la manière de faire les rectangles à changer
				}else {
					r.setFill(this.colorOfWalls);
					r.setStroke(this.colorOfWalls);
					r.setStrokeWidth(1);
				}
				r.setOnMouseClicked(e->{
					this.select(e, r.getCoord());
				});
				/*r.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent ke) {if(ke.getCode()==KeyCode.ENTER) {}}
	        	});*/
				
				root.getChildren().add(r);
			}
		}
		return root;
	}
	
	public void select(MouseEvent e, ICoordinate c) {
		int row=c.getRow();
		int col=c.getCol();
		//System.out.println("("+row+","+col+") est sélectionné.");
		this.selection.setY(this.calculDrawY(row));
		this.selection.setX(this.calculDrawX(col));
		this.selection.setCoord(c); //IMPORTANT, on met à jour la nouvelle coord
		if(this.monster.walls[row][col]) {
			this.selection.setVisible(true);
			this.selection.toFront();
			int distanceX= Math.abs(this.monster.coord.getCol()-col);
			int distanceY=Math.abs(this.monster.coord.getRow()-row);
			if(this.isMonsterTurn() && ((distanceX==1 && distanceY==0) || (distanceX==0 && distanceY==1) || (distanceX==0 && distanceY==0))) { //(distanceX==1 && distanceY==1) //deplacement en diagonale
				this.validSelection();
				if(e.isShiftDown()) {
					monster.move(new Coordinate(row,col));
				}
			}else{
				//System.out.println("Le monstre est à ("+this.monster.coord.getRow()+","+this.monster.coord.getCol()+"), soit ("+(this.monster.coord.getRow()-row)+","+(this.monster.coord.getCol()-col)+") de distance.");
				//System.out.println("("+row+","+col+") n'est pas une case adjacente !");
				//System.out.println("(x:"+distanceX+", y:"+distanceY+") sont superieur à 1 ou égaux.");
				this.invalidSelection();
			}	
		}else {
			//System.out.println("("+row+","+col+") est un mur !");
			this.selection.setVisible(false);
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
	
	
	public boolean isMonsterTurn() {
		return this.monster.monsterTurn;
	}
	//Inutilisé
	/*private CellInfo getCellInfo(int y, int x) {
		if(this.monster.coord.getRow()==y && this.monster.coord.getCol()==x) {
			return CellInfo.MONSTER;
		}else if(this.monster.coord_hunted.getRow()==y && this.monster.coord_hunted.getCol()==x) {
			return CellInfo.HUNTER;
		}else if(this.monster.coord_exit.getRow()==y && this.monster.coord_exit.getCol()==x) {
			return CellInfo.EXIT;
		}else if(this.monster.walls[y][x]) {
			return CellInfo.EMPTY;
		}else {
			return CellInfo.WALL;
		}
	}*/

	
	
}
