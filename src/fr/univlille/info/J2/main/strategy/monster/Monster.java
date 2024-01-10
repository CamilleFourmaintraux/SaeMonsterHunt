/**
 * Le package main.strategy.monster contient les classes relatives à la gestion
 * du monstree dans le jeu. Il propose des stratégies pour le comportement du monstre
 * et les informations concernant la sortie du labyrinthe.
 */
package fr.univlille.info.J2.main.strategy.monster;
import fr.univlille.info.J2.main.management.cells.CellEvent;
import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
/**
 *
 * La classe Monster représente un Monster dans le jeu. Elle implémente l'interface IMonsterStrategy
 * pour définir différentes stratégies pour le monstre.
 *
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class Monster {
	
	 /**
     * Données sauvegardées du monstre.
     */
	private SaveMonsterData data;
	
	/**
	 * Strategy du monstre.
	 */
	private IMonsterStrategy strategy;

	/**
     * Constructeur pour créer un monstre avec des paramètres spécifiques.
     *
     * @param walls     Tableau de boolean représentant les murs du labyrinthe.
     * @param spawn     Coordonnées de spawn du monstre.
     * @param exit      Coordonnées de la sortie du labyrinthe.
     * @param gameplay  Données de gameplay du monstre.
     */
	public Monster(boolean[][] walls,ICoordinate spawn, ICoordinate exit, GameplayMonsterData gameplay) {
		this.data = new SaveMonsterData(gameplay, new boolean[walls.length][walls[0].length], walls, spawn.getRow(), spawn.getCol());
		if(!data.getGameplay().isVisionLimited()) {
			this.setToAllExplored();
		}
		this.strategy=this.chooseMonsterStrategy(data.getIA());
		this.strategy.initialize(walls);
		CellEvent initEntity;
		initEntity=new CellEvent(exit, 0, CellInfo.EXIT); //Attention, la stratégie considère avoir bougé
		this.strategy.update(initEntity);
	}
	
	/**
     * Constructeur pour créer un monstre avec des données sauvegardées spécifiques.
     *
     * @param data Données sauvegardées du monstre.
     * @param exit Coordonnées de la sortie du labyrinthe.
     */
	public Monster(SaveMonsterData data,ICoordinate exit) {
		this.data=data;
		this.strategy=this.chooseMonsterStrategy(data.getIA());
		this.strategy.initialize(data.getWalls());
		CellEvent initEntity;
		initEntity=new CellEvent(exit, 0, CellInfo.EXIT); //Attention, la stratégie considère avoir bougé
		this.strategy.update(initEntity);
	}

	/**
	 * Choisi une stratégie de monstre en fonction du niveau d'intelligence artificielle spécifié.
	 *
	 * @param IA_monster Le niveau d'intelligence artificielle du monstre. Les valeurs possibles sont :
	 *                   - "IA-Easy" pour une intelligence artificielle facile.
	 *                   - "IA-Moderate" pour une intelligence artificielle modérée.
	 *                   - "IA-Hardcore" pour une intelligence artificielle hardcore.
	 * @return Une instance de l'interface IMonsterStrategy correspondant au niveau d'intelligence artificielle spécifié.
	 *         Si le niveau spécifié n'est pas reconnu, une instance de NoIAMonster est retournée par défaut.
	 */
	private IMonsterStrategy chooseMonsterStrategy(String IA_monster) {
		if(IA_monster.equals("IA-Easy")){
			return new IAeasyMonster();
		}else if(IA_monster.equals("IA-Moderate")) {
			return new IAmoderateMonster();
		}else if(IA_monster.equals("IA-Hardcore")) {
			return new IAhardMonster();
		}
		return new NoIAMonster();
	}

	/**
	 * Cette méthode marque toutes les cases comme explorées par le monstre.
	 * Elle parcourt le tableau bidimensionnel 'explored' et assigne la valeur 'true'
	 * à chaque élément, indiquant que le monstre a exploré toutes les cases.
	 */
	public void setToAllExplored() {
		for(int h=0;h<this.data.getExplored().length;h++) {
			for(int l=0;l<this.data.getExplored()[h].length;l++) {
				this.data.getExplored()[h][l]=true;
			}
		}
	}

	/**
     * Met à jour l'état du monstre en fonction d'un événement de cellule.
     *
     * @param ce L'événement de cellule qui a eu lieu.
     */
	public void update(ICellEvent ce) {
		this.strategy.update(ce);
	}

	/**
     * Obtient un tableau indiquant les cases explorées par le monstre.
     * Une case est explorée si le monstre y est passé au moins une fois.
     * 
     * @return Le tableau des cases explorées par le monstre.
     */
	public boolean[][] getExplored() {
		return this.data.getExplored();
	}
	
	/**
     * Joue un tour pour le monstre en utilisant sa stratégie associée.
     *
     * @return Les coordonnées où le monstre a décidé de se déplacer.
     */
	public ICoordinate play() {
		return this.strategy.play();
	}
	
	/**
     * Crée et renvoie une coordonnée valide à l'intérieur des limites du labyrinthe.
     *
     * @param row   Coordonnée en ligne.
     * @param col   Coordonnée en colonne.
     * @param walls Tableau de boolean représentant les murs du labyrinthe.
     * @return Coordonnée valide à l'intérieur des limites du labyrinthe.
     */
	protected static ICoordinate createInBoundCoord(int row, int col, boolean[][]walls) {
		if(row>=walls.length) {
			row=walls.length-1;
		}else if(row<0) {
			row=0;
		}
		if(col>=walls[row].length) {
			col=walls[row].length-1;
		}else if(col<0) {
			col=0;
		}
		return new Coordinate(row,col);
	}

	/**
     * Vérifie si la vision du monstre est limitée.
     *
     * @return true si la vision est limitée, sinon false.
     */
	public boolean isVisionLimited() {
		return this.data.isVisionLimited();
	}

	/**
     * Renvoie la portée de vision du monstre.
     *
     * @return Portée de vision du monstre.
     */
	public int getVisionRange() {
		return this.data.getVisionRange();
	}

	/**
     * Renvoie la portée de déplacement du monstre.
     *
     * @return Portée de déplacement du monstre.
     */
	public int getMovingRange() {
		return this.data.getMovingRange();
	}

	/**
     * Renvoie le niveau d'intelligence artificielle du monstre.
     *
     * @return Niveau d'intelligence artificielle du monstre.
     */
	public String getIA() {
		return this.data.getIA();
	}
	
	/**
     * Renvoie le nom du monstre.
     *
     * @return Nom du monstre.
     */
	public String getName() {
		return this.data.getName();
	}
	
	/**
     * Définit les coordonnées du monstre.
     *
     * @param c Les nouvelles coordonnées du monstre.
	 */
	public void setCoord(ICoordinate c) {
		this.data.setRow(c.getRow());
		this.data.setCol(c.getCol());
	}
	
	/**
     * Obtient la ligne de la coordonnée du monstre.
     *
     * @return La ligne de la coordonnée du monstre.
	 */
	public int getRow() {
		return this.data.getRow();
	}

	/**
     * Obtient la colonne de la coordonnée du monstre.
     *
     * @return La colonne de la coordonnée du monstre.
	 */
	public int getCol() {
		return this.data.getCol();
	}

	/**
     * Obtient les coordonnées du monstre.
     *
     * @return Les coordonnées du monstre.
	 */
	public ICoordinate getCoord() {
		return new Coordinate(this.data.getRow(),this.data.getCol());
	}
	
	/**
     * Renvoie les données sauvegardées du monstre.
     *
     * @return Données sauvegardées du monstre.
     */
	public SaveMonsterData getData() {
		return this.data;
	}
}
