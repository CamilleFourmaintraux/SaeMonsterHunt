package main.maze;


/*import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;*/

import javafx.scene.Group;
import main.maze.cells.*;
import main.maze.cells.ICellEvent.CellInfo; //Inutilisé
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
	
	public Maze() {
		this.walls=this.generateBasicMap();
		this.traces = this.initTraces();
		this.initMonsterExitHunter();
		
		this.itIsMonsterTurn=true;
	}
	public Maze(int probability, int height, int width, int size) {
			this.walls=this.generateWalls(probability, height, width);
			this.traces = this.initTraces();
			this.initMonsterExitHunter();
			this.itIsMonsterTurn=true;
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
	public void initMonsterExitHunter() {
		this.coord_exit=new Coordinate();
		this.coord_monster=new Coordinate();
		this.coord_hunter=new Coordinate();
		this.coord_exit.setCoordinate(4, 4);
		this.coord_hunter.setCoordinate(-100, -100);//TODO On initie hunter en dehors de l'écran pour ne pas faire apparaitre de tir, fonctionne pour le moment car le hunter ne decouvre pas encore la map avec les tirs, à faire attention lors du développement.
		this.monster = new Monster(this.walls,this.coord_monster,this.coord_exit,this.coord_hunter);
		this.monster.attach(this);
		this.hunter = new Hunter(this.walls.length,this.walls[0].length,this.coord_hunter);
		this.hunter.attach(this);
		this.coord_monster.setCoordinate(0, 8);
		this.monster.move(coord_monster);
		
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
	}
	
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
		if(s.getClass()==Monster.class) {//&&this.itIsMonsterTurn
			this.turn=this.turn+1;
			//System.out.println("TEST:Monster"+this.turn);
			this.coord_monster=(Coordinate)o;
			this.traces[coord_monster.getRow()][coord_monster.getCol()]=this.turn;
			//System.out.println("["+coord_monster.getRow()+"]["+coord_monster.getCol()+"]="+this.turn+"("+this.traces[coord_monster.getRow()][coord_monster.getCol()]+")");
			//this.printTraces();
			this.itIsMonsterTurn=false;
			this.hunter.setMonsterTurn(itIsMonsterTurn);
			
		}else if(s.getClass()==Hunter.class) {
			//System.out.println("TEST:Hunter");
			Coordinate c = (Coordinate) o;
			this.coord_hunter=c;
			this.monster.actualizeShot(c);
			if(this.hunter.isMonster(this.coord_monster)) {
				System.out.println("Chasseur-Vous avez tiré sur le monste ! Bien joué !");
			}
			this.hunter.actualizeTraces(coord_hunter, this.traces[coord_hunter.getRow()][coord_hunter.getCol()]);
			this.itIsMonsterTurn=true;
		}
		
	}
	
	
 }
