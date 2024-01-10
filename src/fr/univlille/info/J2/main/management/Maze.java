/**
 * Le package main.maze contient les classes nécessaires pour la mise en œuvre
 * du jeu Monster Hunt. Il gère la logique du jeu, y compris la gestion du labyrinthe,
 * les déplacements du monstre, le tir du chasseur, et les vues associées.
 */
package fr.univlille.info.J2.main.management;

import java.util.Arrays;

import fr.univlille.info.J2.main.application.system.Save;
import fr.univlille.info.J2.main.management.cells.CellEvent;
import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.info.J2.main.management.exit.Exit;
import fr.univlille.info.J2.main.strategy.hunter.GameplayHunterData;
import fr.univlille.info.J2.main.strategy.hunter.Hunter;
import fr.univlille.info.J2.main.strategy.monster.GameplayMonsterData;
import fr.univlille.info.J2.main.strategy.monster.Monster;
import fr.univlille.info.J2.main.utils.Utils;
import fr.univlille.info.J2.main.utils.patrons.Subject;
import fr.univlille.info.J2.main.utils.resources.MediaLoader;
import fr.univlille.info.J2.main.utils.resources.Theme;
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

	/**
	 * Carte par défaut représentant le labyrinthe avec des murs et des espaces libres.
	 */
	public static final boolean[][] DEFAULT_MAP = new boolean[][] {
		{true,true,false,true,true,false,true,false,true,false}, 	// . . X . . X . X . X
		{false,true,true,true,true,false,true,false,true,true},		// X . . . . X . X . .
		{true,true,true,true,false,false,true,false,false,true},	// . . . . X X . X X .
		{true,true,false,true,true,false,true,true,true,true},		// . . X . . X . . . .
		{false,true,false,false,true,false,true,true,true,false},	// X . X X . X . . . X
		{false,true,true,false,false,false,true,true,true,false},	// X . . X X X . . . X
		{false,true,true,true,false,true,true,true,true,true},		// X . . . X . . . . .
		{true,true,true,true,true,true,true,false,true,false},		// . . . . . . . X . X
		{true,true,false,true,true,false,true,false,false,false},	// . . X . . X . X X X
		{false,false,true,true,false,false,true,true,true,true}		// X X . . X X . . . .
	};
	
	/**
     * Données de sauvegarde du labyrinthe.
     */
	private SaveMazeData data;
	
	/**
     * Données de sauvegarde de la gestion du jeu.
     */
	private SaveManagementData dataMan;

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
	private int idWinner;
	
	/**
     * Distance la plus proche au monstre.
     */
	private int closestDistanceToMonster;

	/**
     * Constructeur par défaut de la classe Maze. Initialise un labyrinthe avec une carte de base
     * et des données par défaut pour le chasseur et le monstre.
     */
	public Maze() {
		this(Maze.generateBasicMap(),new GameplayHunterData("Hunter",Management.IA_LEVELS[0],0),new GameplayMonsterData("Monster",Management.IA_LEVELS[0], false, 1, 1),new SaveManagementData(Theme.THEME_CAVE, false, false));
	}

	/**
     * Constructeur de la classe Maze avec paramètres. Initialise un labyrinthe avec une carte spécifiée,
     * des données pour le chasseur et le monstre, ainsi que des données de gestion en option.
     *
     * @param maze     Le tableau de booléens représentant la carte du labyrinthe.
     * @param dataH    Les données de jeu pour le chasseur.
     * @param dataM    Les données de jeu pour le monstre.
     * @param dataMan  Les données de gestion du jeu.
     */
	public Maze(boolean[][] maze, GameplayHunterData dataH, GameplayMonsterData dataM, SaveManagementData dataMan) {
		this.data = new SaveMazeData(maze, new int[maze.length][maze[0].length], 1, true);
		this.dataMan=dataMan;
		this.initTraces();
		this.initMonsterExitHunter(dataH, dataM);
		this.idWinner = 0;
		this.closestDistanceToMonster=this.getWalls().length*this.getWalls()[0].length;
		this.exploring(this.monster.getCoord(), this.monster.getVisionRange());
		this.move(this.monster.getCoord());
	}

	/**
     * Constructeur de la classe Maze qui génère un labyrinthe aléatoire en fonction des paramètres spécifiés.
     * 
     * @param probability   Le taux de chances qu'une case du labyrinthe soit un mur.
     * @param height        La hauteur du labyrinthe.
     * @param width         La largeur du labyrinthe.
     * @param dataH         Les données de jeu associées au chasseur (GameplayHunterData).
     * @param dataM         Les données de jeu associées au monstre (GameplayMonsterData).
     * @param dataMan       Les données de gestion de sauvegarde (SaveManagementData).
     */
	public Maze(int probability, int height, int width, GameplayHunterData dataH, GameplayMonsterData dataM, SaveManagementData dataMan) {
		this(Maze.generateRandomMap(probability, height, width), dataH, dataM, dataMan);
	}
	
	/**
     * Constructeur de la classe Maze prenant une sauvegarde en paramètre. Initialise un labyrinthe
     * à partir des données de sauvegarde spécifiées.
     *
     * @param save La sauvegarde à partir de laquelle initialiser le labyrinthe.
     */
	public Maze(Save save) {
		this.data=save.getData_maze();
		this.dataMan=save.getData_management();
		this.initMonsterExitHunter(save);
		this.exploring(this.monster.getCoord(), this.monster.getVisionRange());
		this.move(this.monster.getCoord());
	}

	/**
	 * Initialisation du tableau des traces du monstre vu par le chasseur.
	 */
	public void initTraces(){
		for(int h=0; h<this.getWalls().length;h++) {
			for(int l=0; l<this.getWalls()[h].length;l++) {
				this.getTraces()[h][l]=0;
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
	 * @param probability 	le taux de chances que la case du labyrinthe soit un mur plein.
	 * @param height 		la hauteur du labyrinthe.
	 * @param width 		la largeur du labyrinthe.
	 * 
	 * @return un tableau de boolean représentant un labyrinthe.
	 */
	public static boolean[][] generateRandomMap(int probability, int height, int width) {
		return MazeGenerator.getMazeGenerator().generateRandomMaze(height, width, probability);
	}

	/**
	 * Initialise un labyrinthe vide représenté par un tableau de booléens.
	 *
	 * @param height La hauteur du labyrinthe.
	 * @param width  La largeur du labyrinthe.
	 * 
	 * @return Un tableau bidimensionnel de booléens représentant un labyrinthe vide,
	 *         où chaque case est initialement définie comme libre (true).
	 *         
	 * @throws IllegalArgumentException Si la hauteur ou la largeur est inférieure ou égale à zéro.
	 */
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
     * Initialise les instances du monstre, de la sortie et du chasseur à partir des données sauvegardées.
     * 
     * @param save L'objet Save contenant les données nécessaires pour l'initialisation.
     */
	public void initMonsterExitHunter(Save save) {
		this.exit = new Exit(new Coordinate(save.getData_exit().getRow(),save.getData_exit().getCol()));
		
		this.monster = new Monster(save.getData_monster(),exit.getCoord());
		
		this.hunter = new Hunter(save.getData_hunter());
	
	}
	
	/**
	 * Initialise les positions du monstre, de la sortie et du chasseur dans le labyrinthe en fonction des données de gameplay fournies.
	 * 
	 * @param dataH Les données de gameplay du chasseur.
	 * @param dataM Les données de gameplay du monstre.
	 */
	public void initMonsterExitHunter(GameplayHunterData dataH, GameplayMonsterData dataM) {
		int exit_x = Utils.random.nextInt(this.getWalls()[0].length);
		int exit_y = Utils.random.nextInt(this.getWalls().length);
		if(Utils.random.nextBoolean()) {
			if(exit_x>this.getWalls()[0].length/2) {
				exit_x=this.getWalls()[0].length-2;
			}else {
				exit_x=1;
			}
		}else {
			if(exit_y>this.getWalls().length/2) {
				exit_y=this.getWalls().length-2;
			}else {
				exit_y=1;
			}
		}
		this.exit = new Exit(new Coordinate(exit_y, exit_x));
		this.setFloor(this.exit.getCoord(),true);
		
		int monster_x = (this.getWalls()[0].length-this.exit.getCol())-1;
		int monster_y = (this.getWalls().length-this.exit.getRow())-1;
		ICoordinate far = new Coordinate(monster_y, monster_x);
		
		this.monster = new Monster(Arrays.copyOf(this.getWalls(),this.getWalls().length),far,exit.getCoord(),dataM);
		this.setFloor(this.monster.getCoord(),true);

		this.hunter = new Hunter(this.getWalls().length,this.getWalls()[0].length,new Coordinate(0,0), dataH);
	}


	/**
     * Retourne une représentation ASCII du labyrinthe avec les murs et les espaces.
     *
     * @return Une chaîne de caractères représentant le labyrinthe.
     */
	@Override
	public String toString() {
		return Maze.toString(getWalls());
	}
	
	/**
     * Retourne une représentation ASCII du labyrinthe avec les murs et les espaces.
     *
     * @param walls Le tableau bidimensionnel de booléens représentant les murs du labyrinthe.
     * @return Une chaîne de caractères représentant le labyrinthe.
     */
	public static String toString(boolean[][] walls) {
		StringBuilder sb = new StringBuilder();
		for (int h=0; h<walls.length; h++) {
			for(int l=0; l<walls[h].length; l++) {
				if(walls[h][l]) {
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
     * Retourne une représentation ASCII des traces laissées par le monstre dans le labyrinthe.
     *
     * @return Une chaîne de caractères représentant les traces du monstre.
     */
	public String getStringTraces() {
		StringBuilder sb = new StringBuilder();
		for(int h=0; h<this.getWalls().length;h++) {
			for(int l=0; l<this.getWalls()[h].length;l++) {
				sb.append(" "+this.getWalls()[h][l]+" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * Retourne des informations sur le contenu d'une cellule à une coordonnée spécifique du labyrinthe.
	 *
	 * @param c la coordonnée de la cellule dans le labyrinthe.
	 * @return une valeur de l'énumération CellInfo indiquant le contenu de la cellule à la coordonnée spécifiée.
	 *         - {@link CellInfo#MONSTER} si la cellule contient le monstre.
	 *         - {@link CellInfo#HUNTER} si la cellule contient le chasseur.
	 *         - {@link CellInfo#EXIT} si la cellule contient la sortie.
	 *         - {@link CellInfo#EMPTY} si la cellule est vide (pas un mur).
	 *         - {@link CellInfo#WALL} si la cellule est un mur.
	 */
	public CellInfo getCellInfo(ICoordinate c) {
		if(this.monster.getCoord().equals(c)) {
			return CellInfo.MONSTER;
		}else if(this.hunter.getCoord().equals(c)) {
			return CellInfo.HUNTER;
		}else if(this.exit.getCoord().equals(c)) {
			return CellInfo.EXIT;
		}else if(this.getWalls()[c.getRow()][c.getCol()]) {
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
		return this.getWalls()[c.getRow()][c.getCol()];
	}

	/**
	 * Définis la présence d'un ou non d'un mur à une coordonée précise du labyrinthe
	 *
	 * @param c une coordonnée du labyrinthe.
	 * @param empty un boolean -> si True : la case du labyrinthe à c sera vide, -> si False : la case du labyrinthe à c sera un mur
	 */
	public void setFloor(ICoordinate c, boolean empty) {
		this.getWalls()[c.getRow()][c.getCol()]=empty;
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
	 * Définit la présence ou l'absence d'un mur à une coordonnée précise dans le labyrinthe.
	 *
	 * @param c la coordonnée du labyrinthe.
	 * @param explored un booléen :
	 *                 - Si true : la case du labyrinthe à la coordonnée c sera considérée comme explorée.
	 *                 - Si false : la case du labyrinthe à la coordonnée c sera considérée comme un mur.
	 */
	public void setExplored(ICoordinate c, boolean explored) {
		this.monster.getExplored()[c.getRow()][c.getCol()]=explored;
	}

	/**
	 * Explore les cellules autour d'une coordonnée donnée dans une portée de vision spécifiée.
	 *
	 * @param c La coordonnée autour de laquelle explorer.
	 * @param visionRange La portée de vision pour l'exploration.
	 * @throws IndexOutOfBoundsException Si la coordonnée spécifiée est en dehors des limites du labyrinthe.
	 */
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
	 */ //BUG qui modifie mon labyrinthe
	public boolean move(ICoordinate c) { //Fais le déplacement du monstre, retourne true si le déplacement à été possible.
		this.spotted=false;
		if(this.canMonsterMoveAt(c)) {
			if(this.hunter.getTrace(this.monster.getCoord())!=-2) {
				this.spotted=true;
			}
			this.setTrace(c, this.getTurn());

			CellEvent ce = new CellEvent(c, this.getTrace(c), this.getCellInfo(c));
			this.monster.setCoord(c);
			this.monster.update(ce);
			if(ce.getState().equals(CellInfo.EXIT)) {
				this.isGameOver=true;
				this.idWinner = 1;
			}
			if(this.monster.isVisionLimited()) {
				this.exploring(c, this.monster.getVisionRange());
			}
			this.endMonsterTurn();
			return true;
		}
		if(!this.getMonsterIA().equals(Management.IA_LEVELS[0])) { //Inatteignable par un joueur, sert à passer le tour d'une IA qui essaye d'aller à un endroit impossible.
			if(this.canMonsterMoveAt(c)) {
				this.monster.setCoord(c);
				CellEvent ce = new CellEvent(c, this.getTrace(c), this.getCellInfo(c));
				this.monster.update(ce);
			}
			this.endMonsterTurn();
		}
		return false;
	}
	
	/**
	 * Termine le tour du monstre en incrémentant le numéro de tour,
	 * en indiquant que ce n'est plus le tour du monstre, et notifie
	 * les observateurs du labyrinthe.
	 * 
	 * Cette méthode est appelée pour finaliser le tour du monstre après
	 * que celui-ci a effectué son déplacement dans le labyrinthe. Elle
	 * incrémente également le numéro de tour, indique que ce n'est plus
	 * le tour du monstre, puis notifie les observateurs (si présents)
	 * qu'une mise à jour a eu lieu.
	 */
	public void endMonsterTurn() {
		if(this.dataMan.isAudioActivated()) {
			MediaLoader.playSound(Theme.themesMap.get(this.dataMan.getTheme()).getSound_monster());
		}
		this.data.incrementTurn();
		this.setMonsterTurn(false);
		this.notifyObservers();
	}

	/**
	 * Action de tir du Chasseur.
	 *
	 * @param c la coordonnée à laquelle le chasseur tire.
	 * @return true si l'action a reussi,sinon false.
	 */
	public boolean shoot(ICoordinate c) { //Fais le tir du chasseur, renvoie toujours true sauf si c'est pas le tour du chasseur
		if(!this.isMonsterTurn()) {
			CellEvent ce;
			Coordinate temp;
			//S'occupe du tir secondaire si jamais le hunter à un bonus.
			for(int y=c.getRow()-this.getBonusRange(); y<c.getRow()+(this.getBonusRange()+1); y++) {
				for(int x=c.getCol()-this.getBonusRange(); x<c.getCol()+(this.getBonusRange()+1); x++) {
					try {
						temp = new Coordinate(y,x);
						if(this.getCellInfo(temp)!=CellInfo.WALL) {
							ce = new CellEvent(temp, this.getTrace(temp), CellInfo.EMPTY);
						}else {
							ce = new CellEvent(temp, this.getTrace(temp), CellInfo.WALL);
						}
						this.hunter.actualizeTraces(ce);
						this.hunter.update(ce);
					}catch(Exception e) {
						//Signifie que l'est est en dehors de la map
					}
				}
			}
			//Vérifie le tir principal.
			ce = new CellEvent(c, this.getTrace(c), this.getCellInfo(c));
			this.hunter.setCoord(ce.getCoord());
			if(ce.getState().equals(CellInfo.MONSTER)) {
				this.isGameOver=true;
				this.idWinner = 2;
			}
			
			if(this.dataMan.isAudioActivated()) {
				MediaLoader.playSound(Theme.themesMap.get(this.dataMan.getTheme()).getSound_hunter());
			}
			
			int dist = Maze.calculDistance(this.getMonster().getCoord(), this.getHunter().getCoord());
			if(this.closestDistanceToMonster>dist) {
				this.closestDistanceToMonster = dist;
			}
			
			this.setMonsterTurn(true);
			this.notifyObservers();
			return true;
			//Faire un Bazooka mode ?
		}
		return false;
	}

	/**
	 * Déclenche la fin de partie en mettant à jour le statut de la partie à "game over" 
	 * et en notifiant les observateurs enregistrés.
	 * 
	 * Cette méthode met à jour le drapeau indiquant que la partie est terminée (isGameOver)
	 * et informe les observateurs en appelant la méthode notifyObservers().
	 * Les observateurs enregistrés seront ainsi notifiés du changement d'état de la partie.
	 */
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
		if(this.isMonsterTurn()) { //Est-ce le tour du monstre?
			if(this.areCoordinateInBounds(c)) { //Est-ce des coordonnées qui ne sont pas en dehors du terrain de jeu?
				if(this.getWalls()[c.getRow()][c.getCol()]){ //Est-ce qu'il y a un mur?
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
	/**
	 * Vérifie si la coordonnée spécifiée est valide dans le labyrinthe.
	 *
	 * @param c la coordonnée à vérifier.
	 * @return true si la coordonnée est valide, c'est-à-dire qu'elle se situe à l'intérieur des limites du labyrinthe ; false sinon.
	 */
	public boolean areCoordinateInBounds(ICoordinate c) {
		return !((c.getRow()>=this.getWalls().length)||(c.getCol()>=this.getWalls()[c.getRow()].length));
	}

	/**
	 * Calcule la distance en largeur et hauteur entre deux coordonnée du labyrinthe.
	 *
	 * @param c1 une première coordonnée du labyrinthe.
	 * @param c2 une seconde coordonnée du labyrinthe.
	 * @return un tableau d'entier de 2 cases contenant la distance en largeur en première case
	 * puis la distance en hauteur en seconde case.
	 */
	public static int[] calculDistanceTab(ICoordinate c1, ICoordinate c2) {
		int[] distances = new int[2];
		int distanceX= Math.abs(c1.getCol()-c2.getCol());
		int distanceY= Math.abs(c1.getRow()-c2.getRow());
		distances[0]=distanceX;
		distances[1]=distanceY;
		return distances;
	}
	
	/**
     * Calcule la distance totale entre deux coordonnées du labyrinthe en additionnant
     * la distance en largeur et la distance en hauteur.
     *
     * @param c1 La première coordonnée du labyrinthe.
     * @param c2 La seconde coordonnée du labyrinthe.
     * @return La distance totale entre les deux coordonnées.
     */
	public static int calculDistance(ICoordinate c1, ICoordinate c2) {
		int[] distances = calculDistanceTab(c1,c2);
		return distances[0]+distances[1];
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
		int[] distances = calculDistanceTab(c1,c2);
		return (distances[0]<reach+1 && distances[1]<reach+1);
	}
	/**
	 * Modifie le tableau de traces à la coordonnée c pour ajouter la nouvelle trace.
	 *
	 * @param c 	une coordonnée du labyrinthe.
	 * @param trace la trace que le monstre laisse (correspondant au numero du tour actuel)
	 */
	public void setTrace(ICoordinate c, int trace) {
		this.getTraces()[c.getRow()][c.getCol()]=trace;
	}
	/**
	 * Renvoie la potentielle trace du Monstre sur la coordonnée indiquée.
	 *
	 * @param c une coordonnée du labyrinthe.
	 * @return un entier correspondant au numéro du tour du passage du Monstre sur la coordonnée du labyrinthe (0 si le monstre n'y est jamais passé).
	 */
	public int getTrace(ICoordinate c) {
		return this.getTraces()[c.getRow()][c.getCol()];
	}

	/**
	 * Récupère la portée de vision du monstre.
	 *
	 * @return La portée de vision actuelle du monstre.
	 */
	public int getVisionRange() {
		return this.monster.getVisionRange();
	}

	/**
	 * Récupère la portée bonus du chasseur.
	 *
	 * @return La portée bonus actuelle du chasseur.
	 */
	public int getBonusRange() {
		return this.hunter.getBonusRange();
	}

	/**
     * @return Le tableau de boolean représentant les murs plein ou non du labyrinthe (false=mur, true=pas de mur).
     */
	public boolean[][] getWalls() {
		return data.getWalls();
	}

	/**
     * @return Le tableau d'entier stockant les numéros de tours où le monstre est déjà passé.
     */
	public int[][] getTraces() {
		return data.getTraces();
	}

	/**
     * @return La sortie (les coordonnées) du labyrinthe.
     */
	public Exit getExit() {
		return exit;
	}

	/**
     * @return Le Monstre associé au labyrinthe.
     */
	public Monster getMonster() {
		return monster;
	}

	/**
     * @return Le Chasseur associé au labyrinthe.
     */
	public Hunter getHunter() {
		return hunter;
	}

	/**
     * @return Le numéro du tour actuel.
     */
	public int getTurn() {
		return data.getTurn();
	}

	/**
     * @return true si c'est le tour du monstre, sinon false.
     */
	public boolean isMonsterTurn() {
		return data.isMonsterTurn();
	}

	/**
     * @return true si la partie est terminée, sinon false.
     */
	public boolean isGameOver() {
		return isGameOver;
	}

	/**
     * @return true si le monstre a traversé une case déjà découverte par le chasseur, sinon false.
     */
	public boolean isSpotted() {
		return spotted;
	}

	/**
     * @param isGameOver Boolean utilisé dans les actions move du monstre et shoot du chasseur pour indiquer si la partie est finie.
     */
	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	/**
     * @param isMonsterTurn Boolean qui permet de savoir si c'est le tour du monstre ou non.
     */
	public void setMonsterTurn(boolean isMonsterTurn) {
		this.data.setMonsterTurn(isMonsterTurn);
	}
	
	/**
     * @return Le niveau de l'intelligence artificielle du monstre.
     */
	public String getMonsterIA() {
		return this.monster.getIA();
	}
	
	/**
     * @return Le niveau de l'intelligence artificielle du chasseur.
     */
	public String getHunterIA() {
		return this.hunter.getIA();
	}
	
	/**
     * Renvoie l'identifiant du gagnant du jeu.
     *
     * @return l'identifiant du gagnant du jeu.
     */
	public int getIdWinner() {
		return this.idWinner;
	}
	
	/**
     * Renvoie les données du labyrinthe sauvegardées.
     *
     * @return les données du labyrinthe sauvegardées.
     */
	public SaveMazeData getData() {
		return this.data;
	}
	
	/**
     * Renvoie les données de gestion de sauvegarde.
     *
     * @return les données de gestion de sauvegarde.
     */
	public SaveManagementData getDataMan() {
		return this.dataMan;	
	}

	/**
     * Renvoie la distance la plus proche au monstre depuis une position donnée.
     *
     * @return la distance la plus proche au monstre depuis une position donnée.
     */
	public int getClosestDistanceToMonster() {
		return closestDistanceToMonster;
	}
	

 }
