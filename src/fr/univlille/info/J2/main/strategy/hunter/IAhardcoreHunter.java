package fr.univlille.info.J2.main.strategy.hunter;

import fr.univlille.info.J2.main.management.cells.Coordinate;
import fr.univlille.info.J2.main.utils.Utils;
import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;

/**
 * Implémentation de la stratégie du chasseur pour un niveau de difficulté hardcore.
 */
public class IAhardcoreHunter implements IHunterStrategy{
	
	/**
     * Valeur représentant une cellule mur.
     */
	private final int T_WALL=-1;
	
	/**
     * Valeur représentant une cellule non découverte.
     */
	private final int T_UNDISCOVERED = -2;
	
	/**
     * Dernière coordonnée où le chasseur a effectué un tir.
     */
	private ICoordinate lastShot;
	
	/**
     * Nombre de lignes dans le labyrinthe.
     */
	private int rows;
	
	/**
     * Nombre de colonnes dans le labyrinthe.
     */
	private int cols;
	
	/**
     * Tableau représentant les traces laissées par le chasseur lors de ses déplacements.
     * Les valeurs sont utilisées pour suivre les mouvements du chasseur dans le labyrinthe.
     */
	private int traces[][];
	
	/**
	 * Initialise le tableau des traces avec des valeurs par défaut.
     *
     * @param nbrRows Le nombre de lignes du labyrinthe.
     * @param nbrCols Le nombre de colonnes du labyrinthe.
     **/
	@Override
	public void initialize(int nbrRows, int nbrCols) {
		this.rows=nbrRows;
		this.cols=nbrCols;
		this.traces = new int[nbrRows][nbrCols];
		this.lastShot=null;
		for(int row = 0; row<nbrRows;row++) {
			for (int col = 0; col<nbrCols;col++){
				traces[row][col]=this.T_UNDISCOVERED;
			}
		}
	}
	
	/**
     * Méthode principale pour le jeu du chasseur. Implémente le comportement du chasseur.
     *
     * @return Les nouvelles coordonnées pour l'action du chasseur.
	 */
	@Override
	public ICoordinate play() {
		if(this.lastShot==null) {
			return new Coordinate(Utils.random.nextInt(this.rows),Utils.random.nextInt(this.cols));
		}else {
			int y;
			int x;
			int ctp=0;
			do {
				if(traces[lastShot.getRow()][lastShot.getCol()]>0) {
					y = lastShot.getRow()+(Utils.random.nextInt(3)-1);
					x = lastShot.getCol()+(Utils.random.nextInt(3)-1);
				}else {
					y = Utils.random.nextInt(this.rows);
					x = Utils.random.nextInt(this.cols);
				}
				if(y>0 && y<this.rows && x>0 && x<this.cols) {
					if(this.traces[y][x]!=T_WALL) {
						break;
					}
				}
				
				ctp++;
			}while(ctp<100);
			return new Coordinate(y,x);
		}
	}
	
	/**
     * Méthode de mise à jour appelée lorsqu'un événement de cellule se produit.
     *
     * @param ce L'événement de cellule.
     */
	@Override
	public void update(ICellEvent ce) {
		if(this.lastShot==null) {
			this.lastShot=ce.getCoord();
		}else {
			if(this.traces[lastShot.getRow()][lastShot.getCol()]<ce.getTurn()) {
				this.lastShot=ce.getCoord();
			}
		}
		if(ce.getState()==CellInfo.WALL) {
			this.setTrace(ce.getCoord(), this.T_WALL);
		}else {
			this.setTrace(ce.getCoord(), ce.getTurn());
		}
	}
	
	/**
	 * Modifie le tableau de traces à la coordonnée c pour ajouter la nouvelle trace. 
	 * 
	 * @param c 	une coordonnée du labyrinthe.
	 * @param trace la trace que le chasseur laisse (correspondant au numero du tour actuel)
	 */
	public void setTrace(ICoordinate c, int trace) {
		this.traces[c.getRow()][c.getCol()]=trace;
	}
}