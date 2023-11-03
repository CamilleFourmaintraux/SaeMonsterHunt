/**
 * Le package main.maze contient les classes nécessaires pour la mise en œuvre
 * du jeu Monster Hunt. Il gère la logique du jeu, y compris la gestion du labyrinthe,
 * les déplacements du monstre, le tir du chasseur, et les vues associées.
 */
package main.maze;


import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
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
 * Modèle du jeu qui inclus la génération du labyrinthe, le déplacement du monstre, le tir du chasseur ainsi que de l'affichage 
 * telle que les traces du monstres au cours de la partie.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class Maze extends Subject{
	
	private static final boolean[][] DEFAULT_MAP = new boolean[][] {
		{false,true,false,true,true,false,true,false,true,false}, 	// X . X . . X . X . X 
		{false,true,true,true,true,false,true,false,true,true},		// X . . . . X . X . .
		{true,true,true,true,false,false,true,false,false,true},	// . . . . X X . X X . 
		{true,true,false,true,true,false,true,true,true,true},		// . . X . . X . . . . 
		{false,true,false,false,true,false,true,true,true,false},	// X . X X . X . . . X 
		{false,true,true,false,false,false,true,true,true,false},	// X . . X X X . . . X 
		{false,true,true,true,false,true,true,true,true,true},		// X . . . X . . . . . 
		{true,true,true,true,true,true,true,false,true,false},		// . . . . . . . X . X 
		{true,true,false,true,true,false,true,false,false,false},	// . . X . . X . X X X 
		{false,false,true,true,false,false,true,true,true,false}	// X X . . X X . . . X 
	};
	
	/**
	 * Tableau de boolean représentant les murs plein ou non du labyrinthe (false=mur true=pas de mur).
	 */
	protected boolean[][] walls;
	
	/**
	 * Tableau d'entier stockant les numéros de tours ou le monstre est déjà passé.
	 */
	protected int[][] traces;//TODO message ou alerte si le monstre passe sur une case déjà découverte
	
	/**
	 * La sortie (les coordonnées) du labyrinthe.
	 */
	protected Exit exit;
	
	/**
	 * Le Monstre associé au labyrinthe.
	 */
	protected Monster monster;
	
	/**
	 * Le Chasseur associé au labyrinthe.
	 */
	protected Hunter hunter;
	
	/**
	 * Le numéro du tour actuel.
	 */
	public int turn;
	
	/**
	 * Boolean qui permet de savoir si c'est le tour du monstre ou non.
	 */
	public boolean isMonsterTurn;
	
	/**
	 * Boolean utilisé dans les actions move du monstre et shoot du chasseur pour indique si la partie est fini.
	 * (i.e si le monstre/chasseur s'est échappé/fait tirer dessus).
	 */
	public boolean isGameOver;
	
	/**
	 * Constructeur vide, crée un labyrinthe Maze à partir d'un labyrinthe prédéfini.
	 * @see Maze#Maze(boolean[][], String, String)
	 */
	public Maze() {
		this(Maze.generateBasicMap(),"Player","Player");
	}
	
	/**
	 * Constructeur originel, crée un labyrinthe Maze à partir d'un labyrinthe existant donné en parametre.
	 * @see Maze#Maze()
	 * @param maze 			un labyrinthe.
	 * @param monster_IA 	Nom du Monstre.
	 * @param hunter_IA		Nom du Chasseur.
	 */
	public Maze(boolean[][] maze, String monster_IA, String hunter_IA) {
		this.walls=maze;
		this.turn=1;
		this.initMonsterExitHunter(monster_IA, hunter_IA);
		this.traces = this.initTraces();
		this.isMonsterTurn=true;
		this.move(this.monster.getCoord());
	}
	
	/**
	 * Constructeur spécial, crée un labyrinthe Maze personnalisé et génére aléatoirement à partir de la largeur, la hauteur et la probabilité d'apparition de murs.
	 * 
	 * @param probability le taux de chances qu'une case du labyrinthe soit un mur.
	 * @param height la hauteur du labyrinthe.
	 * @param width la largeur du labyrinthe.
	 * @param monster_IA 	Nom du Monstre.
	 * @param hunter_IA		Nom du Chasseur.
	 */
	public Maze(int probability, int height, int width, String monster_IA, String hunter_IA) {
		this(Maze.generateRandomMap(probability, height, width), monster_IA, hunter_IA);
	}
	
	/**
	 * Initialisation du tableau des traces du monstre vu par le chasseur.
	 * 
	 * @return un tableau d'entier initialise a zero si la case correspond à une case vide et -1 si la case correspond à un mur.
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
	 * Méthode de génération prédéfini du labyrinthe.
	 * 
	 * @return un tableau de boolean qui représente le labyrithe avec ses murs.
	 */
	public static boolean[][] generateBasicMap() {
		return DEFAULT_MAP;
	}
	
	/**
	 * Méthode de génération personnalisé du labyrinthe avec sa taille voulu ainsi que le taux d'apparition des murs.
	 * 
	 * @param probability le taux de chances que la case du labyrinthe soit un mur plein.
	 * @param height la hauteur du labyrinthe.
	 * @param width la largeur du labyrinthe.
	 * @return un tableau de boolean représentant un labyrinthe.
	 */
	public static boolean[][] generateRandomMap(int probability, int height, int width) {
		boolean[][] maze = new boolean[height][width];
		boolean onlyWalls;
		for(int h=1; h<maze.length; h+=2) {
			onlyWalls=true;
			for(int l=0; l<maze[h].length; l++) {
				maze[h-1][l]=true;
				if(probability<Utils.random.nextInt(99)+1) {
					maze[h][l]=true;
					onlyWalls=false;
				}else {
					maze[h][l]=false;
				}	
			}
			if(onlyWalls) {
				maze[h][Utils.random.nextInt(maze[h].length)]=true;
			}
		}
		return maze;
	}
	
	/**
	 * Calcule le nombre de mur du labyrinthe présent autour de la coordonnée.
	 * Si la coordonnée correspond à un bord du labyrinthe, la fonction comptera les bordures comme des murs.
	 * Par exemple, si la coordonnee est (0,0) et qu'il n'y a aucun mur, la fonction va renvoyer 5, à cause des bordures du coin de la map.
	 *
	 * @param c une coordonnée du labyrinthe.
	 * @return un entier correspondant au nombre de murs autour de cette coordonnée.
	 */
	//Inutilisé pour le moment
	/*public int numberOfWallsAround(ICoordinate c){
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
	}*/
	
	/**
	 * Initialise les coordonnées de la sortie du labyrinthe.
	 * @param hunter_IA niveau de l'IA du chasseur.
	 * @param monster_IA niveau de l'IA du monstre.
	 */
	public void initMonsterExitHunter(String monster_IA, String hunter_IA) {
		this.exit = new Exit(new Coordinate(this.walls.length-1, Utils.random.nextInt(this.walls[this.walls.length-1].length)));
		this.setFloor(this.exit.getCoord(),true);
		this.monster = new Monster(this.walls,new Coordinate(0,Utils.random.nextInt(this.walls[0].length)),monster_IA);
		this.setFloor(this.monster.getCoord(),true);
		this.hunter = new Hunter(this.walls.length,this.walls[0].length,new Coordinate(0,0),hunter_IA);
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
	
	/**
	 * Affichage en ASCII (Terminal ) du tableau des traces laissées par le monstre.
	 */
	public void printTraces() {
		for(int h=0; h<this.walls.length;h++) {
			for(int l=0; l<this.walls[h].length;l++) {
				System.out.print(" "+this.traces[h][l]+" ");
			}
			System.out.println();
		}
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
	 * Vérifie si la coordonnée donnée est mur du labyrinthe ou non.
	 * 
	 * @param c une coordonnée du labyrinthe.
	 * @return true si la coordonnée indiqué est un mur, sinon false.
	 */
	public boolean isWall(Coordinate c) {
		return this.walls[c.getRow()][c.getCol()];
	}
	
	/**
	 * Supprime un mur du labyrinthe à la case de la coordonnée. 
	 * 
	 * @param c une coordonnée du labyrinthe.
	 * @param willBeWall un boolean qui supprime le mur du labyrinthe.
	 */
	public void setFloor(ICoordinate c, boolean willBeWall) {
		this.walls[c.getRow()][c.getCol()]=willBeWall;
	}
	
	/**
	 * Action de déplacement du Monstre. 
	 * 
	 * @param c la coordonnée ou laquelle veut se déplacer le monstre.
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
	 * 
	 * @param c la coordonnée à laquelle le chasseur tire.
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
	 * Vérifie si le Monstre a la possibilté de se déplacer à la coordonnée du labyrinthe donnée.
	 * 
	 * @param c une coordonnée du labyrinthe.
	 * @return true si le Monstre peut se déplacer à la case demandé, sinon false.
	 */
	public boolean canMonsterMoveAt(ICoordinate c) {
		if(this.isMonsterTurn && this.walls[c.getRow()][c.getCol()] && this.inReach(this.monster.getCoord(), c, 1)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Calcule la distance en largeur et hauteur entre deux coordonnée du labyrinthe.
	 * 
	 * @param c1 une première coordonnée du labyrinthe.
	 * @param c2 une seconde coordonnée du labyrinthe.
	 * @return un tableau d'entier de 2 cases contenant la distance en largeur en première case
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
	 * Détermine si deux coordonnées sont à portée.
	 * 
	 * @param c1 		une première coordonnée du labyrinthe.
	 * @param c2 		une seconde coordonnée du labyrinthe.
	 * @param reach 	portée maximale à laquelle les coordonnées doivent être l'une de l'autre.
	 * @return true si les deux coordonnées sont à portée,sinon false.
	 */
	public boolean inReach(ICoordinate c1, ICoordinate c2, int reach) {
		return (this.calculDistance(c1, c2)[0]<reach+1 && this.calculDistance(c1, c2)[1]<reach+1);
	}
	/**
	 * Modifie le tableau de traces à la coordonnée c pour ajouter la nouvelle trace. 
	 * 
	 * @param c 	une coordonnée du labyrinthe.
	 * @param trace la trace que le monstre laisse (correspondant au numero du tour actuel)
	 */
	public void setTrace(ICoordinate c, int trace) {
		this.traces[c.getRow()][c.getCol()]=trace;
	}
	/**
	 * Renvoie la potentielle trace du Monstre sur la coordonnée indiquée.
	 * 
	 * @param c une coordonnée du labyrinthe.
	 * @return un entier correspondant au numéro du tour du passage du Monstre sur la coordonnée du labyrinthe (0 si le monstre n'y est jamais passé).
	 */
	public int getTrace(ICoordinate c) {
		return this.traces[c.getRow()][c.getCol()];
	}
	
	/**
	 * Révèle une cellule donnee en modifiant sa couleur en fonction de si c'est un mur ou un sol.
	 * 
	 * @param cwt 			La cellule avec texte que l'on veut révéler
	 * @param colorOfWalls 	La couleur associé au mur, future couleur de la cellule si la cellule se révèle être un mur.
	 * @param colorOfFloors La couleur associé au sol, future couleur de la cellule si la cellule se révèle être un sol.
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
