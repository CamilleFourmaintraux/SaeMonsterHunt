package fr.univlille.info.J2.main.strategy.monster;

import java.util.List;

import fr.univlille.info.J2.main.strategy.monster.brain.*;
import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;

/**
 * Implémentation de la stratégie du monstre pour un niveau de difficulté difficile.
 */
class IAhardMonster implements IMonsterStrategy{

	/**
	 * La grille de murs du labyrinthe.
	 */
	private boolean[][] walls;
	
	/**
	 * La grille des chemins empruntable
	 */
	private Node[][] pathMap;
	
	/**
	 * Le chemin que l'IA souhaite emprunter
	 */
	private List<Node> path;
	
	private Node current;
	private Node exit;
	
	private AlgoAStar brain;
	
	private int progress;
	
	/**
     * Initialise les murs du labyrinthe pour le monstre.
     *
     * @param walls La grille de murs du labyrinthe.
	 */
	@Override
	public void initialize(boolean[][] walls) {
		this.walls=walls;
		this.pathMap = this.initPathMap(walls);
		this.brain=new AlgoAStar();
		this.progress=0;
		this.current=new Node(0,0,true);
		this.exit=new Node(0,0,true);
	}
	
	/**
     * Met à jour l'état du monstre en fonction d'un événement de cellule.
     *
     * @param ce L'événement de cellule qui a eu lieu.
     */
	@Override
	public void update(ICellEvent ce) {
		if(!ce.getState().equals(CellInfo.WALL)) {
			if(ce.getState().equals(CellInfo.EXIT)) {
				this.exit.setCoord(ce.getCoord());
			}
			this.current.setCoord(ce.getCoord());
			this.walls[ce.getCoord().getRow()][ce.getCoord().getCol()]=true;
		}else {
			this.walls[ce.getCoord().getRow()][ce.getCoord().getCol()]=false;
		}
		if(this.pathMap[ce.getCoord().getRow()][ce.getCoord().getCol()].isTraversable()!=this.walls[ce.getCoord().getRow()][ce.getCoord().getCol()]) {
			this.pathMap[ce.getCoord().getRow()][ce.getCoord().getCol()].setTraversable(this.walls[ce.getCoord().getRow()][ce.getCoord().getCol()]);
			this.path=this.brain.think(pathMap, current, exit);
			this.progress=0;
		}
	}
	
	/**
     * Méthode principale pour le jeu du monstre. Implémente le comportement du monstre.
     * 	Ne décide pas si l'ia à le droit ou non d'aller à un endroit.
     * 	Vérifie juste si elle essaye de jouer au moins dans le plateau.
     *
     * @return Les nouvelles coordonnées du monstre après son action.
	 */
	@Override
	public ICoordinate play() {
		ICoordinate choice;
		if(this.path==null || progress>this.path.size() || progress<0) {
			progress=0;
			this.path=this.brain.think(pathMap, current, exit);
		}
		try {
		choice=this.path.get(progress).getCoord();
		}catch(NullPointerException npe) {
			return this.current.getCoord();
		}
		progress++;
		return choice;
	}
	
	public Node[][] initPathMap(boolean[][] walls) {
		Node[][]pathMap=new Node[walls.length][walls[0].length];
		for(int row=0; row<walls.length; row++) {
			for(int col=0; col<walls[row].length; col++) {
				pathMap[row][col]=new Node(row,col,walls[row][col]);
			}
		}
		return pathMap; //Retourne le nombre de modifications
	}
}
