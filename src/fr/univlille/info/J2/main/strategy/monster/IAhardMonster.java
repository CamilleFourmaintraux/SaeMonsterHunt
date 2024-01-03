package fr.univlille.info.J2.main.strategy.monster;

import java.util.List;

import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.info.J2.main.strategy.monster.astar.AStar;
import fr.univlille.info.J2.main.utils.Utils;
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
	 * Le chemin que l'IA souhaite emprunter
	 */
	private List<ICoordinate> path;
	private ICoordinate monsterPos;
	private ICoordinate exitPos;
	private int progress;
	
	/**
     * Initialise les murs du labyrinthe pour le monstre.
     *
     * @param walls La grille de murs du labyrinthe.
	 */
	@Override
	public void initialize(boolean[][] walls) {
		this.walls=walls;
		progress=1;
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
				this.setExitPos(ce.getCoord());
			}else if(ce.getState().equals(CellInfo.MONSTER)) {
				this.setMonsterPos(ce.getCoord());

				this.path=AStar.findPath(walls,monsterPos,exitPos);
				this.progress=0;
				
			}else{
				
			}
			this.setMonsterPos(ce.getCoord());
			this.walls[ce.getCoord().getRow()][ce.getCoord().getCol()]=true;
		}else {
			this.walls[ce.getCoord().getRow()][ce.getCoord().getCol()]=false;
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
		if(path.isEmpty()) {
			return this.randomMove();
		}else {
			if(progress>=path.size()) {
				progress=0;
			}
			progress++;
			try {
				ICoordinate c = path.get(progress);
				if(c==null) {
					throw new IllegalArgumentException();
				}
			}catch(Exception e){
				return this.randomMove();
			}
			return path.get(progress);
		}
		
	}
	
	public ICoordinate randomMove() {
		return new Coordinate(this.getMonsterPos().getRow()+(Utils.random.nextInt(3)-1), this.getMonsterPos().getCol()+(Utils.random.nextInt(3)-1));
	}

	public ICoordinate getMonsterPos() {
		return monsterPos;
	}

	public void setMonsterPos(ICoordinate monsterPos) {
		this.monsterPos = monsterPos;
	}

	public ICoordinate getExitPos() {
		return exitPos;
	}

	public void setExitPos(ICoordinate exitPos) {
		this.exitPos = exitPos;
	}
	
}
