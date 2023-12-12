package fr.univlille.info.J2.main.strategy.monster;

import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.info.J2.main.utils.Utils;
import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * Implémentation de la stratégie du monstre pour un niveau de difficulté facile.
 */
class IAeasyMonster implements IMonsterStrategy{

	/**
	 * La position du monstre pour aider la stratégie à se repérer.
	 */
	private ICoordinate current_position;
	
	/**
	 * La grille de murs du labyrinthe.
	 */
	@SuppressWarnings("unused")
	private boolean[][] walls;
	
	/**
     * Initialise les murs du labyrinthe pour le monstre.
     *
     * @param walls La grille de murs du labyrinthe.
	 */
	@Override
	public void initialize(boolean[][] walls) {
		this.walls=walls;
	}
	
	/**
     * Met à jour l'état du monstre en fonction d'un événement de cellule.
     *
     * @param ce L'événement de cellule qui a eu lieu.
     */
	@Override
	public void update(ICellEvent ce) {
		if(!ce.getState().equals(CellInfo.WALL)) {
			this.current_position=ce.getCoord();
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
		return new Coordinate(this.current_position.getRow()+(Utils.random.nextInt(3)-1), this.current_position.getCol()+(Utils.random.nextInt(3)-1));
	}
}
