package fr.univlille.info.J2.main.strategy.monster;

import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.info.J2.main.utils.Utils;
import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * Implémentation de la stratégie du monstre pour un niveau de difficulté difficile.
 */
public class IAhardMonster implements IMonsterStrategy{
	
	/**
	 * Le nombre d'essai max avant que l'ia abandonne.
	 */
	private static final int MAX_ATTEMPTS=10;
	
	/**
	 * Le numéro d'essai actuel, si il dépasse le nombre d'essai max, alors l'IA abandonne et passe son tour.
	 */
	private int numAttempt=0;
	
	/**
	 * La position du monstre pour aider la stratégie à se repérer.
	 */
	private ICoordinate current_position;
	
	/**
	 * La grille de murs du labyrinthe.
	 */
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
		this.current_position=ce.getCoord();
		this.numAttempt=0;
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
		ICoordinate c;
		do {
			this.numAttempt++;
			c = this.createInBoundCoord(this.current_position.getRow()+(Utils.random.nextInt(3)-1), this.current_position.getCol()+(Utils.random.nextInt(3)-1));
		}while(this.numAttempt<MAX_ATTEMPTS && !this.walls[c.getRow()][c.getCol()]);
		
		
		//c=new Coordinate(row,col);
		
		return c;
	}
	
	private ICoordinate createInBoundCoord(int row, int col) {
		if(row>=this.walls.length) {
			row=this.walls.length-1;
		}else if(row<0) {
			row=0;
		}
		if(col>=this.walls[row].length) {
			col=this.walls[row].length-1;
		}else if(col<0) {
			col=0;
		}
		return new Coordinate(row,col);
	}
}
