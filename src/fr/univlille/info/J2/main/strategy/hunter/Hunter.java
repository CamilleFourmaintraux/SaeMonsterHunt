/**
 * Le package main.strategy.hunter contient les classes relatives à la gestion
 * du chasseur dans le jeu. Il propose des stratégies pour le comportement du chasseur.
 */
package fr.univlille.info.J2.main.strategy.hunter;

import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
/**
 * La classe Hunter représente un chasseur dans le jeu. Elle implémente l'interface IHunterStrategy
 * pour définir différentes stratégies pour le chasseur.
 *
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class Hunter {
	
	/**
     * Tableau pour stocker les traces laissées par le chasseur dans le labyrinthe.
	 */
	private int[][] traces;
	
	/**
	 * Les coordonnées du dernier tir du chasseur.
	 */
	private ICoordinate coord;
	/**
	 * La portée bonus pour la vision du hunter à chachun de ses tirs (sachant que seul le tir précis qui touche le monstre déclenche la fin de jeu)
	 */
	private int bonusRange;
	/**
	* String de la Strategy du chasseur
	**/
	String IA;
	/**
	* Strategy du chasseur.
	**/
	private IHunterStrategy strategy;
	



	/**
     * Constructeur de la classe Hunter, crée un Chasseur.
     *
     * @param height     	La hauteur du labyrinthe.
     * @param width      	La largeur du labyrinthe.
     * @param coord_hunter 	Les coordonnées du chasseur.
     * @param IA_level   	Le niveau de l'IA du chasseur.
	 */
	public Hunter(int height, int width, ICoordinate coord_hunter, int bonusRange, String IA) {
		this.coord=coord_hunter;
		this.bonusRange=bonusRange;
		this.IA=IA;
		this.strategy=this.chooseHunterStrategy(IA);
		this.initTraces(height,width);
		this.strategy.initialize(height, width);
	}
	
	public IHunterStrategy chooseHunterStrategy(String IA_hunter) {
		if(IA_hunter.equals("IA-Easy")){
			return new IAeasyHunter();
		}else if(IA_hunter.equals("IA-Moderate")) {
			return new IAmoderateHunter();
		}else if(IA_hunter.equals("IA-Hardcore")) {
			return new IAhardcoreHunter();
		}
		return new NoIAHunter();
		}

	/**
     * Obtient le numéro de ligne actuelle du chasseur.
     *
     * @return Le numéro de ligne actuelle du chasseur.
	 */
	public int getRow() {
		return this.coord.getRow();
	}

	/**
     * Obtient le numéro de colonne actuelle du chasseur.
     *
     * @return Le numéro de colonne actuelle du chasseur.
	 */
	public int getCol() {
		return this.coord.getCol();
	}

	/**
     * Obtient les coordonnées actuelles du chasseur.
     *
     * @return Les coordonnées actuelles du chasseur.
	 */
	public ICoordinate getCoord() {
		return this.coord;
	}

	/**
     * Définit les coordonnées actuelles du chasseur.
     *
     * @param c Les nouvelles coordonnées du chasseur.
	 */
	public void setCoord(ICoordinate c) {
		this.coord=c;
	}
	
	public void initTraces(int nbrRows, int nbrCols) {
		this.traces=new int[nbrRows][nbrCols];
		for(int h=0; h<this.traces.length;h++) {
			for(int l=0; l<this.traces[h].length;l++) {
				traces[h][l]=-2;// -2 -> Inexploré, -1 -> Mur, 0 -> pas de trace >0 -> trace (tour)
			}
		}
	}
	
	/**
     * Définit la trace laissée par le chasseur à des coordonnées spécifiques.
     *
     * @param c     Les coordonnées où la trace doit être définie.
     * @param trace La valeur de la trace.
	 */
	public void setTrace(ICoordinate c, int trace) {
		this.traces[c.getRow()][c.getCol()]=trace;
	}
	
	/**
     * Obtient la valeur de la trace laissée par le chasseur à des coordonnées spécifiques.
     *
     * @param c Les coordonnées de la trace à obtenir.
     * @return La valeur de la trace.
	 */
	public int getTrace(ICoordinate c) {
		return this.traces[c.getRow()][c.getCol()];
	}

	public int[][] getTraces() {
		return traces;
	}

	public int getBonusRange() {
		return bonusRange;
	}


	public String getIA() {
		return this.IA;
	}


	public void setStrategy(IHunterStrategy strategy) {
		this.strategy = strategy;
	}
	public void actualizeTraces(ICellEvent ce) {
		if(ce.getState().equals(CellInfo.WALL)) {
			this.setTrace(ce.getCoord(), -1);
		}else {
			this.setTrace(ce.getCoord(), ce.getTurn());
		}
	}
	
	public void update(ICellEvent ce) {
		this.strategy.update(ce);
	}
	
	public ICoordinate play() {
		return this.strategy.play();
	}
	
	
}
