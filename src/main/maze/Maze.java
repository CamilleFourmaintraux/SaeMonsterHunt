package main.maze;


/*import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;*/
import javafx.scene.paint.Color;
import main.maze.cells.*;
//import main.maze.cells.ICellEvent.CellInfo; //Inutilisé
import main.strategy.hunter.*;
import main.strategy.monster.*;
import main.utils.Subject;
import main.utils.Utils;

public class Maze extends Subject{
	
	protected boolean[][] walls;
	protected int[][] traces;//TODO message si le monstre passe sur une case déjà découverte
	protected Exit exit;
	protected Monster monster;
	protected Hunter hunter;
	
	public int turn=0;
	public boolean isMonsterTurn;
	public boolean isGameOver;
	
	public Maze() {
		this(Maze.generateBasicMap());
		
	}
	
	public Maze(boolean[][] maze) {
		this.walls=maze;
		this.initMonsterExitHunter();
		this.traces = this.initTraces();
		this.isMonsterTurn=false;
	}
	
	public Maze(int probability, int height, int width) {
		this(Maze.generateRandomMap(probability, height, width));
		
	}
	
	public int[][] initTraces(){
		int[][] traces = new int[this.walls.length][this.walls[0].length];
		for(int h=0; h<this.walls.length;h++) {
			for(int l=0; l<this.walls[h].length;l++) {
				if(this.walls[h][l]) {
					traces[h][l]=0;
				}else {
					traces[h][l]=-1;//Mur
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

	public static boolean[][] generateBasicMap() {
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
	
	public static boolean[][] generateRandomMap(int probability, int height, int width) {
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
	
	//Inutilisé pour le moment
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
	
	public void initMonsterExitHunter() {
		this.exit = new Exit(new Coordinate(this.walls.length-1, Utils.random.nextInt(this.walls[this.walls.length-1].length)));
		this.setNotWall(this.exit.getCoord(),true);
		this.monster = new Monster(this.walls,new Coordinate(0,Utils.random.nextInt(this.walls[0].length)));
		this.setNotWall(this.monster.getCoord(),true);
		this.hunter = new Hunter(this.walls.length,this.walls[0].length,new Coordinate(0,0));
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
	
	public void setNotWall(ICoordinate c, boolean willBeWall) {
		this.walls[c.getRow()][c.getCol()]=willBeWall;
	}
	
	public boolean move(ICoordinate c) { //Fais le déplcament du monstre, retoure true si le déplacement à été possible.:
		if(this.canMonsterMoveAt(c)) {
			if(this.hunter.getTrace(this.monster.getCoord())!=-2) {
				System.out.println("ATTENTION - Le mur s'est déplacé dans l'une des parties explorées !");
			}
			this.monster.setCoord(c);
			this.isMonsterTurn=false;
			this.setTrace(c, turn);
			/*if(this.hunter.getTrace(c)!=-2) {
				System.out.println("ATTENTION - Le mur s'est déplacé dans l'une des parties explorées !");
			}*/
			this.notifyObservers(c);
			return true;
		}
		return false;
	}
	
	public boolean shoot(ICoordinate c) { //Fais le tir du chasseur, devrait toujours renvoyer true logiquement, peut etre changer le type de retour.
		if(!this.isMonsterTurn) {
			this.hunter.setCoord(c);
			this.hunter.setTrace(c, this.getTrace(c));
			this.isMonsterTurn=true;
			this.notifyObservers(c);
			return true;
		}
		return false;
	}
	
	public boolean canMonsterMoveAt(ICoordinate c) {
		if(this.isMonsterTurn && this.walls[c.getRow()][c.getCol()] && this.inReach(this.monster.getCoord(), c, 1)) {
			return true;
		}
		return false;
	}
	
	public int[] calculDistance(ICoordinate c1, ICoordinate c2) {
		int[] distances = new int[2];
		int distanceX= Math.abs(c1.getCol()-c2.getCol());
		int distanceY= Math.abs(c1.getRow()-c2.getRow());
		distances[0]=distanceX;
		distances[1]=distanceY;
		return distances;
	}
	
	public boolean inReach(ICoordinate c1, ICoordinate c2, int reach) {
		return (this.calculDistance(c1, c2)[0]<reach+1 && this.calculDistance(c1, c2)[1]<reach+1);
	}
	
	public void setTrace(ICoordinate c, int trace) {
		this.traces[c.getRow()][c.getCol()]=trace;
	}
	public int getTrace(ICoordinate c) {
		return this.traces[c.getRow()][c.getCol()];
	}
			
	/*@Override //Smelly code
	public void update(Subject s, Object o) {
		System.out.println(this.monster.getCoord());
		//Permet les déplacements en updatant les vues et le labyrinthe.
		/*if(s.getClass()==Monster.class) {
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
		
	}*/
	public void revealCell(CellWithText cwt, Color colorOfWalls, Color colorOfFloors) {
		System.out.println("TEST:"+this.hunter.traces[this.hunter.getRow()][this.hunter.getCol()]);
		cwt.setStroke(Color.TRANSPARENT);
		if(this.hunter.traces[this.hunter.getRow()][this.hunter.getCol()]==-1) {
			cwt.setFill(colorOfWalls);
		}else {
			cwt.setFill(colorOfFloors);
			int trace = this.hunter.traces[cwt.getRow()][cwt.getCol()];
			if(trace>0) {
				cwt.setText(""+trace);
			}
		}
	}
	
	
 }
