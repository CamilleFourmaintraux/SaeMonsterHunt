package fr.univlille.info.J2.main.strategy.hunter;

import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.info.J2.main.utils.Utils;
import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * Implémentation de l'interface IHunterStrategy pour une stratégie simple de chasseur.
 * La stratégie consiste à choisir aléatoirement des coordonnées dans le labyrinthe.
 */
public class IAeasyHunter implements IHunterStrategy{
	
	/**
	 * Nombre de lignes du labyrinthe.
	 */
	private int rows;
	
	/**
	 * Nombre de colonnes du labyrinthe.
	 */
	private int cols;
	
	/**
	 * Initialise le tableau des traces avec des valeurs par défaut.
     *
     * @param nbrRows Le nombre de lignes du labyrinthe.
     * @param nbrCols Le nombre de colonnes du labyrinthe.
     */
	@Override
	public void initialize(int nbrRows, int nbrCols) {
		this.rows=nbrRows;
		this.cols=nbrCols;
	}
	
	/**
     * Méthode principale pour le jeu du chasseur. Implémente le comportement du chasseur.
     *
     * @return Les nouvelles coordonnées pour l'action du chasseur.
	 */
	@Override
	public ICoordinate play() {
		int y = Utils.random.nextInt(this.rows);
		int x = Utils.random.nextInt(this.cols);
		return new Coordinate(y,x);
	}
	
	/**
     * Méthode de mise à jour appelée lorsqu'un événement de cellule se produit.
     *
     * @param ce L'événement de cellule.
     */
	@Override
	public void update(ICellEvent ce) {
		//Stratégie trop bête pour s'adapter.
	}
}
