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
	 * La grille des parties déjà explorés du labyrinthe
	 */
	private boolean[][] explored;
	/**
	 * Les coordonnées initiales du monstre.
	 */
	private ICoordinate coord;
	/**
	* String de la strategy du Monstre
	**/
	String IA;
	/**
	 * Strategy du monstre.
	 */
	private IMonsterStrategy strategy;
	/**
	 * La portée de la vision du monstre (seulement si l'attribut boolean visionLimited de Maze est True, sinon vaut -1)
	 */
	private int visionRange;
	/**
	 * La portée de la vision du monstre (seulement si l'attribut boolean visionLimited de Maze est True, sinon vaut -1)
	 */
	private int movingRange;

	/**
     * Constructeur de la classe Monster, crée un Monstre.
	 *
	 * @param walls 	La grille de murs du labyrinthe.
	 * @param coord		Les coordonnées initiales du monstre.
	 * @param IA_level	 Le niveau de l'IA du monstre.
	 * @param visionRange int correspondant à la distance jusqu'où le monstre peut voir (seulement si limitedVision est True)
	 */
	public Monster(boolean[][] walls,ICoordinate spawn, ICoordinate exit, int visionRange, int movingRange, String IA) {
		super();
		this.explored=new boolean[walls.length][walls[0].length];
		this.coord = spawn;
		this.IA=IA;
		this.strategy=this.chooseMonsterStrategy(IA);
		this.strategy.initialize(walls);
		CellEvent initEntity;
		initEntity=new CellEvent(exit, 0, CellInfo.EXIT); //Attention, la stratégie considère avoir bougé
		this.strategy.update(initEntity);
		initEntity=new CellEvent(spawn, 0, CellInfo.MONSTER); //Bien pensé à le remttre à sa position initiale
		this.strategy.update(initEntity);
		this.visionRange=visionRange;
		this.movingRange=movingRange;
	}
	
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

	public void setToAllExplored() {
		for(int h=0;h<this.explored.length;h++) {
			for(int l=0;l<this.explored[h].length;l++) {
				this.explored[h][l]=true;
			}
		}
	}

	/**
     * Obtient la ligne de la coordonnée du monstre.
     *
     * @return La ligne de la coordonnée du monstre.
	 */
	public int getRow() {
		return this.coord.getRow();
	}

	/**
     * Obtient la colonne de la coordonnée du monstre.
     *
     * @return La colonne de la coordonnée du monstre.
	 */
	public int getCol() {
		return this.coord.getCol();
	}

	/**
     * Obtient les coordonnées du monstre.
     *
     * @return Les coordonnées du monstre.
	 */
	public ICoordinate getCoord() {
		return this.coord;
	}

	/**
     * Définit les coordonnées du monstre.
     *
     * @param c Les nouvelles coordonnées du monstre.
	 */
	public void setCoord(ICoordinate c) {
		this.coord=c;
	}

	/**
     * Met à jour l'état du monstre en fonction d'un événement de cellule.
     *
     * @param ce L'événement de cellule qui a eu lieu.
     */
	public void update(ICellEvent ce) {
		this.setCoord(ce.getCoord());
		this.strategy.update(ce);
	}

	public boolean[][] getExplored() {
		return explored;
	}

	public int getVisionRange() {
		return visionRange;
	}

	public int getMovingRange() {
		return movingRange;
	}
	
	public String getIA() {
		return this.IA;
	}
	
	public ICoordinate play() {
		return this.strategy.play();
	}
	
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
	
}
