package main.maze;


/*import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;*/

import javafx.scene.Group;
import main.maze.cells.*;
//import main.maze.cells.ICellEvent.CellInfo; //Inutilisé
import main.strategy.hunter.*;
import main.strategy.monster.*;
import main.utils.Observer;
import main.utils.Subject;
import main.utils.Utils;

public class Maze implements Observer{
	
	protected boolean[][] walls;
	protected int[][] traces;
	protected Coordinate coord_exit;
	protected Coordinate coord_monster;
	protected Coordinate coord_hunter;
	protected Monster monster;
	protected Hunter hunter;
	
	public int turn=0;
	public boolean itIsMonsterTurn;
	public boolean isGameOver;
	
	public Maze() {
		
		this.walls=this.generateBasicMap();
		
		this.traces = this.initTraces();
		this.initMonsterExitHunter();
		this.itIsMonsterTurn=true;
	}
	public Maze(int probability, int height, int width) {
		
		this.walls=this.generateWalls(probability, height, width);
			
		this.traces = this.initTraces();
		this.initMonsterExitHunter();
		this.itIsMonsterTurn=true;
		this.itIsMonsterTurn=false;
	}
	public int[][] initTraces(){
		int[][] traces = new int[this.walls.length][this.walls[0].length];
		for(int h=0; h<this.walls.length;h++) {
			for(int l=0; l<this.walls[h].length;l++) {
				if(this.walls[h][l]) {
					traces[h][l]=0;
				}else {
					traces[h][l]=-1;
				}	
			}
		}
		return traces;
	}
	
	public void printTraces() {
		for(int h=0; h<this.walls.length;h++) {
			for(int l=0; l<this.walls[h].length;l++) {
				System.out.print(" "+this.traces[h][l]+" ");
			}
			System.out.println();
		}
	}

	public boolean[][] generateBasicMap() {
		return new boolean[][] {
			{false,false,false,true,true,false,true,false,true,false}, 	// X X X . . X . X . X 
			{false,true,true,true,true,false,true,false,true,true},		// X . . . . X . X . .
			{true,true,true,true,false,false,true,true,false,true},		// . . . . X X . . X . 
			{true,true,false,true,true,false,true,true,true,true},		// . . X . . X . . . . 
			{false,true,true,false,true,false,true,true,true,false},	// X . . X . X . . . X 
			{false,true,true,true,false,true,true,true,true,false},		// X . . . X . . . . X 
			{false,true,true,true,false,true,true,true,true,true},		// X . . . X . . . . . 
			{true,true,true,true,true,true,true,false,true,false},		// . . . . . . . X . X 
			{true,true,false,true,true,false,true,false,false,false},	// . . X . . X . X X X 
			{false,true,true,true,false,false,true,true,true,false}};	// X . . . X X . . . X 
	}
	
	public boolean[][] generateWalls(int probability, int height, int width) {
		boolean[][] maze = new boolean[height][width];
		for(int h=1; h<maze.length; h+=2) {
			
			for(int l=0; l<maze[h].length; l++) {
				maze[h-1][l]=true;
				if(probability>Utils.random.nextInt(100)) {
					maze[h][l]=true;
				}else {
					maze[h][l]=false;
				}
					
			}
		}
		return maze;
	}
	
	public int numberOfWalls(ICoordinate c){
		int cpt=0;
		for(int y=c.getRow()-1; y<c.getRow()+2; y++) {
			for(int x=c.getCol()-1; x<c.getCol()+2; x++) {
				try {
					if(this.walls[y][x]==false) {
						cpt++;
					}
				}catch(Exception e) {
					cpt++; //Signifie que l'on est sur le bord de la map, et donc c'est forcément un mur.
				}
			}
		}
		return cpt;
	}
	
	/*public void declutterPath() {
		for(int h=0; h<this.walls.length; h++) {
			for(int l=0; l<this.walls[h].length; l++) {
				if(this.numberOfWalls(new Coordinate(h,l)>4));
			}
		}
	}*/
	
	public void initMonsterExitHunter() {
		this.coord_exit=new Coordinate();
		this.coord_monster=new Coordinate();
		this.coord_hunter=new Coordinate();
		this.coord_monster.setCoordinate(0,Utils.random.nextInt(this.walls[0].length));
		this.coord_exit.setCoordinate(this.walls.length-1, Utils.random.nextInt(this.walls[this.walls.length-1].length));
		this.coord_hunter.setCoordinate(0,0);
		this.setWall(coord_monster);
		this.setWall(coord_exit);
		this.monster = new Monster(this.walls,this.coord_monster,this.coord_exit,this.coord_hunter);
		this.monster.attach(this);
		this.hunter = new Hunter(this.walls.length,this.walls[0].length,this.coord_hunter);
		this.hunter.attach(this);
		this.monster.move(coord_monster);
		
	}
	
	public void setWall(ICoordinate c) {
		this.walls[c.getRow()][c.getCol()]=true;
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
		/*Color paint;
		CellInfo state;
		Rectangle s = Utils.makeRectangle(10,10, size,size, Color.TRANSPARENT);
		s.setStroke(Color.RED);
		root.getChildren().add(s);
		for(int h=0; h<this.hauteur; h++) {
			for(int l=0; l<this.largeur; l++) {
				state = this.getCellInfo(h, l);
				paint=this.getColor(state);
				Rectangle r = Utils.makeRectangle(l*size, h*size, size,size, paint);
				r.setOnMouseClicked(e->{
					int y = (int)(r.getY()/size);
					int x = (int)(r.getX()/size);
					if(this.walls[y][x]) {
						if(Math.abs(coord_monster.getRow()-y)<2 && Math.abs(coord_monster.getCol()-x)<2) {
							s.setY(y*size);
							s.setX(x*size);
							s.toFront();
							System.out.println("("+y+","+x+") est sélectionné.");
						}else {
							System.out.println("Le monstre est à ("+this.coord_monster.getRow()+","+this.coord_monster.getCol()+"), soit ("+(this.coord_monster.getRow()-y)+","+(this.coord_monster.getCol()-x)+") de distance.");
							System.out.println("("+y+","+x+") n'est pas une case adjacente !");
						}
							
					}else {
						System.out.println("("+y+","+x+") est un mur !");
					}
				});
				root.getChildren().add(r);
			}
		}*/
		return root;
	}
	
	//Inutilisé
	/*
	private CellInfo getCellInfo(int y, int x) {
		if(this.coord_monster.getRow()==y && this.coord_monster.getCol()==x) {
			return CellInfo.MONSTER;
		}else if(this.coord_hunter.getRow()==y && this.coord_hunter.getCol()==x) {
			return CellInfo.HUNTER;
		}else if(this.coord_exit.getRow()==y && this.coord_exit.getCol()==x) {
			return CellInfo.EXIT;
		}else if(this.walls[y][x]) {
			return CellInfo.EMPTY;
		}else {
			return CellInfo.WALL;
		}
	}*/
	
	protected boolean isWall(Coordinate c) {
		return this.walls[c.getRow()][c.getCol()];
		
	}
	
	@Override
	public void update(Subject s) {
		//Permet d'update les vues sans toucher au labyrinthe.
		
	}
		
		
		
	@Override
	public void update(Subject s, Object o) {
		//Permet les déplacements en updatant les vues et le labyrinthe.
		if(s.getClass()==Monster.class) {
			this.turn=this.turn+1;
			this.coord_monster=(Coordinate)o;
			this.traces[coord_monster.getRow()][coord_monster.getCol()]=this.turn;
			if(this.coord_monster.equals(this.coord_exit)) {
				System.out.println("Monstre - Vous avez atteint la sortie ! Bien joué !");
				this.isGameOver=true;
			}
			this.itIsMonsterTurn=false;
			this.hunter.setMonsterTurn(itIsMonsterTurn);
			
			
		}else if(s.getClass()==Hunter.class) {
			Coordinate c = (Coordinate) o;
			this.coord_hunter=c;
			this.monster.actualizeShot(c);
			if(this.coord_hunter.equals(this.coord_monster)) {
				System.out.println("Chasseur - Vous avez tiré sur le monste ! Bien joué !");
				this.isGameOver=true;
			}
			this.hunter.actualizeTraces(coord_hunter, this.traces[coord_hunter.getRow()][coord_hunter.getCol()]);
			this.itIsMonsterTurn=true;
		}
		
	}
	
	
 }
