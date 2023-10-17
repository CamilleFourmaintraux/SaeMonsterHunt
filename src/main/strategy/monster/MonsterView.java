 package main.strategy.monster;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.maze.cells.Coordinate;
//import main.maze.cells.ICellEvent.CellInfo; //Inutilisé
import main.utils.Observer;
import main.utils.Subject;
import main.utils.Utils;

public class MonsterView extends Stage implements Observer{
	//Affichage
	protected int window_height = 500; //500 par défault
	protected int window_width = 500; //500 par défault
	protected int gap_X=0; //0 par défault
	protected int gap_y=0; //0 par défault
	protected int zoom = 50 ; //50 par défault
	protected Color colorOfWalls=Color.DARKGRAY;
	protected Color colorOfFloors=Color.LIGHTGRAY;
	/*
	final int gap_X=this.window_width/10;
	final int gap_y=this.window_height/10;
	*/
	
	
	//Subject
	Monster monster;
	
	//Sprites (Rectangles pour le moment)
	Rectangle sprite_monster;
	Rectangle sprite_shot;
	Rectangle sprite_exit;
	Rectangle selection;
	
	public MonsterView(Monster monster) {
		this.monster=monster;
		this.monster.attach(this);
		this.initiateSprites();
	}
	
	
	
	public MonsterView(int window_height, int window_width, int gap_X, int gap_y, int zoom, Color colorOfWalls,
			Color colorOfFloors, Monster monster) {
		this.window_height = window_height;
		this.window_width = window_width;
		this.gap_X = gap_X;
		this.gap_y = gap_y;
		this.zoom = zoom;
		this.colorOfWalls = colorOfWalls;
		this.colorOfFloors = colorOfFloors;
		this.monster = monster;
		this.monster.attach(this);
		this.initiateSprites();
	}



	@Override
	public void update(Subject s) {
		this.sprite_monster.setX(this.monster.coord.getCol()*this.zoom+this.gap_X);
		this.sprite_monster.setY(this.monster.coord.getRow()*this.zoom+this.gap_y);
		this.sprite_shot.setX(this.monster.coord_hunted.getCol()*this.zoom+this.gap_X);
		this.sprite_shot.setY(this.monster.coord_hunted.getRow()*this.zoom+this.gap_y);
	}

	@Override
	public void update(Subject s, Object o) {
		this.sprite_monster.setX(this.monster.coord.getCol()*this.zoom+this.gap_X);
		this.sprite_monster.setY(this.monster.coord.getRow()*this.zoom+this.gap_y);
		this.sprite_monster.setVisible(true);
		this.sprite_monster.toFront();
		this.sprite_shot.setX(this.monster.coord_hunted.getCol()*this.zoom+this.gap_X);
		this.sprite_shot.setY(this.monster.coord_hunted.getRow()*this.zoom+this.gap_y);
	}
	
	private void initiateSprites() {
		//Initialisation du sprite du monstre
		this.sprite_monster=Utils.makeRectangle(this.monster.coord.getCol()*this.zoom+this.gap_X,this.monster.coord.getRow()*this.zoom+this.gap_y, this.zoom,this.zoom, Color.MAROON);
		
		//initialisation du sprite du dernier tir du chasseur
		this.sprite_shot=Utils.makeRectangle(this.monster.coord_hunted.getCol()*this.zoom+this.gap_X,this.monster.coord_hunted.getRow()*this.zoom+this.gap_y, this.zoom,this.zoom, Color.TRANSPARENT);
		this.sprite_shot.setStroke(Color.YELLOW);
		this.sprite_shot.setStrokeWidth(5);
		this.sprite_shot.setOnMouseClicked(e->{
			int y = (int)((this.sprite_shot.getY()-this.gap_y)/this.zoom);
			int x = (int)((this.sprite_shot.getX()-this.gap_X)/this.zoom);
			this.select(e, x, y);
		});
		
		//Initialisation du sprite de la sortie
		this.sprite_exit=Utils.makeRectangle(this.monster.coord_exit.getCol()*this.zoom+this.gap_X,this.monster.coord_exit.getRow()*this.zoom+this.gap_y, this.zoom,this.zoom, Color.GREEN);
		this.sprite_exit.setOnMouseClicked(e->{
			int y = (int)((this.sprite_exit.getY()-this.gap_y)/this.zoom);
			int x = (int)((this.sprite_exit.getX()-this.gap_X)/this.zoom);
			this.select(e, x, y);
		});
		
		//initialisation du rectangle de sélection
		this.selection=Utils.makeRectangle(-1,-1, this.zoom,this.zoom, Color.TRANSPARENT);
		this.selection.setStroke(Color.RED);
		this.selection.setStrokeWidth(3);
		this.selection.setVisible(false);
		this.selection.setOnMouseClicked(e->{
			int y = (int)((selection.getY()-this.gap_y)/this.zoom);
			int x = (int)((selection.getX()-this.gap_X)/this.zoom);
			this.select(e, x,y);
			/*if(e.isShiftDown()) {
				monster.move(new Coordinate(((int)selection.getY()-this.gap_y)/this.zoom,((int)selection.getX()-this.gap_X)/this.zoom));
			}else {
				this.selection.toBack();
				//selection.setVisible(false);
			}*/
		});
		
	}
	
	public Scene draw() {
		Group root = new Group();
		root.getChildren().add(this.selection);
		for(int h=0; h<this.monster.walls.length; h++) {
			for(int l=0; l<this.monster.walls[h].length; l++) {
				Rectangle r = Utils.makeRectangle(l*this.zoom+this.gap_X, h*this.zoom+this.gap_y, this.zoom,this.zoom, this.colorOfFloors);
				
				//Codage des rectangles
				if(this.monster.walls[h][l]) {
					//Code à calculer si c'est un sol : obsolète car la manière de faire les rectangles à changer
				}else {
					r.setFill(this.colorOfWalls);
					r.setStroke(this.colorOfWalls);
					r.setStrokeWidth(1);
				}
				r.setOnMouseClicked(e->{
					int y = (int)((r.getY()-this.gap_y)/this.zoom);
					int x = (int)((r.getX()-this.gap_X)/this.zoom);
					this.select(e, x, y);
				});
				/*r.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent ke) {if(ke.getCode()==KeyCode.ENTER) {}}
	        	});*/
				
				root.getChildren().add(r);
			}
		}
		
		root.getChildren().add(sprite_monster);
		root.getChildren().add(sprite_exit);
		root.getChildren().add(sprite_shot);
		Scene scene = new Scene(root,this.window_width,this.window_height);
		return scene;
	}
	
	public void select(MouseEvent e, int x, int y) {
		this.selection.setY(y*this.zoom+this.gap_y);
		this.selection.setX(x*this.zoom+this.gap_X);
		if(this.monster.walls[y][x]) {
			this.selection.setVisible(true);
			this.selection.toFront();
			if(Math.abs(this.monster.coord.getRow()-y)<2 && Math.abs(this.monster.coord.getCol()-x)<2) {
				this.selection.setStroke(Color.RED);
				this.selection.setStrokeWidth(3);
				System.out.println("("+y+","+x+") est sélectionné.");
				if(e.isShiftDown()&&this.monster.monsterTurn) {
					monster.move(new Coordinate(y,x));
				}else {
					System.out.println("Monstre-C'est au tour du chasseur.");
				}
			}else {
				System.out.println("Le monstre est à ("+this.monster.coord.getRow()+","+this.monster.coord.getCol()+"), soit ("+(this.monster.coord.getRow()-y)+","+(this.monster.coord.getCol()-x)+") de distance.");
				System.out.println("("+y+","+x+") n'est pas une case adjacente !");
				this.selection.setStroke(Color.DARKRED);
				this.selection.setStrokeWidth(1);
			}	
		}else {
			System.out.println("("+y+","+x+") est un mur !");
			this.selection.toBack();
		}
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
