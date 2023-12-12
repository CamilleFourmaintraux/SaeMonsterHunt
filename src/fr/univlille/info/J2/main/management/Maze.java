/**
 * Le package main.maze contient les classes nécessaires pour la mise en œuvre
 * du jeu Monster Hunt. Il gère la logique du jeu, y compris la gestion du labyrinthe,
 * les déplacements du monstre, le tir du chasseur, et les vues associées.
 */
package fr.univlille.info.J2.main.management;

import java.util.logging.Logger;

import fr.univlille.info.J2.main.management.cells.CellEvent;
import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.info.J2.main.strategy.hunter.Hunter;
import fr.univlille.info.J2.main.strategy.monster.Exit;
import fr.univlille.info.J2.main.strategy.monster.Monster;
import fr.univlille.info.J2.main.utils.Utils;
import fr.univlille.info.J2.main.utils.patrons.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;


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

	private static final Logger LOGGER = Logger.getLogger(Maze.class.getName());

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
	private boolean[][] walls;

	/**
	 * Tableau d'entier stockant les numéros de tours ou le monstre est déjà passé.
	 */
	private int[][] traces;

	/**
	 * La sortie (les coordonnées) du labyrinthe.
	 */
	private Exit exit;

	/**
	 * Le Monstre associé au labyrinthe.
	 */
	private Monster monster;

	/**
	 * Le Chasseur associé au labyrinthe.
	 */
	private Hunter hunter;

	/**
	 * Le numéro du tour actuel.
	 */
	private int turn;

	/**
	 * Boolean qui permet de savoir si c'est le tour du monstre ou non.
	 */
	private boolean isMonsterTurn;

	/**
	 * Boolean utilisé dans les actions move du monstre et shoot du chasseur pour indique si la partie est fini.
	 * (i.e si le monstre/chasseur s'est échappé/fait tirer dessus).
	 */
	private boolean isGameOver;

	/**
	 * Boolean utilisé dans les actions move du monstre pour savoir si le monstre a traversé une case déjà découverte par le chasseur
	 */
	private boolean spotted;
	
	/**
	 * int utilisé pour determiner qui a gagné a la fin de la partie (0 si le joueur quitte la partie, 1 si le monster gagne et 2 si le chasseur gagne) 
	 */
	public  int winner= 0;

	/**
	 * Constructeur vide, crée un labyrinthe Maze à partir d'un labyrinthe prédéfini.
	 * @see Maze#Maze(boolean[][], String, String)
	 */
	public Maze() {
		this(Maze.generateBasicMap(),Management.IA_LEVELS[0],Management.IA_LEVELS[0], false, -1, 1, 0);
	}

	/**
	 * Constructeur originel, crée un labyrinthe Maze à partir d'un labyrinthe existant donné en parametre.
	 * @see Maze#Maze()
	 * @param maze 			un labyrinthe.
	 * @param monster_IA 	Niveau de l'ia du monstre
	 * @param hunter_IA		Niveau de l'ia du chasseur@param limitedVision boolean indiquant si oui ou non la vision du monstre est limité
	 * @param visionRange int correspondant à la distance jusqu'où le monstre peut voir (seulement si limitedVision est True)
	 */
	public Maze(boolean[][] maze, String monster_IA, String hunter_IA, boolean limitedVision, int visionRange, int movingRange, int bonusRange) {
		this.walls=maze;
		this.turn=1;
		this.initTraces();
		this.initMonsterExitHunter(monster_IA, hunter_IA,limitedVision, visionRange, movingRange, bonusRange);
		this.isMonsterTurn=true;
		this.exploring(this.monster.getCoord(), visionRange);
		this.move(this.monster.getCoord());
	}

	/**
	 * Constructeur spécial, crée un labyrinthe Maze personnalisé et génére aléatoirement à partir de la largeur, la hauteur et la probabilité d'apparition de murs.
	 *
	 * @param probability le taux de chances qu'une case du labyrinthe soit un mur.
	 * @param height la hauteur du labyrinthe.
	 * @param width la largeur du labyrinthe.
	 * @param monster_IA 	Niveau de l'ia du monstre
	 * @param hunter_IA		Niveau de l'ia du chasseur
	 */
	public Maze(int probability, int height, int width, String monster_IA, String hunter_IA, boolean limitedVision, int visionRange, int movingRange, int bonusRange) {
		this(Maze.generateRandomMap(probability, height, width), monster_IA, hunter_IA, limitedVision, visionRange, movingRange, bonusRange);
	}

	/**
	 * Initialisation du tableau des traces du monstre vu par le chasseur.
	 *
	 * @return un tableau d'entier initialise a zero si la case correspond à une case vide et -1 si la case correspond à un mur.
	 */
	public void initTraces(){
		this.traces = new int[this.walls.length][this.walls[0].length];
		for(int h=0; h<this.walls.length;h++) {
			for(int l=0; l<this.walls[h].length;l++) {
				this.traces[h][l]=0;
			}
		}
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
		boolean[][] maze = Maze.initEmptyMaze(height, width);
		boolean onlyWalls;
		for(int h=1; h<maze.length; h+=2) {
			onlyWalls=true;
			for(int l=0; l<maze[h].length; l++) {
				if(probability<Utils.random.nextInt(100)+1) {
					//maze[h][l]=true; //De base mit à true, grâce à la fonction intEmptyMaze
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


	public static boolean[][] initEmptyMaze(int height, int width) {
		boolean[][] maze = new boolean[height][width];
		for(int h=0; h<maze.length; h++) {
			for(int l=0; l<maze[h].length; l++) {
				maze[h][l]=true;
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
	 * @param limitedVision boolean indiquant si oui ou non la vision du monstre est limité
	 * @param visionRange int correspondant à la distance jusqu'où le monstre peut voir (seulement si limitedVision est True)
	 */
	public void initMonsterExitHunter(String monster_IA, String hunter_IA, boolean limitedVision, int visionRange, int movingRange, int bonusRange) {
		this.exit = new Exit(new Coordinate(this.walls.length-1, Utils.random.nextInt(this.walls[this.walls.length-1].length)));
		this.setFloor(this.exit.getCoord(),true);
		ICoordinate coordSpawnMonster = new Coordinate(0,Utils.random.nextInt(this.walls[0].length));
		CellEvent eventSpawnMonster = new CellEvent(coordSpawnMonster, 0, CellInfo.EMPTY);
		if(limitedVision) {
			this.monster = new Monster(Maze.initEmptyMaze(this.walls.length, this.walls[0].length),eventSpawnMonster, visionRange, movingRange, monster_IA);
		}else {
			this.monster = new Monster(this.walls,eventSpawnMonster, -1, movingRange, monster_IA);
			this.monster.setToAllExplored();
		}
		this.setFloor(this.monster.getCoord(),true);

		this.hunter = new Hunter(this.walls.length,this.walls[0].length,new Coordinate(0,0), bonusRange,hunter_IA);
	}

	/**
	 * Affichage en ASCII (Terminal) du labyrinthe.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (boolean[] wall : this.walls) {
			for(int l=0; l<wall.length; l++) {
				if(wall[l]) {
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

	public CellInfo getCellInfo(ICoordinate c) {
		if(this.monster.getCoord().equals(c)) {
			return CellInfo.MONSTER;
		}else if(this.hunter.getCoord().equals(c)) {
			return CellInfo.HUNTER;
		}else if(this.exit.getCoord().equals(c)) {
			return CellInfo.EXIT;
		}else if(this.walls[c.getRow()][c.getCol()]) {
			return CellInfo.EMPTY;
		}else {
			return CellInfo.WALL;
		}
	}

	/**
	 * Vérifie si un mur est présent ou non à la coordonné doonné du labyrinthe
	 *
	 * @param c une coordonnée du labyrinthe.
	 * @return false si la coordonnée indiqué est un mur, sinon true.
	 */
	public boolean isFloor(ICoordinate c) {
		return this.walls[c.getRow()][c.getCol()];
	}

	/**
	 * Définis la présence d'un ou non d'un mur à une coordonée précise du labyrinthe
	 *
	 * @param c une coordonnée du labyrinthe.
	 * @param empty un boolean -> si True : la case du labyrinthe à c sera vide, -> si False : la case du labyrinthe à c sera un mur
	 */
	public void setFloor(ICoordinate c, boolean empty) {
		this.walls[c.getRow()][c.getCol()]=empty;
	}

	/**
	 * Vérifie si un mur est présent ou non à la coordonné doonné du labyrinthe
	 *
	 * @param c une coordonnée du labyrinthe.
	 * @return false si la coordonnée indiqué est un mur, sinon true.
	 */
	public boolean isExplored(ICoordinate c) {
		return this.monster.getExplored()[c.getRow()][c.getCol()];
	}

	/**
	 * Définis la présence d'un ou non d'un mur à une coordonée précise du labyrinthe
	 *
	 * @param c une coordonnée du labyrinthe.
	 * @param empty un boolean -> si True : la case du labyrinthe à c sera vide, -> si False : la case du labyrinthe à c sera un mur
	 */
	public void setExplored(ICoordinate c, boolean explored) {
		this.monster.getExplored()[c.getRow()][c.getCol()]=explored;
	}

	public void exploring(ICoordinate c, int visionRange) {
		for(int y=c.getRow()-visionRange; y<c.getRow()+(visionRange+1); y++) {
			for(int x=c.getCol()-visionRange; x<c.getCol()+(visionRange+1); x++) {
				try {
					this.setExplored(new Coordinate(y,x), true);
				}catch(Exception e) {
					//Signifie que l'on est en dehors de la map
				}
			}
		}
	}

	/**
	 * Action de déplacement du Monstre.
	 *
	 * @param c la coordonnée ou laquelle veut se déplacer le monstre.
	 * @return true si l'action a reussi, sinon false.
	 */
	public boolean move(ICoordinate c) { //Fais le déplacement du monstre, retourne true si le déplacement à été possible.
		this.spotted=false;
		if(this.canMonsterMoveAt(c)) {
			if(this.hunter.getTrace(this.monster.getCoord())!=-2) {
				this.spotted=true;
			}
			this.setTrace(c, turn);

			CellEvent ce = new CellEvent(c, this.getTrace(c), this.getCellInfo(c));
			this.monster.update(ce);

			if(ce.getState().equals(CellInfo.EXIT)) {
				this.isGameOver=true;
				this.winner = 1;
			}
			if(this.monster.getVisionRange()!=-1) {
				this.exploring(c, this.monster.getVisionRange());
			}
			this.endMonsterTurn();
			return true;
		}
		if(!this.getMonsterIA().equals(Management.IA_LEVELS[0])) { //Inateignable par un joueur, sert à passer le tour d'une IA qui essaye d'aller à un endroit impossible.
			
		}
		return false;
	}
	
	public void endMonsterTurn() {
		this.turn++;
		this.isMonsterTurn=false;
		this.notifyObservers();
	}

	/**
	 * Action de tir du Chasseur.
	 *
	 * @param c la coordonnée à laquelle le chasseur tire.
	 * @return true si l'action a reussi,sinon false.
	 */
	public boolean shoot(ICoordinate c) { //Fais le tir du chasseur, renvoie toujours true
		CellEvent ce;
		Coordinate temp;
		for(int y=c.getRow()-this.getBonusRange(); y<c.getRow()+(this.getBonusRange()+1); y++) {
			for(int x=c.getCol()-this.getBonusRange(); x<c.getCol()+(this.getBonusRange()+1); x++) {
				try {
					temp = new Coordinate(y,x);
					ce = new CellEvent(temp, this.getTrace(temp), this.getCellInfo(temp));
					this.hunter.actualize(ce);
				}catch(Exception e) {//Signifie que l'est est en dehors de la map
					LOGGER.info("["+y+"]["+x+"] Out of Bounds in Maze -> normal behavior don't worry");
				}
			}
		}
		//???
		ce = new CellEvent(c, this.getTrace(c), this.getCellInfo(c)); //TODO Quel est l'interet de ces lignes déja ?
		this.hunter.actualize(ce);//A Vérifier...
		//???
		if(ce.getState().equals(CellInfo.MONSTER)) {
			this.isGameOver=true;
			this.winner = 2;
		}
		this.isMonsterTurn=true;
		this.notifyObservers();
		return true;
	}

	public void triggersGameOver() {
		this.isGameOver=true;
		this.notifyObservers();
	}

	/**
	 * Vérifie si le Monstre a la possibilté de se déplacer à la coordonnée du labyrinthe donnée.
	 *
	 * @param c une coordonnée du labyrinthe.
	 * @return true si le Monstre peut se déplacer à la case demandé, sinon false.
	 */
	public boolean canMonsterMoveAt(ICoordinate c) {
		if(this.isMonsterTurn) { //Est-ce le tour du monstre?
			if(this.isCorrectCoordinate(c)) { //Est-ce des coordonnées qui ne sont pas en dehors du terrain de jeu?
				if(this.walls[c.getRow()][c.getCol()]){ //Est-ce qu'il y a un mur?
					if(this.inReach(this.monster.getCoord(), c, this.monster.getMovingRange())){ //Est-ce que c'est à la portée du monstre ?
						if(this.isExplored(c)) { //Est-ce que cette case à été exploré par le monstre ?
							return true;
						}
					}
				}
				
			}
		}
		return false;
	}


	public boolean isCorrectCoordinate(ICoordinate c) {
		return !((c.getRow()>=this.walls.length)||(c.getCol()>=this.walls[c.getRow()].length));
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

	public int getVisionRange() {
		return this.monster.getVisionRange();
	}

	public int getBonusRange() {
		return this.hunter.getBonusRange();
	}

	public boolean[][] getWalls() {
		return walls;
	}

	public int[][] getTraces() {
		return traces;
	}

	public Exit getExit() {
		return exit;
	}

	public Monster getMonster() {
		return monster;
	}

	public Hunter getHunter() {
		return hunter;
	}

	public int getTurn() {
		return turn;
	}

	public boolean isMonsterTurn() {
		return isMonsterTurn;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public boolean isSpotted() {
		return spotted;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public void setMonsterTurn(boolean isMonsterTurn) {
		this.isMonsterTurn = isMonsterTurn;
	}
	
	public String getMonsterIA() {
		return this.monster.getIA();
	}
	
	public String getHunterIA() {
		return this.hunter.getIA();
	}
 }
