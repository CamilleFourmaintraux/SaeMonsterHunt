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


/**
 * Mod�le du jeu qui inclus la g�n�ration du labyrinthe, le d�placement du monstre, le tir du chasseur ainsi que de l'affichage 
 * telle que les traces du monstres au cours de la partie.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class Maze extends Subject{
	/**
	 * Tableau de boolean repr�sentant les murs plein ou non du labyrinthe (false=mur true=pas de mur).
	 */
	protected boolean[][] walls;
	/**
	 * Tableau d'entier stockant les num�ros de tours ou le monstre est d�j� pass�.
	 */
	protected int[][] traces;//TODO message ou alerte si le monstre passe sur une case déjà découverte
	/**
	 * La sortie (les coordonn�es) du labyrinthe.
	 */
	protected Exit exit;
	/**
	 * Le Monstre.
	 */
	protected Monster monster;
	/**
	 * Le Chasseur.
	 */
	protected Hunter hunter;
	/**
	 * Le num�ro du tour actuel.
	 */
	public int turn;
	/**
	 * Boolean qui permet de savoir si c'est le tour du monstre ou non.
	 */
	public boolean isMonsterTurn;
	/**
	 * Boolean utilis� dans les actions move du monstre & shoot du chasseur pour indique si la partie est fini.
	 * (i.e si le monstre/chasseur s'est �chapp�/fait tirer dessus).
	 */
	public boolean isGameOver;
	
	/**
	 * Constructeur vide, cr�er un labyrinthe Maze � partir d'une m�thode pr�d�fini ou g�n�r� al�atoirement 
	 * (generateBasicMap() & generateRandomMap()).
	 */
	public Maze() {
		this(Maze.generateBasicMap());
	}
	
	/**
	 * Cr�er un labyrinthe Maze � partir d'un labyrinthe existant en param�tre.
	 * @see Maze#Maze()
	 * @param maze
	 */
	public Maze(boolean[][] maze) {
		this.walls=maze;
		this.turn=1;
		this.initMonsterExitHunter();
		this.traces = this.initTraces();
		this.isMonsterTurn=true;
		this.move(this.monster.getCoord());
	}
	
	/**
	 * Cr�er un labyrinthe Maze personnalis� � partir de la largeur, la hauteur et le taux d'apparition de mur.
	 * @param probability le taux de chances que la case du labyrinthe soit un mur plein.
	 * @param height la hauteur du labyrinthe.
	 * @param width la largeur du labyrinthe.
	 */
	public Maze(int probability, int height, int width) {
		this(Maze.generateRandomMap(probability, height, width));
	}
	
	/**
	 * Initialisation du tableau des traces du monstre vu par le chasseur.
	 * @return un talbeau d'entier initialis� � z�ro et -1 l� ou il y a des murs.
	 */
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
	/**
	 * Affichage du tableau des traces du monstre vu par le chasseur.
	 */
	public void printTraces() {
		for(int h=0; h<this.walls.length;h++) {
			for(int l=0; l<this.walls[h].length;l++) {
				System.out.print(" "+this.traces[h][l]+" ");
			}
			System.out.println();
		}
	}

	/**
	 * M�thode de g�n�ration pr�d�fini du labyrinthe.
	 * @return un tableau de boolean qui repr�sente le labyrithe avec ses murs.
	 */
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
	
	/**
	 * M�thode de g�n�ration personnalis� du labyrinthe avec sa taille voulu ainsi que le taux d'apparition des murs.
	 * @param probability le taux de chances que la case du labyrinthe soit un mur plein.
	 * @param height la hauteur du labyrinthe.
	 * @param width la largeur du labyrinthe.
	 * @return un tableau de boolean repr�sentant un labyrinthe.
	 */
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
	
	/**
	 * Calcule le nombre de mur du labyrinthe pr�sent autour de la coordonn�e.
	 * @param c une coordonn�e du labyrinthe.
	 * @return un entier repr�sentant le nombre de mur autour de cette coordonn�e.
	 */
	//Inutilisé pour le moment
	public int numberOfWallsAround(ICoordinate c){
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
	
	/**
	 * Initialise les coordonn�es de la sortie du labyrinthe.
	 */
	public void initMonsterExitHunter() {
		this.exit = new Exit(new Coordinate(this.walls.length-1, Utils.random.nextInt(this.walls[this.walls.length-1].length)));
		this.setFloor(this.exit.getCoord(),true);
		this.monster = new Monster(this.walls,new Coordinate(0,Utils.random.nextInt(this.walls[0].length)));
		this.setFloor(this.monster.getCoord(),true);
		this.hunter = new Hunter(this.walls.length,this.walls[0].length,new Coordinate(0,0));
	}
	
	/**
	 * Affichage en ASCII (Terminal) du labyrinthe.
	 */
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
	
	/**
	 * V�rifie si la coordonn�e donn� est mur du labyrinthe ou non.
	 * @param c une coordonn�e du labyrinthe.
	 * @return true si � la coordonn�e indiqu� c'est un mur, sinon false.
	 */
	protected boolean isWall(Coordinate c) {
		return this.walls[c.getRow()][c.getCol()];
	}
	
	/**
	 * Supprime un mur du labyrinthe � la case de la coordonn�e. 
	 * @param c une coordonn�e du labyrinthe.
	 * @param willBeWall un boolean qui supprime le mur du labyrinthe.
	 */
	public void setFloor(ICoordinate c, boolean willBeWall) {
		this.walls[c.getRow()][c.getCol()]=willBeWall;
	}
	
	/**
	 * Action de d�placement du Monstre. 
	 * @param c la coordonn�e ou laquelle veut se d�placer le monstre.
	 * @return true si l'action a reussi, sinon false.
	 */
	public boolean move(ICoordinate c) { //Fais le déplcament du monstre, retoure true si le déplacement à été possible.:
		if(this.canMonsterMoveAt(c)) {
			//System.out.println("Tour n°"+this.turn);
			//Quel endroit le mettre -> pendant ou après le déplacement ?
			//APRES
			if(this.hunter.getTrace(this.monster.getCoord())!=-2) {
				System.out.println("ATTENTION [Tour n°"+this.turn+"]- Le monstre à traversé (est entré et à quitté) l'une de vos cases déjà découverte !");
			}
			//
			this.monster.setCoord(c);
			this.isMonsterTurn=false;
			this.setTrace(c, turn);
			//PENDANT
			/*if(this.hunter.getTrace(c)!=-2) {
				System.out.println("ATTENTION [Tour n°"+this.turn+"]- Le monstre à traversé (est entré et à quitté) l'une de vos cases déjà découverte !");
			}*/
			//
			if(this.monster.coord.equals(exit.getCoord())) {
				this.isGameOver=true;
				System.out.println("MONSTER GAGNE");
			}
			this.turn++;
			this.notifyObservers();
			return true; 
		}
		return false;
	}
	
	/**
	 * Action de tir du Chasseur.
	 * @param c la coordonn�e � laquelle le chasseur tire.
	 * @return true si l'action a reussi,sinon false.
	 */
	public boolean shoot(ICoordinate c) { //Fais le tir du chasseur, devrait toujours renvoyer true logiquement, peut etre changer le type de retour.
		if(!this.isMonsterTurn) {
			this.hunter.setCoord(c);
			this.hunter.setTrace(c, this.getTrace(c));
			this.isMonsterTurn=true;
			if(this.hunter.coord.equals(monster.getCoord())) {
				this.isGameOver=true;
				System.out.println("HUNTER GAGNE");
			}
			this.notifyObservers();
			return true;
		}
		return false;
	}
	
	/**
	 * V�rifie si le Monstre a la possibilt� de se d�placer � la coordonn�e du labyrinthe donn�e.
	 * @param c une coordonn�e du labyrinthe.
	 * @return true si le Monstre peut se d�placer � la case demand�, sinon false.
	 */
	public boolean canMonsterMoveAt(ICoordinate c) {
		if(this.isMonsterTurn && this.walls[c.getRow()][c.getCol()] && this.inReach(this.monster.getCoord(), c, 1)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Calcule la distance en largeur et hauteur entre deux coordonn�e du labyrinthe.
	 * @param c1 une premi�re coordonn�e du labyrinthe.
	 * @param c2 une seconde coordonn�e du labyrinthe.
	 * @return un tableau d'entier de 2 cases contenant la distance en largeur en premi�re case
	 * puis la distance en hauteur en seconde case.
	 */
	public int[] calculDistance(ICoordinate c1, ICoordinate c2) {
		int[] distances = new int[2];
		int distanceX= Math.abs(c1.getCol()-c2.getCol());
		int distanceY= Math.abs(c1.getRow()-c2.getRow());
		distances[0]=distanceX;
		distances[1]=distanceY;
		return distances;
	}
	
	/**
	 * D�termine si deux coordonn�es sont � port�e.
	 * @param c1 une premi�re coordonn�e du labyrinthe.
	 * @param c2 une seconde coordonn�e du labyrinthe.
	 * @param reach port�e maximale � laquelle les coordonn�es doivent �tre l'une de l'autre.
	 * @return true si les deux coordonn�es sont � port�e,sinon false.
	 */
	public boolean inReach(ICoordinate c1, ICoordinate c2, int reach) {
		return (this.calculDistance(c1, c2)[0]<reach+1 && this.calculDistance(c1, c2)[1]<reach+1);
	}
	/**
	 * Ajoute le num�ro du tour actuel � la coordonn�e indiqu� dans le tableau de trace qui
	 * repr�sente au nouveau d�placement du Monstre.
	 * @param c une coordonn�e du labyrinthe.
	 * @param trace le num�ro du tour actuel lors de l'appel de la m�thode.
	 */
	public void setTrace(ICoordinate c, int trace) {
		this.traces[c.getRow()][c.getCol()]=trace;
	}
	/**
	 * Renvoie la potentielle trace du Monstre sur la coordonn�e indiqu�e.
	 * @param c une coordonn�e du labyrinthe.
	 * @return un entier correspondant au num�ro du tour du passage du Monstre sur la coordonn�e du labyrinthe.
	 */
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
	
	/**
	 * R�v�le une cellule donn�e en modifiant sa couleur de contour et de remplissage.
	 * @param cwt La cellule avec du texte � r�v�ler.
	 * @param colorOfWalls La couleur des murs de la cellule r�v�l�e.
	 * @param colorOfFloors La couleur du sol de la cellule r�v�l�e.
	 */
	public void revealCell(CellWithText cwt, Color colorOfWalls, Color colorOfFloors) {
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
