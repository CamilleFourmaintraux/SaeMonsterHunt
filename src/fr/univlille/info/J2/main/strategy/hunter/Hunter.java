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
	* Strategy du chasseur.
	**/
	private IHunterStrategy strategy;
	
	private GameplayHunterData data;
	



	/**
     * Constructeur de la classe Hunter, crée un Chasseur.
     *
     * @param height     	La hauteur du labyrinthe.
     * @param width      	La largeur du labyrinthe.
     * @param coord_hunter 	Les coordonnées du chasseur.
     * @param IA   			Le niveau de l'IA du chasseur.
     * @param bonusRange	La portée bonus de la vision du chasseur.
	 */
	public Hunter(int height, int width, ICoordinate coord_hunter, GameplayHunterData data) {
		this.coord=coord_hunter;
		this.data=data;
		this.strategy=this.chooseHunterStrategy(data.getIA());
		this.initTraces(height,width);
		this.strategy.initialize(height, width);
	}
	
	/**
	 *  Méthode permettant de choisir une stratégie de chasseur en fonction du niveau d'IA spécifié.
	 *
	 * @param IA_hunter 	Le niveau d'IA du chasseur. Les valeurs possibles sont "IA-Easy", "IA-Moderate", "IA-Hardcore".
	 * @return Une instance de l'interface IHunterStrategy correspondant à la stratégie choisie.
	 *         Si le niveau d'IA n'est pas reconnu, retourne une instance de NoIAHunter.
	 */
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
	
	/**
     * Initialise le tableau des traces laissées par le chasseur.
     *
     * @param nbrRows Le nombre de lignes du labyrinthe.
     * @param nbrCols Le nombre de colonnes du labyrinthe.
	 */
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

	/**
	 * Obtient le tableau des traces laissées par le chasseur dans le labyrinthe.
	 *
	 * @return Le tableau des traces laissées par le chasseur.
	 */
	public int[][] getTraces() {
		return traces;
	}

	/**
	 * Définit la stratégie du chasseur.
	 *
	 * @param strategy La nouvelle stratégie du chasseur.
	 */
	public void setStrategy(IHunterStrategy strategy) {
		this.strategy = strategy;
	}

/**
 * Met à jour les traces du chasseur en fonction de l'événement de cellule spécifié.
 *
 * @param ce L'événement de cellule à prendre en compte.
 */
	public void actualizeTraces(ICellEvent ce) {
		if(ce.getState().equals(CellInfo.WALL)) {
			this.setTrace(ce.getCoord(), -1);
		}else {
			this.setTrace(ce.getCoord(), ce.getTurn());
		}
	}

	/**
	 * Met à jour la stratégie du chasseur en fonction de l'événement de cellule spécifié.
	 *
	 * @param ce L'événement de cellule à prendre en compte.
	 */
	public void update(ICellEvent ce) {
		this.strategy.update(ce);
	}
	
	/**
	 * Effectue une action de jeu en utilisant la stratégie actuelle du chasseur.
	 *
	 * @return Les coordonnées de l'action effectuée par le chasseur.
	 */
	public ICoordinate play() {
		return this.strategy.play();
	}

	public int getBonusRange() {
		return this.data.getBonusRange();
	}

	public String getIA() {
		return this.data.getIA();
	}
	
	
}
